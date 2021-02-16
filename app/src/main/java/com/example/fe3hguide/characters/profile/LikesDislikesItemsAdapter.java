package com.example.fe3hguide.characters.profile;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.fe3hguide.R;

public class LikesDislikesItemsAdapter extends
        RecyclerView.Adapter<LikesDislikesItemsAdapter.ViewHolderLikes> {

    private List<String> items;

    public LikesDislikesItemsAdapter(List<String> items){
        this.items = items;
    }

    @Override
    public int getItemCount(){
        return items.size();
    }

    @Override
    public LikesDislikesItemsAdapter.ViewHolderLikes onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolderLikes(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolderLikes holder, int position){
        CardView cardView = holder.cardView;
        TextView textView = (TextView) cardView.findViewById(R.id.textView_standard_card_item);
        textView.setText(items.get(position));
    }

    public static class ViewHolderLikes extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolderLikes(CardView v){
            super(v);
            cardView = v;
        }
    }

}
