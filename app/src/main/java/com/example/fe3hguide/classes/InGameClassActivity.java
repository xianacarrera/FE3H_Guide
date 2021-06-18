package com.example.fe3hguide.classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.Ability;
import com.example.fe3hguide.model.CombatArtClassMastery;
import com.example.fe3hguide.model.InGameClass;
import com.example.fe3hguide.model.Skill;
import com.example.fe3hguide.model.Stat;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;
import java.util.HashMap;

public class InGameClassActivity extends AppCompatActivity {

    private InGameClass inGameClass;
    private Facade fc;

    private TextView title;
    private ImageView icon;
    private TextView classLevel;
    private ArrayList<TextView> proficiencies;
    private ArrayList<ImageView> imageViewsProficiencies;
    private ArrayList<TextView> abilities;
    private ArrayList<ImageView> imageViewsAbilities;
    private TextView masteryAbility;
    private ImageView imageViewMasteryAbility;
    private TextView masteryCombatArt;
    private ImageView imageViewMasteryCombatArt;
    private HashMap<Stat, TextView> growthRates;
    private TextView certificationRequirements;
    private TextView seal;
    private TextView restrictions;
    private TextView canUse;
    private TextView experience;

    private RelativeLayout relativeLayout;
    private SweetSheet sweetSheetAbility, sweetSheetCombatArt;
    private View popUpLayoutAbility, popUpLayoutCombatArt;


    // Ability popUp components
    private TextView aPUTitle;
    private ImageView aPUIcon;
    private TextView aPUEffect;
    private TextView aPUOrigin;

