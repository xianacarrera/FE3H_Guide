package com.example.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAO {

    protected SQLiteDatabase db;

    protected DAO(SQLiteDatabase db){
        this.db = db;
    }

    protected SQLiteDatabase getDb(){
        return db;
    }

    protected void setDb(SQLiteDatabase db){
        this.db = db;
    }



    //////////////////////// Methods common to several DAOs /////////////////////////////////////

    /**
     *
     * @param characterName Name of the character whose portrait is to be searched
     * @return The id of their icon or null, if it wasn't a valid character
     */
    public Integer getPortrait(String characterName) {
        Cursor cursor = db.query("Characters", new String[]{"portrait"},
                "name = ?", new String[]{characterName},
                null, null, null);

        if (cursor.moveToFirst()){
            int portrait = cursor.getInt(0);
            cursor.close();
            return portrait;
        }

        cursor.close();
        return null;
    }
}
