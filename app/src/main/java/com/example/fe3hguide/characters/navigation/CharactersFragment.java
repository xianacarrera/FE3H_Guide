package com.example.fe3hguide.characters.navigation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.FactionsCardsAdapter;
import com.example.fe3hguide.database.Facade;

import java.util.ArrayList;

public class CharactersFragment extends Fragment {

    private FactionsCardsAdapter adapter;
    private RecyclerView factionsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_characters, container, false);

        initComponents(layout);
        setupComponents();

        return layout;
    }

    private void initComponents(LinearLayout layout){
        // Create an ArrayList with the names of the factions
        ArrayList<String> factionsNames = createFactionsNames();
        // Create an ArrayList with the images of the factions
        ArrayList<Integer> factionsImages = createFactionsImages();
        adapter = new FactionsCardsAdapter(factionsNames, factionsImages, getActivity());

        factionsRecycler = (RecyclerView) layout.findViewById(R.id.characters_factions_recycler);
    }

    private void setupComponents(){
        // Set "Characters" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().
                setTitle(getString(R.string.nav_characters));

        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 3x2
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        factionsRecycler.setLayoutManager(layoutManager);
    }

    private ArrayList<String> createFactionsNames(){
        // For simplicity and speed purposes, the names are hardcoded
        ArrayList<String> factionsNames = new ArrayList<>(6);
        factionsNames.add(getResources().getString(R.string.byleth));
        factionsNames.add(getResources().getString(R.string.black_eagles));
        factionsNames.add(getResources().getString(R.string.church_of_seiros));
        factionsNames.add(getResources().getString(R.string.blue_lions));
        factionsNames.add(getResources().getString(R.string.ashen_wolves));
        factionsNames.add(getResources().getString(R.string.golden_deer));
        return factionsNames;
    }

    private ArrayList<Integer> createFactionsImages(){
        // For simplicity and speed purposes, the images are hardcoded
        ArrayList<Integer> factionsImages = new ArrayList<>(6);
        factionsImages.add(R.drawable.ic_byleth);
        factionsImages.add(R.drawable.ic_black_eagles);
        factionsImages.add(R.drawable.ic_church_of_seiros);
        factionsImages.add(R.drawable.ic_blue_lions);
        factionsImages.add(R.drawable.ic_ashen_wolves);
        factionsImages.add(R.drawable.ic_golden_deer);
        return factionsImages;
    }
}