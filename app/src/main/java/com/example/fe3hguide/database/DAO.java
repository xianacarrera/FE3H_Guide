package com.example.fe3hguide.database;

import android.database.sqlite.SQLiteDatabase;

public abstract class DAO {

    private SQLiteDatabase db;

    protected DAO(SQLiteDatabase db){
        this.db = db;
    }

    protected SQLiteDatabase getDb(){
        return db;
    }

    protected void setDb(SQLiteDatabase db){
        this.db = db;
    }
}
