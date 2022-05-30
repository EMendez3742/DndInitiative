package com.example.dndinitiative;

/* This is the Character class, which includes information on Hp, spell slots,
initiative and other character related information.
*/
public class Character {
    int spellSlot;
    int currentHp, maxHp, initiative, initiativeBonus, ac;
    String name;

    // Empty Constructor
    public Character() {
        this.name = "noName";
        this.currentHp = 1;
        this.maxHp = 1;
        this.initiative = 1;
        this.initiativeBonus = 1;
        this.ac = 1;
    }

    // Half Constructor. This is good for creatures or just basic characters.
    public Character(String name, int hp, int initiative) {
        this.name = name;
        this.currentHp = hp;
        this.maxHp = hp;
        this.initiative = initiative;
        this.initiativeBonus = 0;
        this.ac = 1;
    }

    // Full Constructor
    public Character(String name, int currentHp, int maxHp, int initiative, int initiativeBonus, int ac) {
        this.name = name;
        this.currentHp = currentHp;
        this.maxHp = maxHp;
        this.initiative = initiative;
        this.initiativeBonus = initiativeBonus;
        this.ac = ac;
    }

    // Getter for character name
    String getName(){
        return name;
    }

    // Setter for character name
    void setName(String n){
        name = n;
    }

    // Getter for character current hp
    int getCurrentHp(){
        return currentHp;
    }

    // Setter for character current hp
    void setCurentHp(int points){
        currentHp = points;
    }

    // Getter for character max hp
    int getMaxHp(){
        return maxHp;
    }

    // Setter for character max hp
    void setMaxHp(int points){
        maxHp = points;
    }

    // Getter for character initiative
    int getInitiative(){
        return initiative;
    }

    // Setter for character initiative
    void setInitiative(int init){
        initiative = init;
    }

    // Getter for character initiative bonus
    int getInitiativeBonus(){
        return initiativeBonus;
    }

    // Setter for character initiative bonus
    void setInitiativeBonus(int initBonus){
        initiativeBonus = initBonus;
    }

    // Getter for character AC
    public int getAc() {
        return ac;
    }

    // Setter for character AC
    public void setAc(int ac) {
        this.ac = ac;
    }

    // When a character takes damage
    void takeHp(int points){
        currentHp -= points;
    }

    // When a character heals
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

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name +
                ", currentHp=" + currentHp +
                ", maxHp=" + maxHp +
                ", initiative=" + initiative +
                ", initiativeBonus="+ initiativeBonus +
                ", ac=" + ac + '\'' +
                '}';
    }
}