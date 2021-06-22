package com.xiana.fe3hguide.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.characters.profile.CombatArtsFragment;
import com.xiana.fe3hguide.model.CombatArt;

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
        ConstraintLayout cv = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item_w_image, parent, false);
        return new CombatArtsAdapter.ViewHolderCombatArts(this, cv, fragment);
    }

    @Override
    public void onBindViewHolder(CombatArtsAdapter.ViewHolderCombatArts holder, int position){
        ConstraintLayout constraintLayout = holder.constraintLayout;

        // The title is the name of the combat art
        TextView textView = (TextView) constraintLayout.findViewById(R.id.textView_card_title);
        textView.setText(combatArts.get(position).getName());

        // The image is the combat art icon
        ImageView imageView = (ImageView) constraintLayout.findViewById(R.id.imageView_card_icon);
        imageView.setImageResource(combatArts.get(position).getIcon());

        // The detail text shows the description of the combat art
        TextView description = (TextView) constraintLayout.findViewById(R.id.textView_card_detail);
        description.setText(combatArts.get(position).getEffect());
    }

    public CombatArt getCombatArt(String name){
        return combatArts.get(positions.get(name));
    }

    public static class ViewHolderCombatArts extends RecyclerView.ViewHolder {

        private CombatArtsAdapter parent;
        private ConstraintLayout constraintLayout;
        private CombatArtsFragment fragment;

        public ViewHolderCombatArts(final CombatArtsAdapter parent,
                                    ConstraintLayout v, final CombatArtsFragment fragment){
            super(v);
            this.parent = parent;
            constraintLayout = v;
            this.fragment = fragment;

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView combatArtName = (TextView)
                            constraintLayout.findViewById(R.id.textView_card_title);
                    String art = combatArtName.getText().toString();
                    fragment.shopPopup(parent.getCombatArt(art));
                }
            });
        }
    }

}