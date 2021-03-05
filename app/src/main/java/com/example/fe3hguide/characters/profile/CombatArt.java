package com.example.fe3hguide.characters.profile;

public class CombatArt {

    private String art;
    private String type;
    private String effect;
    private String weapon;
    private String skillLevel;      // Weapon proficiency combat art
    private String crest;           // Weapon exclusive combat art
    private String classInGame;     // Class mastery combat art
    private String talent;          // Budding talent combat art
    private String origin;          // Other combat art
    private String dur, mt, hit, avo, crit, range;

    public CombatArt(String art, String type, String effect, String weapon, String skillLevel,
                     String crest, String classInGame, String talent, String origin, String dur,
                     String mt, String hit, String avo, String crit, String range){
        this.art = art;
        this.type = type;
        this.effect = effect;
        this.weapon = weapon;
        this.skillLevel = skillLevel;
        this.crest = crest;
        this.classInGame = classInGame;
        this.talent = talent;
        this.origin = origin;
        this.dur = dur;
        this.mt = mt;
        this.hit = hit;
        this.avo = avo;
        this.crit = crit;
        this.range = range;
    }

    public String getArt() {
        return art;
    }

    public String getType() {
        return type;
    }

    public String getEffect() {
        return effect;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public String getCrest() {
        return crest;
    }

    public String getClassInGame() {
        return classInGame;
    }

    public String getTalent() {
        return talent;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDur() {
        return dur;
    }

    public String getMt() {
        return mt;
    }

    public String getHit() {
        return hit;
    }

    public String getAvo() {
        return avo;
    }

    public String getCrit() {
        return crit;
    }

    public String getRange() {
        return range;
    }
}
