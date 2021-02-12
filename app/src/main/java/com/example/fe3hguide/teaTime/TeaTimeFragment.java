package com.example.fe3hguide.teaTime;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fe3hguide.R;

import java.util.ArrayList;
import java.util.List;

//TODO: CONSTANCE

public class TeaTimeFragment extends Fragment
        implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final SQLiteDatabase db;
    private ImageView icon;
    private AutoCompleteTextView searchCharacter;
    private ConstraintLayout infoTeas;

    public TeaTimeFragment(SQLiteDatabase db){
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView scrollView = (ScrollView) inflater.inflate(
                R.layout.fragment_tea_time, container, false);

        // Get all the names
        ArrayList<String> names = new ArrayList<>();
        Cursor cursor = db.query("CHARACTERS", new String[] {"NAME"},
                "NAME NOT LIKE ?", new String[] {"Byleth%"},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Populate the AutoCompleteTextView with the list of characters
        searchCharacter = (AutoCompleteTextView) scrollView.findViewById(
                R.id.autoCompleteTextView_search_character);
        // Store the data
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, names);
        searchCharacter.setAdapter(namesAdapter);
        // Declare the class as a listener for the autoCompleteTextView
        searchCharacter.setOnItemClickListener(this);

        // Store reference to what will be the ImageView of the character and set it invisible
        icon = (ImageView) scrollView.findViewById(R.id.imageView_icon);
        icon.setVisibility(View.INVISIBLE);         // No character has been selected yet

        // Prepare listener for the "Have tea" button
        Button buttonHaveTea = (Button) scrollView.findViewById(R.id.button_have_tea);
        buttonHaveTea.setOnClickListener(this);     // Button that displays information

        // Hide all elements with information about a specific character (nobody was selected yet)
        infoTeas = (ConstraintLayout) scrollView.findViewById(R.id.constraintLayout_infoTeas);
        infoTeas.setVisibility(View.GONE);          // No information is shown yet

        return scrollView;
    }

    @Override
    public void onClick(View view) {
        /*
         * When the button is clicked, if the name that was introduced corresponds to a
         * character, their information is displayed.
         */

        String character = searchCharacter.getText().toString();
        // Search for the id of a character with the name that was introduced
        Cursor cursor = db.query("CHARACTERS", new String[] {"_id"},
                "NAME = ? AND NAME NOT LIKE ?", new String[] {character, "Byleth%"},
                null, null, null);

        // If there are no characters with that name, the cursor returns 0 rows of the table
        if (cursor.moveToFirst()){
            int id = cursor.getInt(0);

            // Retrieve the favourite teas of the character
            cursor = db.query("FAVOURITE_TEAS", new String[] {"TEA"}, "_id = ?",
                    new String[] {Integer.toString(id)}, null, null, null);
            ArrayList<String> teas = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    teas.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            // Retrieve the character's liked topics
            cursor = db.query("TOPICS", new String[] {"TOPIC"}, "_id = ?",
                    new String[] {Integer.toString(id)}, null, null, null);
            ArrayList<String> topics = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    topics.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            // Retrieve the final conversations that can pop up and their valid answers
            cursor = db.query("FINAL_CONVERSATIONS",
                    new String[] {"CONVERSATION", "OPTION_1", "OPTION_2", "OPTION_3"},
                    "_id = ?", new String[] {Integer.toString(id)},
                    null, null, null);
            ArrayList<String> finalConversations = new ArrayList<>();
            ArrayList<ArrayList<String>> options = new ArrayList<>();
            for (int i = 0; i < 3; i++){
                options.add(new ArrayList<String>());
            }
            if (cursor.moveToFirst()) {
                do {
                    finalConversations.add(cursor.getString(0));
                    for (int i = 0; i < 3; i++) {
                        options.get(i).add(cursor.getString(i + 1));
                    }
                } while (cursor.moveToNext());
            }

            // The information is shown to the user
            showInfo(character, teas, topics, finalConversations, options);

            cursor.close();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Change the portrait to the icon of the selected character
        String name = (String) parent.getItemAtPosition(position);
        Cursor cursor = db.query("CHARACTERS", new String[] {"PORTRAIT"},
                "name = ?", new String[] {name},
                null, null, null);
        if (cursor.moveToFirst()){
            icon.setImageResource(cursor.getInt(0));
            icon.setVisibility(View.VISIBLE);
            // In case information for another character was on screen, it is hidden
            infoTeas.setVisibility(View.GONE);
        }
        cursor.close();
    }

    private void showInfo(String character, List<String> teas, List<String> topics,
                          List<String> finalConversations, List<ArrayList<String>> options){

        // Show information about the character's preferred teas
        TextView likedSpecifically = (TextView)
                getView().findViewById(R.id.textView_liked_especifically_by);
        likedSpecifically.setText(getString(R.string.liked_specifically_by) + " " + character);
        TextView teasLikedSpecifically = (TextView)
                getView().findViewById(R.id.textView_teas_liked_specifically);
        // Teas are shown in different lines. After the last line there's no new line
        for (int i = 0; i < teas.size() - 1; i++){
            teasLikedSpecifically.append(teas.get(i) + "\n");
        }
        teasLikedSpecifically.append(teas.get(teas.size() - 1));

        // Populate the second AutoCompleTextView with the corresponding topics
        AutoCompleteTextView autoCompleteTextViewTopics =
                (AutoCompleteTextView) getView().findViewById(R.id.autoCompleteTextView_topics);
        ArrayAdapter<String> topicsAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, topics);
        autoCompleteTextViewTopics.setAdapter(topicsAdapter);

        // Set the adapter for the expandable list view with the topics
        ExpandableListAdapter expandableListAdapter = new TopicsExpandableListAdapter(getContext(),
                "Topics", topics);
        ExpandableListView expandableTopics = (ExpandableListView)
                getView().findViewById(R.id.expandable_topics);
        expandableTopics.setAdapter(expandableListAdapter);
        // Heck if I know how this works, but IT WORKS
        expandableTopics.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition,
                                        long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

        // Set the adapter for the recycler view with the cards of the final conversations
        FinalConversationsAdapter finalConversationsAdapter = new FinalConversationsAdapter(
                finalConversations, options.get(0), options.get(1), options.get(2));
        RecyclerView recyclerFinalConversations = (RecyclerView)
                getView().findViewById(R.id.recycler_final_conversations);
        recyclerFinalConversations.setAdapter(finalConversationsAdapter);

        // Display the cards stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerFinalConversations.setLayoutManager(layoutManager);

        infoTeas.setVisibility(View.VISIBLE);
    }


    private void setListViewHeight(ExpandableListView listView, int group) {
        // IT WORKS, OK? DON'T ASK ME

        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.
                getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group)) || ((!listView.isGroupExpanded(i))
                    && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight +
                (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10) {
            height = 200;
        }
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}