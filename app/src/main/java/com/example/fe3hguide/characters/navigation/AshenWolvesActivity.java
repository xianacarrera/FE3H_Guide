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

public class AshenWolvesActivity extends AppCompatActivity {

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

        // Create an ArrayList with the names of the Ashen Wolves members
        ArrayList<String> ashenWolvesNames = createAshenWolvesNames();
        // Create an ArrayList with their pictures
        ArrayList<Integer> ashenWolvesImages = createAshenWolvesImages();

        adapter = new FactionsCardsAdapter(ashenWolvesNames, ashenWolvesImages, this);
        factionsRecycler = (RecyclerView) findViewById(R.id.characters_factions_recycler);
    }

    private void setupComponents(){
        // Set "Ashen Wolves characters" as the text in the toolbar
        toolbar.setTitle("Ashen Wolves characters");
        setSupportActionBar(toolbar);

        // Add back arrow to the toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Set adapter for the recycler view
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 2x2
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createAshenWolvesNames(){
        // For simplicity and speed purposes, the names are hardcoded
        ArrayList<String> ashenWolvesNames = new ArrayList<>();
        ashenWolvesNames.add("Yuri");
        ashenWolvesNames.add("Balthus");
        ashenWolvesNames.add("Constance");
        ashenWolvesNames.add("Hapi");
        return ashenWolvesNames;
    }

    private ArrayList<Integer> createAshenWolvesImages(){
        // For simplicity, the images are hardcoded
        ArrayList<Integer> ashenWolvesImages = new ArrayList<>();
        ashenWolvesImages.add(R.drawable.ic_yuri);
        ashenWolvesImages.add(R.drawable.ic_balthus);
        ashenWolvesImages.add(R.drawable.ic_constance);
        ashenWolvesImages.add(R.drawable.ic_hapi);
        return ashenWolvesImages;
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