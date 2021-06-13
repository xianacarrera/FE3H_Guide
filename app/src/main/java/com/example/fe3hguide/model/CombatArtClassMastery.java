package com.example.fe3hguide.model;

import java.util.HashMap;

public class CombatArtClassMastery extends CombatArt {

    private String className;

    private CombatArtClassMastery(){
        super();
    };

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getSpecificText(){
        return getClassName();
    }

    @Override
    public String saySpecificTextMeaning(){
        return "Class";
    }

    public static class Builder{
        private final CombatArtClassMastery combatArt = new CombatArtClassMastery();

        public Builder(String art){
            combatArt.setName(art);
        }

        public CombatArtClassMastery.Builder withEffect(String effect){
            combatArt.setEffect(effect);
            return this;
        }

        public CombatArtClassMastery.Builder withWeapon(String weapon){
            combatArt.setWeapon(weapon);
            return this;
        }

        public CombatArtClassMastery.Builder withClass(String className){
            combatArt.className = className;
            return this;
        }

        public CombatArtClassMastery.Builder withStats(String dur, String mt, String hit,
                                                        String avo, String crit, String range){
            combatArt.setStats(new HashMap<String, String>());
            combatArt.getStats().put("dur", dur);
            combatArt.getStats().put("mt", mt);
            combatArt.getStats().put("hit", hit);
            combatArt.getStats().put("avo", avo);
            combatArt.getStats().put("crit", crit);
            combatArt.getStats().put("range", range);
            return this;
        }

        public CombatArtClassMastery.Builder withStats(HashMap<String, String> stats){
            combatArt.setStats(stats);
            return this;
        }

        public CombatArtClassMastery build(){ return combatArt; }
    }
}
