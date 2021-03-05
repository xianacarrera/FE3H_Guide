package com.example.fe3hguide.characters.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fe3hguide.R;

import java.util.ArrayList;

public class BlackEaglesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_navigation);

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set "Characters" as the text in the toolbar
        toolbar.setTitle(getString(R.string.nav_characters));

        // Create an ArrayList with the names of the Black Eagles members
        ArrayList<String> blackEaglesNames = createBlackEaglesNames();

        // Create an ArrayList with their pictures
        ArrayList<Integer> blackEaglesImages = createBlackEaglesImages();

        // Set adapter for the recycler view
        FactionsCardsAdapter adapter =
                new FactionsCardsAdapter(blackEaglesNames, blackEaglesImages, this);
        RecyclerView factionsRecycler = (RecyclerView)
                findViewById(R.id.characters_factions_recycler);
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 4x2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createBlackEaglesNames(){
        // For simplicity, the names are hardcoded
        ArrayList<String> blackEaglesNames = new ArrayList<>();
        blackEaglesNames.add("Edelgard");
        blackEaglesNames.add("Hubert");
        blackEaglesNames.add("Ferdinand");
        blackEaglesNames.add("Bernadetta");
        blackEaglesNames.add("Linhardt");
        blackEaglesNames.add("Caspar");
        blackEaglesNames.add("Dorothea");
        blackEaglesNames.add("Petra");
        return blackEaglesNames;
    }

    private ArrayList<Integer> createBlackEaglesImages(){
        ArrayList<Integer> blackEaglesImages = new ArrayList<>();
        blackEaglesImages.add(R.drawable.ic_edelgard);
        blackEaglesImages.add(R.drawable.ic_hubert);
        blackEaglesImages.add(R.drawable.ic_ferdinand);
        blackEaglesImages.add(R.drawable.ic_bernadetta);
        blackEaglesImages.add(R.drawable.ic_linhardt);
        blackEaglesImages.add(R.drawable.ic_caspar);
        blackEaglesImages.add(R.drawable.ic_dorothea);
        blackEaglesImages.add(R.drawable.ic_petra);
        return blackEaglesImages;
    }
}