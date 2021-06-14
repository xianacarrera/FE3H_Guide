package com.example.fe3hguide.characters.profile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.model.Character;
import com.example.fe3hguide.model.Skill;
import com.example.fe3hguide.model.Stat;

import java.util.HashMap;


public class GeneralFragment extends Fragment {

    private final String characterName;
    private Character character;
    private final SQLiteDatabase db;

    private ImageView portrait;

    private TextView titleName;
    private TextView pronouns;
    private TextView faction;
    private TextView birthday, fodlanBirthday;
    private TextView crest;
    private TextView recruitment;
    private HashMap<String, TextView> baseStats;     // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<String, TextView> growthRates;   // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<String, TextView> growthRatesClass; //HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<String, TextView> growthRatesTotal; //HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<String, TextView> skills;   // Sword, Lance, Axe, Bow, Brawling, Reason,
                                                // Faith, Authority, Heavy_Armor, Riding, Flying

    public GeneralFragment(String characterName, SQLiteDatabase db){
        if (characterName.equals("BylethM") || characterName.equals("BylethF")){
            this.characterName = "Byleth";
        } else {
            this.characterName = characterName;
        }

        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout = (ScrollView)
                inflater.inflate(R.layout.fragment_general, container, false);

        searchCharacterInfo();
        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    private void searchCharacterInfo(){
        Cursor cursor = db.rawQuery("SELECT * " +
                        "FROM Characters WHERE name like ?",
                new String[] {characterName + "%"});

        if (cursor.moveToFirst()){
            character = new Character.Builder(cursor.getInt(0))
                    .withName(characterName)
                    .withPortrait(cursor.getInt(2))
                    .withPronouns(cursor.getString(3))
                    .withFaction(cursor.getString(4))
                    .withAge(cursor.getInt(5))
                    .withBirthday(cursor.getString(6))
                    .withFodlanBirthday(cursor.getString(7))
                    .withCrest(cursor.getString(8))
                    .withBaseStats(cursor.getString(9), cursor.getString(10),
                            cursor.getString(11), cursor.getString(12),
                            cursor.getString(13), cursor.getString(14),
                            cursor.getString(15), cursor.getString(16),
                            cursor.getString(17))
                    .withGrowthRates(cursor.getString(18), cursor.getString(19),
                            cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23),
                            cursor.getString(24), cursor.getString(25),
                            cursor.getString(26))
                    .withSkills(cursor.getString(27), cursor.getString(28),
                            cursor.getString(29), cursor.getString(30),
                            cursor.getString(31), cursor.getString(32),
                            cursor.getString(33), cursor.getString(34),
                            cursor.getString(35), cursor.getString(36),
                            cursor.getString(37))
                    .build();
        }
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

        // Base stats
        baseStats = new HashMap<>();
        baseStats.put("HP", (TextView) layout.findViewById(R.id.bs_hp));
        baseStats.put("Str", (TextView) layout.findViewById(R.id.bs_str));
        baseStats.put("Mag", (TextView) layout.findViewById(R.id.bs_mag));
        baseStats.put("Dex", (TextView) layout.findViewById(R.id.bs_dex));
        baseStats.put("Spd", (TextView) layout.findViewById(R.id.bs_spd));
        baseStats.put("Lck", (TextView) layout.findViewById(R.id.bs_lck));
        baseStats.put("Def", (TextView) layout.findViewById(R.id.bs_def));
        baseStats.put("Res", (TextView) layout.findViewById(R.id.bs_res));
        baseStats.put("Cha", (TextView) layout.findViewById(R.id.bs_cha));

        // Growth rates
        growthRates = new HashMap<>();
        growthRates.put("HP", (TextView) layout.findViewById(R.id.gr_hp));
        growthRates.put("Str", (TextView) layout.findViewById(R.id.gr_str));
        growthRates.put("Mag", (TextView) layout.findViewById(R.id.gr_mag));
        growthRates.put("Dex", (TextView) layout.findViewById(R.id.gr_dex));
        growthRates.put("Spd", (TextView) layout.findViewById(R.id.gr_spd));
        growthRates.put("Lck", (TextView) layout.findViewById(R.id.gr_lck));
        growthRates.put("Def", (TextView) layout.findViewById(R.id.gr_def));
        growthRates.put("Res", (TextView) layout.findViewById(R.id.gr_res));
        growthRates.put("Cha", (TextView) layout.findViewById(R.id.gr_cha));

        // Growth rates from the class
        growthRatesClass = new HashMap<>();
        growthRatesClass.put("HP", (TextView) layout.findViewById(R.id.gr_hp_class));
        growthRatesClass.put("Str", (TextView) layout.findViewById(R.id.gr_str_class));
        growthRatesClass.put("Mag", (TextView) layout.findViewById(R.id.gr_mag_class));
        growthRatesClass.put("Dex", (TextView) layout.findViewById(R.id.gr_dex_class));
        growthRatesClass.put("Spd", (TextView) layout.findViewById(R.id.gr_spd_class));
        growthRatesClass.put("Lck", (TextView) layout.findViewById(R.id.gr_lck_class));
        growthRatesClass.put("Def", (TextView) layout.findViewById(R.id.gr_def_class));
        growthRatesClass.put("Res", (TextView) layout.findViewById(R.id.gr_res_class));
        growthRatesClass.put("Cha", (TextView) layout.findViewById(R.id.gr_cha_class));

        // Total growth rates (normal growth rates + class growth rates)
        growthRatesTotal = new HashMap<>();
        growthRatesTotal.put("HP", (TextView) layout.findViewById(R.id.gr_hp_tot));
        growthRatesTotal.put("Str", (TextView) layout.findViewById(R.id.gr_str_tot));
        growthRatesTotal.put("Mag", (TextView) layout.findViewById(R.id.gr_mag_tot));
        growthRatesTotal.put("Dex", (TextView) layout.findViewById(R.id.gr_dex_tot));
        growthRatesTotal.put("Spd", (TextView) layout.findViewById(R.id.gr_spd_tot));
        growthRatesTotal.put("Lck", (TextView) layout.findViewById(R.id.gr_lck_tot));
        growthRatesTotal.put("Def", (TextView) layout.findViewById(R.id.gr_def_tot));
        growthRatesTotal.put("Res", (TextView) layout.findViewById(R.id.gr_res_tot));
        growthRatesTotal.put("Cha", (TextView) layout.findViewById(R.id.gr_cha_tot));

        // Skills
        skills = new HashMap<>();
        skills.put("Sword", (TextView) layout.findViewById(R.id.sk_sword_rank));
        skills.put("Lance", (TextView) layout.findViewById(R.id.sk_lance_rank));
        skills.put("Axe", (TextView) layout.findViewById(R.id.sk_axe_rank));
        skills.put("Bow", (TextView) layout.findViewById(R.id.sk_bow_rank));
        skills.put("Brawling", (TextView) layout.findViewById(R.id.sk_brawling_rank));
        skills.put("Reason", (TextView) layout.findViewById(R.id.sk_reason_rank));
        skills.put("Faith", (TextView) layout.findViewById(R.id.sk_faith_rank));
        skills.put("Authority", (TextView) layout.findViewById(R.id.sk_authority_rank));
        skills.put("Heavy_Armor", (TextView) layout.findViewById(R.id.sk_heavy_armor_rank));
        skills.put("Riding", (TextView) layout.findViewById(R.id.sk_riding_rank));
        skills.put("Flying", (TextView) layout.findViewById(R.id.sk_flying_rank));
    }

    private void setupComponents(){
        titleName.setText(characterName);
        portrait.setImageResource(character.getPortrait());
        pronouns.setText(character.getPronouns());
        faction.setText(character.getFaction());
        birthday.setText(character.getBirthday());
        fodlanBirthday.setText(character.getFodlanBirthday());

        for (Stat stat : Stat.values()){
            baseStats.get(stat.name()).setText(character.getBaseStats().get(stat.name()));
            growthRates.get(stat.name()).setText(character.getGrowthRates().get(stat.name()));
        }

        for (Skill skill : Skill.values()){
            String rawSkill = character.getSkills().get(skill.name());
            String[] dividedSkills = rawSkill.split("_");
            String[] finalSkills = dividedSkills[0].split("\\$");
            skills.get(skill.name()).setText(finalSkills[0]);
        }
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
                //TODO
            }
        });
    }
}