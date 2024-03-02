package org.example.battle;

import org.example.pokemon.ability_effects.PreventCritsEffect;
import org.example.pokemon.stats.Stats;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.*;

import java.util.List;

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
                !(defender.getActiveAbility().getEffect() instanceof PreventCritsEffect);
    }

    private static double getTypeAdvantage(Typing attackType, List<Typing> defenderTypes, List<Typing> attackerTypes) {
        double typeEffectiveness = 1.0;

        if (attackerTypes.contains(attackType)) {
            typeEffectiveness *= 1.5;
        }
        for (Typing defenderType : defenderTypes) {
            typeEffectiveness *= TypeChart.getEffectiveness(attackType, defenderType);
        }
        return typeEffectiveness;
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
