package com.example.fe3hguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


/**
 * DATABASE SCHEMA:
 * <p>
 * CHARACTERS(_id, NAME, PORTRAIT)
 * PK: _id
 * <p>
 * FAVOURITE_TEAS(_id, TEA)
 * PK: _id, TEA
 * FK: _id references CHARACTERS(_id)
 * <p>
 * TOPICS(_id, TOPIC)
 * PK: _id, TOPIC
 * FK: _id references CHARACTERS(_id)
 * <p>
 * FINAL_CONVERSATIONS(_id, CONVERSATION, OPTION_1, OPTION_2, OPTION_3)
 * PK : _id, CONVERSATION
 * FK: _id references CHARACTERS(_id)
 * <p>
 * SUPPORTS(CHARACTER_1, CHARACTER_2, C_SUPPORT, B_SUPPORT, A_SUPPORT, INTER_SUPPORT,
 * INTER_RANK, S_SUPPORT)
 * PK: CHARACTER_1, CHARACTER_2
 * FK: CHARACTER_1 references CHARACTERS(_id)
 * FK: CHARACTER_2 references CHARACTERS(_id)
 * <p>
 * MAGIC(_id, SKILL_LEVEL, REASON, FAITH)
 * PK: _id, SKILL_LEVEL
 * FK: _id references CHARACTERS(_id)
 *
 * CHARACTER_GIFTS(_id, gift, liked, rank)
 *      PK: _id, gift
 *      FK: _id references CHARACTERS(_id)
 *
 * CHARACTER_MEALS(_id, meal, liked)
 *      PK: _id, meal
 *      FK: _id references CHARACTERS(_id)
 *
 * CHARACTER_LOST_ITEMS(_id, item)
 *      PK: _id, item
 *      FK: _id references CHARACTERS(_id)
 */


