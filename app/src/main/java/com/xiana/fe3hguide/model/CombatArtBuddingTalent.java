package com.xiana.fe3hguide.model;

import java.util.HashMap;

public class CombatArtBuddingTalent extends CombatArt{

    private String characterName;

    private CombatArtBuddingTalent(){
        super();
    };

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    @Override
    public String getSpecificText(){
        return getCharacterName();
    }

    @Override
    public String saySpecificTextMeaning(){
        return "Character";
    }

    public static class Builder{
        private final CombatArtBuddingTalent combatArt = new CombatArtBuddingTalent();

        public Builder(String art){
            combatArt.setName(art);
        }

        public CombatArtBuddingTalent.Builder withEffect(String effect){
            combatArt.setEffect(effect);
            return this;
        }

        public CombatArtBuddingTalent.Builder withWeapon(String weapon){
            combatArt.setWeapon(weapon);
            return this;
        }

        public CombatArtBuddingTalent.Builder withAssociatedCharacter(String characterName){
            combatArt.characterName = characterName;
            return this;
        }

        public CombatArtBuddingTalent.Builder withStats(String dur, String mt, String hit,
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

        public CombatArtBuddingTalent.Builder withStats(HashMap<String, String> stats){
            combatArt.setStats(stats);
            return this;
        }

        public CombatArtBuddingTalent build(){ return combatArt; }
    }
}
