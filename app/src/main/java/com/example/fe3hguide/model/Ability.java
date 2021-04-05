package com.example.fe3hguide.model;

public class Ability {

    private String name;
    private int icon;
    private String effect;
    private String origin;
    private String type;

    private Ability(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Builder{
        private Ability ability = new Ability();

        public Builder(String name){
            ability.name = name;
        }

        public Builder withIcon(int icon){
            ability.icon = icon;
            return this;
        }

        public Builder withEffect(String effect){
            ability.effect = effect;
            return this;
        }

        public Builder withOrigin(String origin){
            ability.origin = origin;
            return this;
        }

        public Builder withType(String type){
            ability.type = type;
            return this;
        }

        public Ability build(){ return ability; }

    }
}
