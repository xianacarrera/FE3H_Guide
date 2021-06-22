package com.xiana.fe3hguide.model;

import java.util.HashMap;

public class CombatArtWeaponProficient extends CombatArt {

    private String skillLevel;

    private CombatArtWeaponProficient(){
        super();
    };

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public String getSpecificText(){
        return getSkillLevel();
    }

    @Override
    public String saySpecificTextMeaning(){
        return "Skill level";
    }

    public static class Builder{
        private final CombatArtWeaponProficient combatArt = new CombatArtWeaponProficient();

        public Builder(String art){
            combatArt.setName(art);
        }

        public CombatArtWeaponProficient.Builder withEffect(String effect){
            combatArt.setEffect(effect);
            return this;
        }

        public CombatArtWeaponProficient.Builder withWeapon(String weapon){
            combatArt.setWeapon(weapon);
            return this;
        }

        public CombatArtWeaponProficient.Builder withSkillLevel(String skillLevel){
            combatArt.skillLevel = skillLevel;
            return this;
        }

        public CombatArtWeaponProficient.Builder withStats(String dur, String mt, String hit,
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

        public CombatArtWeaponProficient.Builder withStats(HashMap<String, String> stats){
            combatArt.setStats(stats);
            return this;
        }

        public CombatArtWeaponProficient build(){ return combatArt; }
    }
}
