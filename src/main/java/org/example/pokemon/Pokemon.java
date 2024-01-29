package org.example.pokemon;

import javafx.scene.image.Image;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.teams.Team;

import java.util.*;
import java.util.stream.Collectors;

public class Pokemon {
    private final String name;
    private int pokeDex;
    private final String frontSpritePath;
    private final String backSpritePath;
    private final String iconSpritePath;

    private final String typeString;
    private Gender gender;
    private final List<PokeTyping> typing;
    private final int level = 50;
    private Stats stats;
    private List<Moves> moves;
    private Nature nature;
    private List<Ability> abilities;
    private Ability activeAbility;
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

    private boolean isFlinching = false;

    private int originalHp;
    private int originalAttack;
    private int originalDefense;
    private int originalSpDefense;
    private int originalSpAttack;
    private int originalSpeed;

    private int lastDamageTaken;

    private final int[] evs = new int[6];
    private boolean statsCalculated = false;

    private boolean thickFatActive;

    private Map<Moves, Integer> disabledMoves = new HashMap<>();

    public Pokemon(String name, int pokeDex ,List<PokeTyping> typing, Stats stats, Nature nature, List<Ability> abilities, String frontSpritePath, String backSpritePath, String iconSpritePath, List<Moves> moves) {
        this.name = name;
        this.pokeDex = pokeDex;
        this.gender = randomGender();
        this.typing = typing;
        this.typeString = typing.stream().map(PokeTyping::name).collect(Collectors.joining(", ")); // showing the type
        this.stats = stats;
        this.nature = nature;
        this.abilities = abilities;
        this.activeAbility = null;

        this.frontSpritePath = frontSpritePath;
        this.backSpritePath = backSpritePath;
        this.iconSpritePath = iconSpritePath;

        this.moves = moves;

        if (this.nature != null) {
            applyNatureEffects();
        }
        this.thickFatActive = false;
    }

    public String getName(){
        return name;
    }
    public int getPokeDex() { return pokeDex; }
    public int getLevel() {
        return level;
    }

    public Image getFrontSprite() {
        return new Image(frontSpritePath);
    }

    public Image getBackSprite() {
        return new Image(backSpritePath);
    }

    public Image getIconSprite() {
        return new Image(iconSpritePath);
    }

    public String getTypeString() {
        return typeString;
    }

