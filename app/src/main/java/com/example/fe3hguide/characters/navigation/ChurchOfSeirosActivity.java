package com.example.fe3hguide.characters.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.FactionsCardsAdapter;
import com.example.fe3hguide.database.Facade;

import java.util.ArrayList;

public class ChurchOfSeirosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FactionsCardsAdapter adapter;
    private RecyclerView factionsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_navigation);

        initComponents();
        setupComponents();
    }

    private void initComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Create an ArrayList with the names of the Church of Seiros members
        ArrayList<String> churchOfSeirosNames = createChurchOfSeirosNames();
        // Create an ArrayList with their pictures
        ArrayList<Integer> churchOfSeirosImages = createChurchOfSeirosImages();
        adapter = new FactionsCardsAdapter(churchOfSeirosNames, churchOfSeirosImages, this);

        factionsRecycler = (RecyclerView) findViewById(R.id.characters_factions_recycler);
    }

    private void setupComponents(){
        // Set "Church of Seiros characters" as the text in the toolbar
        toolbar.setTitle("Church of Seiros characters");
        setSupportActionBar(toolbar);

        // Add back arrow to the toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set adapter for the recycler view
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 5x2 + 1
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createChurchOfSeirosNames(){
        // For simplicity and speed purposes, the names are hardcoded
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
        // For simplicity and speed purposes, the images are hardcoded
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

    @Override
    // Back arrow on the toolbar returns to previous activity (if there is any)
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}