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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fe3hguide.R;

import java.util.ArrayList;

public class CombatArtsFragment extends Fragment {

    private Dialog myDialog;
    private final String character;
    private final SQLiteDatabase db;
    private final CombatArtsFragment fragment;

    public CombatArtsFragment(String character, SQLiteDatabase db){
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
                inflater.inflate(R.layout.fragment_combat_arts, container, false);

        /*
         * Add all unique combat arts for the character, which are considered to be the ones
         * obtained as a budding talent, and the combat arts related to weapon proficiency
         * which are exclusive to some characters.
         */

        Cursor cursor = db.rawQuery("SELECT art, effect, weapon, dur, mt, hit, avo, crit, " +
                "range  FROM CombatArtsBuddingTalents WHERE character = ?",
                new String[] {character});

        ArrayList<CombatArt> uniqueCombatArts = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                uniqueCombatArts.add(new CombatArt(cursor.getString(0),
                        CombatArtsType.buddingTalent, cursor.getString(1),
                        cursor.getString(2), null, cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8)));
            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("SELECT art, specificSkillLevel " +
                "FROM CharacterHasCombatArtWeaponProficiency " +
                "WHERE character = ?", new String[]{character});

        Cursor cursor2 = null;
        if (cursor.moveToFirst()){
            do {
                cursor2 = db.rawQuery("SELECT art, effect, weapon, skillLevel, dur, " +
                        "mt, hit, avo, crit, range FROM CombatArtsCharactersWeaponProficient " +
                        "WHERE art = ?", new String[] {cursor.getString(0)});
                if (cursor2.moveToFirst()){
                    uniqueCombatArts.add(new CombatArt(cursor.getString(0),
                            CombatArtsType.uniqueWeaponProficiency, cursor2.getString(1),
                            cursor2.getString(2), cursor2.getString(3),
                            cursor2.getString(4), cursor2.getString(5),
                            cursor2.getString(6), cursor2.getString(7),
                            cursor2.getString(8), cursor2.getString(9)));
                }
            } while (cursor.moveToNext());
        }

        if (cursor2 != null) {
            cursor2.close();
        }
        cursor.close();

        // Create adapter for the unique abilities recycler view and link them
        CombatArtsAdapter uniqueAdapter = new CombatArtsAdapter(uniqueCombatArts, this);
        RecyclerView uniqueRecycler =
                (RecyclerView) layout.getViewById(R.id.recycler_combat_arts_1);
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
        ArrayList<CombatArt> combatArts = searchNotUniqueCombatArts(db, true,
                true, true, true);

        // Prepare the adapter
        CombatArtsAdapter defaultAdapter = new CombatArtsAdapter(combatArts, this);
        final RecyclerView allCombatArtsRecycler = (RecyclerView)
                layout.findViewById(R.id.recycler_combat_arts_2);
        allCombatArtsRecycler.setAdapter(defaultAdapter);

        // Display the abilities stacked vertically
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        allCombatArtsRecycler.setLayoutManager(layoutManager2);

        // Attach a listener to the spinner
        final Spinner spinner = (Spinner) layout.findViewById(R.id.spinner_combat_arts);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = spinner.getSelectedItem().toString();

                // The recycler view gets populated with the abilities of the selected type
                Cursor cursor = null;
                ArrayList<CombatArt> combatArts = new ArrayList();
                switch (selection){
                    case "All combat arts":
                        combatArts = searchNotUniqueCombatArts(db, true,
                                true, true, true);
                        break;
                    case "Skill level combat arts":
                        combatArts = searchNotUniqueCombatArts(db, true,
                                false, false, false);
                        break;
                    case "Weapon exclusive combat arts":
                        combatArts = searchNotUniqueCombatArts(db, false,
                                true, false, false);
                        break;
                    case "Class mastery combat arts":
                        combatArts = searchNotUniqueCombatArts(db, false,
                                false, true, false);
                        break;
                    case "Other combat arts":
                        combatArts = searchNotUniqueCombatArts(db, false,
                                false, false, true);
                }

