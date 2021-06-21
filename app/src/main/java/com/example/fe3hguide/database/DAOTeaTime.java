package com.example.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;


import com.example.fe3hguide.model.TeaTimeInfo;

import java.util.ArrayList;
import java.util.List;

public class DAOTeaTime extends DAO {

    public DAOTeaTime(SQLiteDatabase db){
        super(db);
    }

    /**
     *
     * @return All the character names, excluding BylethM and BylethF
     */
    public ArrayList<String> getAllNamesButByleth(){
        ArrayList<String> names = new ArrayList<>();
        Cursor cursor = db.query("Characters", new String[]{"name"},
                "name not like ?", new String[]{"Byleth%"},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return names;
    }

    /**
     *
     * @param character Name of the character whose information is going to be searched
     * @return TeaTimeInfo with their favourite teas, topics and final conversations.
     *         If the character is not valid, these fields will be null.
     */
    public TeaTimeInfo getTeaTimeInfo(String character) {
        // Prepare a TeaTimeInfo with the name passed as a parameter
        TeaTimeInfo teaTimeInfo = new TeaTimeInfo.Builder(character).build();

        // Search for the id of a character with the name that was introduced
        // Byleth is not a valid result
        Cursor cursor = db.query("Characters", new String[]{"_id"},
                "name = ? and name not like ?",
                new String[]{character, "Byleth%"},
                null, null, null);

        // If there are no characters with that name, the cursor returns 0 rows of the table
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);

            // Retrieve the favourite teas of the character
            cursor = db.query("FavouriteTeas", new String[]{"tea"},
                    "_id = ?",
                    new String[]{Integer.toString(id)}, null, null,
                    null);

            // Create an ArrayList with the results and store it in teaTimeInfo
            ArrayList<String> teas = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    teas.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            teaTimeInfo.setFavouriteTeas(teas);

            // Retrieve the character's liked topics
            cursor = db.query("Topics", new String[]{"topic"}, "_id = ?",
                    new String[]{Integer.toString(id)}, null, null,
                    null);

            // Create an ArrayList with the results and store it in teaTimeInfo
            ArrayList<String> topics = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    topics.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
            teaTimeInfo.setTopics(topics);

            // Retrieve the final conversations that can pop up and their valid answers
            cursor = db.query("FinalConversations",
                    new String[]{"conversation", "option1", "option2", "option3"},
                    "_id = ?", new String[]{Integer.toString(id)},
                    null, null, null);

            // Store the final conversations and their associated answers
            ArrayList<String> finalConversations = new ArrayList<>();
            ArrayList<ArrayList<String>> options = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                options.add(new ArrayList<String>());
            }
            if (cursor.moveToFirst()) {
                do {
                    if (character.equals("Constance")){
                        // More final conversations, some indoors, some outdoors
                        if (cursor.getString(0).startsWith("$")) {
                            finalConversations.add(cursor.getString(0).replace("$", "(Outdoors) "));
                        } else {
                            finalConversations.add("(Indoors) " + cursor.getString(0));
                        }
                    } else {
                        finalConversations.add(cursor.getString(0));
                    }

                    for (int i = 0; i < 3; i++) {
                        options.get(i).add(cursor.getString(i + 1));
                    }
                } while (cursor.moveToNext());
            }
            teaTimeInfo.setFinalConversations(finalConversations);
            teaTimeInfo.setOptions(options);
        }

        cursor.close();
        return teaTimeInfo;
    }
}
