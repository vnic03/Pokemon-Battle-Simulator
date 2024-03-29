package org.example.pokemon.stats;

import org.example.pokemon.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class Stats {

    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public Stats(int maxHp, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.speed = speed;
    }

    public Stats(Stats other) {
        this.maxHp = other.maxHp;
        this.hp = other.hp;
        this.attack = other.attack;
        this.defense = other.defense;
        this.specialAttack = other.specialAttack;
        this.specialDefense = other.specialDefense;
        this.speed = other.speed;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0) {
            this.hp = 0;
        } else this.hp = Math.min(hp, this.maxHp);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }


    public void calculateFinalStats(Pokemon pokemon) {

        this.hp = pokemon.getEVs()[0] > 3 ? pokemon.getStats().getMaxHp() + increasedStats(pokemon.getEVs()[0]) + 1 : pokemon.getStats().getMaxHp();
        this.maxHp = this.hp;
        this.attack = pokemon.getEVs()[1] > 3 ? pokemon.getStats().getAttack() + increasedStats(pokemon.getEVs()[1]) + 1 : pokemon.getStats().getAttack();
        this.defense = pokemon.getEVs()[2] > 3 ? pokemon.getStats().getDefense() + increasedStats(pokemon.getEVs()[2]) + 1 : pokemon.getStats().getDefense();
        this.specialAttack = pokemon.getEVs()[3] > 3 ? pokemon.getStats().getSpecialAttack() + increasedStats(pokemon.getEVs()[3]) + 1 : pokemon.getStats().getSpecialAttack();
        this.specialDefense = pokemon.getEVs()[4] > 3 ? pokemon.getStats().getSpecialDefense() + increasedStats(pokemon.getEVs()[4]) + 1 : pokemon.getStats().getSpecialDefense();
        this.speed = pokemon.getEVs()[5] > 3 ? pokemon.getStats().getSpeed() + increasedStats(pokemon.getEVs()[5]) + 1 : pokemon.getStats().getSpeed();
    }

    private int increasedStats(int evs) {
        return evs / 8;
    }

    public Map<String, Integer> getFinalStats() {
        Map<String, Integer> finalStats = new HashMap<>();
        finalStats.put("HP", this.maxHp);
        finalStats.put("Attack", this.attack);
        finalStats.put("Defense", this.defense);
        finalStats.put("Sp.Atk", this.specialAttack);
        finalStats.put("Sp.Def", this.specialDefense);
        finalStats.put("Speed", this.speed);
        return finalStats;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "hp=" + hp +
                ", attack=" + attack +
                ", defense=" + defense +
                ", specialAttack=" + specialAttack +
                ", specialDefense=" + specialDefense +
                ", speed=" + speed +
                '}';
    }
}
