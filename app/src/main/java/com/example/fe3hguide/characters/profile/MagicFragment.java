package com.example.fe3hguide.characters.profile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fe3hguide.FE3HDatabaseHelper;
import com.example.fe3hguide.R;

import java.util.ArrayList;
import java.util.List;

public class MagicFragment extends Fragment {

    private final String character;
    private final SQLiteDatabase db;

    public MagicFragment(String character, SQLiteDatabase db){
        this.character = character;
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_magic, container, false);

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
        putSpellsTextViews(layout, reasonSpells, faithSpells);

        // Close cursor and database
        cursor.close();

        return layout;
    }

    private void putSpellsTextViews(View layout, List<String> reasonSpells,
                                    List<String> faithSpells){
        // Get all the text views
        ArrayList<TextView> textViews = new ArrayList<>();

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

        String spell = null;
        // The first 8 TextViews correspond to reason spells
        for (int i = 0; i < 8; i++){
            if ((spell = reasonSpells.get(i)) != null){
                // If there's no spell, the default text ("-") stays
                textViews.get(i).setText(spell);
            }
        }
        // The last 8 TextViews correspond to faith spells
        for (int i = 0; i < 8; i++){
            if ((spell = faithSpells.get(i)) != null){
                // If there's no spell, the default text ("-") stays
                textViews.get(i + 8).setText(spell);
            }
        }
    }
}