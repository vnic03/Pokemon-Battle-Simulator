package org.example.pokemon;

import org.example.pokemon.effects.move_effects.MoveEffect;

public class Moves {

    private Pokemon attacker;
    private final String name;
    private Typing type;
    private  MoveCategory category;
    private int power;
    private int originalPower;
    private int accuracy;
    private int currentPP;
    private final MoveEffect effect;

    public Moves(String name, Typing type, MoveCategory category, int power, int accuracy, int initialPP, MoveEffect effect){
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.currentPP = initialPP;
        this.effect = effect;
    }

    public String getName(){
        return name;
    }

    public Typing getType(){
        return type;
    }
    public int getPower(){
        return power;
    }
    public int getAccuracy() {
        return accuracy; }
    public void setType(Typing type) { this.type = type; }
    public void setPower(int power) { this.power = power; }
    public void saveOriginalPower() {
        this.originalPower = this.power;
    }
    public void resetPower() {
        this.power = this.originalPower;
    }

    public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

    public MoveCategory getCategory() {
        return category;
    }
    public void setCategory(MoveCategory category) {
        this.category = category;
    }
    public int getPp() {
        return this.currentPP;
    }

    public void setPp(int pp) {
        this.currentPP = pp;
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public MoveEffect getEffect() {
        return effect;
    }

    public void setAttacker(Pokemon attacker) {
        this.attacker = attacker;
    }

    public Pokemon getAttacker() {
        return attacker;
    }

    public boolean useMove() {
        if (this.currentPP > 0) {
            this.currentPP--;
            return true;
        } else {
            System.out.println(this.name + " has no PP left!");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Move: " + name +
                ", Type: " + type +
                ", Category: " + category +
                ", Power: " + power +
                ", Accuracy: " + accuracy +
                ", PP: " + currentPP +
                ", Effect: " + (effect != null ? effect.getClass().getSimpleName() : "None");
    }
}

