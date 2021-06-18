package com.example.fe3hguide.characters.profile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.Character;
import com.example.fe3hguide.model.InGameClass;
import com.example.fe3hguide.model.Skill;
import com.example.fe3hguide.model.Stat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GeneralFragment extends Fragment {

    private final String characterName;
    private Character character;
    private final SQLiteDatabase db;
    private final Facade fc;

    private ImageView portrait;

    private TextView titleName;
    private TextView pronouns;
    private TextView faction;
    private TextView birthday, fodlanBirthday;
    private TextView crest;
    private TextView recruitment;
    private Spinner spinner;
    private HashMap<Stat, TextView> baseStats;     // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<Stat, TextView> growthRates;   // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<Stat, TextView> growthRatesClass; //HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<Stat, TextView> growthRatesTotal; //HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<Skill, TextView> skills;   // Sword, Lance, Axe, Bow, Brawling, Reason,
                                                // Faith, Authority, Heavy_Armor, Riding, Flying
    private HashMap<Skill, ImageView> arrows;
    private HashMap<Skill, ImageView> buddingTalents;

    public GeneralFragment(String characterName, SQLiteDatabase db){
        if (characterName.equals("BylethM") || characterName.equals("BylethF")){
            this.characterName = "Byleth";
        } else {
            this.characterName = characterName;
        }

        this.db = db;
        this.fc = Facade.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView)
                inflater.inflate(R.layout.fragment_general, container, false);

        // Complete all the character attributes
        character = fc.getCharacter(characterName);

        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ScrollView layout){
        titleName = (TextView) layout.findViewById(R.id.full_character_name);
        portrait = (ImageView) layout.findViewById(R.id.imageView_portrait);
        pronouns = (TextView) layout.findViewById(R.id.textView_pronouns);
        faction = (TextView) layout.findViewById(R.id.textView_faction);
        birthday = (TextView) layout.findViewById(R.id.textView_birthday);
        fodlanBirthday = (TextView) layout.findViewById(R.id.textView_fodlan_birthday);
        crest = (TextView) layout.findViewById(R.id.textView_crest);
        recruitment = (TextView) layout.findViewById(R.id.textView_recruitment);

        spinner = (Spinner) layout.findViewById(R.id.char_general_classes_spinner);

        // Base stats
        baseStats = new HashMap<>();
        baseStats.put(Stat.HP, (TextView) layout.findViewById(R.id.bs_hp));
        baseStats.put(Stat.Str, (TextView) layout.findViewById(R.id.bs_str));
        baseStats.put(Stat.Mag, (TextView) layout.findViewById(R.id.bs_mag));
        baseStats.put(Stat.Dex, (TextView) layout.findViewById(R.id.bs_dex));
        baseStats.put(Stat.Spd, (TextView) layout.findViewById(R.id.bs_spd));
        baseStats.put(Stat.Lck, (TextView) layout.findViewById(R.id.bs_lck));
        baseStats.put(Stat.Def, (TextView) layout.findViewById(R.id.bs_def));
        baseStats.put(Stat.Res, (TextView) layout.findViewById(R.id.bs_res));
        baseStats.put(Stat.Cha, (TextView) layout.findViewById(R.id.bs_cha));

        // Growth rates
        growthRates = new HashMap<>();
        growthRates.put(Stat.HP, (TextView) layout.findViewById(R.id.gr_hp));
        growthRates.put(Stat.Str, (TextView) layout.findViewById(R.id.gr_str));
        growthRates.put(Stat.Mag, (TextView) layout.findViewById(R.id.gr_mag));
        growthRates.put(Stat.Dex, (TextView) layout.findViewById(R.id.gr_dex));
        growthRates.put(Stat.Spd, (TextView) layout.findViewById(R.id.gr_spd));
        growthRates.put(Stat.Lck, (TextView) layout.findViewById(R.id.gr_lck));
        growthRates.put(Stat.Def, (TextView) layout.findViewById(R.id.gr_def));
        growthRates.put(Stat.Res, (TextView) layout.findViewById(R.id.gr_res));
        growthRates.put(Stat.Cha, (TextView) layout.findViewById(R.id.gr_cha));

        // Growth rates from the class
        growthRatesClass = new HashMap<>();
        growthRatesClass.put(Stat.HP, (TextView) layout.findViewById(R.id.gr_hp_class));
        growthRatesClass.put(Stat.Str, (TextView) layout.findViewById(R.id.gr_str_class));
        growthRatesClass.put(Stat.Mag, (TextView) layout.findViewById(R.id.gr_mag_class));
        growthRatesClass.put(Stat.Dex, (TextView) layout.findViewById(R.id.gr_dex_class));
        growthRatesClass.put(Stat.Spd, (TextView) layout.findViewById(R.id.gr_spd_class));
        growthRatesClass.put(Stat.Lck, (TextView) layout.findViewById(R.id.gr_lck_class));
        growthRatesClass.put(Stat.Def, (TextView) layout.findViewById(R.id.gr_def_class));
        growthRatesClass.put(Stat.Res, (TextView) layout.findViewById(R.id.gr_res_class));
        growthRatesClass.put(Stat.Cha, (TextView) layout.findViewById(R.id.gr_cha_class));

        // Total growth rates (normal growth rates + class growth rates)
        growthRatesTotal = new HashMap<>();
        growthRatesTotal.put(Stat.HP, (TextView) layout.findViewById(R.id.gr_hp_tot));
        growthRatesTotal.put(Stat.Str, (TextView) layout.findViewById(R.id.gr_str_tot));
        growthRatesTotal.put(Stat.Mag, (TextView) layout.findViewById(R.id.gr_mag_tot));
        growthRatesTotal.put(Stat.Dex, (TextView) layout.findViewById(R.id.gr_dex_tot));
        growthRatesTotal.put(Stat.Spd, (TextView) layout.findViewById(R.id.gr_spd_tot));
        growthRatesTotal.put(Stat.Lck, (TextView) layout.findViewById(R.id.gr_lck_tot));
        growthRatesTotal.put(Stat.Def, (TextView) layout.findViewById(R.id.gr_def_tot));
        growthRatesTotal.put(Stat.Res, (TextView) layout.findViewById(R.id.gr_res_tot));
        growthRatesTotal.put(Stat.Cha, (TextView) layout.findViewById(R.id.gr_cha_tot));

        // Skills
        skills = new HashMap<>();
        skills.put(Skill.Sword, (TextView) layout.findViewById(R.id.sk_sword_rank));
        skills.put(Skill.Lance, (TextView) layout.findViewById(R.id.sk_lance_rank));
        skills.put(Skill.Axe, (TextView) layout.findViewById(R.id.sk_axe_rank));
        skills.put(Skill.Bow, (TextView) layout.findViewById(R.id.sk_bow_rank));
        skills.put(Skill.Brawling, (TextView) layout.findViewById(R.id.sk_brawling_rank));
        skills.put(Skill.Reason, (TextView) layout.findViewById(R.id.sk_reason_rank));
        skills.put(Skill.Faith, (TextView) layout.findViewById(R.id.sk_faith_rank));
        skills.put(Skill.Authority, (TextView) layout.findViewById(R.id.sk_authority_rank));
        skills.put(Skill.Heavy_Armor, (TextView) layout.findViewById(R.id.sk_heavy_armor_rank));
        skills.put(Skill.Riding, (TextView) layout.findViewById(R.id.sk_riding_rank));
        skills.put(Skill.Flying, (TextView) layout.findViewById(R.id.sk_flying_rank));

        // Down and up arrows
        arrows = new HashMap<>();
        arrows.put(Skill.Sword, (ImageView) layout.findViewById(R.id.sk_sword_ud));
        arrows.put(Skill.Lance, (ImageView) layout.findViewById(R.id.sk_lance_ud));
        arrows.put(Skill.Axe, (ImageView) layout.findViewById(R.id.sk_axe_ud));
        arrows.put(Skill.Bow, (ImageView) layout.findViewById(R.id.sk_bow_ud));
        arrows.put(Skill.Brawling, (ImageView) layout.findViewById(R.id.sk_brawling_ud));
        arrows.put(Skill.Reason, (ImageView) layout.findViewById(R.id.sk_reason_ud));
        arrows.put(Skill.Faith, (ImageView) layout.findViewById(R.id.sk_faith_ud));
        arrows.put(Skill.Authority, (ImageView) layout.findViewById(R.id.sk_authority_ud));
        arrows.put(Skill.Heavy_Armor, (ImageView) layout.findViewById(R.id.sk_heavy_armor_ud));
        arrows.put(Skill.Riding, (ImageView) layout.findViewById(R.id.sk_riding_ud));
        arrows.put(Skill.Flying, (ImageView) layout.findViewById(R.id.sk_flying_ud));

        // Budding talents
        buddingTalents = new HashMap<>();
        buddingTalents.put(Skill.Sword, (ImageView) layout.findViewById(R.id.sk_sword_bt));
        buddingTalents.put(Skill.Lance, (ImageView) layout.findViewById(R.id.sk_lance_bt));
        buddingTalents.put(Skill.Axe, (ImageView) layout.findViewById(R.id.sk_axe_bt));
        buddingTalents.put(Skill.Bow, (ImageView) layout.findViewById(R.id.sk_bow_bt));
        buddingTalents.put(Skill.Brawling, (ImageView) layout.findViewById(R.id.sk_brawling_bt));
        buddingTalents.put(Skill.Reason, (ImageView) layout.findViewById(R.id.sk_reason_bt));
        buddingTalents.put(Skill.Faith, (ImageView) layout.findViewById(R.id.sk_faith_bt));
        buddingTalents.put(Skill.Authority, (ImageView) layout.findViewById(R.id.sk_authority_bt));
        buddingTalents.put(Skill.Heavy_Armor, (ImageView) layout.findViewById(R.id.sk_heavy_armor_bt));
        buddingTalents.put(Skill.Riding, (ImageView) layout.findViewById(R.id.sk_riding_bt));
        buddingTalents.put(Skill.Flying, (ImageView) layout.findViewById(R.id.sk_flying_bt));
    }

    private void setupComponents(){
        titleName.setText(characterName);
        portrait.setImageResource(character.getPortrait());
        pronouns.setText(character.getPronouns());
        faction.setText(character.getFaction());
        birthday.setText(character.getBirthday());
        fodlanBirthday.setText(character.getFodlanBirthday());

        prepareSpinner();

        for (Stat stat : Stat.values()){
            baseStats.get(stat).setText(character.getBaseStats().get(stat));
            growthRates.get(stat).setText(character.getGrowthRates().get(stat));
        }

        for (Skill skill : Skill.values()){
            String rawSkill = character.getSkills().get(skill);

            String[] dividedSkills = rawSkill.split("\\$");
            // \\$ sets budding talents apart
            if (dividedSkills.length == 2){
                buddingTalents.get(skill).setImageResource(R.drawable.budding_talent);
            } else {
                buddingTalents.get(skill).setVisibility(View.GONE);
            }

            // UP and DOWN indicate positive or negative growth, respectively
            if (dividedSkills[0].contains("UP")){
                arrows.get(skill).setImageResource(R.drawable.positive_growth);
            } else if (dividedSkills[0].contains("DOWN")){
                arrows.get(skill).setImageResource(R.drawable.negative_growth);
            } else {
                arrows.get(skill).setVisibility(View.GONE);
                buddingTalents.get(skill).setY(-20);        // Alignment
            }

            String[] finalSkills = dividedSkills[0].split("_");
            skills.get(skill).setText(finalSkills[0]);
        }
    }

    private void prepareSpinner(){
        // Search for all the classes exclusive to the gender of the character
        List<InGameClass> spinnerClasses = null;
        switch (character.getPronouns()){
            case "she/her":
                spinnerClasses = fc.getFemaleClasses();
                break;
            case "he/him":
                spinnerClasses = fc.getMaleClasses();
                break;
            default:        // Chosen by the player (Byleth) -> both genders
                spinnerClasses = fc.getNonExclusiveClasses();
        }

        // Add all the classes that are exclusive to that particular character, if there are any
        spinnerClasses.addAll(fc.getCharacterOnlyClasses(characterName));

        // Get their names
        List<String> spinnerClassesNames = new ArrayList<>();
        spinnerClassesNames.add("None");          // Option to deselect
        for (InGameClass inGameClass : spinnerClasses){
            spinnerClassesNames.add(inGameClass.getName());
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getContext(), R.layout.support_simple_spinner_dropdown_item, spinnerClassesNames
        );
        spinner.setAdapter(spinnerAdapter);

        spinner.setSelection(0);        // "None" is selected by default
    }

    private void addListeners(){
        // When the user clicks on crest or recruitment, the spoiler warning is hidden and the info
        // appears

        crest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crest.setText(character.getCrest());
            }
        });

        recruitment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recruitment.setText("");
                String[] recruitmentLines = character.getRecruitment().split("_");
                for (int i = 0; i < recruitmentLines.length; i++){
                    recruitment.append(recruitmentLines[i] + "\n");
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = spinner.getSelectedItem().toString();
                if (selection.equals("None")){
                    restoreGrowthRatesClass();
                } else {
                    InGameClass selectedClass = fc.getInGameClass(selection);
                    changeGrowthRatesClass(selectedClass);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void restoreGrowthRatesClass(){
        for (Stat stat : Stat.values()){
            growthRatesClass.get(stat).setText("");
            // Total growth rates are equal to the character's growth rates
            growthRatesTotal.get(stat).setText(growthRates.get(stat).getText().toString());
        }
    }

    /**
     * Changes the class growth rates to those of the class passed as a parameter.
     * Calculates total growth rates as the sum of the character's growth rates and the class
     * growth rates of that class.
     *
     * @param inGameClass
     */
    public void changeGrowthRatesClass(InGameClass inGameClass){
        for (Stat stat : Stat.values()){
            growthRatesClass.get(stat).setText(inGameClass.getGrowthRates().get(stat));
            int charGrowthRate = Integer.parseInt(character.getGrowthRates().get(stat)
                    .replace("%", ""));
            int classGrowthRate = Integer.parseInt(inGameClass.getGrowthRates().get(stat)
                    .replace("%", ""));
            growthRatesTotal.get(stat).setText(
                    (charGrowthRate + classGrowthRate) + "%"
        );
        }
    }
}