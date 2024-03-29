package org.example.pokemon;

import javafx.scene.image.Image;
import org.example.pokemon.stats.Stat;
import org.example.pokemon.stats.Stats;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.teams.Team;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Pokemon {
    private final String name;
    private String nickname = null;
    private final int pokeDex;
    private final String frontSpritePath;
    private final String backSpritePath;
    private final String iconSpritePath;
    private final String typeString;
    private Gender gender;
    private final List<Typing> typing;
    private final Stats stats;
    private List<Moves> moves;
    private Nature nature;
    private final List<Ability> abilities;
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

    private final  Map<Moves, Integer> disabledMoves = new HashMap<>();

    public Pokemon(
            String name, int pokeDex , List<Typing> typing, Stats stats, Nature nature, List<Ability> abilities,
            String frontSpritePath, String backSpritePath, String iconSpritePath, List<Moves> moves)
    {
        this.name = name;
        this.pokeDex = pokeDex;
        this.gender = randomGender();
        this.typing = typing;
        this.typeString = typing.stream().map(Typing::name).collect(Collectors.joining(", ")); // showing the type
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
        if (this.nickname != null && !this.nickname.isEmpty()) {
            return this.nickname;
        } else {
            return name;
        }
    }
    public String getBaseName() {
        return name;
    }
    public void setNickname(String name) {
        this.nickname = name;
    }
    public int getPokeDex() { return pokeDex; }
    public int getLevel() {
        return 50;
    }

    public Image getFrontSprite() {
        return SpriteManager.loadImage(frontSpritePath);
    }

    public Image getBackSprite() {
        return SpriteManager.loadImage(backSpritePath);
    }

    public Image getIconSprite() {
        return SpriteManager.loadImage(iconSpritePath);
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

    public List<Typing> getTyping(){
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
            System.err.println(this.name + " moves slots are full !");
        }
    }
    public void clearMoves() {
        this.moves.clear();
    }

    public Moves chooseMoveByName(String moveName) {
        return moves.stream()
                .filter(move -> move.getName().equalsIgnoreCase(moveName)).findFirst().orElse(null);
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
                result.setMessage(this.name + " has thawed out !");
            } else {
                result.setMessage(this.name + " is frozen solid !");
                return false;
            }
        }
        if (isParalyzed) {
            if (random.nextDouble() > 0.25) {
                result.setMessage(this.name + " is paralyzed and can't move !");
                return false;
            }
        }

        if (isAsleep) {
            result.setMessage(this.name + " is fast asleep.");
            decrementSleepTurns(result);
            return false;
        }

        if (isFlinching) {
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

    public void decrementSleepTurns(BattleRoundResult result) {

        if (isAsleep) {
            sleepTurns--;
            if (sleepTurns <= 0) {
                isAsleep = false;
                result.setMessage(this.name + " woke up !");
            }
        }
    }

    public void applyNatureEffects() {
        final double INCREASE = 1.1;
        final double DECREASE = 0.9;

        if (nature.getIncreasedStat() != null) {
            updateStat(nature.getIncreasedStat(), INCREASE);
        }
        if (nature.getDecreasedStat() != null) {
            updateStat(nature.getDecreasedStat(), DECREASE);
        }
    }
    private void updateStat(Stat stat, double multiplier) {
        switch (stat) {
            case ATTACK -> stats.setAttack((int) (stats.getAttack() * multiplier));
            case DEFENSE -> stats.setDefense((int) (stats.getDefense() * multiplier));
            case SPECIAL_ATTACK -> stats.setSpecialAttack((int) (stats.getSpecialAttack() * multiplier));
            case SPECIAL_DEFENSE -> stats.setSpecialDefense((int) (stats.getSpecialDefense() * multiplier));
            case SPEED -> stats.setSpeed((int) (stats.getSpeed() * multiplier));
        }
    }
    public Stats getStatsAfterNatureEffects() {
        Stats modifiedStats = new Stats(
                this.stats.getMaxHp(), this.stats.getAttack(), this.stats.getDefense(),
                this.stats.getSpecialAttack(), this.stats.getSpecialDefense(), this.stats.getSpeed()
        );
        Pokemon temp = new Pokemon(
                this.name, this.pokeDex, this.typing, modifiedStats, this.nature, this.abilities,
                this.frontSpritePath, this.backSpritePath, this.iconSpritePath, this.moves
        );
        temp.applyNatureEffects();

        return temp.getStats();
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
        return moves.stream().allMatch(move -> move.getCurrentPP() <= 0);
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
        return abilities.stream()
                .anyMatch(ability -> ability.name().equalsIgnoreCase(abilityName));
    }

    public boolean hasActiveAbility(String abilityName) {
        return activeAbility != null && activeAbility.name().equalsIgnoreCase(abilityName);
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

    public boolean belongsTo(Team team) {
        return team.containsPokemon(this);
    }

    private static class SpriteManager {
        private static final Map<String, Image> imageCache = new HashMap<>();

        public static Image loadImage(String path) {
            return imageCache.computeIfAbsent(path, p -> {
                try {
                    final URL url = SpriteManager.class.getResource(p);
                    if (url == null) {
                        throw new IllegalArgumentException("Resource not found: " + p);
                    }
                    return new Image(url.toString());
                } catch (Exception e) {
                    throw new RuntimeException("Failed to load image: " + p, e);
                }
            });
        }
    }

    public static Map<String, Integer> getPokemonBaseStats(Pokemon pokemon) {
        Stats base = pokemon.getStats();
        Map<String, Integer> stats = new HashMap<>();

        stats.put("HP", base.getMaxHp());
        stats.put("Attack", base.getAttack());
        stats.put("Defense", base.getDefense());
        stats.put("Sp.Atk", base.getSpecialAttack());
        stats.put("Sp.Def", base.getSpecialDefense());
        stats.put("Speed", base.getSpeed());

        return stats;
    }

    public String toString() {
        String type = typing.stream().map(Typing::name).collect(Collectors.joining(", "));

        return "Pokemon {" +
                "name: " + name + '\'' +
                ", gender: " + gender.getSymbol() +
                ", typing: " + type +
                ", abilities: " + abilities +
                ", " + stats + " }";
    }
}