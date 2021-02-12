package com.example.fe3hguide.characters.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.fe3hguide.R;

import java.util.ArrayList;

public class ChurchOfSeirosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_navigation);
        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create an ArrayList with the names of the Church of Seiros members
        ArrayList<String> churchOfSeirosNames = createChurchOfSeirosNames();

        // Create an ArrayList with their pictures
        ArrayList<Integer> churchOfSeirosImages = createChurchOfSeirosImages();

        // Set adapter for the recycler view
        FactionsCardsAdapter adapter =
                new FactionsCardsAdapter(churchOfSeirosNames, churchOfSeirosImages, this);
        RecyclerView factionsRecycler = (RecyclerView)
                findViewById(R.id.characters_factions_recycler);
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 5x2 + 1
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createChurchOfSeirosNames(){
        // For simplicity, the names are hardcoded
        ArrayList<String> churchOfSeirosNames = new ArrayList<>();
        churchOfSeirosNames.add("Seteth");
        churchOfSeirosNames.add("Flayn");
        churchOfSeirosNames.add("Hanneman");
        churchOfSeirosNames.add("Manuela");
        churchOfSeirosNames.add("Gilbert");
        churchOfSeirosNames.add("Alois");
        churchOfSeirosNames.add("Catherine");
        churchOfSeirosNames.add("Shamir");
        churchOfSeirosNames.add("Cyril");
        churchOfSeirosNames.add("Jeritza");
        churchOfSeirosNames.add("Anna");
        return churchOfSeirosNames;
    }

    private ArrayList<Integer> createChurchOfSeirosImages(){
        ArrayList<Integer> churchOfSeirosImages = new ArrayList<>();
        churchOfSeirosImages.add(R.drawable.ic_seteth);
        churchOfSeirosImages.add(R.drawable.ic_flayn);
        churchOfSeirosImages.add(R.drawable.ic_hanneman);
        churchOfSeirosImages.add(R.drawable.ic_manuela);
        churchOfSeirosImages.add(R.drawable.ic_gilbert);
        churchOfSeirosImages.add(R.drawable.ic_alois);
        churchOfSeirosImages.add(R.drawable.ic_catherine);
        churchOfSeirosImages.add(R.drawable.ic_shamir);
        churchOfSeirosImages.add(R.drawable.ic_cyril);
        churchOfSeirosImages.add(R.drawable.ic_jeritza);
        churchOfSeirosImages.add(R.drawable.ic_anna);
        return churchOfSeirosImages;
    }
}