package org.example.Pokemon;

import org.example.Battle.DamageCalculator;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Pokemon {
    String name;
    private List<PokeTyping> typing;
    private final int level = 50;
    private Stats stats;
    private List<Moves> moves;
    private Nature nature;
    private boolean isParalyzed;
    private boolean isBurned;
    private boolean isPoisoned;
    private boolean isBadlyPoisoned;
    private int badlyPoisonedTurns;
    private boolean isFrozen;
    private static final double CHANCE_TO_THAW = 0.20;
    private boolean isAsleep;
    private int sleepTurns;
    private static final Random random = new Random();

    private int originalDefense;
    private int originalSpDefense;

    private int lastDamageTaken;


    public Pokemon(String name, List<PokeTyping> typing, Stats stats, Nature nature, List<Moves> moves) {
        this.name = name;
        this.typing = typing;
        this.stats = stats;
        this.nature = nature;
        this.moves = moves;
        applyNatureEffects();
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
        return this.moves;
    }

    public Nature getNature(){
        return this.nature;
    }

    public void setNature(Nature nature){
        this.nature = nature;
    }

    public void addMove(Moves move) {
        if (this.moves.size() < 4) {
            this.moves.add(move);
        } else {
            System.out.println(this.name + " moves slots are full !");
        }
    }

    public Moves chooseMoveByName(String moveName) {
        for (Moves move : this.moves) {
            if (move.getName().equalsIgnoreCase(moveName)) {
                return move;
            }
        }
        System.out.println("Move not found!");
        return null;
    }

    public boolean isAlive() {
        return stats.getHp() > 0;
    }

    public void takeDamage(int damage){

        this.lastDamageTaken = damage;

        int currentHP = stats.getHp() - damage;


        if (currentHP < 0) {
            currentHP = 0;
        }

        stats.setHp(currentHP);
    }

    public int getLastDamageTaken() {
        return lastDamageTaken;
    }

    public void setParalyzed(boolean paralyzed) {
        this.isParalyzed = paralyzed;
        if (paralyzed){
            this.stats.setSpeed(this.stats.getSpeed() / 2);
        }
    }
    public boolean isParalyzed() {
        return this.isParalyzed;
    }

    public boolean canAct() {

        if (isFrozen) {
            if (random.nextDouble() < CHANCE_TO_THAW) {
                isFrozen =false;
                System.out.println(this.name + " has thawed out !");

            } else {
                System.out.println(this.name + " is frozen solid !");
                return false;
            }
        }

        if (isParalyzed) {
            if (random.nextDouble() > 0.25) {
                System.out.println(this.name + " is paralyzed and can't move !");
                return false;
            }
        }

        if (isAsleep) {
            System.out.println(this.name + " is fast asleep.");
            decrementSleepTurns();
            return false;
        }

        return true;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public void setBurned(boolean burned){
        this.isBurned = burned;
        if (burned) {
            this.stats.setAttack(this.stats.getAttack() / 2);
        }
    }
    public boolean isBurned() {
        return isBurned;
    }

    public void setPoisoned(boolean poisoned) {
        isPoisoned = poisoned;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }

    public void setBadlyPoisoned(boolean badlyPoisoned) {
        isBadlyPoisoned = badlyPoisoned;
        badlyPoisonedTurns = 1; // Counter to increase damage every round
    }

    public boolean isBadlyPoisoned() {
        return isBadlyPoisoned;
    }

    public int getBadlyPoisonedTurns() {
        return badlyPoisonedTurns;
    }

    public void incrementBadlyPoisonedTurns() {
        badlyPoisonedTurns++;
    }

    public boolean isAsleep() {
        return isAsleep;
    }

    public void setAsleep(boolean asleep, int turns) {
        isAsleep = asleep;
        sleepTurns = asleep ? turns : 0;
    }

    public void decrementSleepTurns() {

        if (isAsleep) {
            sleepTurns --;
            if (sleepTurns <= 0) {
                isAsleep = false;
                System.out.println(this.name +" woke up !");
            }
        }
    }


    private void applyNatureEffects() {

        if (this.nature == null) {
            return;
        }

        double increase = 1.1;
        double decrease = 0.9;

        if (nature.getIncreasedStat() != null) {
            switch (nature.getIncreasedStat()) {
                case ATTACK -> stats.setAttack((int) (stats.getAttack() * increase));
                case DEFENSE -> stats.setDefense((int) (stats.getDefense() * increase));
                case SPECIAL_ATTACK -> stats.setSpecialAttack((int) (stats.getSpecialAttack() * increase));
                case SPECIAL_DEFENSE -> stats.setSpecialDefense((int) (stats.getSpecialDefense() * increase));
                case SPEED -> stats.setSpeed((int) (stats.getSpeed() * increase));
            }
        }

        if (nature.getDecreasedStat() != null) {
            switch (nature.getDecreasedStat()) {
                case ATTACK -> stats.setAttack((int) (stats.getAttack() * decrease));
                case DEFENSE -> stats.setDefense((int) (stats.getDefense() * decrease));
                case SPECIAL_ATTACK -> stats.setSpecialAttack((int) (stats.getSpecialAttack() * decrease));
                case SPECIAL_DEFENSE -> stats.setSpecialDefense((int) (stats.getSpecialDefense() * decrease));
                case SPEED -> stats.setSpeed((int) (stats.getSpeed() * decrease));
            }
        }
    }

    public void heal(int amount) {
        int currentHP = stats.getHp();
        int maxHp = stats.getMaxHp();

        int healedAmount = Math.min(amount, maxHp - currentHP);

        currentHP += amount;

        if (currentHP > maxHp) {
            currentHP = maxHp;
        }

        stats.setHp(currentHP);

        System.out.println(this.getName() + " healed " + healedAmount + " HP !");
    }

    public boolean hasStatusCondition() {
        return isBurned || isParalyzed || isPoisoned || isBadlyPoisoned || isFrozen || isAsleep;
    }

    public void clearStatusCondition(){
        isBurned = false;
        isPoisoned = false;
        isParalyzed = false;
        isBadlyPoisoned = false;
    }

    public void saveOriginalStats() {
        this.originalDefense = this.getStats().getDefense();
        this.originalSpDefense = this.getStats().getSpecialDefense();

        // more later for stat boost ?
    }

    public void resetStats() {
         this.getStats().setDefense(this.originalDefense);
         this.getStats().setSpecialDefense(this.originalSpDefense);
    }

    public boolean allMovesOutOfPP() {
        for (Moves move : this.moves) {
            if (move.getCurrentPP() > 0) {
                return false;
            }
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
                ", nature=" + nature +
                ", stats=" + stats +
                ", moves=" + moveString +
                '}';
    }
}