                // Create a new adapter and link it to the recycler view
                CombatArtsAdapter combatArtsAdapter = new CombatArtsAdapter(combatArts, fragment);
                allCombatArtsRecycler.setAdapter(combatArtsAdapter);

                // TODO: is it necessary to add another LayoutManager to the recycler view?
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return layout;
    }

    public ArrayList<CombatArt> searchNotUniqueCombatArts(SQLiteDatabase db, boolean prof,
                                                     boolean exclusive, boolean classMastery,
                                                     boolean other){
        Cursor cursor = null;
        ArrayList<CombatArt> combatArts = new ArrayList<>();
        if (prof){
            cursor = db.rawQuery("SELECT art, effect, weapon, skillLevel, dur, " +
                            "mt, hit, avo, crit, range " +
                            "FROM CombatArtsAllWeaponProficient ",
                    new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArt(cursor.getString(0),
                            CombatArtsType.allWeaponProficiency,
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9)));
                } while (cursor.moveToNext());
            }
        }
        if (exclusive){
            cursor = db.rawQuery("SELECT art, effect, weapon, crest, dur, " +
                    "mt, hit, avo, crit, range " +
                    "FROM CombatArtsWeaponExclusive", new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArt(cursor.getString(0),
                            CombatArtsType.weaponExclusive,
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9)));
                } while (cursor.moveToNext());
            }
        }
        if (classMastery){
            cursor = db.rawQuery("SELECT art, effect, weapon, class, dur, " +
                    "mt, hit, avo, crit, range " +
                    "FROM CombatArtsClassMastery", new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArt(cursor.getString(0),
                            CombatArtsType.classMastery,
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9)));
                } while (cursor.moveToNext());
            }
        }
        if (other){
            cursor = db.rawQuery("SELECT art, effect, weapon, origin, dur, " +
                    "mt, hit, avo, crit, range " +
                    "FROM CombatArtsOther", new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArt(cursor.getString(0),
                            CombatArtsType.other,
                            cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9)));
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        return combatArts;
    }

    public void shopPopup(CombatArt cArt){
        myDialog.setContentView(R.layout.popup_combat_art);

        // Set listener for the button that closes the popup
        TextView textclose = (TextView) myDialog.findViewById(R.id.text_close);
        textclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        // Set the name of the combat art as the title for the popup
        TextView titleCombatArtName = (TextView)
                myDialog.findViewById(R.id.textview_title_combat_art_name);
        titleCombatArtName.setText(cArt.getArt());

        // Show the effect of the combat art
        TextView textEffect = (TextView) myDialog.findViewById(R.id.textview_combat_art_effect);
        textEffect.setText(cArt.getEffect());

        // Show the weapon associated with the combat art
        TextView textWeapon = (TextView) myDialog.findViewById(R.id.text_weapon);
        textWeapon.setText(cArt.getWeapon());

        // The second attribute depends on the type of the combat art
        TextView text2 = (TextView) myDialog.findViewById(R.id.text2_combat_art_popup);
        switch (cArt.getType()){
            case allWeaponProficiency:
            case uniqueWeaponProficiency:
                text2.setText(getResources().getString(R.string.skill_level));
                break;
            case weaponExclusive:
                text2.setText(getResources().getString(R.string.crest));
                break;
            case classMastery:
                text2.setText(getResources().getString(R.string.class_ingame));
                break;
            case other:
                text2.setText(getResources().getString(R.string.origin));
        }

        // Set the answer to the previous text field
        TextView text2Answer = (TextView) myDialog.findViewById(R.id.text2_answer);
        text2Answer.setText(cArt.getText2());

        // Set all 6 stats to the table (dur, mt, hit, avo, crit, range)
        ConstraintLayout table = (ConstraintLayout)
                myDialog.findViewById(R.id.constraint_layout_combat_art_table);
        for (int i = 6; i < table.getChildCount(); i++){
            TextView textViewStat = (TextView) table.getChildAt(i);
            // Stats are stored ordered in an ArrayList in cArt
            textViewStat.setText(cArt.getStats().get(i - 6));
        }

        myDialog.show();
    }
}