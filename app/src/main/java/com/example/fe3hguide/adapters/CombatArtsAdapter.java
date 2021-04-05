package com.example.fe3hguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.characters.profile.CombatArtsFragment;
import com.example.fe3hguide.model.CombatArt;

import java.util.HashMap;
import java.util.List;

public class CombatArtsAdapter
        extends RecyclerView.Adapter<CombatArtsAdapter.ViewHolderCombatArts> {

    private List<CombatArt> combatArts;
    private HashMap<String, Integer> positions;     // Maps names to positions in combatArtsNames
    private CombatArtsFragment fragment;

    public CombatArtsAdapter(List<CombatArt> abilities, CombatArtsFragment fragment){
        this.combatArts = abilities;
        positions = new HashMap<>();
        for (int i = 0; i < combatArts.size(); i++){
            positions.put(combatArts.get(i).getName(), i);
        }
        this.fragment = fragment;
    }

    @Override
    public int getItemCount(){
        return combatArts.size();
    }

    @Override
    public CombatArtsAdapter.ViewHolderCombatArts onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new CombatArtsAdapter.ViewHolderCombatArts(this, cv, fragment);
    }

    @Override
    public void onBindViewHolder(CombatArtsAdapter.ViewHolderCombatArts holder, int position){
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.textView_standard_card_item);
        textView.setText(combatArts.get(position).getName());
    }

    public CombatArt getCombatArt(String name){
        return combatArts.get(positions.get(name));
    }

    public static class ViewHolderCombatArts extends RecyclerView.ViewHolder {

        private CombatArtsAdapter parent;
        private CardView cardView;
        private CombatArtsFragment fragment;

        public ViewHolderCombatArts(final CombatArtsAdapter parent,
                                    CardView v, final CombatArtsFragment fragment){
            super(v);
            this.parent = parent;
            cardView = v;
            this.fragment = fragment;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView combatArtName = (TextView)
                            cardView.findViewById(R.id.textView_standard_card_item);
                    String art = combatArtName.getText().toString();
                    fragment.shopPopup(parent.getCombatArt(art));
                }
            });
        }
    }

}