package com.example.dndinitiative;

/* This is the Character class, which includes information on Hp, spell slots,
initiative and other characer related information.
*/
public class Character {
    int spellSlot;
    int currentHp, maxHp, initiative, ac;
    String name;
    String imageUrl;

    // Empty Constructor
    public Character() {
        this.name = "noName";
        this.currentHp = 1;
        this.maxHp = 1;
        this.initiative = 1;
        this.ac = 1;
    }

    // Half Constructor. This is good for creatures or just basic characters.
    public Character(String name, int hp, int initiative) {
        this.name = name;
        this.currentHp = hp;
        this.maxHp = hp;
        this.initiative = initiative;
        this.ac = 1;
    }

    // Full Constructor
    public Character(String name, int currentHp, int maxHp, int initiative, int ac) {
        this.name = name;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.initiative = initiative;
        this.ac = ac;
    }

    // getters and setters
    String getName(){
        return name;
    }

    void setName(String n){
        name = n;
    }

    /*int getSpellSlot(){
        return spellSlot;
    }

    void setSpellSlot(int slots){
        spellSlot = slots;
    }*/

    int getCurrentHp(){
        return currentHp;
    }

    void setCurentHp(int points){
        currentHp = points;
    }

    int getMaxHp(){
        return maxHp;
    }

    void setMaxHp(int points){
        maxHp = points;
    }

    int getInitiative(){
        return initiative;
    }

    void setInitiative(int init){
        initiative = init;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    String getImageUrl(){
        return imageUrl;
    }

    void setImageUrl(String url){
        imageUrl = url;
    }

    // hp damage methods
    void takeHp(int points){
        currentHp -= points;
    }
    void addHp(int points){
        currentHp += points;
    }

    // spell slot methods
    /*void takeSpellSlot(int slots){
        spellSlot -= slots;
    }
    void addSpellSlot(int slots){
        spellSlot += slots;
    }*/
}