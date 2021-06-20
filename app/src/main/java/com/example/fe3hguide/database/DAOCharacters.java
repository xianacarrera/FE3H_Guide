package com.example.fe3hguide.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fe3hguide.model.Ability;
import com.example.fe3hguide.model.Character;
import com.example.fe3hguide.model.CombatArt;
import com.example.fe3hguide.model.CombatArtBuddingTalent;
import com.example.fe3hguide.model.CombatArtClassMastery;
import com.example.fe3hguide.model.CombatArtOther;
import com.example.fe3hguide.model.CombatArtWeaponExclusive;
import com.example.fe3hguide.model.CombatArtWeaponProficient;
import com.example.fe3hguide.model.Spell;

import java.util.ArrayList;
import java.util.List;

public class DAOCharacters extends DAO {

    public DAOCharacters(SQLiteDatabase db){
        super(db);
    }

    public Character getCharacter(String characterName){
        Cursor cursor = db.rawQuery("SELECT * " +
                        "FROM Characters WHERE name like ?",
                new String[] {characterName + "%"});

        Character character = null;
        if (cursor.moveToFirst()){
             character = new Character.Builder(cursor.getInt(0))
                    .withName(characterName)
                    .withPortrait(cursor.getInt(2))
                    .withPronouns(cursor.getString(3))
                    .withFaction(cursor.getString(4))
                    .withAge(cursor.getInt(5))
                    .withBirthday(cursor.getString(6))
                    .withFodlanBirthday(cursor.getString(7))
                    .withCrest(cursor.getString(8))
                    .withBaseStats(cursor.getString(9), cursor.getString(10),
                            cursor.getString(11), cursor.getString(12),
                            cursor.getString(13), cursor.getString(14),
                            cursor.getString(15), cursor.getString(16),
                            cursor.getString(17))
                    .withGrowthRates(cursor.getString(18), cursor.getString(19),
                            cursor.getString(20), cursor.getString(21),
                            cursor.getString(22), cursor.getString(23),
                            cursor.getString(24), cursor.getString(25),
                            cursor.getString(26))
                    .withSkills(cursor.getString(27), cursor.getString(28),
                            cursor.getString(29), cursor.getString(30),
                            cursor.getString(31), cursor.getString(32),
                            cursor.getString(33), cursor.getString(34),
                            cursor.getString(35), cursor.getString(36),
                            cursor.getString(37))
                     .withRecruitment(cursor.getString(38))
                    .build();
        }

        return character;
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


    /***
     *
     * @param characterName
     * @return List with two elements:
     *  0 -> A list of the reason spells that the character can learn
     *  1 -> A list of the faith spells that the character can learn
     *
     */
    public List<List<Spell>> getSpells(String characterName){
        // Get the information about the character's magic spells
        // Search condition is defined as 'like ...%' to take into account both BylethM and BylethF
        Cursor cursor = db.rawQuery("SELECT m.reason, m.faith " +
                "FROM Characters AS c NATURAL JOIN Magic AS m " +
                "WHERE c.name like ?", new String[] {characterName + "%"});

        List<List<Spell>> spells = new ArrayList<>();
        spells.add(new ArrayList<Spell>());
        spells.add(new ArrayList<Spell>());

        if (cursor.moveToFirst()){
            do {
                for (int i = 0; i < 2; i++){
                    if (cursor.getString(i).equals("null")) {   // No spell
                        spells.get(i).add(null);
                    } else {
                        spells.get(i).add(getSpell(cursor.getString(i)));
                    }
                }
            } while (cursor.moveToNext());
        }

        // Close cursor and database
        cursor.close();
        return spells;
    }

    public Spell getSpell(String spellName){
        Cursor cursor = db.rawQuery("SELECT * FROM Spells WHERE spell = ?",
                new String[]{spellName});

        Spell spell = null;
        if (cursor.moveToFirst()){
            spell = new Spell.Builder(spellName)
                    .withMagicType(cursor.getString(1))
                    .withDescription(cursor.getString(2))
                    .withRank(cursor.getString(3))
                    .withUses(cursor.getString(4))
                    .withStats(cursor.getString(5), cursor.getString(6),
                            cursor.getString(7), cursor.getString(8),
                            cursor.getString(9))
                    .build();
        }

        cursor.close();
        return spell;
    }
}
