package com.xiana.fe3hguide.characters.navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.xiana.fe3hguide.R;
import com.xiana.fe3hguide.adapters.FactionsCardsAdapter;

import java.util.ArrayList;

public class GoldenDeerActivity extends AppCompatActivity {

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

        // Create an ArrayList with the names of the Golden Deer members
        ArrayList<String> goldenDeerNames = createGoldenDeerNames();
        // Create an ArrayList with their pictures
        ArrayList<Integer> goldenDeerImages = createGoldenDeerImages();
        adapter = new FactionsCardsAdapter(goldenDeerNames, goldenDeerImages, this);

        factionsRecycler = (RecyclerView) findViewById(R.id.characters_factions_recycler);
    }

    private void setupComponents(){
        // Set "Golden Deer characters" as the text in the toolbar
        toolbar.setTitle("Golden Deer characters");
        setSupportActionBar(toolbar);

        // Add back arrow to the toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set adapter for the recycler view
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 4x2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createGoldenDeerNames(){
        // For simplicity and speed purposes, the names are hardcoded
        ArrayList<String> goldenDeerNames = new ArrayList<>();
        goldenDeerNames.add("Claude");
        goldenDeerNames.add("Hilda");
        goldenDeerNames.add("Lorenz");
        goldenDeerNames.add("Lysithea");
        goldenDeerNames.add("Leonie");
        goldenDeerNames.add("Marianne");
        goldenDeerNames.add("Raphael");
        goldenDeerNames.add("Ignatz");
        return goldenDeerNames;
    }

    private ArrayList<Integer> createGoldenDeerImages(){
        // For simplicity and speed purposes, the images are hardcoded
        ArrayList<Integer> goldenDeerImages = new ArrayList<>();
        goldenDeerImages.add(R.drawable.ic_claude);
        goldenDeerImages.add(R.drawable.ic_hilda);
        goldenDeerImages.add(R.drawable.ic_lorenz);
        goldenDeerImages.add(R.drawable.ic_lysithea);
        goldenDeerImages.add(R.drawable.ic_leonie);
        goldenDeerImages.add(R.drawable.ic_marianne);
        goldenDeerImages.add(R.drawable.ic_raphael);
        goldenDeerImages.add(R.drawable.ic_ignatz);
        return goldenDeerImages;
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