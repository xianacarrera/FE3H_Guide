package com.example.fe3hguide.characters.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fe3hguide.R;

import java.util.HashMap;


public class GeneralFragment extends Fragment {

    private TextView titleName, pronouns;
    private HashMap<String, Integer> baseStats;     // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ScrollView layout =
                inflater.inflate(R.layout.fragment_general, container, false);

        initComponents(layout);
        setupComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ScrollView layout){
        titleName = (TextView) layout.findViewById(R.id.full_character_name);
        pronouns = (TextView) layout.findViewById(R.id.textview_pronouns);


    }
}