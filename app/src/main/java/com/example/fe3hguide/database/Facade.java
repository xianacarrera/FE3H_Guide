package com.example.fe3hguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Facade {

    private SQLiteDatabase db;
    private DAOCharacters daoCharacters;
    private DAOSupports daoSupports;
    private DAOTeaTime daoTeaTime;

    public Facade(Context context){
        // Get access to the database
        SQLiteOpenHelper fe3hDatabaseHelper = new FE3HDatabaseHelper(context);
        db = fe3hDatabaseHelper.getReadableDatabase();

        daoCharacters = new DAOCharacters(db);
        daoSupports = new DAOSupports(db);
        daoTeaTime = new DAOTeaTime(db);
    }

    public void closeDatabase(){
        db.close();
    }
}
