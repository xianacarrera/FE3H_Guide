package com.example.fe3hguide.characters.profile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.mingle.sweetpick.CustomDelegate;
import com.mingle.sweetpick.DimEffect;
import com.mingle.sweetpick.SweetSheet;

import java.util.ArrayList;
import java.util.List;

public class MagicFragment extends Fragment {

    private final String character;
    private final SQLiteDatabase db;
    private SweetSheet sweetSheet;
    private View popUpLayout;

    private ArrayList<TextView> textViews;

    // PopUp components
    private TextView titleSpellName;
    private ImageView iconMagicType;
    private TextView textMagicType;
    private TextView description;
    private TextView rank;
    private TextView uses;
    private TextView mt, hit, crit, range, weight;


    public MagicFragment(String character, SQLiteDatabase db){
        this.character = character;
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)
                inflater.inflate(R.layout.fragment_magic, container, false);

        preparePopUp(layout);
        initComponents(layout);
        addListeners();

        // Search for all the character's spells and put them in the layout
        loadSpells();


        return layout;
    }

    private void preparePopUp(RelativeLayout layout){
        sweetSheet = new SweetSheet(layout);
        CustomDelegate customDelegate = new CustomDelegate(true,
                CustomDelegate.AnimationType.DuangLayoutAnimation, 1700);
        popUpLayout = LayoutInflater.from(getContext()).inflate(R.layout.popup_spell,
                null, false);
        customDelegate.setCustomView(popUpLayout);
        sweetSheet.setDelegate(customDelegate);
        sweetSheet.setBackgroundEffect(new DimEffect(0.5f));
    }

    private void initComponents(RelativeLayout layout){
        // Get all the text views
        textViews = new ArrayList<>();

        textViews.add((TextView) layout.findViewById(R.id.reason_D));
        textViews.add((TextView) layout.findViewById(R.id.reason_D_plus));
        textViews.add((TextView) layout.findViewById(R.id.reason_C));
        textViews.add((TextView) layout.findViewById(R.id.reason_C_plus));
        textViews.add((TextView) layout.findViewById(R.id.reason_B));
        textViews.add((TextView) layout.findViewById(R.id.reason_B_plus));
        textViews.add((TextView) layout.findViewById(R.id.reason_A));
        textViews.add((TextView) layout.findViewById(R.id.reason_A_plus));
        textViews.add((TextView) layout.findViewById(R.id.faith_D));
        textViews.add((TextView) layout.findViewById(R.id.faith_D_plus));
        textViews.add((TextView) layout.findViewById(R.id.faith_C));
        textViews.add((TextView) layout.findViewById(R.id.faith_C_plus));
        textViews.add((TextView) layout.findViewById(R.id.faith_B));
        textViews.add((TextView) layout.findViewById(R.id.faith_B_plus));
        textViews.add((TextView) layout.findViewById(R.id.faith_A));
        textViews.add((TextView) layout.findViewById(R.id.faith_A_plus));


        // PopUp components
        titleSpellName = (TextView) popUpLayout.findViewById(R.id.textview_title_spell_name);
        iconMagicType = (ImageView) popUpLayout.findViewById(R.id.spell_magic_type_icon);
        textMagicType = (TextView) popUpLayout.findViewById(R.id.textView_magic_type);
        description = (TextView) popUpLayout.findViewById(R.id.textView_spell_description);
        rank = (TextView) popUpLayout.findViewById(R.id.textview_spell_rank);
        uses = (TextView) popUpLayout.findViewById(R.id.textview_spell_uses);
        mt = (TextView) popUpLayout.findViewById(R.id.mt_popup);
        hit = (TextView) popUpLayout.findViewById(R.id.hit_popup);
        crit = (TextView) popUpLayout.findViewById(R.id.crit_popup);
        range = (TextView) popUpLayout.findViewById(R.id.range_popup);
        weight = (TextView) popUpLayout.findViewById(R.id.weight_popup);
    }

    private void addListeners(){
        // Each text view has a listener, but only those that have a spell work
        for (final TextView textView : textViews){
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String spell = textView.getText().toString();

                    // There's no spell -> nothing happens
                    if (spell.equals("-")) return;

                    // More info about the spell is shown in a popup
                    showPopup(spell);
                }
            });
        }
    }

    private void loadSpells(){
        // Get the information about the character's magic spells
        Cursor cursor = db.rawQuery("SELECT m.reason, m.faith " +
                "FROM Characters AS c NATURAL JOIN Magic AS m " +
                "WHERE c.name = ?", new String[] {character});

        ArrayList<String> reasonSpells = new ArrayList<>(8);
        ArrayList<String> faithSpells = new ArrayList<>(8);
        if (cursor.moveToFirst()){
            do {
                reasonSpells.add(cursor.getString(0));
                faithSpells.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // Put the spells in the layout
        putSpellsTextViews(reasonSpells, faithSpells);

        // Close cursor and database
        cursor.close();
    }

    private void putSpellsTextViews(List<String> reasonSpells, List<String> faithSpells){
        String spell = null;
        // The first 8 TextViews correspond to reason spells
        for (int i = 0; i < 8; i++){
            if (!(spell = reasonSpells.get(i)).equals("null")){
                // If there's no spell, the default text ("-") stays
                textViews.get(i).setText(spell);
                textViews.get(i).setBackgroundColor(getResources().getColor(R.color.blue_background));
            } else {
                textViews.get(i).setText("-");
                textViews.get(i).setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        }
        // The last 8 TextViews correspond to faith spells
        for (int i = 0; i < 8; i++){
            if (!(spell = faithSpells.get(i)).equals("null")){
                // If there's no spell, the default text ("-") stays
                textViews.get(i + 8).setText(spell);
                textViews.get(i + 8).setBackgroundColor(getResources().getColor(R.color.blue_background));
            } else {
                textViews.get(i + 8).setText("-");
                textViews.get(i + 8).setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        }
    }

    public void showPopup(String spell){
        // Set the name of the spell as the title
        titleSpellName.setText(spell);

        // Search for the spell's complete information
        Cursor cursor = db.rawQuery("SELECT magicType, description, rank, uses, mt, hit," +
                "range, crit, weight " +
                "FROM Spells WHERE spell = ?", new String[]{spell});

        if (cursor.moveToFirst()){
            textMagicType.setText(cursor.getString(0));
            description.setText(cursor.getString(1));
            rank.setText(cursor.getString(2));
            uses.setText(cursor.getString(3));
            mt.setText(cursor.getString(4));
            hit.setText(cursor.getString(5));
            range.setText(cursor.getString(6));
            crit.setText(cursor.getString(7));
            weight.setText(cursor.getString(8));
        }

        cursor.close();
        sweetSheet.show();
    }
}