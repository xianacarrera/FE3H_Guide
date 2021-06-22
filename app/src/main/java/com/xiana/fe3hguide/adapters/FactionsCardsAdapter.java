package com.xiana.fe3hguide.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.characters.navigation.AshenWolvesActivity;
import com.xiana.fe3hguide.characters.navigation.BlackEaglesActivity;
import com.xiana.fe3hguide.characters.navigation.BlueLionsActivity;
import com.xiana.fe3hguide.characters.navigation.ChurchOfSeirosActivity;
import com.xiana.fe3hguide.characters.navigation.GoldenDeerActivity;
import com.xiana.fe3hguide.characters.profile.ProfileActivity;

import java.util.List;

public class FactionsCardsAdapter extends RecyclerView.Adapter<FactionsCardsAdapter.ViewHolderFactions> {

    private List<String> factions;
    private List<Integer> imageIds;
    private Activity activity;

    public FactionsCardsAdapter(List<String> factions, List<Integer> imageIds, Activity activity){
        this.factions = factions;
        this.imageIds = imageIds;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return factions.size();
    }

    @Override
    // onCreateViewHolder is called when the recycler view requires a new view holder
    public FactionsCardsAdapter.ViewHolderFactions onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_faction, parent, false);
        return new ViewHolderFactions(cv, activity);
    }

    @Override
    // onBindViewHolder is called when the recycler view need to use a view holder for data
    public void onBindViewHolder(ViewHolderFactions holder, int position){
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.faction_image);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), imageIds.get(position));
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(factions.get(position));
        TextView textView =  (TextView) cardView.findViewById(R.id.faction_text);
        textView.setText(factions.get(position));
    }

    public static class ViewHolderFactions extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView faction;
        private Activity activity;

        public ViewHolderFactions(CardView v, Activity a){
            super(v);
            cardView = v;
            activity = a;

            faction = (TextView) v.findViewById(R.id.faction_text);

            cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = null;
                    switch (faction.getText().toString()){
                        case "Church of Seiros":
                            intent = new Intent(activity, ChurchOfSeirosActivity.class);
                            break;
                        case "Ashen Wolves":
                            intent = new Intent(activity, AshenWolvesActivity.class);
                            break;
                        case "Black Eagles":
                            intent = new Intent(activity, BlackEaglesActivity.class);
                            break;
                        case "Blue Lions":
                            intent = new Intent(activity, BlueLionsActivity.class);
                            break;
                        case "Golden Deer":
                            intent = new Intent(activity, GoldenDeerActivity.class);
                            break;
                        default:
                            intent = new Intent(activity, ProfileActivity.class);
                            intent.putExtra("character", faction.getText().toString());
                    }
                    activity.startActivity(intent);
                }
            });
        }
    }
}
