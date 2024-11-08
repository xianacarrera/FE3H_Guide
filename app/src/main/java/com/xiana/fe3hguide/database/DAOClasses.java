package com.xiana.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiana.fe3hguide.model.Ability;
import com.xiana.fe3hguide.model.CombatArtClassMastery;
import com.xiana.fe3hguide.model.InGameClass;

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
                        withAbilities(abilities).
                        withMasteryAbility(getAbility(cursor.getString(6))).
                        withMasteryCombatArt(getMasteryCombatArt(cursor.getString(7))).
                        withCanUse(cursor.getString(8)).
                        withRestrictions(cursor.getString(9)).
                        withCertificationRequirement(cursor.getString(10)).
                        withSeal(cursor.getString(11)).
                        withExperience(cursor.getInt(12)).
                        withIcon(cursor.getInt(13)).
                        withGrowthRates(cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19),
                                cursor.getString(20), cursor.getString(21),
                                cursor.getString(22)).
                        build());
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
            abilities.add(getAbility(cursor.getString(3)));
            abilities.add(getAbility(cursor.getString(4)));
            abilities.add(getAbility(cursor.getString(5)));

            inGameClass = new InGameClass.Builder(cursor.getString(0)).withClassLevel(
                    cursor.getString(1)).withProficiencies(cursor.getString(2)).
                    withAbilities(abilities).
                    withMasteryAbility(getAbility(cursor.getString(6))).
                    withMasteryCombatArt(getMasteryCombatArt(cursor.getString(7))).
                    withCanUse(cursor.getString(8)).
                    withRestrictions(cursor.getString(9)).
                    withCertificationRequirement(cursor.getString(10)).
                    withSeal(cursor.getString(11)).
                    withExperience(cursor.getInt(12)).
                    withIcon(cursor.getInt(13)).
                    withGrowthRates(cursor.getString(14), cursor.getString(15),
                            cursor.getString(16), cursor.getString(17),
                            cursor.getString(18), cursor.getString(19),
                            cursor.getString(20), cursor.getString(21),
                            cursor.getString(22)).
                    build();
        }

        cursor.close();
        return inGameClass;
    }

    /**
     *
     * @return Classes available to female units
     */
    public List<InGameClass> getFemaleClasses(){
        Cursor cursor = db.rawQuery("SELECT * FROM Classes " +
                "WHERE restrictions like ? or restrictions like ?",
                new String[]{"No character restriction", "Female only"});

        ArrayList<InGameClass> classes = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ArrayList<Ability> abilities = new ArrayList<>();
                abilities.add(new Ability.Builder(cursor.getString(3)).build());
                abilities.add(new Ability.Builder(cursor.getString(4)).build());
                abilities.add(new Ability.Builder(cursor.getString(5)).build());

                classes.add(new InGameClass.Builder(cursor.getString(0)).withClassLevel(
                        cursor.getString(1)).withProficiencies(cursor.getString(2)).
                        withAbilities(abilities).
                        withMasteryAbility(getAbility(cursor.getString(6))).
                        withMasteryCombatArt(getMasteryCombatArt(cursor.getString(7))).
                        withCanUse(cursor.getString(8)).
                        withRestrictions(cursor.getString(9)).
                        withCertificationRequirement(cursor.getString(10)).
                        withSeal(cursor.getString(11)).
                        withExperience(cursor.getInt(12)).
                        withIcon(cursor.getInt(13)).
                        withGrowthRates(cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19),
                                cursor.getString(20), cursor.getString(21),
                                cursor.getString(22)).
                        build());
            } while(cursor.moveToNext());
        }

        cursor.close();
        return classes;
    }

    /**
     *
     * @return Classes available to male classes
     */
    public List<InGameClass> getMaleClasses(){
        Cursor cursor = db.rawQuery("SELECT * FROM Classes " +
                        "WHERE restrictions like ? or restrictions like ?",
                new String[]{"No character restriction", "Male only"});

        ArrayList<InGameClass> classes = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ArrayList<Ability> abilities = new ArrayList<>();
                abilities.add(new Ability.Builder(cursor.getString(3)).build());
                abilities.add(new Ability.Builder(cursor.getString(4)).build());
                abilities.add(new Ability.Builder(cursor.getString(5)).build());

                classes.add(new InGameClass.Builder(cursor.getString(0)).withClassLevel(
                        cursor.getString(1)).withProficiencies(cursor.getString(2)).
                        withAbilities(abilities).
                        withMasteryAbility(getAbility(cursor.getString(6))).
                        withMasteryCombatArt(getMasteryCombatArt(cursor.getString(7))).
                        withCanUse(cursor.getString(8)).
                        withRestrictions(cursor.getString(9)).
                        withCertificationRequirement(cursor.getString(10)).
                        withSeal(cursor.getString(11)).
                        withExperience(cursor.getInt(12)).
                        withIcon(cursor.getInt(13)).
                        withGrowthRates(cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19),
                                cursor.getString(20), cursor.getString(21),
                                cursor.getString(22)).
                        build());
            } while(cursor.moveToNext());
        }

        cursor.close();
        return classes;
    }

    /**
     *
     * @param characterName
     * @return List of classes exclusive to a character
     */
    public List<InGameClass> getCharacterOnlyClasses(String characterName){
        Cursor cursor = db.rawQuery("SELECT * FROM Classes " +
                        "WHERE restrictions like ?",
                new String[]{characterName + " only"});

        ArrayList<InGameClass> classes = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                ArrayList<Ability> abilities = new ArrayList<>();
                abilities.add(new Ability.Builder(cursor.getString(3)).build());
                abilities.add(new Ability.Builder(cursor.getString(4)).build());
                abilities.add(new Ability.Builder(cursor.getString(5)).build());

                classes.add(new InGameClass.Builder(cursor.getString(0)).withClassLevel(
                        cursor.getString(1)).withProficiencies(cursor.getString(2)).
                        withAbilities(abilities).
                        withMasteryAbility(getAbility(cursor.getString(6))).
                        withMasteryCombatArt(getMasteryCombatArt(cursor.getString(7))).
                        withCanUse(cursor.getString(8)).
                        withRestrictions(cursor.getString(9)).
                        withCertificationRequirement(cursor.getString(10)).
                        withSeal(cursor.getString(11)).
                        withExperience(cursor.getInt(12)).
                        withIcon(cursor.getInt(13)).
                        withGrowthRates(cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19),
                                cursor.getString(20), cursor.getString(21),
                                cursor.getString(22)).
                        build());
            } while(cursor.moveToNext());
        }

        cursor.close();
        return classes;
    }

    /**
     *
     * @return List of classes not exclusive to a particular character (but gender exclusive
     * classes are included in the result).
     */
    public List<InGameClass> getNonExclusiveClasses(){
        Cursor cursor = db.rawQuery("SELECT * FROM Classes " +
                        "WHERE restrictions like ? " +
                        "or restrictions like ? " +
                        "or restrictions like ?",
                new String[]{"No character restriction", "Female only", "Male only"});

        ArrayList<InGameClass> classes = new ArrayList<>();
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
                        withMasteryCombatArt(new CombatArtClassMastery
                                .Builder(cursor.getString(7)).build()).
                        withCanUse(cursor.getString(8)).
                        withRestrictions(cursor.getString(9)).
                        withCertificationRequirement(cursor.getString(10)).
                        withSeal(cursor.getString(11)).
                        withExperience(cursor.getInt(12)).
                        withIcon(cursor.getInt(13)).
                        withGrowthRates(cursor.getString(14), cursor.getString(15),
                                cursor.getString(16), cursor.getString(17),
                                cursor.getString(18), cursor.getString(19),
                                cursor.getString(20), cursor.getString(21),
                                cursor.getString(22)).
                        build());
            } while(cursor.moveToNext());
        }

        cursor.close();
        return classes;
    }

    public Ability getAbility(String abilityName){
        Cursor cursor = db.rawQuery("SELECT * FROM Abilities " +
                "WHERE ability = ?", new String[] {abilityName});

        if (cursor.moveToFirst()){
            return new Ability.Builder(abilityName)
                    .withIcon(cursor.getInt(1))
                    .withEffect(cursor.getString(2))
                    .withOrigin(cursor.getString(3))
                    .withType(cursor.getString(4))
                    .build();
        }

        return null;
    }

    public CombatArtClassMastery getMasteryCombatArt(String name){
        Cursor cursor = db.rawQuery("SELECT * FROM CombatArtsClassMastery " +
                "WHERE art = ?", new String[]{name});

        if (cursor.moveToFirst()){
            return new CombatArtClassMastery.Builder(name)
                    .withEffect(cursor.getString(1))
                    .withWeapon(cursor.getString(2))
                    .withClass(cursor.getString(3))
                    .withStats(cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7),
                            cursor.getString(8), cursor.getString(9))
                    .build();
        }
        return null;
    }
}
