package com.xiana.fe3hguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.characters.profile.MagicFragment;
import com.xiana.fe3hguide.model.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpellsAdapter
        extends RecyclerView.Adapter<SpellsAdapter.ViewHolderSpells> {

    private List<Spell> spells;             // Combines reason and faith spells in a single list
    private HashMap<String, Integer> positions;     // Maps spell names to positions in spells
    private MagicFragment fragment;

    public SpellsAdapter(List<List<Spell>> spells, MagicFragment fragment){
        // Join reason and faith spells in a single list
        this.spells = new ArrayList<>();
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < spells.get(i).size(); j++){
                if (spells.get(i).get(j) != null) this.spells.add(spells.get(i).get(j));
            }
        }

        // Map spell names to positions in the previous list
        positions = new HashMap<>();
        for (int i = 0; i < this.spells.size(); i++){
            positions.put(this.spells.get(i).getName(), i);
        }

        this.fragment = fragment;
    }

    @Override
    public int getItemCount(){
        return spells.size();
    }

    @Override
    public SpellsAdapter.ViewHolderSpells onCreateViewHolder(
            ViewGroup parent, int viewType){
        ConstraintLayout cv = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_w_image, parent, false);
        return new SpellsAdapter.ViewHolderSpells(this, cv, fragment);
    }

    @Override
    public void onBindViewHolder(SpellsAdapter.ViewHolderSpells holder, int position){
        ConstraintLayout constraintLayout = holder.constraintLayout;

        // The title is the name of the spell
        TextView textView = (TextView) constraintLayout.findViewById(R.id.textView_card_title);
        textView.setText(spells.get(position).getName());

        // The image is the spell's magic type icon
        ImageView imageView = (ImageView) constraintLayout.findViewById(R.id.imageView_card_icon);
        imageView.setImageResource(spells.get(position).getIcon());

        // The detail text shows the description of the spell
        TextView description = (TextView) constraintLayout.findViewById(R.id.textView_card_detail);
        description.setText(spells.get(position).getDescription());
    }

    public Spell getSpell(String spellName){
        return spells.get(positions.get(spellName));
    }

    public static class ViewHolderSpells extends RecyclerView.ViewHolder {

        private SpellsAdapter parent;
        private ConstraintLayout constraintLayout;
        private MagicFragment fragment;

        public ViewHolderSpells(final SpellsAdapter parent,
                                    ConstraintLayout v, final MagicFragment fragment){
            super(v);
            this.parent = parent;
            constraintLayout = v;
            this.fragment = fragment;

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView spellTextView = (TextView)
                            constraintLayout.findViewById(R.id.textView_card_title);
                    String spellName = spellTextView.getText().toString();
                    fragment.showPopup(parent.getSpell(spellName));
                }
            });
        }
    }


}
