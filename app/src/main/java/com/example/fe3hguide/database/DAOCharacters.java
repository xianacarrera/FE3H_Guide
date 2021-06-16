package com.example.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fe3hguide.model.Ability;
import com.example.fe3hguide.model.CombatArt;
import com.example.fe3hguide.model.CombatArtBuddingTalent;
import com.example.fe3hguide.model.CombatArtClassMastery;
import com.example.fe3hguide.model.CombatArtOther;
import com.example.fe3hguide.model.CombatArtWeaponExclusive;
import com.example.fe3hguide.model.CombatArtWeaponProficient;

import java.util.ArrayList;
import java.util.List;

public class DAOCharacters extends DAO {

    public DAOCharacters(SQLiteDatabase db){
        super(db);
    }

    public Ability getAbility(String abilityName){
        Cursor cursor = db.rawQuery("SELECT * FROM Abilities " +
                "WHERE ability = ?", new String[] {abilityName});

        if (cursor.moveToFirst()){
            return new Ability.Builder(cursor.getString(0))
                    .withIcon(cursor.getInt(1))
                    .withEffect(cursor.getString(2))
                    .withOrigin(cursor.getString(3))
                    .withType(cursor.getString(4))
                    .build();
        }

        return null;
    }

    public List<Ability> getAllAbilities(){
        Cursor cursor = db.rawQuery("SELECT * " +
                        "FROM Abilities WHERE type NOT LIKE ?",
                new String[] {"%Unique%"});

        ArrayList<Ability> abilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                abilities.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return abilities;
    }

    public List<Ability> getSkillLevelAbilities(){
        Cursor cursor = db.rawQuery("SELECT * " +
                        "FROM Abilities WHERE type LIKE ? AND type NOT LIKE ?",
                new String[] {"%Learned%", "%Unique%"});

        ArrayList<Ability> abilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                abilities.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return abilities;
    }

    public List<Ability> getClassAbilities(){
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM Abilities WHERE type LIKE ?", new String[] {"%Class%"});

        ArrayList<Ability> abilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                abilities.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return abilities;
    }

    public List<Ability> getClassMasteryAbilities(){
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM abilities WHERE type LIKE ?", new String[] {"%Master%"});

        ArrayList<Ability> abilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                abilities.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return abilities;
    }

    public List<Ability> getOtherAbilities(){
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM Abilities WHERE type LIKE ?", new String[] {"%Other%"});

        ArrayList<Ability> abilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                abilities.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return abilities;
    }

    /**
     * Get all unique abilities for the character, which are considered to be the personal
     * ability, the learned abilities not common to all the characters and those ones
     * coming from budding talents.
     *
     * @param characterName
     * @return List of unique abilities
     */
    public List<Ability> getUniqueAbilities(String characterName){
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM Abilities WHERE origin LIKE ?", new String[] {"%" + characterName + "%"});

        ArrayList<Ability> uniqueAbilities = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                uniqueAbilities.add(ability);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return uniqueAbilities;
    }

    /**
     *
     * @return List of abilities that are not exclusive to a certain character (or group of
     * characters)
     */
    public List<Ability> getNotUniqueAbilities(){
        Cursor cursor = db.rawQuery("SELECT * " +
                "FROM Abilities WHERE type NOT LIKE ?", new String[] {"%Unique%"});

        ArrayList<Ability> defaultAbilities = new ArrayList();
        if (cursor.moveToFirst()){
            do {
                Ability ability = new Ability.Builder(cursor.getString(0))
                        .withIcon(cursor.getInt(1))
                        .withEffect(cursor.getString(2))
                        .withOrigin(cursor.getString(3))
                        .withType(cursor.getString(4))
                        .build();
                defaultAbilities.add(ability);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return defaultAbilities;
    }


    public ArrayList<CombatArt> getUniqueCombatArts(String characterName){
        Cursor cursor = db.rawQuery("SELECT art, effect, weapon, dur, mt, hit, avo, crit, " +
                        "range  FROM CombatArtsBuddingTalents WHERE character = ?",
                new String[] {characterName});

        ArrayList<CombatArt> uniqueCombatArts = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                uniqueCombatArts.add(new CombatArtBuddingTalent.Builder(cursor.getString(0))
                        .withEffect(cursor.getString(1))
                        .withWeapon(cursor.getString(2))
                        .withAssociatedCharacter(characterName)
                        .withStats(cursor.getString(3), cursor.getString(4),
                                cursor.getString(5), cursor.getString(6),
                                cursor.getString(7), cursor.getString(8))
                        .build());
            } while (cursor.moveToNext());
        }

        cursor = db.rawQuery("SELECT art, specificSkillLevel " +
                "FROM CharacterHasCombatArtWeaponProficiency " +
                "WHERE character = ?", new String[]{characterName});

        Cursor cursor2 = null;
        if (cursor.moveToFirst()){
            do {
                cursor2 = db.rawQuery("SELECT art, effect, weapon, skillLevel, dur, " +
                        "mt, hit, avo, crit, range FROM CombatArtsCharactersWeaponProficient " +
                        "WHERE art = ?", new String[] {cursor.getString(0)});
                if (cursor2.moveToFirst()){
                    uniqueCombatArts.add(new CombatArtWeaponProficient.Builder(
                            cursor.getString(0)
                    ).withEffect(cursor2.getString(1))
                            .withWeapon(cursor2.getString(2))
                            .withSkillLevel(cursor2.getString(3))
                            .withStats(cursor2.getString(4), cursor2.getString(5),
                                    cursor2.getString(6), cursor2.getString(7),
                                    cursor2.getString(8), cursor2.getString(9))
                            .build());
                }
            } while (cursor.moveToNext());
        }

        if (cursor2 != null) {
            cursor2.close();
        }
        cursor.close();

        return uniqueCombatArts;
    }

