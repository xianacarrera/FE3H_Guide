package com.example.fe3hguide.characters.profile;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fe3hguide.R;
import com.example.fe3hguide.adapters.LikesDislikesItemsAdapter;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;


public class LikesDislikesFragment extends Fragment {

    private final String character;
    private final SQLiteDatabase db;
    private NavigationTabBar navigationTabBar;
    private ViewPager viewPager;


    public LikesDislikesFragment(String character, SQLiteDatabase db){
        this.character = character;
        this.db = db;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_likes_dislikes, container, false);

        initComponents(layout);
        setUpComponents();
        addListeners();

        return layout;
    }

    private void initComponents(ConstraintLayout layout){
        navigationTabBar = (NavigationTabBar) layout.findViewById(R.id.navigation_tab_bar_likes_dislikes);
        viewPager = (ViewPager) layout.findViewById(R.id.viewPager_likes_dislikes);
    }

    private void setUpComponents(){
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                View view = null;
                switch (position){
                    case 1:
                        view = LayoutInflater.from(
                                getContext()).inflate(R.layout.meals, null, false);
                        prepareRecyclerViewsMeals(view);
                        break;
                    case 2:
                        view = LayoutInflater.from(
                                getContext()).inflate(R.layout.lost_items, null, false);
                        prepareRecyclerLostItems(view);
                        break;
                    default:
                        view = LayoutInflater.from(
                                getContext()).inflate(R.layout.gifts, null, false);
                        prepareRecyclerViewsGifts(view);
                }

                container.addView(view);
                return view;
            }
        });



        ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        // Get the array with the colors for each tab
        TypedArray typedArray = getResources().obtainTypedArray(R.array.nav_tab_bar_colors);
        ArrayList<Integer> navTabColors = new ArrayList<>();
        for (int i = 0; i < typedArray.length(); i++){
            navTabColors.add(typedArray.getColor(i, 0));
        }
        typedArray.recycle();

        // Prepare the icons and names of the tabs
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.gift),
                        navTabColors.get(0)
                ).title("Gifts")
                .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.meals),
                        navTabColors.get(1)
                ).title("Meals")
                .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.lost_items),
                        navTabColors.get(2)
                ).title("Lost items")
                .build()
        );

        // Add the model
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager);
    }

    private void addListeners(){
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    private void prepareRecyclerViewsGifts(View layout){
        // Get the information about the character's liked and disliked gifts
        Cursor cursor = db.rawQuery("SELECT g.gift, g.liked " +
                "FROM Characters AS c NATURAL JOIN CharacterGifts AS g " +
                "WHERE c.name = ?", new String[] {character});

        // Divide the info between the gifts liked and disliked by the character
        ArrayList<String> favouriteGifts = new ArrayList<>();
        ArrayList<String> dislikedGifts = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                if (cursor.getInt(1) == 1){
                    favouriteGifts.add(cursor.getString(0));
                } else {
                    dislikedGifts.add(cursor.getString(0));
                }
            } while (cursor.moveToNext());
        }

        // Create the adapters
        LikesDislikesItemsAdapter favouriteGiftsAdapter =
                new LikesDislikesItemsAdapter(favouriteGifts);
        LikesDislikesItemsAdapter dislikesGiftsAdapter =
                new LikesDislikesItemsAdapter(dislikedGifts);

        // Attach the adapters to their corresponding recycler views
        RecyclerView recyclerFavouriteGifts = (RecyclerView)
                layout.findViewById(R.id.recycler_favourite_gifts);
        recyclerFavouriteGifts.setAdapter(favouriteGiftsAdapter);
        RecyclerView recyclerDislikedGifts = (RecyclerView)
                layout.findViewById(R.id.recycler_disliked_gifts);
        recyclerDislikedGifts.setAdapter(dislikesGiftsAdapter);

        // Display the gifts stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerFavouriteGifts.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerDislikedGifts.setLayoutManager(layoutManager2);

        // Close the cursor
        cursor.close();
    }

    private void prepareRecyclerViewsMeals(View layout){
        // Get the information about the character's liked and disliked meals
        Cursor cursor = db.rawQuery("SELECT m.meal, m.liked " +
                "FROM Characters AS c NATURAL JOIN CharacterMeals AS m " +
                "WHERE c.name = ?", new String[] {character});

        // Divide the info between the meals liked and disliked by the character
        ArrayList<String> favouriteMeals = new ArrayList<>();
        ArrayList<String> dislikedMeals = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                if (cursor.getInt(1) == 1){
                    favouriteMeals.add(cursor.getString(0));
                } else {
                    dislikedMeals.add(cursor.getString(0));
                }
            } while (cursor.moveToNext());
        }

        // Create the adapters
        LikesDislikesItemsAdapter favouriteMealsAdapter =
                new LikesDislikesItemsAdapter(favouriteMeals);
        LikesDislikesItemsAdapter dislikesMealsAdapter =
                new LikesDislikesItemsAdapter(dislikedMeals);

        // Attach the adapters to their corresponding recycler views
        RecyclerView recyclerFavouriteMeals = (RecyclerView)
                layout.findViewById(R.id.recycler_favourite_meals);
        recyclerFavouriteMeals.setAdapter(favouriteMealsAdapter);
        RecyclerView recyclerDislikedMeals = (RecyclerView)
                layout.findViewById(R.id.recycler_disliked_meals);
        recyclerDislikedMeals.setAdapter(dislikesMealsAdapter);

        // Display the meals stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerFavouriteMeals.setLayoutManager(layoutManager);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        recyclerDislikedMeals.setLayoutManager(layoutManager2);

        // Close the cursor
        cursor.close();
    }

    private void prepareRecyclerLostItems(View layout){
        // Get the information about the character's lost items
        Cursor cursor = db.rawQuery("SELECT l.item " +
                "FROM Characters AS c NATURAL JOIN CharacterLostItems AS l " +
                "WHERE c.name = ?", new String[] {character});

        // Store the lost items
        ArrayList<String> lostItems = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                lostItems.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // Create the adapter
        LikesDislikesItemsAdapter lostItemsAdapter =
                new LikesDislikesItemsAdapter(lostItems);

        // Attach the adapter to the recycler view
        RecyclerView recyclerLostItems = (RecyclerView)
                layout.findViewById(R.id.recycler_lost_items);
        recyclerLostItems.setAdapter(lostItemsAdapter);

        // Display the lost items stacked vertically
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerLostItems.setLayoutManager(layoutManager);

        // Close the cursor
        cursor.close();
    }
}