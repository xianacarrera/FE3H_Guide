package com.example.fe3hguide.characters.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.fe3hguide.R;
import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends AppCompatActivity {

    private String character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Get the name of the character that defines the profile
        Intent intent = getIntent();
        character = intent.getStringExtra("character");

        // Set up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Attach a TabsPagerAdapter to the ViewPager
        SwipePagerAdapter pagerAdapter = new SwipePagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        // Attach the ViewPager to the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);            // Link the ViewPager and the TabLayout
    }


    private class SwipePagerAdapter extends FragmentPagerAdapter {

        public SwipePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount(){
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GeneralFragment();
                case 1:
                    return new AbilitiesFragment();
                case 2:
                    return new MagicFragment(character);
                case 3:
                    return new CombatArtsFragment();
                case 4:
                    return new LikesDislikesFragment(character);
            }
            return null;
        }

        @Override
        // Adds the title corresponding to each of the tabs
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return getResources().getText(R.string.title_general_tab);
                case 1:
                    return getResources().getText(R.string.title_abilities_tab);
                case 2:
                    return getResources().getText(R.string.title_magic_tab);
                case 3:
                    return getResources().getText(R.string.combat_arts_tab);
                case 4:
                    return getResources().getText(R.string.likes_dislikes_tab);
            }
            return null;
        }
    }
}