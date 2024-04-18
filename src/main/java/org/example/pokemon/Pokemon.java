package org.example.pokemon;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.ResourceManager;
import org.example.pokemon.ability.Ability;
import org.example.pokemon.ability.EffectHandler;
import org.example.pokemon.stats.Stat;
import org.example.pokemon.stats.Stats;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.teams.Team;

import java.util.*;
import java.util.stream.Collectors;


public class Pokemon {

    private static final Logger LOGGER = LogManager.getLogger(Pokemon.class);

    private final String name;
    private String nickname = null;
    private final int pokeDex;
    private final List<Typing> typing;
    private final Stats stats;
    private Nature nature;
    private final List<Ability> abilities;
    private Ability activeAbility;
    private final EffectHandler effectHandler;

    private final String frontSpritePath;
    private final String iconSpritePath;
    private final String frontAnimationPath;
    private final String backAnimationPath;
    private final String cryPath;

    private List<Moves> moves;
    private Gender gender;

    private final EnumSet<StatusCondition> status = EnumSet.noneOf(StatusCondition.class);
    private int badlyPoisonedTurns;
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

    private final Map<Moves, Integer> disabledMoves = new HashMap<>();

    public Pokemon(
            String name, int pokeDex, List<Typing> typing, Stats stats, Nature nature, List<Ability> abilities,
            String frontSpritePath, String iconSpritePath, String frontAnimationPath, String backAnimationPath,
            String cryPath, List<Moves> moves)
    {
        this.name = name;
        this.pokeDex = pokeDex;
        this.gender = randomGender();
        this.typing = typing;
        this.stats = stats;
        this.nature = nature;
        this.abilities = abilities;
        this.effectHandler = new EffectHandler(this);
        this.activeAbility = null;

        this.frontSpritePath = frontSpritePath;
        this.iconSpritePath = iconSpritePath;
        this.frontAnimationPath = frontAnimationPath;
        this.backAnimationPath = backAnimationPath;
        this.cryPath = cryPath;

        this.moves = moves;

        if (this.nature != null) {
            applyNatureEffects();
        }
    }

    public String getName() {
        return nickname != null && !nickname.isEmpty() ? nickname : name;
    }

    public String getBaseName() {
        return name;
    }

    public void setNickname(String name) {
        nickname = name;
    }

    public int getPokeDex() { return pokeDex; }

    public int getLevel() {
        // The Level of all Pokemon are 50
        return 50;
    }

    public Image getFrontSprite() {
        return ResourceManager.loadResource(frontSpritePath, Image::new);
    }

    public Image getIconSprite() {
        return ResourceManager.loadResource(iconSpritePath, Image::new);
    }

    public Image getFrontAnimation() {
        return ResourceManager.loadResource(frontAnimationPath, Image::new);
    }

    public Image getBackAnimation() {
        return ResourceManager.loadResource(backAnimationPath, Image::new);
    }

