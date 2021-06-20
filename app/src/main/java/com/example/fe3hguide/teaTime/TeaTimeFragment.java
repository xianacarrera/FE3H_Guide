package com.example.fe3hguide.teaTime;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.example.fe3hguide.model.TeaTimeInfo;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

//TODO: CONSTANCE

public class TeaTimeFragment extends Fragment
        implements View.OnClickListener {

    private final Facade fc;

    // First cardView (selection)
    private ImageView icon;
    private SearchableSpinner searchCharacter;
    private SimpleListAdapter searchableSpinnerAdapter;
    private Button buttonHaveTea;

    // Second half of the screen
    private CardView cardInfoTeas;
    private ConstraintLayout layoutTeas, layoutTopics, layoutFinalConvos;
    private Button buttonFavouriteTeas, buttonTopics, buttonFinalConvos;
    private ConstraintLayout bottomTab;
    private String selectedCharacter;

    // Information shown as a result
    private TextView likedSpecifically, teasLikedSpecifically;
    private AutoCompleteTextView autoCompleteTextViewTopics;
    private ExpandableListView expandableTopics;
    private RecyclerView recyclerFinalConversations;

    // Navigation Tab Bar
    private NavigationTabBar navigationTabBar;


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

    private void initComponents(ScrollView scrollView) {
        searchCharacter = (SearchableSpinner) scrollView.findViewById(
                R.id.searchable_spinner);
        icon = (ImageView) scrollView.findViewById(R.id.imageview_icon);
        buttonHaveTea = (Button) scrollView.findViewById(R.id.button_have_tea);
        cardInfoTeas = (CardView) scrollView.findViewById(R.id.cardview_info_teas);
        layoutTeas = (ConstraintLayout) scrollView.findViewById(R.id.constraintLayout_infoTeas);
        layoutTopics = (ConstraintLayout)
                scrollView.findViewById(R.id.contraint_layout_info_topics);
        layoutFinalConvos = (ConstraintLayout)
                scrollView.findViewById(R.id.constraintLayout_info_final_convos);
        bottomTab = (ConstraintLayout) scrollView.findViewById(R.id.tea_time_botton_tab);
        //buttonFavouriteTeas = (Button) scrollView.findViewById(R.id.button_favourite_teas);
        //buttonTopics = (Button) scrollView.findViewById(R.id.button_topics);
        //buttonFinalConvos = (Button) scrollView.findViewById(R.id.button_final_conversations);
        navigationTabBar = (NavigationTabBar) scrollView.findViewById(R.id.navigation_tab_bar_tea_time);

        // Search result
        likedSpecifically = (TextView) scrollView.findViewById(R.id.textView_liked_especifically_by);
        teasLikedSpecifically = (TextView)
                scrollView.findViewById(R.id.textView_teas_liked_specifically);
        autoCompleteTextViewTopics =
                (AutoCompleteTextView) scrollView.findViewById(R.id.autoCompleteTextView_topics);
        expandableTopics = (ExpandableListView) scrollView.findViewById(R.id.expandable_topics);
        recyclerFinalConversations = (RecyclerView)
                scrollView.findViewById(R.id.recycler_final_conversations);
    }

    private void setupComponents() {
        // Set "Tea time" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_tea_time));

        // Get all the character names, excluding BylethM and BylethF.
        ArrayList<String> names = fc.getAllNamesButByleth();

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

        prepareNavigationTabBar();
    }

    private void prepareNavigationTabBar() {
        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        // Get the array with the colors for each tab
        TypedArray typedArray = getResources().obtainTypedArray(R.array.nav_tab_bar_colors);
        ArrayList<Integer> navTabColors = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++) {
            navTabColors.add(typedArray.getColor(i, 0));
        }
        typedArray.recycle();

        // Prepare the icons and names of the tabs
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_menu_teatime),
                        navTabColors.get(0)
                ).title("Favorite teas")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_topics),
                        navTabColors.get(1)
                ).title("Topics")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_final_convo),
                        navTabColors.get(2)
                ).title("Final conversations")
                        .build()
        );

        // Add the model
        navigationTabBar.setModels(models);
    }

    private void addListeners() {
        // Declare the class as a listener for the SearchableSpinner
        searchCharacter.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                if (searchableSpinnerAdapter.getItem(position) != null) {
                    selectedCharacter = (String) searchableSpinnerAdapter.getItem(position);

                    // Change the portrait to the icon of the selected character
                    Integer iconID = null;
                    if ((iconID = fc.getPortrait(selectedCharacter)) != null) {
                        icon.setImageResource(iconID);
                        icon.setVisibility(View.VISIBLE);

                        // In case information for another character was on screen, it is hidden
                        cardInfoTeas.setVisibility(View.GONE);
                        bottomTab.setVisibility(View.INVISIBLE);
                    } else {
                        // If there was no character with that name in the database, no error
                        // is shown. However, the icon does not change.

                        // If info about another character was being shown, it does not disappear
                        // neither.

                        // The selectedCharacter is invalid, so it is set to null.
                        selectedCharacter = null;
                    }
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

        // Prepare a listener for the bottom tabs
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                changeTab(index);
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        /*
         * When the button is clicked, if the name that was introduced corresponds to a
         * character, their information is displayed.
         */

        if (selectedCharacter == null) {         // Invalid character
            return;
        }

        // Look up the character's info (favourite teas, topics and final conversations)
        TeaTimeInfo teaTimeInfo = fc.getTeaTimeInfo(selectedCharacter);

        if (teaTimeInfo.getFavouriteTeas() == null) {
            // The character was not valid
            return;
        }

        // The information is shown to the user
        changeTab(0);
        showInfo(selectedCharacter, teaTimeInfo.getFavouriteTeas(),
                teaTimeInfo.getTopics(), teaTimeInfo.getFinalConversations(),
                teaTimeInfo.getOptions());

        // The bottom tab is now visible
        bottomTab.setVisibility(View.VISIBLE);

        // Select the first tab from the navigation tab bar (favorite teas)
        navigationTabBar.setModelIndex(0);
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
        likedSpecifically.setText(getString(R.string.liked_specifically_by) + " " + character);
        // Teas are shown in different lines. After the last line there's no new line
        teasLikedSpecifically.setText("");
        for (int i = 0; i < teas.size() - 1; i++) {
            teasLikedSpecifically.append(teas.get(i) + "\n");
        }
        teasLikedSpecifically.append(teas.get(teas.size() - 1));


        // Populate the second AutoCompleTextView with the corresponding topics
        ArrayAdapter<String> topicsAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, topics);
        autoCompleteTextViewTopics.setAdapter(topicsAdapter);


        // Set the adapter for the expandable list view with the topics
        ExpandableListAdapter expandableListAdapter = new TopicsExpandableListAdapter(getContext(),
                "Topics", topics);
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