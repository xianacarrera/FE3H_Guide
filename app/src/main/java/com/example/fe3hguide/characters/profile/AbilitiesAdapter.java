package com.example.fe3hguide.characters.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.fe3hguide.R;

public class AbilitiesAdapter extends
        RecyclerView.Adapter<AbilitiesAdapter.ViewHolderAbilities> {

    private List<String> abilitiesNames;
    private AbilitiesFragment fragment;

    public AbilitiesAdapter(List<String> abilities, AbilitiesFragment fragment){
        this.abilitiesNames = abilities;
        this.fragment = fragment;
    }

    @Override
    public int getItemCount(){
        return abilitiesNames.size();
    }

    @Override
    public ViewHolderAbilities onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolderAbilities(cv, fragment);
    }

    @Override
    public void onBindViewHolder(ViewHolderAbilities holder, int position){
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.textView_card_likes_item);
        textView.setText(abilitiesNames.get(position));
    }

    public static class ViewHolderAbilities extends RecyclerView.ViewHolder {

        private CardView cardView;
        private AbilitiesFragment fragment;

        public ViewHolderAbilities(CardView v, final AbilitiesFragment fragment){
            super(v);
            cardView = v;
            this.fragment = fragment;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.shopPopup();
                }
            });
        }
    }

}
