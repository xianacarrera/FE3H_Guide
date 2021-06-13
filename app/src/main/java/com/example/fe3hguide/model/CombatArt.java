package com.example.fe3hguide.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class CombatArt {

    private String name;
    private String effect;
    private String weapon;
    private HashMap<String, String> stats;            // dur, mt, hit, avo, crit, range

    public abstract String getSpecificText();
    public abstract String saySpecificTextMeaning();

    protected CombatArt(){}

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public String getWeapon() {
        return weapon;
    }

    public HashMap<String, String> getStats(){ return stats; }

    public void setName(String name) {
        this.name = name;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setStats(HashMap<String, String> stats) {
        this.stats = stats;
    }
}
