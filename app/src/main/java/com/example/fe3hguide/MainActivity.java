package com.example.fe3hguide;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.fe3hguide.characters.navigation.CharactersFragment;
import com.example.fe3hguide.classes.ClassesFragment;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.supports.SupportsFragment;
import com.example.fe3hguide.teaTime.TeaTimeFragment;
import com.google.android.material.navigation.NavigationView;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Facade fc;
    private Toolbar toolbar;
    private FlowingDrawer drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private SharedPreferences sharedPreferences;
    private boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDBConnection();

        initComponents();
        setupComponents();
        addListeners();
        manageDarkMode();

        // By default, display the home fragment
        Fragment fragment = new CharactersFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();
    }

    private void setupDBConnection(){
        fc = Facade.getInstance(this);
    }

    private void initComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (FlowingDrawer) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setupComponents(){
        setSupportActionBar(toolbar);
        // Icon of 3 white stripes
        toolbar.setNavigationIcon(R.drawable.ic_menu_lines_white);

        drawer.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        //toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
         //       R.string.nav_open_drawer, R.string.nav_close_drawer);
    }

    private void addListeners(){
        navigationView.setNavigationItemSelectedListener(this);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.toggleMenu();
            }
        });

        drawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                    Log.i("MainActivity", "Drawer STATE_CLOSED");
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
                Log.i("MainActivity", "openRatio=" + openRatio
                        + " ,offsetPixels=" + offsetPixels);
            }
        });
    }

    @Override
    // Manages the clicks on the options of the navigation menu
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.nav_calculator:
                fragment = new CalculatorFragment();
                break;
            case R.id.nav_classes:
                fragment = new ClassesFragment(fc);
                break;
            case R.id.nav_tea_time:
                fragment = new TeaTimeFragment(fc);
                break;
            case R.id.nav_supports:
                fragment = new SupportsFragment(fc);
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new CharactersFragment();
        }

        // Display the selected fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // Close the drawer
        drawer.closeMenu();

        return true;
    }

    public void manageDarkMode(){
        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        // Get the saved preference associated with NightMode. The default value is false.
        isDarkMode = sharedPreferences.getBoolean("DarkMode", false);

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    // If the navigation drawer is open, the back button closes it
    public void onBackPressed(){
        if (drawer.isMenuVisible()) {
            drawer.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}