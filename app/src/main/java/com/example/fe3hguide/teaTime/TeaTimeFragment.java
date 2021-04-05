package com.example.fe3hguide.teaTime;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.FinalConversationsAdapter;
import com.example.fe3hguide.adapters.TopicsExpandableListAdapter;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.adapters.SimpleListAdapter;

import java.util.ArrayList;
import java.util.List;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

//TODO: CONSTANCE

public class TeaTimeFragment extends Fragment
        implements View.OnClickListener {

    private final Facade fc;
    private ImageView icon;
    private SearchableSpinner searchCharacter;
    private SimpleListAdapter searchableSpinnerAdapter;
    private Button buttonHaveTea;
    private CardView cardInfoTeas;
    private ConstraintLayout layoutTeas, layoutTopics, layoutFinalConvos;
    private ImageView imageFavouriteTeas, imageTopics, iamgeFinalConvos;
    private ConstraintLayout bottomTab;
    private String selectedCharacter;

    public TeaTimeFragment(Facade fc) {
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView scrollView = (ScrollView) inflater.inflate(
                R.layout.fragment_tea_time, container, false);

        initComponents(scrollView);
        setupComponents();
        addListeners();

        return scrollView;
    }

    private void initComponents(ScrollView scrollView){
        searchCharacter = (SearchableSpinner) scrollView.findViewById(
                R.id.searchable_spinner);
        icon = (ImageView) scrollView.findViewById(R.id.imageView_icon);
        buttonHaveTea = (Button) scrollView.findViewById(R.id.button_have_tea);
        cardInfoTeas = (CardView) scrollView.findViewById(R.id.cardView_info_teas);
        layoutTeas = (ConstraintLayout) scrollView.findViewById(R.id.constraintLayout_infoTeas);
        layoutTopics = (ConstraintLayout)
                scrollView.findViewById(R.id.contraint_layout_info_topics);
        layoutFinalConvos = (ConstraintLayout)
                scrollView.findViewById(R.id.constraintLayout_info_final_convos);
        bottomTab = (ConstraintLayout) scrollView.findViewById(R.id.tea_time_botton_tab);
        imageFavouriteTeas = (ImageView) scrollView.findViewById(R.id.imageView_tea_cup);
        imageTopics = (ImageView) scrollView.findViewById(R.id.imageView_topics);
        iamgeFinalConvos = (ImageView) scrollView.findViewById(R.id.imageView_final_convo);
    }

    private void setupComponents(){
        // Set "Tea time" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().
                setTitle(getString(R.string.nav_tea_time));

        // Get all the names
        ArrayList<String> names = new ArrayList<>();
        Cursor cursor = db.query("Characters", new String[]{"name"},
                "name not like ?", new String[]{"Byleth%"},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Populate the SearchableSpinner with the list of characters
        searchableSpinnerAdapter = new SimpleListAdapter(getActivity(), names);
        searchCharacter.setAdapter(searchableSpinnerAdapter);
        searchCharacter.setSelectedItem(0);

        // Store reference to what will be the ImageView of the character and set it invisible
        icon.setVisibility(View.INVISIBLE);         // No character has been selected yet

        // Hide all elements with information about a specific character (nobody was selected yet)
        cardInfoTeas.setVisibility(View.GONE);          // No information is shown yet

        // Hide bottom tab until the button "have tea" is clicked
        bottomTab.setVisibility(View.INVISIBLE);
    }

    private void addListeners(){
        // Declare the class as a listener for the SearchableSpinner
        searchCharacter.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                if (searchableSpinnerAdapter.getItem(position) != null) {
                    selectedCharacter = (String) searchableSpinnerAdapter.getItem(position);

                    // Change the portrait to the icon of the selected character
                    Cursor cursor = db.query("Characters", new String[]{"portrait"},
                            "name = ?", new String[]{selectedCharacter},
                            null, null, null);
                    if (cursor.moveToFirst()) {
                        icon.setImageResource(cursor.getInt(0));
                        icon.setVisibility(View.VISIBLE);
                        // In case information for another character was on screen, it is hidden
                        cardInfoTeas.setVisibility(View.GONE);
                        bottomTab.setVisibility(View.INVISIBLE);
                    }
                    cursor.close();
                } else {
                    selectedCharacter = null;
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        // Prepare listener for the "Have tea" button
        buttonHaveTea.setOnClickListener(this);     // Button that displays information

        // Prepare listeners for the bottom tab buttons
        imageFavouriteTeas.setOnClickListener(this);
        imageTopics.setOnClickListener(this);
        iamgeFinalConvos.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_have_tea:
                /*
                 * When the button is clicked, if the name that was introduced corresponds to a
                 * character, their information is displayed.
                 */

                if (selectedCharacter == null){
                    return;
                }

                // Search for the id of a character with the name that was introduced
                Cursor cursor = db.query("Characters", new String[]{"_id"},
                        "name = ? and name not like ?",
                        new String[]{selectedCharacter, "Byleth%"},
                        null, null, null);

                // If there are no characters with that name, the cursor returns 0 rows of the table
                if (cursor.moveToFirst()) {
                    int id = cursor.getInt(0);

                    // Retrieve the favourite teas of the character
                    cursor = db.query("FavouriteTeas", new String[]{"tea"},
                            "_id = ?",
                            new String[]{Integer.toString(id)}, null, null,
                            null);
                    ArrayList<String> teas = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            teas.add(cursor.getString(0));
                        } while (cursor.moveToNext());
                    }

                    // Retrieve the character's liked topics
                    cursor = db.query("Topics", new String[]{"topic"}, "_id = ?",
                            new String[]{Integer.toString(id)}, null, null,
                            null);
                    ArrayList<String> topics = new ArrayList<>();
                    if (cursor.moveToFirst()) {
                        do {
                            topics.add(cursor.getString(0));
                        } while (cursor.moveToNext());
                    }

                    // Retrieve the final conversations that can pop up and their valid answers
                    cursor = db.query("FinalConversations",
                            new String[]{"conversation", "option1", "option2", "option3"},
                            "_id = ?", new String[]{Integer.toString(id)},
                            null, null, null);
                    ArrayList<String> finalConversations = new ArrayList<>();
                    ArrayList<ArrayList<String>> options = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
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
                    changeTab(0);
                    showInfo(selectedCharacter, teas, topics, finalConversations, options);

                    // The bottom tab is now visible
                    bottomTab.setVisibility(View.VISIBLE);

                    cursor.close();
                }
                break;
            case R.id.imageView_tea_cup:
                // Show views related to the character's favourite teas and hide the rest
                changeTab(0);
                break;
            case R.id.imageView_topics:
                // Show views related to the topics and hide the rest
                changeTab(1);
                break;
            case R.id.imageView_final_convo:
                // Show views related to the character's final conversations and hide the rest
                changeTab(2);
        }


    }

    // Method that displays the information regarding teas, topics or final convos depending on
    // which button was clicked
    private void changeTab(int tab) {
        switch (tab) {
            case 0:
                layoutTeas.setVisibility(View.VISIBLE);
                layoutTopics.setVisibility(View.GONE);
                layoutFinalConvos.setVisibility(View.GONE);
                break;
            case 1:
                layoutTeas.setVisibility(View.GONE);
                layoutTopics.setVisibility(View.VISIBLE);
                layoutFinalConvos.setVisibility(View.GONE);
                break;
            case 2:
                layoutTeas.setVisibility(View.GONE);
                layoutTopics.setVisibility(View.GONE);
                layoutFinalConvos.setVisibility(View.VISIBLE);
        }
    }

    private void showInfo(String character, List<String> teas, List<String> topics,
                          List<String> finalConversations, List<ArrayList<String>> options) {

        // Show information about the character's preferred teas
        TextView likedSpecifically = (TextView)
                getView().findViewById(R.id.textView_liked_especifically_by);
        likedSpecifically.setText(getString(R.string.liked_specifically_by) + " " + character);
        TextView teasLikedSpecifically = (TextView)
                getView().findViewById(R.id.textView_teas_liked_specifically);
        // Teas are shown in different lines. After the last line there's no new line
        teasLikedSpecifically.setText("");
        for (int i = 0; i < teas.size() - 1; i++) {
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

        cardInfoTeas.setVisibility(View.VISIBLE);
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