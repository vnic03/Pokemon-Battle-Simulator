package org.example.Pokemon;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Pokemon {
    String name;
    private List<PokeTyping> typing;
    private final int level = 50;
    private Stats stats;
    private List<Moves> moves;
    private boolean isParalyzed;

    public Pokemon(String name, List<PokeTyping> typing, Stats stats, List<Moves> moves){
        this.name = name;
        this.typing = typing;
        this.stats = stats;
        this.moves = moves;

    }

    public String getName(){
        return name;
    }
    public int getLevel() {
        return level;
    }

    public List<PokeTyping> getTyping(){
        return typing;
    }

    public Stats getStats(){
        return stats;
    }
    public List<Moves> getMoves() {
        return moves;
    }

    public Moves chooseMoveByName(String moveName) {
        for (Moves move : moves) {
            if (move.getName().equalsIgnoreCase(moveName)) {
                return MovesRepository.getMoveByName(moveName);
            }
        }
        System.out.println("Move not found!");
        return null;
    }

    public boolean isAlive() {
        return stats.getHp() > 0;
    }

    public void takeDamage(int damage){
        int currentHP = stats.getHp();
        currentHP -= damage;

        if (currentHP < 0) {
            currentHP = 0;
        }

        stats.setHp(currentHP);
    }

    public void setParalyzed(boolean paralyzed) {
        this.isParalyzed = paralyzed;
        if (paralyzed){
            this.stats.setSpeed(this.stats.getSpeed() / 2);
        }
    }
    public boolean isParalyzed() {
        return isParalyzed;
    }

    public boolean canAct() {
        if (isParalyzed) {
            return new Random().nextDouble() > 0.25;
        }
        return true;
    }


    public String toString(){
        String typeString = typing.stream().map(PokeTyping::name).collect(Collectors.joining(", "));

        String moveString = moves.stream().map(Moves::getName).collect(Collectors.joining(", "));

        return "Pokemon{" +
                "name='" + name + '\'' +
                ", typing=" + typeString +
                ", level=" + level +
                ", stats=" + stats +
                ", moves=" + moveString +
                '}';
    }
}
