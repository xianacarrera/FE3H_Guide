package com.example.fe3hguide;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fe3hguide.characters.navigation.CharactersFragment;
import com.example.fe3hguide.database.DAOCharacters;
import com.example.fe3hguide.database.FE3HDatabaseHelper;
import com.example.fe3hguide.database.Facade;
import com.example.fe3hguide.supports.SupportsFragment;
import com.example.fe3hguide.teaTime.TeaTimeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Facade fc;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDBConnection();
        setupComponents();
        addListeners();

        // By default, desplay the home fragment
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, fragment);
        ft.commit();
    }

    private void setupDBConnection(){
        fc = new Facade(this);
    }

    private void initComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_open_drawer, R.string.nav_close_drawer);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void setupComponents(){
        setSupportActionBar(toolbar);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void addListeners(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    // Manages the clicks on the options of the navigation menu
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.nav_guide:
                fragment = new GuideFragment();
                break;
            case R.id.nav_calculator:
                fragment = new CalculatorFragment();
                break;
            case R.id.nav_characters:
                fragment = new CharactersFragment();
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
                fragment = new HomeFragment();
        }

        // Display the selected fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // Close the drawer
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    /** If the navigation drawer is open, the back button closes it **/
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fc.closeDatabase();
    }
}