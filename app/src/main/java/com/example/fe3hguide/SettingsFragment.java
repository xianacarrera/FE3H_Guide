package com.example.fe3hguide;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsFragment extends Fragment {

    private SwitchCompat switchDarkMode;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isDarkMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_settings, container, false);

        // Get saved settings
        sharedPreferences = getActivity().getSharedPreferences(
                "SharedPreferences", Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
        // Check if the user has dark mode enabled. The default is false
        isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        initComponents(layout);
        setUpComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ConstraintLayout layout){
        switchDarkMode = (SwitchCompat) layout.findViewById(R.id.switch_dark_moode);
    }

    private void setUpComponents(){
        switchDarkMode.setChecked(isDarkMode);
    }

    private void addListeners(){
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    // Save the preferences
                    editor.putBoolean("DarkMode", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    // Save the preferences
                    editor.putBoolean("DarkMode", false);
                }

                // Apply the changes
                editor.apply();

                // setDefaultNightMode restarts the activity
                // Come back to the settings fragment
                Fragment fragment = new SettingsFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });
    }
}