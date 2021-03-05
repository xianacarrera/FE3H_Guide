package com.example.fe3hguide.characters.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fe3hguide.R;

import java.util.List;

public class CombatArtsAdapter
        extends RecyclerView.Adapter<CombatArtsAdapter.ViewHolderCombatArts> {

    private List<String> combatArtsNames;
    private CombatArtsFragment fragment;

    public CombatArtsAdapter(List<String> abilities, CombatArtsFragment fragment){
        this.combatArtsNames = abilities;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount(){
        return combatArtsNames.size();
    }

    @Override
    public CombatArtsAdapter.ViewHolderCombatArts onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new CombatArtsAdapter.ViewHolderCombatArts(cv, fragment);
    }

    @Override
    public void onBindViewHolder(CombatArtsAdapter.ViewHolderCombatArts holder, int position){
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.textView_standard_card_item);
        textView.setText(combatArtsNames.get(position));
    }

    public static class ViewHolderCombatArts extends RecyclerView.ViewHolder {

        private CardView cardView;
        private CombatArtsFragment fragment;

        public ViewHolderCombatArts(CardView v, final CombatArtsFragment fragment){
            super(v);
            cardView = v;
            this.fragment = fragment;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView combatArtName = (TextView)
                            cardView.findViewById(R.id.textView_standard_card_item);
                    fragment.shopPopup(combatArtName.getText().toString());
                }
            });
        }
    }

}