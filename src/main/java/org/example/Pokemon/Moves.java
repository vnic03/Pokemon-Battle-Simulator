package org.example.Pokemon;

public class Moves {
    private String name;
    private PokeTyping type;
    private int power;
    private int accuracy;

    public Moves(String name, PokeTyping type, int power, int accuracy){
        this.name = name;
        this.type = type;
        this.power = power;
        this.accuracy = accuracy;
    }

    public PokeTyping getType(){
        return type;
    }
    public int getPower(){
        return power;
    }
    public int getAccuracy() { return accuracy; }
    public void setType(PokeTyping type) { this.type = type; }
    public void setPower(int power) { this.power = power; }
    public void setAccuracy(int accuracy) { this.accuracy = accuracy; }

    @Override
    public String toString() {
        return "Move: " + name + ", Type: " + type + ", Power: " + power + ", Accuracy: " + accuracy;
    }

}

