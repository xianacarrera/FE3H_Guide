package com.example.fe3hguide.characters.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.fe3hguide.R;
import com.example.fe3hguide.characters.navigation.FactionsCardsAdapter;

import java.util.ArrayList;

public class CharactersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)
                inflater.inflate(R.layout.fragment_characters, container, false);

        // Create an ArrayList with the names of the factions
        ArrayList<String> factionsNames = createFactionsNames();

        // Create an ArrayList with the images of the factions
        ArrayList<Integer> factionsImages = createFactionsImages();

        // Set adapter for the recycler view
        FactionsCardsAdapter adapter = new FactionsCardsAdapter(factionsNames, factionsImages,
                getActivity());
        RecyclerView factionsRecycler = (RecyclerView)
                layout.findViewById(R.id.characters_factions_recycler);
        factionsRecycler.setAdapter(adapter);

        // The recycler should put the cards in a grid layout of size 3x2
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        factionsRecycler.setLayoutManager(layoutManager);

        return layout;
    }

    private ArrayList<String> createFactionsNames(){
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