package com.example.fe3hguide.characters.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fe3hguide.R;

import java.util.ArrayList;

public class BlueLionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_navigation);
        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an ArrayList with the names of the Blue Lions members
        ArrayList<String> blueLionsNames = createBlueLionsNames();

        // Create an ArrayList with their pictures
        ArrayList<Integer> blueLionsImages = createBlueLionsImages();

        // Set adapter for the recycler view
        FactionsCardsAdapter adapter =
                new FactionsCardsAdapter(blueLionsNames, blueLionsImages, this);
        RecyclerView factionsRecycler = (RecyclerView)
                findViewById(R.id.characters_factions_recycler);
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 4x2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createBlueLionsNames(){
        // For simplicity, the names are hardcoded
        ArrayList<String> blueLionsNames = new ArrayList<>();
        blueLionsNames.add("Dimitri");
        blueLionsNames.add("Dedue");
        blueLionsNames.add("Felix");
        blueLionsNames.add("Sylvain");
        blueLionsNames.add("Ingrid");
        blueLionsNames.add("Ashe");
        blueLionsNames.add("Annette");
        blueLionsNames.add("Mercedes");
        return blueLionsNames;
    }

    private ArrayList<Integer> createBlueLionsImages(){
        ArrayList<Integer> blueLionsImages = new ArrayList<>();
        blueLionsImages.add(R.drawable.ic_dimitri);
        blueLionsImages.add(R.drawable.ic_dedue);
        blueLionsImages.add(R.drawable.ic_felix);
        blueLionsImages.add(R.drawable.ic_sylvain);
        blueLionsImages.add(R.drawable.ic_ingrid);
        blueLionsImages.add(R.drawable.ic_ashe);
        blueLionsImages.add(R.drawable.ic_annette);
        blueLionsImages.add(R.drawable.ic_mercedes);
        return blueLionsImages;
    }
}