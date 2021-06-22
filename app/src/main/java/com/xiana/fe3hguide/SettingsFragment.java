package com.xiana.fe3hguide;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.google.android.material.navigation.NavigationView;

public class SettingsFragment extends Fragment {

    private SwitchCompat switchDarkMode;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isDarkMode;

    private ConstraintLayout layout;
    private Button homeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_settings, container, false);

        // Get saved settings
        sharedPreferences = getActivity().getSharedPreferences(
                "FireEmblemGuideSharedPrefs", Context.MODE_PRIVATE
        );
        editor = sharedPreferences.edit();
        // Check if the user has dark mode enabled. The default is false
        isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        initComponents(layout);
        setUpComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ConstraintLayout layout) {
        switchDarkMode = (SwitchCompat) layout.findViewById(R.id.switch_dark_moode);
        homeButton = (Button) layout.findViewById(R.id.button_settings_font);
    }

    private void setUpComponents() {
        // Set "Settings" as the text in the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.nav_settings));

        switchDarkMode.setChecked(isDarkMode);
        // The button works, but it is not visible
        homeButton.setBackgroundColor(Color.TRANSPARENT);
    }

    private void addListeners() {
        switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
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

                // Fix some bugs related to the Navigation Drawer and the Toolbar
                manageDarkMode(isChecked);

                // setDefaultNightMode restarts the activity
                // Come back to the settings fragment
                Fragment fragment = new SettingsFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commit();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a dialog with the options for the default tab
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Choose the default tab shown when opening the app");
                final String[] tabs = {"Characters", "Calculator", "Classes", "Tea time",
                "Supports"};

                // Each item is a tab option
                builder.setItems(tabs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int positionClicked) {
                        // Save the preference chosen by the user
                        editor.putInt("DefaultTab", positionClicked);
                        editor.apply();
                    }
                });

                Dialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().getDecorView().getBackground().setColorFilter(
                        getResources().getColor(R.color.about_dialog_tab), PorterDuff.Mode.SRC
                );
            }
        });
    }


    public void manageDarkMode(boolean isDarkMode) {
        Toolbar toolbar = MainActivity.toolbar;
        NavigationView navigationView = MainActivity.navigationView;

        if (isDarkMode) {
            // To fix bug that prevented toolbar from changing color automatically
            toolbar.setBackgroundColor(getResources().getColor(R.color.elevation1));
            // To fix bug that prevented navigation view from changing color automatically
            navigationView.setBackgroundColor(getResources().getColor(R.color.elevation1));
            navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_gray)));

            // Change color of "Options" string
            setTextColorForMenuItem(navigationView.getMenu().getItem(5), R.color.white);
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            navigationView.setBackgroundColor(getResources().getColor(R.color.mainBackground));
            navigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.mainText)));
            navigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.mainText)));
            setTextColorForMenuItem(navigationView.getMenu().getItem(5), R.color.mainText);
        }
    }

    private void setTextColorForMenuItem(MenuItem menuItem, int color) {
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), color)),
                0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }
}