    public ArrayList<CombatArt> getNotUniqueCombatArts(boolean prof, boolean exclusive,
                                                              boolean classMastery, boolean other){
        Cursor cursor = null;
        ArrayList<CombatArt> combatArts = new ArrayList<>();
        if (prof){
            cursor = db.rawQuery("SELECT art, effect, weapon, skillLevel, dur, " +
                            "mt, hit, avo, crit, range " +
                            "FROM CombatArtsAllWeaponProficient ",
                    new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArtWeaponProficient.Builder(cursor.getString(0))
                            .withEffect(cursor.getString(1))
                            .withWeapon(cursor.getString(2))
                            .withSkillLevel(cursor.getString(3))
                            .withStats(cursor.getString(4),
                                    cursor.getString(5), cursor.getString(6),
                                    cursor.getString(7), cursor.getString(8),
                                    cursor.getString(9))
                            .build());
                } while (cursor.moveToNext());
            }
        }
        if (exclusive){
            cursor = db.rawQuery("SELECT art, effect, weapon, crest, dur, " +
                    "mt, hit, avo, crit, range " +
                    "FROM CombatArtsWeaponExclusive", new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArtWeaponExclusive.Builder(cursor.getString(0))
                            .withEffect(cursor.getString(1))
                            .withWeapon(cursor.getString(2))
                            .withCrest(cursor.getString(3))
                            .withStats(cursor.getString(4),
                                    cursor.getString(5), cursor.getString(6),
                                    cursor.getString(7), cursor.getString(8),
                                    cursor.getString(9))
                            .build());
                } while (cursor.moveToNext());
            }
        }
        if (classMastery){
            cursor = db.rawQuery("SELECT art, effect, weapon, class, dur, " +
                    "mt, hit, avo, crit, range " +
                    "FROM CombatArtsClassMastery", new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArtClassMastery.Builder(cursor.getString(0))
                            .withEffect(cursor.getString(1))
                            .withWeapon(cursor.getString(2))
                            .withClass(cursor.getString(3))
                            .withStats(cursor.getString(4),
                                    cursor.getString(5), cursor.getString(6),
                                    cursor.getString(7), cursor.getString(8),
                                    cursor.getString(9))
                            .build());
                } while (cursor.moveToNext());
            }
        }
        if (other){
            cursor = db.rawQuery("SELECT art, effect, weapon, origin, dur, " +
                    "mt, hit, avo, crit, range " +
                    "FROM CombatArtsOther", new String[] {});

            if (cursor.moveToFirst()){
                do {
                    combatArts.add(new CombatArtOther.Builder(cursor.getString(0))
                            .withEffect(cursor.getString(1))
                            .withWeapon(cursor.getString(2))
                            .withOrigin(cursor.getString(3))
                            .withStats(cursor.getString(4),
                                    cursor.getString(5), cursor.getString(6),
                                    cursor.getString(7), cursor.getString(8),
                                    cursor.getString(9))
                            .build());
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        return combatArts;
    }
}
