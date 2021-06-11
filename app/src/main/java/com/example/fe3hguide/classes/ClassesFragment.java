package com.example.fe3hguide.classes;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.InGameClassAdapter;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.model.InGameClass;

import java.util.ArrayList;
import java.util.List;


/*
 * This fragment shows a list view with all the classes in the game. Clicking on one of them opens
 * a ClassDetailActivity.
 *
 * The adapter for the cells is the InGameClassAdapter class.
 */

public class ClassesFragment extends Fragment {

    private Facade fc;
    private List<InGameClass> classes;
    private ListView listView;
    private SearchView searchView;

    private Button allButton;
    private Button uniqueButton;
    private Button beginnerButton;
    private Button intermediateButton;
    private Button advancedButton;
    private Button masterButton;

    private String selectedFilter;          // Filter tab currently selected

    public ClassesFragment(Facade fc){
        this.fc = fc;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_classes, container, false);

        classes = fc.getClasses();          // Get all the data about the in game classes

        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    public void initComponents(LinearLayout layout){
        listView = (ListView) layout.findViewById(R.id.listView_classes);
        initSearchWidgets(layout);
    }

    private void initSearchWidgets(LinearLayout layout){
        searchView = (SearchView) layout.findViewById(R.id.classesListSearchView);
        allButton = (Button) layout.findViewById(R.id.classesAllButton);
        uniqueButton = (Button) layout.findViewById(R.id.classesUniqueButton);
        beginnerButton = (Button) layout.findViewById(R.id.classesBeginnerButton);
        intermediateButton = (Button) layout.findViewById(R.id.classesIntermediateButton);
        advancedButton = (Button) layout.findViewById(R.id.classesAdvancedButton);
        masterButton = (Button) layout.findViewById(R.id.classesMasterButton);
    }

    public void setupComponents(){
        // Get an adapter for the cells and the in game classes
        InGameClassAdapter adapter = new InGameClassAdapter(getActivity(), 0, classes);
        listView.setAdapter(adapter);
    }

    public void addListeners(){
        // When one item is clicked, its detail info is opened
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                InGameClass selectedClass = (InGameClass) listView.getItemAtPosition(position);
                Intent classDetailIntent = new Intent(getActivity(), InGameClassActivity.class);

                // The name of the selected class is passed as extra info
                classDetailIntent.putExtra("className", selectedClass.getName());
                startActivity(classDetailIntent);
            }
        });

        // Filter the InGameClasses shown in the ListView according to the user's input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        addButtonListeners();
    }

    private void addButtonListeners(){
        uniqueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(allButton.getText().toString());
            }
        });

        uniqueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(uniqueButton.getText().toString());
            }
        });

        beginnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(beginnerButton.getText().toString());
            }
        });

        intermediateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(intermediateButton.getText().toString());
            }
        });

        advancedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(advancedButton.getText().toString());
            }
        });

        masterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterList(masterButton.getText().toString());
            }
        });
    }

    private void filterList(String condition){
        selectedFilter = condition;

        List<InGameClass> filteredClasses = new ArrayList<>();
        for (InGameClass inGameClass : classes){
            if (inGameClass.getClassLevel().toLowerCase().contains(condition)){
                // The class satisfies the search condition
                filteredClasses.add(inGameClass);
            }
        }

        // Update the set of cells of the list
        InGameClassAdapter adapter = new InGameClassAdapter(getContext(), 0,
                filteredClasses);
        listView.setAdapter(adapter);
    }
}