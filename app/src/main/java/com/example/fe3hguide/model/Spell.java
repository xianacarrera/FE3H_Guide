package com.example.fe3hguide.model;

import android.database.sqlite.SQLiteDatabase;

import com.example.fe3hguide.R;

import java.util.HashMap;

public class Spell {

    private String name;
    private String magicType;
    private String description;
    private String rank;
    private String uses;
    private HashMap<String, String> stats;       // mt, hit, range, crit, weight

    private Spell() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMagicType() {
        return magicType;
    }

    public void setMagicType(String magicType) {
        this.magicType = magicType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public HashMap<String, String> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, String> stats) {
        this.stats = stats;
    }


    public static class Builder{
        private final Spell spell = new Spell();

        public Builder(String name){
            spell.name = name;
        }

        public Builder withMagicType(String magicType){
            spell.magicType = magicType;
            return this;
        }

        public Builder withDescription(String description){
            spell.description = description;
            return this;
        }

        public Builder withRank(String rank){
            spell.rank = rank;
            return this;
        }

        public Builder withUses(String uses){
            spell.uses = uses;
            return this;
        }

        public Builder withStats(String mt, String hit, String range, String crit, String weight){
            spell.stats = new HashMap<>();
            spell.stats.put("mt", mt);
            spell.stats.put("hit", hit);
            spell.stats.put("range", range);
            spell.stats.put("crit", crit);
            spell.stats.put("weight", weight);
            return this;
        }

        public Builder withStats(HashMap<String, String> stats){
            spell.stats = stats;
            return this;
        }

        public Spell build(){ return spell; }

    }

    public int getIcon(){
        if (magicType.toLowerCase().contains("dark") || magicType.toLowerCase().contains("black")){
            return R.drawable.reason;
        } else if (magicType.toLowerCase().contains("white")){
            return R.drawable.faith;
        } else {
            return R.drawable.missing_number;
        }
    }

}
