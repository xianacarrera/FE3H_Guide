package com.xiana.fe3hguide.model;

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
    private HashMap<Stat, String> baseStats;     // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private int baseLevel;
    private HashMap<Stat, String> growthRates;    // HP, Str, Mag, Dex, Spd, Lck, Def, Res, Cha
    private HashMap<Skill, String> skills;
    private String recruitment;
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

    public HashMap<Stat, String> getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(HashMap<Stat, String> baseStats) {
        this.baseStats = baseStats;
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public void setBaseLevel(int baseLevel) {
        this.baseLevel = baseLevel;
    }

    public HashMap<Stat, String> getGrowthRates() {
        return growthRates;
    }

    public void setGrowthRates(HashMap<Stat, String> growthRates) {
        this.growthRates = growthRates;
    }

    public HashMap<Skill, String> getSkills() {
        return skills;
    }

    public void setSkills(HashMap<Skill, String> skills) {
        this.skills = skills;
    }

    public String getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(String recruitment) {
        this.recruitment = recruitment;
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
            character.baseStats.put(Stat.HP, hp);
            character.baseStats.put(Stat.Str, str);
            character.baseStats.put(Stat.Mag, mag);
            character.baseStats.put(Stat.Dex, dex);
            character.baseStats.put(Stat.Spd, spd);
            character.baseStats.put(Stat.Lck, lck);
            character.baseStats.put(Stat.Def, def);
            character.baseStats.put(Stat.Res, res);
            character.baseStats.put(Stat.Cha, cha);
            return this;
        }

        public Builder withBaseStats(HashMap<Stat, String> baseStats){
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
            character.growthRates.put(Stat.HP, hp);
            character.growthRates.put(Stat.Str, str);
            character.growthRates.put(Stat.Mag, mag);
            character.growthRates.put(Stat.Dex, dex);
            character.growthRates.put(Stat.Spd, spd);
            character.growthRates.put(Stat.Lck, lck);
            character.growthRates.put(Stat.Def, def);
            character.growthRates.put(Stat.Res, res);
            character.growthRates.put(Stat.Cha, cha);
            return this;
        }

        public Builder withGrowthRates(HashMap<Stat, String> growthRates){
            character.growthRates = growthRates;
            return this;
        }

        public Builder withSkills(String sword, String lance, String axe, String bow,
                                  String brawling, String reason, String faith, String authority,
                                  String heavyArmor, String riding, String flying){
            character.skills = new HashMap<>();
            character.skills.put(Skill.Sword, sword);
            character.skills.put(Skill.Lance, lance);
            character.skills.put(Skill.Axe, axe);
            character.skills.put(Skill.Bow, bow);
            character.skills.put(Skill.Brawling, brawling);
            character.skills.put(Skill.Reason, reason);
            character.skills.put(Skill.Faith, faith);
            character.skills.put(Skill.Authority, authority);
            character.skills.put(Skill.Heavy_Armor, heavyArmor);
            character.skills.put(Skill.Riding, riding);
            character.skills.put(Skill.Flying, flying);
            return this;
        }

        public Builder withSkills(HashMap<Skill, String> skills){
            character.skills = skills;
            return this;
        }

        public Builder withRecruitment(String recruitment){
            character.recruitment = recruitment;
            return this;
        }

        public Character build(){ return character; }
    }
}
