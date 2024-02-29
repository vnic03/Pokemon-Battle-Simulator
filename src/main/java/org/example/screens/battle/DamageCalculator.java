package org.example.screens.battle;

import org.example.pokemon.stats.Stats;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class DamageCalculator {

    private static final double CRITICAL_HIT_CHANCE = 0.0417;

    public static int calculateDamage(Pokemon attacker, Pokemon defender, Moves move, Weather weather, BattleRoundResult result) {

        Stats attackStats = attacker.getStats();
        Stats defenderStats = defender.getStats();

        List<Typing> attackerTypes = attacker.getTyping();

        double typeAdvantage = getTypeAdvantage(move.getType(), defender.getTyping(), attackerTypes);
        setTypeAdvantageMsg(typeAdvantage, defender, result);

        double randomFactor = 0.85 + Math.random() * 0.15;

        int attackStat;
        int defenseStat;
        if (move.getCategory() == MoveCategory.PHYSICAL) {
            attackStat = attackStats.getAttack();
            defenseStat = defenderStats.getDefense();
        } else if (move.getCategory() == MoveCategory.SPECIAL) {
            attackStat = attackStats.getSpecialAttack();
            defenseStat = defenderStats.getSpecialDefense();
        } else {
            throw new IllegalArgumentException(move.getCategory() + " does not exist!");
        }

        int damage = (int) ((((2 * attacker.getLevel() / 5 + 2) * move.getPower() * attackStat /
                defenseStat) / 50 + 2) * typeAdvantage * randomFactor);

        damage = applyAbilities(damage, attacker, defender, move);

        damage = applyWeather(damage, attacker, defender, move, weather);

        if (isCriticalHit(defender)) {
            damage = (int) (damage * 1.5);
            result.setMessage("\u001B[31m"+ "CRITICAL HIT !" + "\u001B[0m");
        }

        return damage;
    }

    private static void setTypeAdvantageMsg(double typeAdvantage, Pokemon defender, BattleRoundResult result) {
        if (typeAdvantage == 2.0) {
            result.setMessage("It's super effective !");
        } else if (typeAdvantage < 1.0 && typeAdvantage > 0) {
            result.setMessage("It's not very effective. . .");
        } else if (typeAdvantage == 0) {
            result.setMessage("It had no effect on " + defender.getName());
        }
    }

    private static boolean isCriticalHit(Pokemon defender) {
        return Math.random() < CRITICAL_HIT_CHANCE &&
                !defender.hasActiveAbility("Battle Armor");
    }

    private static double getTypeAdvantage(Typing attackType, List<Typing> defenderTypes, List<Typing> attackerTypes) {
        double typeEffectiveness = 1.0;

        if (attackerTypes.contains(attackType)) {
            typeEffectiveness *= 1.5;
        }
        for (Typing defenderType : defenderTypes) {
            typeEffectiveness *= getEffectiveness(attackType, defenderType);
        }
        return typeEffectiveness;
    }

    private static double getEffectiveness(Typing attackType, Typing defenderType){

        Map<Typing, Map<Typing, Double>> typeChart = new HashMap<>();

        Map<Typing, Double> normal = new HashMap<>();

        normal.put(Typing.GHOST, 0.0);
        normal.put(Typing.STEEL, 0.5);
        normal.put(Typing.ROCK, 0.5);

        Map<Typing, Double> fighting = new HashMap<>();

        fighting.put(Typing.NORMAL, 2.0);
        fighting.put(Typing.FLYING, 0.5);
        fighting.put(Typing.POISON, 0.5);
        fighting.put(Typing.ROCK, 2.0);
        fighting.put(Typing.BUG, 0.5);
        fighting.put(Typing.GHOST, 0.0);
        fighting.put(Typing.STEEL, 2.0);
        fighting.put(Typing.PSYCHIC, 0.5);
        fighting.put(Typing.ICE, 2.0);
        fighting.put(Typing.DARK, 2.0);
        fighting.put(Typing.FAIRY, 0.5);

        Map<Typing, Double> flying = new HashMap<>();

        flying.put(Typing.FIGHTING, 2.0);
        flying.put(Typing.ROCK, 0.5);
        flying.put(Typing.BUG, 2.0);
        flying.put(Typing.STEEL, 0.5);
        flying.put(Typing.GRASS, 2.0);
        flying.put(Typing.ELECTRIC, 0.5);

        Map<Typing, Double> poison = new HashMap<>();

        poison.put(Typing.POISON, 0.5);
        poison.put(Typing.GROUND, 0.5);
        poison.put(Typing.ROCK, 0.5);
        poison.put(Typing.GHOST, 0.5);
        poison.put(Typing.STEEL, 0.0);
        poison.put(Typing.GRASS, 2.0);
        poison.put(Typing.FAIRY, 2.0);

        Map<Typing, Double> ground = new HashMap<>();

        ground.put(Typing.FLYING, 0.0);
        ground.put(Typing.POISON, 2.0);
        ground.put(Typing.ROCK, 2.0);
        ground.put(Typing.BUG, 0.5);
        ground.put(Typing.STEEL, 2.0);
        ground.put(Typing.FIRE, 2.0);
        ground.put(Typing.GRASS, 0.5);
        ground.put(Typing.ELECTRIC, 2.0);

        Map<Typing, Double> rock = new HashMap<>();

        rock.put(Typing.FIGHTING, 0.5);
        rock.put(Typing.FLYING, 2.0);
        rock.put(Typing.GROUND, 0.5);
        rock.put(Typing.BUG, 2.0);
        rock.put(Typing.STEEL, 0.5);
        rock.put(Typing.FIRE, 2.0);
        rock.put(Typing.ICE, 2.0);

        Map<Typing, Double> bug = new HashMap<>();

        bug.put(Typing.FIGHTING, 0.5);
        bug.put(Typing.FLYING, 0.5);
        bug.put(Typing.POISON, 0.5);
        bug.put(Typing.GHOST, 0.5);
        bug.put(Typing.STEEL, 0.5);
        bug.put(Typing.FIRE, 0.5);
        bug.put(Typing.GRASS, 2.0);
        bug.put(Typing.PSYCHIC, 2.0);
        bug.put(Typing.DARK, 2.0);
        bug.put(Typing.FAIRY, 0.5);

        Map<Typing, Double> ghost = new HashMap<>();

        ghost.put(Typing.NORMAL, 0.0);
        ghost.put(Typing.GHOST, 2.0);
        ghost.put(Typing.PSYCHIC, 2.0);
        ghost.put(Typing.DARK, 0.5);

        Map<Typing, Double> steel = new HashMap<>();

        steel.put(Typing.ROCK, 2.0);
        steel.put(Typing.STEEL, 0.5);
        steel.put(Typing.FIRE, 0.5);
        steel.put(Typing.WATER, 0.5);
        steel.put(Typing.ELECTRIC, 0.5);
        steel.put(Typing.ICE, 2.0);
        steel.put(Typing.FAIRY, 2.0);

        Map<Typing, Double> fire = new HashMap<>();

        fire.put(Typing.ROCK, 0.5);
        fire.put(Typing.STEEL, 2.0);
        fire.put(Typing.BUG, 2.0);
        fire.put(Typing.FIRE, 0.5);
        fire.put(Typing.WATER, 0.5);
        fire.put(Typing.GRASS, 2.0);
        fire.put(Typing.DRAGON, 0.5);
        fire.put(Typing.ICE, 2.0);

        Map<Typing, Double> water = new HashMap<>();

        water.put(Typing.GROUND, 2.0);
        water.put(Typing.ROCK, 2.0);
        water.put(Typing.FIRE, 2.0);
        water.put(Typing.GRASS, 0.5);
        water.put(Typing.WATER, 0.5);
        water.put(Typing.DRAGON, 0.5);

        Map<Typing, Double> grass = new HashMap<>();

        grass.put(Typing.FLYING, 0.5);
        grass.put(Typing.POISON, 0.5);
        grass.put(Typing.GROUND, 2.0);
        grass.put(Typing.ROCK, 2.0);
        grass.put(Typing.BUG, 0.5);
        grass.put(Typing.STEEL, 0.5);
        grass.put(Typing.FIRE, 0.5);
        grass.put(Typing.GRASS, 0.5);
        grass.put(Typing.WATER, 2.0);
        grass.put(Typing.DRAGON, 0.5);

        Map<Typing, Double> electric = new HashMap<>();

        electric.put(Typing.FLYING, 2.0);
        electric.put(Typing.GROUND, 0.0);
        electric.put(Typing.WATER, 2.0);
        electric.put(Typing.GRASS, 0.5);
        electric.put(Typing.ELECTRIC, 0.5);
        electric.put(Typing.DRAGON, 0.5);

        Map<Typing, Double> psychic = new HashMap<>();

        psychic.put(Typing.FIGHTING, 2.0);
        psychic.put(Typing.POISON, 2.0);
        psychic.put(Typing.STEEL, 0.5);
        psychic.put(Typing.PSYCHIC, 0.5);
        psychic.put(Typing.DARK, 0.0);

        Map<Typing, Double> ice = new HashMap<>();

        ice.put(Typing.FLYING, 2.0);
        ice.put(Typing.GROUND, 2.0);
        ice.put(Typing.GRASS, 2.0);
        ice.put(Typing.STEEL, 0.5);
        ice.put(Typing.FIRE, 0.5);
        ice.put(Typing.WATER, 0.5);
        ice.put(Typing.ICE, 0.5);
        ice.put(Typing.DRAGON, 2.0);

        Map<Typing, Double> dragon = new HashMap<>();

        dragon.put(Typing.STEEL, 0.5);
        dragon.put(Typing.DRAGON, 2.0);
        dragon.put(Typing.FAIRY, 0.0);

        Map<Typing, Double> dark = new HashMap<>();

        dark.put(Typing.FIGHTING, 0.5);
        dark.put(Typing.GHOST, 2.0);
        dark.put(Typing.PSYCHIC, 2.0);
        dark.put(Typing.DARK, 0.5);
        dark.put(Typing.FAIRY, 0.5);

        Map<Typing, Double> fairy = new HashMap<>();

        fairy.put(Typing.FIGHTING, 2.0);
        fairy.put(Typing.POISON, 0.5);
        fairy.put(Typing.STEEL, 0.5);
        fairy.put(Typing.FIRE, 0.5);
        fairy.put(Typing.DRAGON, 2.0);
        fairy.put(Typing.DARK, 2.0);



        typeChart.put(Typing.NORMAL, normal);
        typeChart.put(Typing.FIGHTING, fighting);
        typeChart.put(Typing.FLYING, flying);
        typeChart.put(Typing.POISON, poison);
        typeChart.put(Typing.GROUND, ground);
        typeChart.put(Typing.ROCK, rock);
        typeChart.put(Typing.BUG, bug);
        typeChart.put(Typing.GHOST, ghost);
        typeChart.put(Typing.STEEL, steel);
        typeChart.put(Typing.FIRE, fire);
        typeChart.put(Typing.WATER, water);
        typeChart.put(Typing.GRASS, grass);
        typeChart.put(Typing.ELECTRIC, electric);
        typeChart.put(Typing.PSYCHIC, psychic);
        typeChart.put(Typing.ICE, ice);
        typeChart.put(Typing.DRAGON, dragon);
        typeChart.put(Typing.DARK, dark);
        typeChart.put(Typing.FAIRY, fairy);

        return typeChart.getOrDefault(attackType, Collections.emptyMap()).getOrDefault(defenderType, 1.0);
    }


    private static int applyWeather(int baseDamage, Pokemon attacker,Pokemon defender ,Moves attack, Weather weather) {

        if (weather == null || weather == Weather.NONE) {
            return baseDamage;
        }

        switch (weather) {
            case SUN:
                if (attack.getType() == Typing.FIRE) {
                    return (int) (baseDamage * 1.5);
                }
                if (attack.getType() == Typing.WATER) {
                    return (int) (baseDamage * 0.5);
                }
                break;
            case RAIN:
                if (attack.getType() == Typing.WATER) {
                    return (int) (baseDamage * 1.5);
                }
                if (attack.getType() == Typing.FIRE) {
                    return (int) (baseDamage * 0.5);
                }
                break;
        }
        return baseDamage;
    }

    private static int applyAbilities(int damage, Pokemon attacker, Pokemon defender, Moves move) {
        double modifiedDamage = damage;

        if (defender.hasActiveAbility("Thick Fat") && (move.getType() == Typing.FIRE || move.getType() == Typing.ICE)) {
            modifiedDamage *= 0.5;
        }
        return (int) modifiedDamage;
    }
}