    private Gender randomGender() {
        return Math.random() < 0.5 ? Gender.MALE : Gender.FEMALE;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender newGender) {
        this.gender = newGender;
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

    public void setActiveAbility(Ability ability) {
        this.activeAbility = ability;
    }

    public Ability getActiveAbility() {
        return this.activeAbility;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setMoves(List<Moves> moves) {
        this.moves = moves;
    }

    public void addMove(Moves move) {
        if (this.moves.size() < 4) {
            this.moves.add(move);
        } else {
            System.out.println(this.name + " moves slots are full !");
        }
    }
    public void clearMoves() {
        this.moves.clear();
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

    public boolean canAct(BattleRoundResult result) {

        if (isFrozen) {
            if (random.nextDouble() < CHANCE_TO_THAW) {
                isFrozen = false;
                System.out.println(this.name + " has thawed out !");
                result.setMessage(this.name + " has thawed out !");
            } else {
                System.out.println(this.name + " is frozen solid !");
                result.setMessage(this.name + " is frozen solid !");
                return false;
            }
        }
        if (isParalyzed) {
            if (random.nextDouble() > 0.25) {
                System.out.println(this.name + " is paralyzed and can't move !");
                result.setMessage(this.name + " is paralyzed and can't move !");
                return false;
            }
        }

        if (isAsleep) {
            System.out.println(this.name + " is fast asleep.");
            result.setMessage(this.name + " is fast asleep.");
            decrementSleepTurns();
            return false;
        }

        if (isFlinching) {
            System.out.println(this.name + " flinched and couldn't move !");
            result.setMessage(this.name + " flinched and couldn't move !");
            isFlinching = false;
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
            sleepTurns--;
            if (sleepTurns <= 0) {
                isAsleep = false;
                System.out.println(this.name + " woke up !");
            }
        }
    }
    public void applyNatureEffects() {
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
    public Stats getStatsAfterNatureEffects() {
        if (this.nature == null) {
            return this.stats;
        }
        Stats modifiedStats = new Stats(
                this.stats.getHp(),
                this.stats.getAttack(),
                this.stats.getDefense(),
                this.stats.getSpecialAttack(),
                this.stats.getSpecialDefense(),
                this.stats.getSpeed()
        );
        double increase = 1.1;
        double decrease = 0.9;

        switch (this.nature.getIncreasedStat()) {
            case ATTACK:
                modifiedStats.setAttack((int) (modifiedStats.getAttack() * increase));
                break;
            case DEFENSE:
                modifiedStats.setDefense((int) (modifiedStats.getDefense() * increase));
                break;
            case SPECIAL_ATTACK:
                modifiedStats.setSpecialAttack((int) (modifiedStats.getSpecialAttack() * increase));
                break;
            case SPECIAL_DEFENSE:
                modifiedStats.setSpecialDefense((int) (modifiedStats.getSpecialDefense() * increase));
                break;
            case SPEED:
                modifiedStats.setSpeed((int) (modifiedStats.getSpeed() * increase));
                break;
        }
        switch (this.nature.getDecreasedStat()) {
            case ATTACK:
                modifiedStats.setAttack((int) (modifiedStats.getAttack() * decrease));
                break;
            case DEFENSE:
                modifiedStats.setDefense((int) (modifiedStats.getDefense() * decrease));
                break;
            case SPECIAL_ATTACK:
                modifiedStats.setSpecialAttack((int) (modifiedStats.getSpecialAttack() * decrease));
                break;
            case SPECIAL_DEFENSE:
                modifiedStats.setSpecialDefense((int) (modifiedStats.getSpecialDefense() * decrease));
                break;
            case SPEED:
                modifiedStats.setSpeed((int) (modifiedStats.getSpeed() * decrease));
                break;
        }
        return modifiedStats;
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
        isAsleep = false;
    }

    public void saveOriginalStats() {
        this.originalHp = this.getStats().getMaxHp();
        this.originalAttack = this.getStats().getAttack();
        this.originalDefense = this.getStats().getDefense();
        this.originalSpAttack = this.getStats().getSpecialAttack();
        this.originalSpDefense = this.getStats().getSpecialDefense();
        this.originalSpeed = this.getStats().getSpeed();

    }

    public void resetStats() {
        this.getStats().setMaxHp(this.originalHp);
        this.getStats().setAttack(this.originalAttack);
        this.getStats().setDefense(this.originalDefense);
        this.getStats().setSpecialAttack(this.originalSpAttack);
        this.getStats().setSpecialDefense(this.originalSpDefense);
        this.getStats().setSpeed(this.originalSpeed);
    }
    public void resetAttack() {
        this.getStats().setAttack(this.originalAttack);
    }


    public boolean allMovesOutOfPP() {
        for (Moves move : this.moves) {
            if (move.getCurrentPP() > 0) {
                return false;
            }
        }
        return true;
    }

    public void setFlinching(boolean flinching) {
        this.isFlinching = flinching;
    }

    public boolean isFlinching() {
        return isFlinching;
    }

    public void setEvs(int hpEVs, int attackEVs, int defenseEvs, int spAttackEvs, int spDefenseEvs, int speedEvs) {

        if (hpEVs + attackEVs + defenseEvs + spAttackEvs + spDefenseEvs + speedEvs <= 508
                && hpEVs <= 252 && attackEVs <= 252 && defenseEvs <= 252
            && spAttackEvs <= 252 && spDefenseEvs <= 252 && speedEvs <= 252) {

            evs[0] = hpEVs;
            evs[1] = attackEVs;
            evs[2] = defenseEvs;
            evs[3] = spAttackEvs;
            evs[4] = spDefenseEvs;
            evs[5] = speedEvs;

            this.statsCalculated = false;
        } else {
            throw new IllegalArgumentException("Invalid Ev spread !");
        }
    }
    public void calculateStatsIfNecessary() {
        if (!this.statsCalculated) {
            this.stats.calculateFinalStats(this);
            this.statsCalculated = true;
        }
    }

    public int[] getEVs() {
        return evs;
    }

    public boolean hasAbility(String abilityName) {
        for (Ability ability : abilities) {
            if (ability.getName().equalsIgnoreCase(abilityName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasActiveAbility(String abilityName) {
        if (activeAbility != null && activeAbility.getName().equalsIgnoreCase(abilityName)) {
            return true;
        }
        return false;
    }

    public void setThickFatActive(boolean isActive) {
        this.thickFatActive = isActive;
    }

    public boolean isThickFatActive() {
        return this.thickFatActive;
    }

    public void disableMove(Moves move, int duration) {
        disabledMoves.put(move, duration);
    }

    public void updateDisabledMoves() {
        disabledMoves.forEach((move, duration) -> disabledMoves.put(move, duration - 1));
        disabledMoves.entrySet().removeIf(entry -> entry.getValue() <= 0);
    }

    public boolean isMoveDisabled(Moves move) {
        return disabledMoves.containsKey(move) && disabledMoves.get(move) > 0;
    }

    public void addAbility(Ability ability) {
        if (abilities == null) {
            abilities = new ArrayList<>();
        }
        abilities.add(ability);
    }
    public boolean belongsTo(Team team) {
        return team.containsPokemon(this);
    }

    public String toString() {

        String typeString = typing.stream().map(PokeTyping::name).collect(Collectors.joining(", "));
        String moveString = moves.stream().map(Moves::getName).collect(Collectors.joining(", "));

        return "Pokemon{" +
                "name='" + name + '\'' +
                ", gender= " + gender.getSymbol() +
                ", typing=" + typeString +
                ", level=" + level +
                ", abilities=" + abilities +
                ", nature=" + nature +
                ", stats=" + stats +
                ", moves=" + moveString +
                '}';
    }
}
