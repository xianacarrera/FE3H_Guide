package com.xiana.fe3hguide.characters.profile;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.AbilitiesAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Ability;

import java.util.List;

public class AbilitiesFragment extends Fragment {

    private final String character;
    private final SQLiteDatabase db;
    private final AbilitiesFragment fragment;
    private final Facade fc;
    private View popUpLayout;

    private Spinner spinner;
    private RecyclerView allAbilitiesRecycler;
    private RecyclerView uniqueRecycler;

    // PopUp components;
    private Dialog myDialog;
    private TextView titleAbilityName;
    private ImageView iconAbility;
    private TextView abilityEffect;
    private TextView abilityOrigin;


    public AbilitiesFragment(String character, SQLiteDatabase db) {
        if (character.equals("BylethM") || character.equals("BylethF")) {
            this.character = "Byleth";
        } else {
            this.character = character;
        }

        this.db = db;
        this.fragment = this;
        this.fc = Facade.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)
                inflater.inflate(R.layout.fragment_abilities, container, false);

        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.popup_ability);

        initComponents(layout);
        setUpComponents();

        // Search and display abilities unique to the character
        prepareUniqueAbilities();
        // Search and display abilities common to all characters
        prepareNotUniqueAbilities();

        addListeners();

        return layout;
    }

    private void initComponents(RelativeLayout layout) {
        uniqueRecycler = (RecyclerView) layout.findViewById(R.id.recycler_abilities_1);
        allAbilitiesRecycler = (RecyclerView) layout.findViewById(R.id.recycler_abilities_2);
        spinner = (Spinner) layout.findViewById(R.id.spinner_abilities);

        // PopUp components
        titleAbilityName = (TextView) myDialog.findViewById(R.id.textView_title_ability_name);
        iconAbility = (ImageView) myDialog.findViewById(R.id.ability_icon);
        abilityEffect = (TextView) myDialog.findViewById(R.id.textView_ability_effect);
        abilityOrigin = (TextView) myDialog.findViewById(R.id.textview_ability_origin);
    }

    private void setUpComponents(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.abilities_types, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void prepareUniqueAbilities() {
        /*
         * Add all unique abilities for the character, which are considered to be the personal
         * ability, the learned abilities not common to all the characters and those ones
         * coming from budding talents.
         */
        List<Ability> uniqueAbilities = fc.getUniqueAbilities(character);

        // Create adapter for the unique abilities recycler view and link them
        AbilitiesAdapter uniqueAdapter = new AbilitiesAdapter(uniqueAbilities, this);
        uniqueRecycler.setAdapter(uniqueAdapter);

        // Display the abilities stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        uniqueRecycler.setLayoutManager(layoutManager);
    }

    private void prepareNotUniqueAbilities() {
        /*
         * The second recycler view is the same for every character.
         * There is a spinner with several categories (learned, master...) so that the abilities
         * can be organized and more easily searched.
         */

        // By default, prepare the second recycler view to show all the abilities (not unique)
        List<Ability> defaultAbilities = fc.getNotUniqueAbilities();

        // Prepare the adapter
        AbilitiesAdapter defaultAdapter = new AbilitiesAdapter(defaultAbilities, this);
        allAbilitiesRecycler.setAdapter(defaultAdapter);

        // Display the abilities stacked vertically
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        allAbilitiesRecycler.setLayoutManager(layoutManager2);
    }

    private void addListeners() {
        // Attach a listener to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = spinner.getSelectedItem().toString();

                // The recycler view gets populated with the abilities of the selected type
                Cursor cursor = null;
                List<Ability> abilities = null;
                switch (selection) {
                    case "All abilities":
                        abilities = fc.getAllAbilities();
                        break;
                    case "Skill level abilities":
                        abilities = fc.getSkillLevelAbilities();
                        break;
                    case "Class abilities":
                        abilities = fc.getClassAbilities();
                        break;
                    case "Class mastery abilities":
                        abilities = fc.getClassMasteryAbilities();
                        break;
                    case "Other abilities":
                        abilities = fc.getOtherAbilities();
                }

                // Create a new adapter and link it to the recycler view
                AbilitiesAdapter abilitiesAdapter = new AbilitiesAdapter(abilities, fragment);
                allAbilitiesRecycler.setAdapter(abilitiesAdapter);

                // TODO: is it necessary to add another LayoutManager to the recycler view?
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void shopPopup(Ability ability) {
        // Set the name of the ability as the title for the popup
        titleAbilityName.setText(ability.getName());

        // Show the icon of the ability
        iconAbility.setImageResource(ability.getIcon());

        // Show the effect of the ability
        abilityEffect.setText(ability.getEffect());

        // Show the origin of the ability
        abilityOrigin.setText(ability.getOrigin());

        myDialog.show();
    }
}