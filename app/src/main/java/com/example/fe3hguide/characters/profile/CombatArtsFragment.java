package com.example.fe3hguide.characters.profile;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.CombatArtsAdapter;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.CombatArt;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.SweetSheet;

import java.util.List;

public class CombatArtsFragment extends Fragment {

    private final String character;
    private final SQLiteDatabase db;
    private final CombatArtsFragment fragment;
    private final Facade fc;
    private SweetSheet sweetSheet;
    private View popUpLayout;

    private Spinner spinner;
    private RecyclerView allCombatArtsRecycler;
    private RecyclerView uniqueRecycler;

    // PopUp components
    private TextView titleCombatArtName;
    private ImageView combatArtIcon;
    private TextView textEffect;
    private TextView textWeapon;
    private TextView text2;
    private TextView text2Answer;
    private ConstraintLayout table;

    public CombatArtsFragment(String character, SQLiteDatabase db){
        if (character.equals("BylethM") || character.equals("BylethF")){
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
                inflater.inflate(R.layout.fragment_combat_arts, container, false);

        preparePopUp(layout);
        initComponents(layout);         // Components of the CombatArtsFragment and the dialog

        // Search and display combat arts unique to the character
        prepareUniqueCombatArts();
        // Search and display combat arts common to all characters
        prepareNotUniqueCombatArts();

        addListeners();

        return layout;
    }

    private void preparePopUp(RelativeLayout layout){
        sweetSheet = new SweetSheet(layout);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation, 1700);
        popUpLayout = LayoutInflater.from(getContext()).inflate(R.layout.popup_combat_art,
                null, false);
        customDelegate.setCustomView(popUpLayout);
        sweetSheet.setDelegate(customDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(0.5f));
    }

    private void initComponents(RelativeLayout layout){
        uniqueRecycler = (RecyclerView) layout.findViewById(R.id.recycler_combat_arts_1);
        allCombatArtsRecycler = (RecyclerView) layout.findViewById(R.id.recycler_combat_arts_2);
        spinner = (Spinner) layout.findViewById(R.id.spinner_combat_arts);

        // PopUp components
        titleCombatArtName = (TextView) popUpLayout.findViewById(R.id.textview_title_combat_art_name);
        combatArtIcon = (ImageView) popUpLayout.findViewById(R.id.combat_art_icon);
        textEffect = (TextView) popUpLayout.findViewById(R.id.textview_combat_art_effect);
        textWeapon = (TextView) popUpLayout.findViewById(R.id.popup_combat_art_text_weapon);
        text2 = (TextView) popUpLayout.findViewById(R.id.text2_combat_art_popup);
        text2Answer = (TextView) popUpLayout.findViewById(R.id.text2_answer);
        table = (ConstraintLayout) popUpLayout.findViewById(R.id.constraint_layout_combat_art_table);
    }

    private void prepareUniqueCombatArts(){
        /*
         * Add all unique combat arts for the character, which are considered to be the ones
         * obtained as a budding talent, and the combat arts related to weapon proficiency
         * which are exclusive to some characters.
         */

        List<CombatArt> uniqueCombatArts = fc.getUniqueCombatArts(character);

        // Create adapter for the unique combat arts recycler view and link them
        CombatArtsAdapter uniqueAdapter = new CombatArtsAdapter(uniqueCombatArts, this);
        uniqueRecycler.setAdapter(uniqueAdapter);

        // Display the combat arts stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        uniqueRecycler.setLayoutManager(layoutManager);
    }

    private void prepareNotUniqueCombatArts(){
        /*
         * The second recycler view is the same for every character.
         * There is a spinner with several categories (learned, master...) so that the abilities
         * can be organized and more easily searched.
         */

        // By default, prepare the second recycler view to show all the abilities (not unique)
        List<CombatArt> combatArts = fc.getNotUniqueCombatArts(true,
                true, true, true);

        // Prepare the adapter
        CombatArtsAdapter defaultAdapter = new CombatArtsAdapter(combatArts, this);
        allCombatArtsRecycler.setAdapter(defaultAdapter);

        // Display the abilities stacked vertically
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        allCombatArtsRecycler.setLayoutManager(layoutManager2);
    }

    private void addListeners(){
        // Attach a listener to the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = spinner.getSelectedItem().toString();

                // The recycler view gets populated with the abilities of the selected type
                Cursor cursor = null;
                List<CombatArt> combatArts = null;
                switch (selection){
                    case "All combat arts":
                        combatArts = fc.getNotUniqueCombatArts( true,
                                true, true, true);
                        break;
                    case "Skill level combat arts":
                        combatArts = fc.getNotUniqueCombatArts( true,
                                false, false, false);
                        break;
                    case "Weapon exclusive combat arts":
                        combatArts = fc.getNotUniqueCombatArts( false,
                                true, false, false);
                        break;
                    case "Class mastery combat arts":
                        combatArts = fc.getNotUniqueCombatArts( false,
                                false, true, false);
                        break;
                    case "Other combat arts":
                        combatArts = fc.getNotUniqueCombatArts( false,
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
    }

    public void shopPopup(CombatArt cArt){
        // Set the name of the combat art as the title for the popup
        titleCombatArtName.setText(cArt.getName());

        // Show the icon associated with the combat art's type
        combatArtIcon.setImageResource(cArt.getIcon());

        // Show the effect of the combat art
        textEffect.setText(cArt.getEffect());

        // Show the weapon associated with the combat art
        textWeapon.setText(cArt.getWeapon());

        // The second attribute depends on the type of the combat art
        text2.setText(cArt.saySpecificTextMeaning());

        // Set the answer to the previous text field
        text2Answer.setText(cArt.getSpecificText());

        // Set all 6 stats to the table (dur, mt, hit, avo, crit, range)
        int i = 6;
        for (String stat : cArt.getStats().values()){
            TextView textViewStat = (TextView) table.getChildAt(i);
            textViewStat.setText(stat);
            i++;
        }

        sweetSheet.show();
    }
}