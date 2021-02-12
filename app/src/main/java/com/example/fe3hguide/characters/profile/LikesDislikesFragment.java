package com.example.fe3hguide.characters.profile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fe3hguide.FE3HDatabaseHelper;
import com.example.fe3hguide.R;

import java.util.ArrayList;


public class LikesDislikesFragment extends Fragment {

    private final String character;
    private SQLiteDatabase db;

    public LikesDislikesFragment(String character){
        this.character = character;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ConstraintLayout layout = (ConstraintLayout)
                inflater.inflate(R.layout.fragment_likes_dislikes, container, false);

        // Get access to the database
        SQLiteOpenHelper fe3hDatabaseHelper = new FE3HDatabaseHelper(getActivity());
        // TODO: TRY-CATCH?
        db = fe3hDatabaseHelper.getReadableDatabase();

        prepareRecyclerViewsGifts(layout);
        prepareRecyclerViewsMeals(layout);
        prepareRecyclerLostItems(layout);

        return layout;
    }

    private void prepareRecyclerViewsGifts(ConstraintLayout layout){
        // Get the information about the character's liked and disliked gifts
        Cursor cursor = db.rawQuery("SELECT g.GIFT, g.LIKED " +
                "FROM CHARACTERS AS c NATURAL JOIN CHARACTER_GIFTS AS g " +
                "WHERE c.NAME = ?", new String[] {character});

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

    private void prepareRecyclerViewsMeals(ConstraintLayout layout){
        // Get the information about the character's liked and disliked meals
        Cursor cursor = db.rawQuery("SELECT m.MEAL, m.LIKED " +
                "FROM CHARACTERS AS c NATURAL JOIN CHARACTER_MEALS AS m " +
                "WHERE c.NAME = ?", new String[] {character});

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

    private void prepareRecyclerLostItems(ConstraintLayout layout){
        // Get the information about the character's lost items
        Cursor cursor = db.rawQuery("SELECT l.ITEM " +
                "FROM CHARACTERS AS c NATURAL JOIN CHARACTER_LOST_ITEMS AS l " +
                "WHERE c.NAME = ?", new String[] {character});

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