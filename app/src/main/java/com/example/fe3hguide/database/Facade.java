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

    /** Methods common to several DAOs **/
    public Integer getPortrait(String characterName){
        return daoTeaTime.getPortrait(characterName);
    }

    /** DAOClasses methods **/
    public List<InGameClass> getClasses(){ return daoClasses.getClasses(); }

    public InGameClass getInGameClass(String name){ return daoClasses.getInGameClass(name); }

    /** DAOSupports methods **/
    public ArrayList<String> getAllNames(){ return daoSupports.getAllCharacterNames(); }

    public Integer getId(String characterName){ return daoSupports.getId(characterName); }

    public ArrayList<String> getCharacterNamesWithSupportWith(int _id){
        return daoSupports.getCharacterNamesWithSupportWith(_id);
    }

    public ArrayList<String> searchSupports(String characterName1, String characterName2){
        return daoSupports.searchSupports(characterName1, characterName2);
    }

    /** DAOTeaTime methods **/
    public ArrayList<String> getAllNamesButByleth(){
        return daoTeaTime.getAllNamesButByleth();
    }

    public TeaTimeInfo getTeaTimeInfo(String character){
        return daoTeaTime.getTeaTimeInfo(character);
    }
}
