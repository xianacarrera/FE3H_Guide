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
    private HashMap<String, String> baseStats;     // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private int baseLevel;
    private HashMap<String, String> growthRates;    // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<String, String> skills;
    // Sword, Lance, Axe, Bow, Brawling, Reason, Faith, Authority, Heavy_Armor, Riding, Flying

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

    public HashMap<String, String> getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(HashMap<String, String> baseStats) {
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
            String[] dividedBirthday = fodlanBirthday.split("_");
            StringBuilder sb = new StringBuilder(dividedBirthday[0]);
            for (int i = 1; i < dividedBirthday.length; i++){
                sb.append("\n").append(dividedBirthday[i]);
            }
            character.fodlanBirthday = sb.toString();
            return this;
        }

        public Builder withCrest(String crest){
            String[] dividedCrests = crest.split("_");
            StringBuilder sb = new StringBuilder(dividedCrests[0]);
            for (int i = 1; i < dividedCrests.length; i++){
                sb.append("\n").append(dividedCrests[i]);
            }
            character.crest = sb.toString();
            return this;
        }

        public Builder withBaseStats(String hp, String str, String mag, String dex, String spd,
                                     String lck, String def, String res, String cha){
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

        public Builder withBaseStats(HashMap<String, String> baseStats){
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

        public Builder withSkills(String sword, String lance, String axe, String bow,
                                  String brawling, String reason, String faith, String authority,
                                  String heavyArmor, String riding, String flying){
            character.skills = new HashMap<>();
            character.skills.put("Sword", sword);
            character.skills.put("Lance", lance);
            character.skills.put("Axe", axe);
            character.skills.put("Bow", bow);
            character.skills.put("Brawling", brawling);
            character.skills.put("Reason", reason);
            character.skills.put("Faith", faith);
            character.skills.put("Authority", authority);
            character.skills.put("Heavy_Armor", heavyArmor);
            character.skills.put("Riding", riding);
            character.skills.put("Flying", flying);
            return this;
        }

        public Builder withSkills(HashMap<String, String> skills){
            character.skills = skills;
            return this;
        }

        public Character build(){ return character; }
    }
}
