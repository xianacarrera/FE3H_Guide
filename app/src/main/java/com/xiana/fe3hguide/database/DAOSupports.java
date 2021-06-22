package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DAOSupports extends DAO {

    public DAOSupports(SQLiteDatabase db){
        super(db);
    }

    /**
     *
     * @return An ArrayList<String> with the names of all the characters
     */
    public ArrayList<String> getAllCharacterNames(){
        // Search for all the characters and store their names
        Cursor cursor = db.query("Characters", new String[]{"name"},
                null, null, null, null, null);

        ArrayList<String> names = new ArrayList<>();
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
     * @param characterName
     * @return The id of the character whose name was passed
     */
    public Integer getId(String characterName){
        // Search for ids of characters with that name (there can only be, at most, 1)
        Cursor cursor = db.query("Characters", new String[]{"_id"},
                "name = ?", new String[]{characterName},
                null, null, null);
        Integer _id = null;

        if (cursor.moveToFirst()) {
            _id = cursor.getInt(0);
        }

        cursor.close();
        return _id;
    }

    /**
     *
     * @param _id
     * @return An ArrayList<String> with the names of all the characters that have supports with
     * the character whose id is characterId
     */
    public ArrayList<String> getCharacterNamesWithSupportWith(int _id){
        Cursor cursor = db.rawQuery("SELECT c.name FROM Supports AS s JOIN Characters"
                        + " AS c ON s.character2 = c._id WHERE s.character1 = ? " +
                        "UNION SELECT c.name FROM Supports AS s JOIN Characters " +
                        "AS c ON s.character1 = c._id WHERE s.character2 = ?",
                new String[]{Integer.toString(_id), Integer.toString(_id)});

        ArrayList<String> namesCharacters2 = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                namesCharacters2.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return namesCharacters2;
    }

    /**+
     *
     * @param characterName1
     * @param characterName2
     * @return An ArrayList<String> with the supports between the two characters, or null
     * if they don't have any.
     */
    public ArrayList<String> searchSupports(String characterName1, String characterName2){
        Cursor cursor = db.rawQuery("SELECT sup.cSupport, sup.bSupport, " +
                "sup.aSupport, sup.interSupport, sup.interRank, sup.sSupport " +
                "FROM Characters AS c1, Supports AS sup, Characters AS c2 " +
                "WHERE sup.character1 = c1._id AND sup.character2 = c2._id AND " +
                "c1.name = ? AND c2.name = ?", new String[]{characterName1, characterName2});

        ArrayList<String> supports = null;

        if (cursor.moveToFirst()) {
            supports = new ArrayList<>();
            supports.add(cursor.getString(0));
            supports.add(cursor.getString(1));
            supports.add(cursor.getString(2).equals("null") ? null : cursor.getString(2));
            supports.add(cursor.getString(3).equals("null") ? null : cursor.getString(3));
            supports.add(cursor.getString(4).equals("null") ? null : cursor.getString(4));
            supports.add(cursor.getString(5).equals("null") ? null : cursor.getString(5));
        }

        cursor.close();
        return supports;
    }
}
