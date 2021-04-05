package com.example.fe3hguide.characters.profile;

import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.AbilitiesAdapter;

import java.util.ArrayList;

public class AbilitiesFragment extends Fragment {

    private Dialog myDialog;
    private final String character;
    private final SQLiteDatabase db;
    private final AbilitiesFragment fragment;

    public AbilitiesFragment(String character, SQLiteDatabase db){
        if (character.equals("BylethM") || character.equals("BylethF")){
            this.character = "Byleth";
        } else {
            this.character = character;
        }

        this.db = db;
        this.fragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDialog = new Dialog(getActivity());
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_abilities, container, false);

        /*
         * Add all unique abilities for the character, which are considered to be the personal
         * ability, the learned abilities not common to all the characters and those ones
         * coming from budding talents.
         */
        Cursor cursor = db.rawQuery("SELECT ability " +
                "FROM Abilities WHERE origin LIKE ?", new String[] {"%" + character + "%"});

        ArrayList<String> uniqueAbilities = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                uniqueAbilities.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // Create adapter for the unique abilities recycler view and link them
        AbilitiesAdapter uniqueAdapter = new AbilitiesAdapter(uniqueAbilities, this);
        RecyclerView uniqueRecycler = (RecyclerView) layout.getViewById(R.id.recycler_abilities_1);
        uniqueRecycler.setAdapter(uniqueAdapter);

        // Display the abilities stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        uniqueRecycler.setLayoutManager(layoutManager);

        /*
         * The second recycler view is the same for every character.
         * There is a spinner with several categories (learned, master...) so that the abilities
         * can be organized and more easily searched.
         */

        // By default, prepare the second recycler view to show all the abilities (not unique)
        cursor = db.rawQuery("SELECT ability " +
                "FROM Abilities WHERE type NOT LIKE ?", new String[] {"%Unique%"});

        ArrayList<String> defaultAbilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                defaultAbilities.add(cursor.getString(0));
            } while(cursor.moveToNext());
        }

        // Prepare the adapter
        AbilitiesAdapter defaultAdapter = new AbilitiesAdapter(defaultAbilities, this);
        final RecyclerView allAbilitiesRecycler = (RecyclerView)
                layout.findViewById(R.id.recycler_abilities_2);
        allAbilitiesRecycler.setAdapter(defaultAdapter);

        // Display the abilities stacked vertically
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        allAbilitiesRecycler.setLayoutManager(layoutManager2);

        cursor.close();

        // Attach a listener to the spinner
        final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner_abilities);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = spinner.getSelectedItem().toString();

                // The recycler view gets populated with the abilities of the selected type
                Cursor cursor = null;
                switch (selection){
                    case "All abilities":
                        cursor = db.rawQuery("SELECT ability " +
                                        "FROM Abilities WHERE type NOT LIKE ?",
                                new String[] {"%Unique%"});
                        break;
                    case "Skill level abilities":
                        cursor = db.rawQuery("SELECT ability " +
                                        "FROM Abilities WHERE type LIKE ? AND type NOT LIKE ?",
                                new String[] {"%Learned%", "%Unique%"});
                        break;
                    case "Class abilities":
                        cursor = db.rawQuery("SELECT ability " +
                                "FROM Abilities WHERE type LIKE ?", new String[] {"%Class%"});
                        break;
                    case "Class mastery abilities":
                        cursor = db.rawQuery("SELECT ability " +
                                "FROM abilities WHERE type LIKE ?", new String[] {"%Master%"});
                        break;
                    case "Other abilities":
                        cursor = db.rawQuery("SELECT ability " +
                                "FROM Abilities WHERE type LIKE ?", new String[] {"%Other%"});
                }

                ArrayList abilities = new ArrayList();
                if (cursor.moveToFirst()){
                    do {
                        abilities.add(cursor.getString(0));
                    } while (cursor.moveToNext());
                }

                cursor.close();

                // Create a new adapter and link it to the recycler view
                AbilitiesAdapter abilitiesAdapter = new AbilitiesAdapter(abilities, fragment);
                allAbilitiesRecycler.setAdapter(abilitiesAdapter);

                // TODO: is it necessary to add another LayoutManager to the recycler view?
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return layout;
    }

    public void shopPopup(String ability){
        myDialog.setContentView(R.layout.popup_ability);

        // Set listener for the button that closes the popup
        TextView textclose = (TextView) myDialog.findViewById(R.id.text_close);
        textclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        // Set the name of the ability as the title for the popup
        TextView titleAbilityName = (TextView)
                myDialog.findViewById(R.id.textview_title_combat_art_name);
        titleAbilityName.setText(ability);

        // Search in the database for the icon, effect and origin of the ability
        Cursor cursor = db.rawQuery("SELECT icon, effect, origin " +
                "FROM Abilities " +
                "WHERE ability = ?", new String[] {ability});

        if (cursor.moveToFirst()) {
            // Show the icon of the ability
            ImageView iconAbility = (ImageView) myDialog.findViewById(R.id.combat_art_icon);
            iconAbility.setImageResource(cursor.getInt(0));

            // Show the effect of the ability
            TextView abilityEffect = (TextView) myDialog.findViewById(R.id.textview_combat_art_effect);
            abilityEffect.setText(cursor.getString(1));

            // Show the origin of the ability
            TextView abilityOrigin = (TextView) myDialog.findViewById(R.id.textview_ability_origin);
            abilityOrigin.setText(cursor.getString(2));
        }

        cursor.close();
        myDialog.show();
    }
}