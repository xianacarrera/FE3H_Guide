package com.example.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fe3hguide.model.Ability;
import com.example.fe3hguide.model.CombatArt;
import com.example.fe3hguide.model.InGameClass;

import java.util.ArrayList;
import java.util.List;

public class DAOClasses extends DAO {

    public DAOClasses(SQLiteDatabase db){
        super(db);
    }

    /**
     *
     * @return A list with all the classes of the game and their respective information
     */
    public List<InGameClass> getClasses(){
        Cursor cursor = db.rawQuery("SELECT * FROM Classes", new String[]{});

        List<InGameClass> classes = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ArrayList<Ability> abilities = new ArrayList<>();
                abilities.add(new Ability.Builder(cursor.getString(3)).build());
                abilities.add(new Ability.Builder(cursor.getString(4)).build());
                abilities.add(new Ability.Builder(cursor.getString(5)).build());

                classes.add(new InGameClass.Builder(cursor.getString(0)).withClassLevel(
                        cursor.getString(1)).withProficiencies(cursor.getString(2)).
                        withAbilities(abilities).withMasteryAbility(
                                new Ability.Builder(cursor.getString(6)).build()).
                        withMasteryCombatArt(new CombatArt.Builder(cursor.getString(7)).build()).
                        withCanUse(cursor.getString(8)).
                        withRestrictions(cursor.getString(9)).
                        withCertificationRequirement(cursor.getString(10)).
                        withSeal(cursor.getString(11)).
                        withExperience(cursor.getInt(12)).build());
            } while(cursor.moveToNext());
        }

        cursor.close();
        return classes;
    }

    /**
     *
      * @param name The name of the class to search
     * @return A complete InGameClass with all the information about that class
     */
    public InGameClass getInGameClass(String name){
        Cursor cursor = db.rawQuery("SELECT * FROM Classes WHERE name = ?",
                new String[]{name});

        InGameClass inGameClass = null;
        if (cursor.moveToFirst()){
            ArrayList<Ability> abilities = new ArrayList<>();
            abilities.add(new Ability.Builder(cursor.getString(3)).build());
            abilities.add(new Ability.Builder(cursor.getString(4)).build());
            abilities.add(new Ability.Builder(cursor.getString(5)).build());

            inGameClass = new InGameClass.Builder(cursor.getString(0)).withClassLevel(
                    cursor.getString(1)).withProficiencies(cursor.getString(2)).
                    withAbilities(abilities).withMasteryAbility(
                    new Ability.Builder(cursor.getString(6)).build()).
                    withMasteryCombatArt(new CombatArt.Builder(cursor.getString(7)).build()).
                    withCanUse(cursor.getString(8)).
                    withRestrictions(cursor.getString(9)).
                    withCertificationRequirement(cursor.getString(10)).
                    withSeal(cursor.getString(11)).
                    withExperience(cursor.getInt(12)).build()
        }

        cursor.close();
        return inGameClass;
    }
}
