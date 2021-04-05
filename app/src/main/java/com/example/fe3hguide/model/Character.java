package com.example.fe3hguide.model;

import java.util.HashMap;

public class Character {

    private int id;
    private String name;
    private int portrait;
    private String pronouns;
    private String faction;
    private int age;
    private String birthday;
    private String fodlanBirthday;
    private String crest;
    private HashMap<String, Integer> baseStats;     // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private int baseLevel;
    private HashMap<String, String> growthRates;    // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<String, String> skills;
    // Sword, Lance, Axe, Bow, Brawling, Reason, Faith, Authority, HeavyArmor, Riding, Flying

    private Character(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFodlanBirthday() {
        return fodlanBirthday;
    }

    public void setFodlanBirthday(String fodlanBirthday) {
        this.fodlanBirthday = fodlanBirthday;
    }

    public String getCrest() {
        return crest;
    }

    public void setCrest(String crest) {
        this.crest = crest;
    }

    public HashMap<String, Integer> getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(HashMap<String, Integer> baseStats) {
        this.baseStats = baseStats;
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public void setBaseLevel(int baseLevel) {
        this.baseLevel = baseLevel;
    }

    public HashMap<String, String> getGrowthRates() {
        return growthRates;
    }

    public void setGrowthRates(HashMap<String, String> growthRates) {
        this.growthRates = growthRates;
    }

    public HashMap<String, String> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<String, String> skills) {
        this.skills = skills;
    }

    public static class Builder{
        private final Character character = new Character();

        public Builder(int id){
            character.id = id;
        }

        public Builder withName(String name){
            character.name = name;
            return this;
        }

        public Builder withPortrait(int portrait){
            character.portrait = portrait;
            return this;
        }

        public Builder withPronouns(String pronouns) {
           character.pronouns = pronouns;
           return this;
        }

        public Builder withFaction(String faction){
            character.faction = faction;
            return this;
        }

        public Builder withAge(int age){
            character.age = age;
            return this;
        }

        public Builder withBirthday(String birthday){
            character.birthday = birthday;
            return this;
        }

        public Builder withFodlanBirthday(String fodlanBirthday){
            character.fodlanBirthday = fodlanBirthday;
            return this;
        }

        public Builder withCrest(String crest){
            character.crest = crest;
            return this;
        }

        public Builder withBaseStats(int hp, int str, int mag, int dex, int spd, int lck,
                                     int def, int res, int cha){
            character.baseStats = new HashMap<>();
            character.baseStats.put("HP", hp);
            character.baseStats.put("Str", str);
            character.baseStats.put("Mag", mag);
            character.baseStats.put("Dex", dex);
            character.baseStats.put("Spd", spd);
            character.baseStats.put("Lck", lck);
            character.baseStats.put("Def", def);
            character.baseStats.put("Res", res);
            character.baseStats.put("Cha", cha);
            return this;
        }

        public Builder withBaseStats(HashMap<String, Integer> baseStats){
            character.baseStats = baseStats;
            return this;
        }

        public Builder withBaseLevel(int baseLevel){
            character.baseLevel = baseLevel;
            return this;
        }

        public Builder withGrowthRates(String hp, String str, String mag, String dex, String spd,
                                       String lck, String def, String res, String cha){
            character.growthRates = new HashMap<>();
            character.growthRates.put("HP", hp);
            character.growthRates.put("Str", str);
            character.growthRates.put("Mag", mag);
            character.growthRates.put("Dex", dex);
            character.growthRates.put("Spd", spd);
            character.growthRates.put("Lck", lck);
            character.growthRates.put("Def", def);
            character.growthRates.put("Res", res);
            character.growthRates.put("Cha", cha);
            return this;
        }

        public Builder withGrowthRates(HashMap<String, String> growthRates){
            character.growthRates = growthRates;
            return this;
        }

        private Character build(){ return character; }
    }
}
