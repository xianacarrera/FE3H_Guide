package com.xiana.fe3hguide.model;

import java.util.HashMap;

public class CombatArtOther extends CombatArt {

    private String origin;

    private CombatArtOther(){
        super();
    };

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String getSpecificText(){
        return getOrigin();
    }

    @Override
    public String saySpecificTextMeaning(){
        return "Origin";
    }

    public static class Builder{
        private final CombatArtOther combatArt = new CombatArtOther();

        public Builder(String art){
            combatArt.setName(art);
        }

        public CombatArtOther.Builder withEffect(String effect){
            combatArt.setEffect(effect);
            return this;
        }

        public CombatArtOther.Builder withWeapon(String weapon){
            combatArt.setWeapon(weapon);
            return this;
        }

        public CombatArtOther.Builder withOrigin(String origin){
            combatArt.origin = origin;
            return this;
        }

        public CombatArtOther.Builder withStats(String dur, String mt, String hit,
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

        public CombatArtOther.Builder withStats(HashMap<String, String> stats){
            combatArt.setStats(stats);
            return this;
        }

        public CombatArtOther build(){ return combatArt; }
    }
}
