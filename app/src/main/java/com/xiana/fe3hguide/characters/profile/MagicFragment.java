package com.xiana.fe3hguide.characters.profile;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.SpellsAdapter;
import com.xiana.fe3hguide.database.Facade;
import com.xiana.fe3hguide.model.Spell;

import java.util.ArrayList;
import java.util.List;

public class MagicFragment extends Fragment {

    private final String character;
    private final SQLiteDatabase db;
    private View popUpLayout;
    private Facade fc;

    private List<List<Spell>> spells;
    // spells.get(0) -> reason spells of the character
    // spells.get(1) -> faith spells of the character

    private ArrayList<TextView> textViews;
    private RecyclerView recyclerViewSpells;

    // PopUp components
    private Dialog myDialog;
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

        myDialog = new Dialog(getActivity());
        myDialog.setContentView(R.layout.popup_spell);

        fc = Facade.getInstance(getContext());
        spells = fc.getSpells(character);

        initComponents(layout);
        setUpRecyclerViewSpells();
        addListeners();

        // Put reason spells and faith spells in the layout
        putSpellsTextViews(spells.get(0), spells.get(1));


        return layout;
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


        recyclerViewSpells = (RecyclerView) layout.findViewById(R.id.recycler_spells);


        // PopUp components
        titleSpellName = (TextView) myDialog.findViewById(R.id.textview_title_spell_name);
        iconMagicType = (ImageView) myDialog.findViewById(R.id.spell_magic_type_icon);
        textMagicType = (TextView) myDialog.findViewById(R.id.textView_magic_type);
        description = (TextView) myDialog.findViewById(R.id.textView_spell_description);
        rank = (TextView) myDialog.findViewById(R.id.textview_spell_rank);
        uses = (TextView) myDialog.findViewById(R.id.textview_spell_uses);
        mt = (TextView) myDialog.findViewById(R.id.mt_popup);
        hit = (TextView) myDialog.findViewById(R.id.hit_popup);
        crit = (TextView) myDialog.findViewById(R.id.crit_popup);
        range = (TextView) myDialog.findViewById(R.id.range_popup);
        weight = (TextView) myDialog.findViewById(R.id.weight_popup);
    }

    private void setUpRecyclerViewSpells(){
        /*
         * The RecyclerView shows all spells available to the character. Clicking on one opens
         * a popup with information about the spell.
         */

        SpellsAdapter adapter = new SpellsAdapter(spells, this);
        recyclerViewSpells.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSpells.setLayoutManager(layoutManager);
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

    private void putSpellsTextViews(List<Spell> reasonSpells, List<Spell> faithSpells){
        Spell spell = null;
        // The first 8 TextViews correspond to reason spells
        for (int i = 0; i < 8; i++){
            if ((spell = reasonSpells.get(i)) != null){
                textViews.get(i).setText(spell.getName());
                textViews.get(i).setBackgroundColor(getResources().getColor(R.color.blue_background));
            } else {
                // If there's no spell, the default text ("-") stays
                textViews.get(i).setText("-");
                textViews.get(i).setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        }
        // The last 8 TextViews correspond to faith spells
        for (int i = 0; i < 8; i++){
            if ((spell = faithSpells.get(i)) != null){
                textViews.get(i + 8).setText(spell.getName());
                textViews.get(i + 8).setBackgroundColor(getResources().getColor(R.color.blue_background));
            } else {
                // If there's no spell, the default text ("-") stays
                textViews.get(i + 8).setText("-");
                textViews.get(i + 8).setBackgroundColor(getResources().getColor(R.color.light_gray));
            }
        }
    }

    public void showPopup(String spellName){
        // Set the name of the spell as the title
        titleSpellName.setText(spellName);

        // Search for the spell's complete information
        Spell spell = fc.getSpell(spellName);

        if (spell != null){
            textMagicType.setText(spell.getMagicType());
            textMagicType.setTextSize(14);
            iconMagicType.setImageResource(spell.getIcon());
            description.setText(spell.getDescription());
            rank.setText(spell.getRank());
            uses.setText(spell.getUses());
            mt.setText(spell.getStats().get("mt"));
            hit.setText(spell.getStats().get("hit"));
            range.setText(spell.getStats().get("range"));
            crit.setText(spell.getStats().get("crit"));
            weight.setText(spell.getStats().get("weight"));
        }
;
        myDialog.show();
    }

    public void showPopup(Spell spell) {
        // Set the name of the spell as the title
        titleSpellName.setText(spell.getName());
        iconMagicType.setImageResource(spell.getIcon());
        textMagicType.setText(spell.getMagicType());
        textMagicType.setTextSize(14);
        description.setText(spell.getDescription());
        rank.setText(spell.getRank());
        uses.setText(spell.getUses());
        mt.setText(spell.getStats().get("mt"));
        hit.setText(spell.getStats().get("hit"));
        range.setText(spell.getStats().get("range"));
        crit.setText(spell.getStats().get("crit"));
        weight.setText(spell.getStats().get("weight"));

        myDialog.show();
    }
}