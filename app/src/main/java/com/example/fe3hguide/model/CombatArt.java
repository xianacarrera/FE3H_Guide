package com.example.fe3hguide.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CombatArt {

    private String name;
    private CombatArtsType type;
    private String effect;
    private String weapon;
    private String text2;                       // Depends on the Combat Art type
    private HashMap<String, String> stats;            // dur, mt, hit, avo, crit, range

    private CombatArt(){}

    public String getName() {
        return name;
    }

    public CombatArtsType getType() {
        return type;
    }

    public String getEffect() {
        return effect;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getText2(){ return text2; }

    public HashMap<String, String> getStats(){ return stats; }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(CombatArtsType type) {
        this.type = type;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public void setStats(HashMap<String, String> stats) {
        this.stats = stats;
    }

    public static class Builder{
        private final CombatArt combatArt = new CombatArt();

        public Builder(String art){
            combatArt.name = art;
        }

        public Builder withType(CombatArtsType type){
            combatArt.type = type;
            return this;
        }

        public Builder withEffect(String effect){
            combatArt.effect = effect;
            return this;
        }

        public Builder withWeapon(String weapon){
            combatArt.weapon = weapon;
            return this;
        }

        public Builder withText2(String text2){
            combatArt.text2 = text2;
            return this;
        }

        public Builder withStats(String dur, String mt, String hit, String avo, String crit,
                String range){
            combatArt.stats = new HashMap<>();
            combatArt.stats.put("dur", dur);
            combatArt.stats.put("mt", mt);
            combatArt.stats.put("hit", hit);
            combatArt.stats.put("avo", avo);
            combatArt.stats.put("crit", crit);
            combatArt.stats.put("range", range);
            return this;
        }

        public Builder withStats(HashMap<String, String> stats){
            combatArt.stats = stats;
            return this;
        }

        public CombatArt build(){ return combatArt; }
    }
}
