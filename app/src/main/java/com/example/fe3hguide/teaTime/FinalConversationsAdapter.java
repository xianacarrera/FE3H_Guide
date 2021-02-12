package com.example.fe3hguide.teaTime;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fe3hguide.R;

import java.util.List;

public class FinalConversationsAdapter
        extends RecyclerView.Adapter<FinalConversationsAdapter.ViewHolder> {

    private List<String> prompts, options1, options2, options3;

    public FinalConversationsAdapter(List<String> prompts, List<String> options1,
                                     List<String> options2, List<String> options3){
        this.prompts = prompts;
        this.options1 = options1;
        this.options2 = options2;
        this.options3 = options3;
    }

    // Used to specify which views should be used for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView finalConvo = (TextView) cardView.findViewById(R.id.textView_final_conversation);
        finalConvo.setText(prompts.get(position));
        TextView option1 = (TextView) cardView.findViewById(R.id.text_option1);
        option1.setText(options1.get(position));
        TextView option2 = (TextView) cardView.findViewById(R.id.text_option2);
        option2.setText(options2.get(position));
        TextView option3 = (TextView) cardView.findViewById(R.id.text_option3);
        option3.setText(options3.get(position));
    }

    @Override
    public FinalConversationsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_final_conversation, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    // Returns the number of items in the recycler view
    public int getItemCount(){
        return prompts.size();
    }
}
