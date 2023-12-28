package org.example.Pokemon;

import org.example.Pokemon.Effects.MoveEffect;

public class Moves {
    private String name;
    private PokeTyping type;
    private  MoveCategory category;
    private int power;
    private int accuracy;
    private int powerPoints;
    private MoveEffect effect;

    public Moves(String name, PokeTyping type,MoveCategory category, int power, int accuracy, int pp, MoveEffect moveEffect){
        this.name = name;
        this.type = type;
        this.category = category;
        this.power = power;
        this.accuracy = accuracy;
        this.powerPoints = pp;
        this.effect = moveEffect;
    }

    public String getName(){
        return name;
    }


    public PokeTyping getType(){
        return type;
    }
    public int getPower(){
        return power;
    }
    public int getAccuracy() {
        return accuracy; }
    public void setType(PokeTyping type) { this.type = type; }
    public void setPower(int power) { this.power = power; }
    public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

    public MoveCategory getCategory() {
        return category;
    }
    public void setCategory(MoveCategory category) {
        this.category = category;
    }
    public int getPp() {
        return powerPoints;
    }

    public void setPp(int pp) {
        this.powerPoints = pp;
    }

    @Override
    public String toString() {
        return "Move: " + name +
                ", Type: " + type +
                ", Category: " + category +
                ", Power: " + power +
                ", Accuracy: " + accuracy +
                ", PP: " + powerPoints +
                ", Effect: " + (effect != null ? effect.getClass().getSimpleName() : "None");
    }
}

