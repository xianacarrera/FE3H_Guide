package com.example.fe3hguide.characters.profile;

import java.util.ArrayList;

public class CombatArt {

    private String art;
    private CombatArtsType type;
    private String effect;
    private String weapon;
    private String text2;                       // Depends on the Combat Art type
    private ArrayList<String> stats;            // dur, mt, hit, avo, crit, range

    public CombatArt(String art, CombatArtsType type, String effect, String weapon, String text2,
                     String dur, String mt, String hit, String avo, String crit, String range){
        this.art = art;
        this.type = type;
        this.effect = effect;
        this.weapon = weapon;
        this.text2 = text2;

        stats = new ArrayList<>(6);
        stats.add(dur);
        stats.add(mt);
        stats.add(hit);
        stats.add(avo);
        stats.add(crit);
        stats.add(range);
    }

    public String getArt() {
        return art;
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

    public ArrayList<String> getStats(){ return stats; }
}
