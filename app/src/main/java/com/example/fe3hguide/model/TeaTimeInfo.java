package com.example.fe3hguide.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TeaTimeInfo {

    private String character;
    private ArrayList<String> favouriteTeas;
    private ArrayList<String> topics;
    private ArrayList<String> finalConversations;       // Final conversations of the character
    private ArrayList<ArrayList<String>> options;

    /*
     * Each index in options contains an ArrayList with the possible answers to the final
     * conversation of the same index stored in finalConversations.
     *
     * So, for example, the answers to the 3rd finalConversation would be options.get(2).get(0),
     * options.get(2).get(1) and options.get(2).get(2).
     */


    protected TeaTimeInfo(){}

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public ArrayList<String> getFavouriteTeas() {
        return favouriteTeas;
    }

    public void setFavouriteTeas(ArrayList<String> favouriteTeas) {
        this.favouriteTeas = favouriteTeas;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public ArrayList<String> getFinalConversations() {
        return finalConversations;
    }

    public void setFinalConversations(ArrayList<String> finalConversations) {
        this.finalConversations = finalConversations;
    }

    public ArrayList<ArrayList<String>> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<ArrayList<String>> options) {
        this.options = options;
    }

    public static class Builder{
        private final TeaTimeInfo teaTimeInfo = new TeaTimeInfo();

        public Builder(String character){
            teaTimeInfo.character = character;
        }

        public Builder withFavouriteTeas(ArrayList<String> favouriteTeas){
            teaTimeInfo.favouriteTeas = favouriteTeas;
            return this;
        }

        public Builder withTopics(ArrayList<String> topics){
            teaTimeInfo.topics = topics;
            return this;
        }

        public Builder withFinalConversations(ArrayList<String> finalConvos){
            teaTimeInfo.finalConversations = finalConvos;
            return this;
        }

        public Builder withOptions(ArrayList<ArrayList<String>> options){
            teaTimeInfo.options = options;
            return this;
        }

        public TeaTimeInfo build() { return teaTimeInfo; }
    }
}
