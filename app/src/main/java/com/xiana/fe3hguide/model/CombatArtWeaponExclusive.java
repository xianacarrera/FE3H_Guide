package com.xiana.fe3hguide.model;

import java.util.HashMap;

public class CombatArtWeaponExclusive extends CombatArt {

    private String crest;

    private CombatArtWeaponExclusive(){
        super();
    };

    public String getCrest() {
        return crest;
    }

    public void setCrest(String crest) {
        this.crest = crest;
    }

    @Override
    public String getSpecificText(){
        return getCrest();
    }

    @Override
    public String saySpecificTextMeaning(){
        return "Crest";
    }

    public static class Builder{
        private final CombatArtWeaponExclusive combatArt = new CombatArtWeaponExclusive();

        public Builder(String art){
            combatArt.setName(art);
        }

        public CombatArtWeaponExclusive.Builder withEffect(String effect){
            combatArt.setEffect(effect);
            return this;
        }

        public CombatArtWeaponExclusive.Builder withWeapon(String weapon){
            combatArt.setWeapon(weapon);
            return this;
        }

        public CombatArtWeaponExclusive.Builder withCrest(String crest){
            combatArt.crest = crest;
            return this;
        }

        public CombatArtWeaponExclusive.Builder withStats(String dur, String mt, String hit,
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

        public CombatArtWeaponExclusive.Builder withStats(HashMap<String, String> stats){
            combatArt.setStats(stats);
            return this;
        }

        public CombatArtWeaponExclusive build(){ return combatArt; }
    }
}
