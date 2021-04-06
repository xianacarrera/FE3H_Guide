package com.example.fe3hguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fe3hguide.model.InGameClass;
import com.example.fe3hguide.model.TeaTimeInfo;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    private SQLiteDatabase db;
    private DAOCharacters daoCharacters;
    private DAOClasses daoClasses;
    private DAOSupports daoSupports;
    private DAOTeaTime daoTeaTime;

    public Facade(Context context){
        // Get access to the database
        SQLiteOpenHelper fe3hDatabaseHelper = new FE3HDatabaseHelper(context);
        db = fe3hDatabaseHelper.getReadableDatabase();

        daoClasses = new DAOClasses(db);
        daoCharacters = new DAOCharacters(db);
        daoSupports = new DAOSupports(db);
        daoTeaTime = new DAOTeaTime(db);
    }

    public void closeDatabase(){
        db.close();
    }

    /** DAOClasses methods **/
    public List<InGameClass> getClasses(){ return daoClasses.getClasses(); }

    public InGameClass getInGameClass(String name){ return daoClasses.getInGameClass(name); }

    /** DAOTeaTime methods **/
    public ArrayList<String> getAllNamesButByleth(){
        return daoTeaTime.getAllNamesButByleth();
    }

    public int getPortrait(String character) throws Exception {
        return daoTeaTime.getPortrait(character);
    }

    public TeaTimeInfo getTeaTimeInfo(String character){
        return daoTeaTime.getTeaTimeInfo(character);
    }
}