public class FE3HDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DB_NAME = "fe3h";       // name of the database
    private static final int DB_VERSION = 1;            // version of the database

    public FE3HDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createCharactersTable(db);
        createMagicTable(db);
        createCharacterGiftsTable(db);
        createCharacterMealsTable(db);
        createCharacterLostItemsTable(db);
        createFavouriteTeasTable(db);
        createTopicsTable(db);
        createFinalConversationsTable(db);
        createSupportsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createCharactersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CHARACTERS ( "
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "PORTRAIT INTEGER);");

        insertCharacters(db);
    }

    public static void insertCharacters(SQLiteDatabase db){
        insertCharacter(db, "Alois", R.drawable.ic_alois);
        insertCharacter(db, "Anna", R.drawable.ic_anna);
        insertCharacter(db, "Annette", R.drawable.ic_annette);
        insertCharacter(db, "Ashe", R.drawable.ic_ashe);
        insertCharacter(db, "Balthus", R.drawable.ic_balthus);
        insertCharacter(db, "Bernadetta", R.drawable.ic_bernadetta);
        insertCharacter(db, "Caspar", R.drawable.ic_caspar);
        insertCharacter(db, "Catherine", R.drawable.ic_catherine);
        insertCharacter(db, "Claude", R.drawable.ic_claude);
        insertCharacter(db, "Constance", R.drawable.ic_constance);
        insertCharacter(db, "Cyril", R.drawable.ic_cyril);
        insertCharacter(db, "Dedue", R.drawable.ic_dedue);
        insertCharacter(db, "Dimitri", R.drawable.ic_dimitri);
        insertCharacter(db, "Dorothea", R.drawable.ic_dorothea);
        insertCharacter(db, "Edelgard", R.drawable.ic_edelgard);
        insertCharacter(db, "Felix", R.drawable.ic_felix);
        insertCharacter(db, "Ferdinand", R.drawable.ic_ferdinand);
        insertCharacter(db, "Flayn", R.drawable.ic_flayn);
        insertCharacter(db, "Gilbert", R.drawable.ic_gilbert);
        insertCharacter(db, "Hanneman", R.drawable.ic_hanneman);
        insertCharacter(db, "Hapi", R.drawable.ic_hapi);
        insertCharacter(db, "Hilda", R.drawable.ic_hilda);
        insertCharacter(db, "Hubert", R.drawable.ic_hubert);
        insertCharacter(db, "Ignatz", R.drawable.ic_ignatz);
        insertCharacter(db, "Ingrid", R.drawable.ic_ingrid);
        insertCharacter(db, "Jeritza", R.drawable.ic_jeritza);
        insertCharacter(db, "Leonie", R.drawable.ic_leonie);
        insertCharacter(db, "Linhardt", R.drawable.ic_linhardt);
        insertCharacter(db, "Lorenz", R.drawable.ic_lorenz);
        insertCharacter(db, "Lysithea", R.drawable.ic_lysithea);
        insertCharacter(db, "Manuela", R.drawable.ic_manuela);
        insertCharacter(db, "Marianne", R.drawable.ic_marianne);
        insertCharacter(db, "Mercedes", R.drawable.ic_mercedes);
        insertCharacter(db, "Petra", R.drawable.ic_petra);
        insertCharacter(db, "Raphael", R.drawable.ic_raphael);
        insertCharacter(db, "Rhea", R.drawable.ic_rhea);
        insertCharacter(db, "Seteth", R.drawable.ic_seteth);
        insertCharacter(db, "Shamir", R.drawable.ic_shamir);
        insertCharacter(db, "Sylvain", R.drawable.ic_sylvain);
        insertCharacter(db, "Yuri", R.drawable.ic_yuri);
        insertCharacter(db, "BylethM", R.drawable.ic_bylethm);
        insertCharacter(db, "BylethF", R.drawable.ic_bylethf);
    }

    public static void insertCharacter(SQLiteDatabase db, String name, int portraitId) {
        ContentValues characterValues = new ContentValues();
        characterValues.put("NAME", name);
        characterValues.put("PORTRAIT", portraitId);
        db.insert("CHARACTERS", null, characterValues);
    }

    private void createAbilitiesTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE ABILITIES ( " +
                "ABILITY TEXT, " +
                "ICON INTEGER, " +
                "EFFECT TEXT, " +
                "ORIGIN TEXT, " +
                "TYPE TEXT, " +
                "PRIMARY KEY (ABILITY));");

        insertAbility(db, );
    }

    private void insertAbility(SQLiteDatabase db, String ability, int icon, String effect,
                               String origin, String type){
        ContentValues abilityValues = new ContentValues();
        abilityValues.put("ABILITY", ability);
        abilityValues.put("ICON", icon);
        abilityValues.put("EFFECT", effect);
        abilityValues.put("ORIGIN", origin);
        abilityValues.put("TYPE", origin);
        db.insert("ABILITIES", null, abilityValues);
    }

    private void createMagicTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MAGIC ( "
                + "_id INTEGER, "
                + "SKILL_LEVEL TEXT, "
                + "REASON TEXT, "
                + "FAITH TEXT, "
                + "PRIMARY KEY (_id, SKILL_LEVEL),"
                + "CONSTRAINT FK_MAGIC FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.magic);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("$"));
                insertMagic(db, parts[0], parts[1], parts[2], parts[3]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertMagic(SQLiteDatabase db, String id, String skillLevel, String reason,
                             String faith) {
        ContentValues magicValues = new ContentValues();
        magicValues.put("_id", Integer.valueOf(id));
        magicValues.put("SKILL_LEVEL", skillLevel);
        magicValues.put("REASON", reason);
        magicValues.put("FAITH", faith);
        db.insert("MAGIC", null, magicValues);
    }

    private void createCharacterGiftsTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE CHARACTER_GIFTS ( "
                + "_id INTEGER, "
                + "GIFT TEXT, "
                + "LIKED INTEGER, "
                + "RANK INTEGER, "
                + "PRIMARY KEY (_id, GIFT),"
                + "CONSTRAINT FK_CHARACTER_GIFTS FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.gifts);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterGift(db, parts[0], parts[1], parts[2], parts[3]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCharacterGift(SQLiteDatabase db, String id, String gift, String liked,
                            String rank){
        ContentValues giftsValues = new ContentValues();
        giftsValues.put("_id", Integer.valueOf(id));
        giftsValues.put("GIFT", gift);
        giftsValues.put("LIKED", liked.equals("y") ? 1 : 0);
        giftsValues.put("RANK", Integer.valueOf(rank));
        db.insert("CHARACTER_GIFTS", null, giftsValues);
    }

    private void createCharacterMealsTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE CHARACTER_MEALS ( "
                + "_id INTEGER, "
                + "MEAL TEXT, "
                + "LIKED INTEGER, "
                + "PRIMARY KEY (_id, MEAL),"
                + "CONSTRAINT FK_CHARACTER_MEALS FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.meals);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterMeal(db, parts[0], parts[1], parts[2]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCharacterMeal(SQLiteDatabase db, String id, String meal, String liked){
        ContentValues mealValues = new ContentValues();
        mealValues.put("_id", Integer.valueOf(id));
        mealValues.put("MEAL", meal);
        mealValues.put("LIKED", liked.equals("y") ? 1 : 0);
        db.insert("CHARACTER_MEALS", null, mealValues);
    }

    private void createCharacterLostItemsTable(SQLiteDatabase db){
        db.execSQL("CREATE TABLE CHARACTER_LOST_ITEMS ( "
                + "_id INTEGER, "
                + "ITEM TEXT, "
                + "PRIMARY KEY (_id, ITEM),"
                + "CONSTRAINT FK_CHARACTER_LOST_ITEMS FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lost_items);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                insertCharacterLostItem(db, parts[0], parts[1]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCharacterLostItem(SQLiteDatabase db, String id, String item){
        ContentValues lostItemValues = new ContentValues();
        lostItemValues.put("_id", Integer.valueOf(id));
        lostItemValues.put("ITEM", item);
        db.insert("CHARACTER_LOST_ITEMS", null, lostItemValues);
    }

    private void createFavouriteTeasTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FAVOURITE_TEAS ( "
                + "_id INTEGER, "
                + "TEA TEXT, "
                + "PRIMARY KEY (_id, TEA),"
                + "CONSTRAINT FK_FAVOURITE_TEAS FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.favourite_teas);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertFavouriteTea(db, parts[0], parts[1]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertFavouriteTea(SQLiteDatabase db, String id, String tea) {
        ContentValues favouriteTeaValues = new ContentValues();
        favouriteTeaValues.put("_id", Integer.valueOf(id));
        favouriteTeaValues.put("TEA", tea);
        db.insert("FAVOURITE_TEAS", null, favouriteTeaValues);
    }

    private void createTopicsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TOPICS ( "
                + "_id INTEGER, "
                + "TOPIC TEXT, "
                + "PRIMARY KEY (_id, TOPIC),"
                + "CONSTRAINT FK_TOPICS FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.topics);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertTopic(db, parts[0], parts[1]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertTopic(SQLiteDatabase db, String id, String topic) {
        ContentValues topicValues = new ContentValues();
        topicValues.put("_id", Integer.valueOf(id));
        topicValues.put("TOPIC", topic);
        db.insert("TOPICS", null, topicValues);
    }

    private void createFinalConversationsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FINAL_CONVERSATIONS ( "
                + "_id INTEGER, "
                + "CONVERSATION TEXT, "
                + "OPTION_1 TEXT, "
                + "OPTION_2 TEXT, "
                + "OPTION_3 TEXT, "
                + "PRIMARY KEY (_id, CONVERSATION),"
                + "CONSTRAINT FK_FINAL_CONVERSATIONS FOREIGN KEY (_id) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.final_conversations);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertFinalConversation(db, parts[0], parts[1], parts[2], parts[3], parts[4]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertFinalConversation(SQLiteDatabase db, String id, String conversation,
                                               String option1, String option2, String option3) {
        ContentValues conversationValues = new ContentValues();
        conversationValues.put("_id", Integer.valueOf(id));
        conversationValues.put("CONVERSATION", conversation);
        conversationValues.put("OPTION_1", option1);
        conversationValues.put("OPTION_2", option2);
        conversationValues.put("OPTION_3", option3);
        db.insert("FINAL_CONVERSATIONS", null, conversationValues);
    }

    private void createSupportsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SUPPORTS ( "
                + "CHARACTER_1 INTEGER, "
                + "CHARACTER_2 INTEGER, "
                + "C_SUPPORT TEXT, "
                + "B_SUPPORT TEXT, "
                + "A_SUPPORT TEXT, "
                + "INTER_SUPPORT TEXT, "
                + "INTER_RANK TEXT, "
                + "S_SUPPORT TEXT, "
                + "PRIMARY KEY (CHARACTER_1, CHARACTER_2),"
                + "CONSTRAINT FK_SUPPORTS_1 FOREIGN KEY (CHARACTER_1) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE, "
                + "CONSTRAINT FK_SUPPORTS_2 FOREIGN KEY (CHARACTER_2) REFERENCES CHARACTERS(_id) "
                + "ON DELETE NO ACTION ON UPDATE CASCADE);");

        insertAloisSupports(db);
        insertAnnetteSupports(db);
        insertAsheSupports(db);
        insertBalthusSupports(db);
        insertBernadettaSupports(db);
        insertCasparSupports(db);
        insertCatherineSupports(db);
        insertClaudeSupports(db);
        insertConstanceSupports(db);
        insertCyrilSupports(db);
        insertDedueSupports(db);
        insertDimitriSupports(db);
        insertDorotheaSupports(db);
        insertEdelgardSupports(db);
        insertFelixSupports(db);
        insertFerdinandSupports(db);
        insertFlaynSupports(db);
        insertGilbertSupports(db);
        insertHannemanSupports(db);
        insertHapiSupports(db);
        insertHildaSupports(db);
        insertHubertSupports(db);
        insertIgnatzSupports(db);
        insertIngridSupports(db);
        insertJeritzaSupports(db);
        insertLeonieSupports(db);
        insertLinhardtSupports(db);
        insertLorenzSupports(db);
        insertLysitheaSupports(db);
        insertManuelaSupports(db);
        insertMarianneSupports(db);
        insertMercedesSupports(db);
        insertPetraSupports(db);
        insertRaphaelSupports(db);
        insertRheaSupports(db);
        insertSetethSupports(db);
        insertShamirSupports(db);
        insertSylvainSupports(db);
        insertYuriSupports(db);
    }

    private void insertSupport(SQLiteDatabase db, String character1, String character2,
                                     String cSupport, String bSupport, String aSupport, String interSup,
                                     String interRank, String sSupport) {
        ContentValues supportValues = new ContentValues();
        supportValues.put("CHARACTER_1", Integer.valueOf(character1));
        supportValues.put("CHARACTER_2", Integer.valueOf(character2));
        supportValues.put("C_SUPPORT", cSupport);
        supportValues.put("B_SUPPORT", bSupport);
        supportValues.put("A_SUPPORT", aSupport);
        supportValues.put("INTER_SUPPORT", interSup);
        supportValues.put("INTER_RANK", interRank);
        supportValues.put("S_SUPPORT", sSupport);
        db.insert("SUPPORTS", null, supportValues);
    }

    private void insertAloisSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.alois_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertAnnetteSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.annette_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertAsheSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ashe_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertBalthusSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.balthus_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertBernadettaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.bernadetta_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCasparSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.caspar_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCatherineSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.catherine_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertClaudeSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.claude_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertConstanceSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.constance_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertCyrilSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.cyril_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertDedueSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.dedue_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertDimitriSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.dimitri_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertDorotheaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.dorothea_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertEdelgardSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.edelgard_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertFelixSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.edelgard_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertFerdinandSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ferdinand_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertFlaynSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.flayn_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertGilbertSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.gilbert_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertHannemanSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hanneman_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertHapiSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hapi_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertHildaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hilda_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertHubertSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.hubert_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertIgnatzSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ignatz_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertIngridSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.ingrid_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertJeritzaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.jeritza_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertLeonieSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.leonie_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertLinhardtSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.linhardt_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertLorenzSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lorenz_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertLysitheaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.lysithea_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertManuelaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.manuela_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertMarianneSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.marianne_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertMercedesSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.mercedes_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertPetraSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.petra_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertRaphaelSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.raphael_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertRheaSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.rhea_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertSetethSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.seteth_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertShamirSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.shamir_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertSylvainSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.sylvain_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertYuriSupports(SQLiteDatabase db){
        String line = null;
        InputStream is = context.getResources().openRawResource(R.raw.yuri_supports);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(Pattern.quote("_"));
                insertSupport(db, parts[0], parts[1], parts[2], parts[3], parts[4],
                        parts[5], parts[6], parts[7]);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