    public AudioClip getCry() {
        return ResourceManager.loadResource(cryPath, AudioClip::new);
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

    public List<Typing> getTyping() {
        return typing;
    }

    public Stats getStats() {
        return stats;
    }

    public List<Moves> getMoves() {
        return moves;
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public void setActiveAbility(Ability ability) {
        this.activeAbility = ability;
    }

    public Ability getActiveAbility() {
        return activeAbility;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public EffectHandler getEffectHandler() {
        return effectHandler;
    }

    public void setMoves(List<Moves> moves) {
        this.moves = moves;
    }

    public void addMove(Moves move) {
        if (this.moves.size() < 4) {
            this.moves.add(move);
        } else LOGGER.warn("{} moves slots are full!", name);
    }

    public void clearMoves() {
        this.moves.clear();
    }

    public Moves chooseMoveByName(String moveName) {
        return moves.stream()
                .filter(move -> move.getName().equalsIgnoreCase(moveName)).findFirst()
                .orElse(null);
    }

    public boolean isAlive() {
        return stats.getHp() > 0;
    }

    public void takeDamage(int damage) {
        this.lastDamageTaken = damage;

        int currentHP = stats.getHp() - damage;

        if (currentHP < 0) currentHP = 0;

        stats.setHp(currentHP);
    }

    public int getLastDamageTaken() {
        return lastDamageTaken;
    }

    public void setParalyzed(boolean paralyzed) {
        if (paralyzed) {
            status.add(StatusCondition.PARALYZE);
            stats.setSpeed(this.stats.getSpeed() / 2);
        } else {
            status.remove(StatusCondition.PARALYZE);
            stats.setSpeed(this.stats.getSpeed() * 2);
        }
    }

    public boolean isParalyzed() {
        return status.contains(StatusCondition.PARALYZE);
    }

    public void setFrozen(boolean frozen) {
        if (frozen) status.add(StatusCondition.FREEZE);
        else status.remove(StatusCondition.FREEZE);
    }

    public boolean isFrozen() {
        return status.contains(StatusCondition.FREEZE);
    }

    public void setBurned(boolean burned) {
        if (burned) {
            status.add(StatusCondition.BURN);
            stats.setAttack(this.stats.getAttack() / 2);
        } else {
            status.remove(StatusCondition.BURN);
            stats.setAttack(this.stats.getAttack() * 2);
        }
    }

    public boolean isBurned() {
        return status.contains(StatusCondition.BURN);
    }

    public void setPoisoned(boolean poisoned) {
        if (poisoned) status.add(StatusCondition.POISON);
        else status.remove(StatusCondition.POISON);
    }

    public boolean isPoisoned() {
        return status.contains(StatusCondition.POISON);
    }

    public void setBadlyPoisoned(boolean badlyPoisoned) {
        if (badlyPoisoned) status.add(StatusCondition.BADLY_POISON);
        else status.remove(StatusCondition.BADLY_POISON);
        badlyPoisonedTurns = 1; // Counter to increase damage every round
    }

    public boolean isBadlyPoisoned() {
        return status.contains(StatusCondition.BADLY_POISON);
    }

    public int getBadlyPoisonedTurns() {
        return badlyPoisonedTurns;
    }

    public void incrementBadlyPoisonedTurns() {
        badlyPoisonedTurns++;
    }

    public void setAsleep(boolean asleep, int turns) {
        if (asleep) status.add(StatusCondition.SLEEP);
        else status.remove(StatusCondition.SLEEP);
        sleepTurns = asleep ? turns : 0;
    }

    public boolean isAsleep() {
        return status.contains(StatusCondition.SLEEP);
    }

    private void decrementSleepTurns(BattleRoundResult result) {
        if (isAsleep()) {
            sleepTurns -= this.hasActiveAbility(Ability.Name.EARLY_BIRD) ? 2 : 1;
            if (sleepTurns <= 0) {
                setAsleep(false, 0);
                result.setMessage(this.name + " woke up !");
            }
        }
    }

    public boolean canAct(BattleRoundResult result) {
        final double CHANCE_TO_THAW = 0.20;
        if (isFrozen()) {
            if (random.nextDouble() < CHANCE_TO_THAW) {
                setFrozen(false);
                result.setMessage(this.name + " has thawed out !");
            } else {
                result.setMessage(this.name + " is frozen solid !");
                return false;
            }
        }
        if (isParalyzed()) {
            if (random.nextDouble() > 0.25) {
                result.setMessage(this.name + " is paralyzed and can't move !");
                return false;
            }
        }
        if (isAsleep()) {
            result.setMessage(this.name + " is fast asleep.");
            decrementSleepTurns(result);
            return false;
        }
        if (isFlinching && !this.hasActiveAbility(Ability.Name.INNER_FOCUS)) {
            result.setMessage(this.name + " flinched and couldn't move !");
            isFlinching = false;
            return false;
        }

        return true;
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
                this.frontSpritePath, this.iconSpritePath, this.frontAnimationPath, this.backAnimationPath,
                this.cryPath, this.moves
        );
        temp.applyNatureEffects();

        return temp.getStats();
    }

    public void heal(int amount) {
        if (amount < 0) return;
        int currentHP = stats.getHp();
        int maxHp = stats.getMaxHp();

        currentHP += Math.min(amount, maxHp - currentHP);

        stats.setHp(currentHP);

        LOGGER.info("{} healed", this.getName());
    }

    public boolean hasStatusCondition() {
        return !status.isEmpty();
    }

    public void clearStatusCondition() {
        status.clear();
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

    public boolean hasActiveAbility(Ability.Name name) {
        return activeAbility != null && activeAbility.name() == name;
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

    public enum StatusCondition {
        BURN, PARALYZE, POISON, BADLY_POISON, FREEZE, SLEEP
    }

    public void applyStatusCondition(StatusCondition condition) {
        status.add(condition);
    }

    public void applyStatModifier(Stat stat, double modifier) {
        switch (stat) {
            case ATTACK -> stats.setAttack((int) (stats.getAttack() * modifier));
            case DEFENSE -> stats.setDefense((int) (stats.getDefense() * modifier));
            case SPECIAL_ATTACK -> stats.setSpecialAttack((int) (stats.getSpecialAttack() * modifier));
            case SPECIAL_DEFENSE -> stats.setSpecialDefense((int) (stats.getSpecialDefense() * modifier));
            case SPEED -> stats.setSpeed((int) (stats.getSpeed() * modifier));
        }
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