    // Combat art popUp components
    private TextView cPUTitle;
    private ImageView cPUIcon;
    private TextView cPUEffect;
    private TextView cPUWeapon;
    private TextView cPUText2;
    private TextView cPUText2Answer;
    private ConstraintLayout cPUTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout_classes);

        fc = Facade.getInstance(this);
        inGameClass = getSelectedClass();

        prepareAbilitiesPopUp();
        prepareCombatArtsPopUp();

        initComponents();
        setData();
    }

    private void prepareAbilitiesPopUp(){
        sweetSheetAbility = new SweetSheet(relativeLayout);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation, 1700);
        popUpLayoutAbility = LayoutInflater.from(this).inflate(R.layout.popup_ability,
                null, false);
        customDelegate.setCustomView(popUpLayoutAbility);
        sweetSheetAbility.setDelegate(customDelegate);
        sweetSheetAbility.setBackgroundEffect(new DimEffect(0.5f));
    }

    private void prepareCombatArtsPopUp(){
        sweetSheetCombatArt = new SweetSheet(relativeLayout);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation, 1700);
        popUpLayoutCombatArt = LayoutInflater.from(this).inflate(R.layout.popup_combat_art,
                null, false);
        customDelegate.setCustomView(popUpLayoutCombatArt);
        sweetSheetCombatArt.setDelegate(customDelegate);
        sweetSheetCombatArt.setBackgroundEffect(new DimEffect(0.5f));
    }

    // Returns the InGameClass whose cell was clicked in the ListView of ClassesFragment
    public InGameClass getSelectedClass(){
        Intent previousIntent = getIntent();
        // The name of the InGameClass was passed as extra info with the intent
        String className = previousIntent.getStringExtra("className");

        // Retrieve all the information about the InGameClass with the previous name
        return fc.getInGameClass(className);
    }

    public void initComponents(){
        title = (TextView) findViewById(R.id.title_class_name);
        icon = (ImageView) findViewById(R.id.imageView_class_icon_detail);
        classLevel = (TextView) findViewById(R.id.textView_class_level);

        proficiencies = new ArrayList<>();
        proficiencies.add((TextView) findViewById(R.id.textView_class_proficiency_1));
        proficiencies.add((TextView) findViewById(R.id.textView_class_proficiency_2));
        proficiencies.add((TextView) findViewById(R.id.textView_class_proficiency_3));
        proficiencies.add((TextView) findViewById(R.id.textView_class_proficiency_4));


        imageViewsProficiencies = new ArrayList<>();
        imageViewsProficiencies.add((ImageView) findViewById(R.id.imageView_class_proficiency_1));
        imageViewsProficiencies.add((ImageView) findViewById(R.id.imageView_class_proficiency_2));
        imageViewsProficiencies.add((ImageView) findViewById(R.id.imageView_class_proficiency_3));
        imageViewsProficiencies.add((ImageView) findViewById(R.id.imageView_class_proficiency_4));


        abilities = new ArrayList<>();
        abilities.add((TextView) findViewById(R.id.textView_class_ability_1));
        abilities.add((TextView) findViewById(R.id.textView_class_ability_2));

        imageViewsAbilities = new ArrayList<>();
        imageViewsAbilities.add((ImageView) findViewById(R.id.imageView_class_ability_1));
        imageViewsAbilities.add((ImageView) findViewById(R.id.imageView_class_ability_2));

        masteryAbility = (TextView) findViewById(R.id.textView_class_mastery_ability);
        imageViewMasteryAbility = (ImageView) findViewById(R.id.imageView_class_mastery_ability);
        masteryCombatArt = (TextView) findViewById(R.id.textView_class_mastery_combat_art);
        imageViewMasteryCombatArt = (ImageView) findViewById(R.id.imageView_class_mastery_combat_art);


        // Growth rates
        growthRates = new HashMap<>();
        growthRates.put(Stat.HP, (TextView) findViewById(R.id.class_gr_hp));
        growthRates.put(Stat.Str, (TextView) findViewById(R.id.class_gr_str));
        growthRates.put(Stat.Mag, (TextView) findViewById(R.id.class_gr_mag));
        growthRates.put(Stat.Dex, (TextView) findViewById(R.id.class_gr_dex));
        growthRates.put(Stat.Spd, (TextView) findViewById(R.id.class_gr_spd));
        growthRates.put(Stat.Lck, (TextView) findViewById(R.id.class_gr_lck));
        growthRates.put(Stat.Def, (TextView) findViewById(R.id.class_gr_def));
        growthRates.put(Stat.Res, (TextView) findViewById(R.id.class_gr_res));
        growthRates.put(Stat.Cha, (TextView) findViewById(R.id.class_gr_cha));

        certificationRequirements = (TextView) findViewById(R.id.textView_class_certification_requirements);
        seal = (TextView) findViewById(R.id.textView_class_seal);
        restrictions = (TextView) findViewById(R.id.textView_class_restrictions);
        canUse = (TextView) findViewById(R.id.textView_class_can_use);
        experience = (TextView) findViewById(R.id.textView_class_experience);


        // Ability popUp components
        aPUTitle = (TextView) popUpLayoutAbility.findViewById(R.id.textView_title_ability_name);
        aPUIcon = (ImageView) popUpLayoutAbility.findViewById(R.id.ability_icon);
        aPUEffect = (TextView) popUpLayoutAbility.findViewById(R.id.textView_ability_effect);
        aPUOrigin = (TextView) popUpLayoutAbility.findViewById(R.id.textview_ability_origin);


        // Combat art popUp components
        cPUTitle = (TextView) popUpLayoutCombatArt.findViewById(R.id.textview_title_combat_art_name);
        cPUIcon = (ImageView) popUpLayoutCombatArt.findViewById(R.id.combat_art_icon);
        cPUEffect = (TextView) popUpLayoutCombatArt.findViewById(R.id.textview_combat_art_effect);
        cPUWeapon = (TextView) popUpLayoutCombatArt.findViewById(R.id.popup_combat_art_text_weapon);
        cPUText2 = (TextView) popUpLayoutCombatArt.findViewById(R.id.text2_combat_art_popup);
        cPUText2Answer = (TextView) popUpLayoutCombatArt.findViewById(R.id.text2_answer);
        cPUTable = (ConstraintLayout) popUpLayoutCombatArt.findViewById(R.id.constraint_layout_combat_art_table);
    }

    private void setData(){
        title.setText(inGameClass.getName());
        icon.setImageResource(inGameClass.getIcon());
        classLevel.setText(inGameClass.getClassLevel());

        String[] proficienciesDivided = inGameClass.getProficiencies().split("\\$");
        for (int i = 0; i < proficienciesDivided.length; i++){
            proficiencies.get(i).setText(proficienciesDivided[i]);
            imageViewsProficiencies.get(i).setImageResource(Skill.getIcon(proficienciesDivided[i]));
        }
        for (int j = proficienciesDivided.length; j < 3; j++){
            proficiencies.get(j).setVisibility(View.GONE);
            imageViewsProficiencies.get(j).setVisibility(View.GONE);
        }

        for (int i = 0; i < 2; i++){
            if (inGameClass.getAbilities().get(i) == null || inGameClass.getAbilities().get(i).getName().equals("-")){
                abilities.get(i).setVisibility(View.GONE);
                imageViewsAbilities.get(i).setVisibility(View.GONE);
            } else {
                abilities.get(i).setText(inGameClass.getAbilities().get(0).getName());
                imageViewsAbilities.get(i).setImageResource(inGameClass.getAbilities().get(0).getIcon());
                addListenerAbilities(i);
            }
        }

        if (inGameClass.getMasteryAbility() == null || inGameClass.getMasteryAbility().getName().equals("-")){
            masteryAbility.setText("-");
            imageViewMasteryAbility.setVisibility(View.INVISIBLE);
        } else {
            masteryAbility.setText(inGameClass.getMasteryAbility().getName());
            imageViewMasteryAbility.setImageResource(inGameClass.getMasteryAbility().getIcon());
            addListenerMasteryAbility();
        }


        if (inGameClass.getMasteryCombatArt() == null || inGameClass.getMasteryCombatArt().getName().equals("-")){
            masteryCombatArt.setText("-");
            imageViewMasteryCombatArt.setVisibility(View.INVISIBLE);
        } else {
            masteryCombatArt.setText(inGameClass.getMasteryCombatArt().getName());
            imageViewMasteryCombatArt.setImageResource(inGameClass.getMasteryCombatArt().getIcon());
            addListenerMasteryCombatArt();
        }

        for (Stat stat : Stat.values()){
            growthRates.get(stat).setText(inGameClass.getGrowthRates().get(stat));
        }

        certificationRequirements.setText(inGameClass.getCertificationRequirement());
        seal.setText(inGameClass.getSeal());
        restrictions.setText(inGameClass.getRestrictions());
        if (inGameClass.getCanUse().equals("-")){
            canUse.setVisibility(View.GONE);
        } else {
            canUse.setText(inGameClass.getCanUse());
        }
        experience.setText(String.valueOf(inGameClass.getExperience()));
    }

    private void addListenerAbilities(int i){
        abilities.get(i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                showAbilityPopup(textView.getText().toString());
            }
        });
    }

    private void addListenerMasteryAbility(){
        masteryAbility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                showAbilityPopup(textView.getText().toString());
            }
        });
    }

    private void addListenerMasteryCombatArt(){
        masteryCombatArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                showCombatArtPopup(textView.getText().toString());
            }
        });
    }

    public void showAbilityPopup(String abilityName){
        Ability ability = fc.getAbility(abilityName);

        // Set the name of the ability as the title for the popup
        aPUTitle.setText(ability.getName());

        // Show the icon of the ability
        aPUIcon.setImageResource(ability.getIcon());

        // Show the effect of the ability
        aPUEffect.setText(ability.getEffect());

        // Show the origin of the ability
        aPUOrigin.setText(ability.getOrigin());

        sweetSheetAbility.show();
    }

    public void showCombatArtPopup(String combatArtName){
        CombatArtClassMastery cArt = fc.getCombatArtClassMastery(combatArtName);

        // Set the name of the combat art as the title for the popup
        cPUTitle.setText(cArt.getName());

        // Show the icon associated with the combat art's type
        cPUIcon.setImageResource(cArt.getIcon());

        // Show the effect of the combat art
        cPUEffect.setText(cArt.getEffect());

        // Show the weapon associated with the combat art
        cPUWeapon.setText(cArt.getWeapon());

        // The second attribute depends on the type of the combat art
        cPUText2.setText(cArt.saySpecificTextMeaning());

        // Set the answer to the previous text field
        cPUText2Answer.setText(cArt.getSpecificText());

        // Set all 6 stats to the table (dur, mt, hit, avo, crit, range)
        int i = 6;
        for (String stat : cArt.getStats().values()){
            TextView textViewStat = (TextView) cPUTable.getChildAt(i);
            textViewStat.setText(stat);
            i++;
        }

        sweetSheetCombatArt.show();
    }

}