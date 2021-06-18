package com.example.fe3hguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fe3hguide.model.Ability;
import com.example.fe3hguide.model.Character;
import com.example.fe3hguide.model.CombatArt;
import com.example.fe3hguide.model.CombatArtClassMastery;
import com.example.fe3hguide.model.InGameClass;
import com.example.fe3hguide.model.Spell;
import com.example.fe3hguide.model.TeaTimeInfo;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    // Uses the Singleton pattern
    private static Facade instance;

    private static SQLiteDatabase db;
    private static DAOCharacters daoCharacters;
    private static DAOClasses daoClasses;
    private static DAOSupports daoSupports;
    private static DAOTeaTime daoTeaTime;

    private Facade(){
    }

    public static Facade getInstance(Context context){
        if (instance == null) {         // Initialice the instance
            // Get access to the database
            SQLiteOpenHelper fe3hDatabaseHelper = new FE3HDatabaseHelper(context);
            db = fe3hDatabaseHelper.getReadableDatabase();

            daoClasses = new DAOClasses(db);
            daoCharacters = new DAOCharacters(db);
            daoSupports = new DAOSupports(db);
            daoTeaTime = new DAOTeaTime(db);

            instance = new Facade();
        }
        return instance;
    }

    public void closeDatabase(){
        db.close();
    }

    /** Methods common to several DAOs **/
    public Integer getPortrait(String characterName){
        return daoTeaTime.getPortrait(characterName);
    }

    /** DAOCharacters methods **/
    public Character getCharacter(String characterName){ return daoCharacters.getCharacter(characterName); }

    public List<Ability> getAllAbilities(){ return daoCharacters.getAllAbilities(); }

    public List<Ability> getSkillLevelAbilities(){ return daoCharacters.getSkillLevelAbilities(); }

    public List<Ability> getClassAbilities(){ return daoCharacters.getClassAbilities(); }

    public List<Ability> getClassMasteryAbilities(){
        return daoCharacters.getClassMasteryAbilities();
    }

    public List<Ability> getOtherAbilities(){ return daoCharacters.getOtherAbilities(); }

    public List<Ability> getUniqueAbilities(String characterName){
        return daoCharacters.getUniqueAbilities(characterName);
    }

    public List<Ability> getNotUniqueAbilities(){ return daoCharacters.getNotUniqueAbilities(); }


    public List<CombatArt> getUniqueCombatArts(String characterName){
        return daoCharacters.getUniqueCombatArts(characterName);
    }

    public List<CombatArt> getNotUniqueCombatArts(boolean prof,
                                                  boolean exclusive,
                                                  boolean classMastery, boolean other){
        return daoCharacters.getNotUniqueCombatArts(prof, exclusive, classMastery, other);
    }

    public List<List<Spell>> getSpells(String characterName){ return daoCharacters.getSpells(characterName); }

    public Spell getSpell(String spellName){ return daoCharacters.getSpell(spellName); }

    /** DAOClasses methods **/
    public List<InGameClass> getClasses(){ return daoClasses.getClasses(); }

    public InGameClass getInGameClass(String name){ return daoClasses.getInGameClass(name); }

    public List<InGameClass> getFemaleClasses(){ return daoClasses.getFemaleClasses(); }

    public List<InGameClass> getMaleClasses(){ return daoClasses.getMaleClasses(); }

    public List<InGameClass> getCharacterOnlyClasses(String characterName){
        return daoClasses.getCharacterOnlyClasses(characterName);
    }

    public List<InGameClass> getNonExclusiveClasses(){ return daoClasses.getNonExclusiveClasses();}

    public Ability getAbility(String abilityName){ return daoClasses.getAbility(abilityName); }

    public CombatArtClassMastery getCombatArtClassMastery(String combatArtName){
        return daoClasses.getMasteryCombatArt(combatArtName);
    }


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
