package com.example.fe3hguide.model;

import com.example.fe3hguide.R;

import java.util.HashMap;
import java.util.List;

public class InGameClass {

    private String name;
    private int icon;
    private String classLevel;
    private String proficiencies;
    private List<Ability> abilities;
    private Ability mastery_ability;
    private CombatArt mastery_combat_art;
    private String canUse;
    private String restrictions;
    private String certification_requirement;
    private String seal;
    private HashMap<Stat, String> growthRates;
    private int experience;

    private InGameClass(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon(){
        return icon;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }

    public String getClassLevel() {
        return classLevel;
    }

    public void setClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    public String getProficiencies() {
        return proficiencies;
    }

    public void setProficiencies(String proficiencies) {
        this.proficiencies = proficiencies;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public Ability getMastery_ability() {
        return mastery_ability;
    }

    public void setMastery_ability(Ability mastery_ability) {
        this.mastery_ability = mastery_ability;
    }

    public CombatArt getMastery_combat_art() {
        return mastery_combat_art;
    }

    public void setMastery_combat_art(CombatArt mastery_combat_art) {
        this.mastery_combat_art = mastery_combat_art;
    }

    public String getCanUse() {
        return canUse;
    }

    public void setCanUse(String canUse) {
        this.canUse = canUse;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getCertification_requirement() {
        return certification_requirement;
    }

    public void setCertification_requirement(String certification_requirement) {
        this.certification_requirement = certification_requirement;
    }

    public String getSeal() {
        return seal;
    }

    public void setSeal(String seal) {
        this.seal = seal;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public HashMap<Stat, String> getGrowthRates() {
        return growthRates;
    }

    public void setGrowthRates(HashMap<Stat, String> growthRates) {
        this.growthRates = growthRates;
    }

    public static class Builder {
        private final InGameClass inGameClass = new InGameClass();

        public Builder() {}

        public Builder(String name){
            inGameClass.name = name;
        }

        public Builder withIcon(int icon){
            inGameClass.icon = icon;
            return this;
        }

        public Builder withClassLevel(String classLevel){
            inGameClass.classLevel = classLevel;
            return this;
        }

        public Builder withProficiencies(String proficiencies){
            inGameClass.proficiencies = proficiencies;
            return this;
        }

        public Builder withAbilities(List<Ability> abilities){
            inGameClass.abilities = abilities;
            return this;
        }

        public Builder withMasteryAbility(Ability masteryAbility){
            inGameClass.mastery_ability = masteryAbility;
            return this;
        }

        public Builder withMasteryCombatArt(CombatArt masteryCombatArt){
            inGameClass.mastery_combat_art = masteryCombatArt;
            return this;
        }

        public Builder withCanUse(String canUse){
            inGameClass.canUse = canUse;
            return this;
        }

        public Builder withRestrictions(String restrictions){
            inGameClass.restrictions = restrictions;
            return this;
        }

        public Builder withCertificationRequirement(String certificationRequirement){
            inGameClass.certification_requirement = certificationRequirement;
            return this;
        }

        public Builder withSeal(String seal){
            inGameClass.seal = seal;
            return this;
        }

        public Builder withExperience(int experience){
            inGameClass.experience = experience;
            return this;
        }

        public Builder withGrowthRates(String hp, String str, String mag, String dex,
                                       String spd, String lck, String def, String res, String cha){
            inGameClass.growthRates = new HashMap<Stat, String>();
            inGameClass.growthRates.put(Stat.HP, hp);
            inGameClass.growthRates.put(Stat.Str, str);
            inGameClass.growthRates.put(Stat.Mag, mag);
            inGameClass.growthRates.put(Stat.Dex, dex);
            inGameClass.growthRates.put(Stat.Spd, spd);
            inGameClass.growthRates.put(Stat.Lck, lck);
            inGameClass.growthRates.put(Stat.Def, def);
            inGameClass.growthRates.put(Stat.Res, res);
            inGameClass.growthRates.put(Stat.Cha, cha);
            return this;
        }

        public Builder withGrowthRates(HashMap<Stat, String> growthRates){
            inGameClass.growthRates = growthRates;
            return this;
        }

        public InGameClass build(){ return inGameClass; }

    }
}