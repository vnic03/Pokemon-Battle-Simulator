package org.example.Battle;

import org.example.Pokemon.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class DamageCalculator {
    public static int calculateDamage(Pokemon attacker, Pokemon defender, Moves attack) {

        Stats attackStats = attacker.getStats();
        Stats defenderStats = defender.getStats();

        List<PokeTyping> attackerTypes = attacker.getTyping();

        double typeAdvantage = getTypeAdvantage(attack.getType(), defender.getTyping(), attackerTypes);
        double randomFactor = 0.85 + Math.random() * 0.15;

        int attackStat;
        int defenseStat;

        if (attack.getCategory() == MoveCategory.PHYSICAL) {
            attackStat = attackStats.getAttack();
            defenseStat = defenderStats.getDefense();
        } else if (attack.getCategory() == MoveCategory.SPECIAL) {
            attackStat = attackStats.getSpecialAttack();
            defenseStat = defenderStats.getSpecialDefense();
        } else {
            throw new IllegalArgumentException(attack.getCategory() + " does not exist!");
        }

        int damage = (int) ((((2 * attacker.getLevel() / 5 + 2) * attack.getPower() * attackStat /
                defenseStat) / 50 + 2) * typeAdvantage * randomFactor);
        return damage;
    }
    public static double getTypeAdvantage(PokeTyping attackType, List<PokeTyping> defenderTypes, List<PokeTyping> attackerType){

        double typeEffectiveness = 1.0;

        if (attackerType.contains(attackType)){
            typeEffectiveness *= 1.5;
        }
        for (PokeTyping defenderType : defenderTypes) {
            typeEffectiveness *= getEffectiveness(attackType, defenderType);
        }

        return typeEffectiveness;
    }

    private static double getEffectiveness(PokeTyping attackType, PokeTyping defenderType){

        Map<PokeTyping, Map<PokeTyping, Double>> typeChart = new HashMap<>();

        Map<PokeTyping, Double> normal = new HashMap<>();

        normal.put(PokeTyping.GHOST, 0.0);
        normal.put(PokeTyping.STEEL, 0.5);
        normal.put(PokeTyping.ROCK, 0.5);

        Map<PokeTyping, Double> fighting = new HashMap<>();

        fighting.put(PokeTyping.NORMAL, 2.0);
        fighting.put(PokeTyping.FLYING, 0.5);
        fighting.put(PokeTyping.POISON, 0.5);
        fighting.put(PokeTyping.ROCK, 2.0);
        fighting.put(PokeTyping.BUG, 0.5);
        fighting.put(PokeTyping.GHOST, 0.0);
        fighting.put(PokeTyping.STEEL, 2.0);
        fighting.put(PokeTyping.PSYCHIC, 0.5);
        fighting.put(PokeTyping.ICE, 2.0);
        fighting.put(PokeTyping.DARK, 2.0);
        fighting.put(PokeTyping.FAIRY, 0.5);

        Map<PokeTyping, Double> flying = new HashMap<>();

        flying.put(PokeTyping.FIGHTING, 2.0);
        flying.put(PokeTyping.ROCK, 0.5);
        flying.put(PokeTyping.BUG, 2.0);
        flying.put(PokeTyping.STEEL, 0.5);
        flying.put(PokeTyping.GRASS, 2.0);
        flying.put(PokeTyping.ELECTRIC, 0.5);

        Map<PokeTyping, Double> poison = new HashMap<>();

        poison.put(PokeTyping.POISON, 0.5);
        poison.put(PokeTyping.GROUND, 0.5);
        poison.put(PokeTyping.ROCK, 0.5);
        poison.put(PokeTyping.GHOST, 0.5);
        poison.put(PokeTyping.STEEL, 0.0);
        poison.put(PokeTyping.GRASS, 2.0);
        poison.put(PokeTyping.FAIRY, 2.0);

        Map<PokeTyping, Double> ground = new HashMap<>();

        ground.put(PokeTyping.FLYING, 0.0);
        ground.put(PokeTyping.POISON, 2.0);
        ground.put(PokeTyping.ROCK, 2.0);
        ground.put(PokeTyping.BUG, 0.5);
        ground.put(PokeTyping.STEEL, 2.0);
        ground.put(PokeTyping.FIRE, 2.0);
        ground.put(PokeTyping.GRASS, 0.5);
        ground.put(PokeTyping.ELECTRIC, 2.0);

        Map<PokeTyping, Double> rock = new HashMap<>();

        rock.put(PokeTyping.FIGHTING, 0.5);
        rock.put(PokeTyping.FLYING, 2.0);
        rock.put(PokeTyping.GROUND, 0.5);
        rock.put(PokeTyping.BUG, 2.0);
        rock.put(PokeTyping.STEEL, 0.5);
        rock.put(PokeTyping.FIRE, 2.0);
        rock.put(PokeTyping.ICE, 2.0);

        Map<PokeTyping, Double> bug = new HashMap<>();

        bug.put(PokeTyping.FIGHTING, 0.5);
        bug.put(PokeTyping.FLYING, 0.5);
        bug.put(PokeTyping.POISON, 0.5);
        bug.put(PokeTyping.GHOST, 0.5);
        bug.put(PokeTyping.STEEL, 0.5);
        bug.put(PokeTyping.FIRE, 0.5);
        bug.put(PokeTyping.GRASS, 2.0);
        bug.put(PokeTyping.PSYCHIC, 2.0);
        bug.put(PokeTyping.DARK, 2.0);
        bug.put(PokeTyping.FAIRY, 0.5);

        Map<PokeTyping, Double> ghost = new HashMap<>();

        ghost.put(PokeTyping.NORMAL, 0.0);
        ghost.put(PokeTyping.GHOST, 2.0);
        ghost.put(PokeTyping.PSYCHIC, 2.0);
        ghost.put(PokeTyping.DARK, 0.5);

        Map<PokeTyping, Double> steel = new HashMap<>();

        steel.put(PokeTyping.ROCK, 2.0);
        steel.put(PokeTyping.STEEL, 0.5);
        steel.put(PokeTyping.FIRE, 0.5);
        steel.put(PokeTyping.WATER, 0.5);
        steel.put(PokeTyping.ELECTRIC, 0.5);
        steel.put(PokeTyping.ICE, 2.0);
        steel.put(PokeTyping.FAIRY, 2.0);

        Map<PokeTyping, Double> fire = new HashMap<>();

        fire.put(PokeTyping.ROCK, 0.5);
        fire.put(PokeTyping.STEEL, 2.0);
        fire.put(PokeTyping.BUG, 2.0);
        fire.put(PokeTyping.FIRE, 0.5);
        fire.put(PokeTyping.WATER, 0.5);
        fire.put(PokeTyping.GRASS, 2.0);
        fire.put(PokeTyping.DRAGON, 0.5);
        fire.put(PokeTyping.ICE, 2.0);

        Map<PokeTyping, Double> water = new HashMap<>();

        water.put(PokeTyping.GROUND, 2.0);
        water.put(PokeTyping.ROCK, 2.0);
        water.put(PokeTyping.FIRE, 2.0);
        water.put(PokeTyping.GRASS, 0.5);
        water.put(PokeTyping.WATER, 0.5);
        water.put(PokeTyping.DRAGON, 0.5);

        Map<PokeTyping, Double> grass = new HashMap<>();

        grass.put(PokeTyping.FLYING, 0.5);
        grass.put(PokeTyping.POISON, 0.5);
        grass.put(PokeTyping.GROUND, 2.0);
        grass.put(PokeTyping.ROCK, 2.0);
        grass.put(PokeTyping.BUG, 0.5);
        grass.put(PokeTyping.STEEL, 0.5);
        grass.put(PokeTyping.FIRE, 0.5);
        grass.put(PokeTyping.GRASS, 0.5);
        grass.put(PokeTyping.WATER, 2.0);
        grass.put(PokeTyping.DRAGON, 0.5);

        Map<PokeTyping, Double> electric = new HashMap<>();

        electric.put(PokeTyping.FLYING, 2.0);
        electric.put(PokeTyping.GROUND, 0.0);
        electric.put(PokeTyping.WATER, 2.0);
        electric.put(PokeTyping.GRASS, 0.5);
        electric.put(PokeTyping.ELECTRIC, 0.5);
        electric.put(PokeTyping.DRAGON, 0.5);

        Map<PokeTyping, Double> psychic = new HashMap<>();

        psychic.put(PokeTyping.FIGHTING, 2.0);
        psychic.put(PokeTyping.POISON, 2.0);
        psychic.put(PokeTyping.STEEL, 0.5);
        psychic.put(PokeTyping.PSYCHIC, 0.5);
        psychic.put(PokeTyping.DARK, 0.0);

        Map<PokeTyping, Double> ice = new HashMap<>();

        ice.put(PokeTyping.FLYING, 2.0);
        ice.put(PokeTyping.GROUND, 2.0);
        ice.put(PokeTyping.GRASS, 2.0);
        ice.put(PokeTyping.STEEL, 0.5);
        ice.put(PokeTyping.FIRE, 0.5);
        ice.put(PokeTyping.WATER, 0.5);
        ice.put(PokeTyping.ICE, 0.5);
        ice.put(PokeTyping.DRAGON, 2.0);

        Map<PokeTyping, Double> dragon = new HashMap<>();

        dragon.put(PokeTyping.STEEL, 0.5);
        dragon.put(PokeTyping.DRAGON, 2.0);
        dragon.put(PokeTyping.FAIRY, 0.0);

        Map<PokeTyping, Double> dark = new HashMap<>();

        dark.put(PokeTyping.FIGHTING, 0.5);
        dark.put(PokeTyping.GHOST, 2.0);
        dark.put(PokeTyping.PSYCHIC, 2.0);
        dark.put(PokeTyping.DARK, 0.5);
        dark.put(PokeTyping.FAIRY, 0.5);

        Map<PokeTyping, Double> fairy = new HashMap<>();

        fairy.put(PokeTyping.FIGHTING, 2.0);
        fairy.put(PokeTyping.POISON, 0.5);
        fairy.put(PokeTyping.STEEL, 0.5);
        fairy.put(PokeTyping.FIRE, 0.5);
        fairy.put(PokeTyping.DRAGON, 2.0);
        fairy.put(PokeTyping.DARK, 2.0);



        typeChart.put(PokeTyping.NORMAL, normal);
        typeChart.put(PokeTyping.FIGHTING, fighting);
        typeChart.put(PokeTyping.FLYING, flying);
        typeChart.put(PokeTyping.POISON, poison);
        typeChart.put(PokeTyping.GROUND, ground);
        typeChart.put(PokeTyping.ROCK, rock);
        typeChart.put(PokeTyping.BUG, bug);
        typeChart.put(PokeTyping.GHOST, ghost);
        typeChart.put(PokeTyping.STEEL, steel);
        typeChart.put(PokeTyping.FIRE, fire);
        typeChart.put(PokeTyping.WATER, water);
        typeChart.put(PokeTyping.GRASS, grass);
        typeChart.put(PokeTyping.ELECTRIC, electric);
        typeChart.put(PokeTyping.PSYCHIC, psychic);
        typeChart.put(PokeTyping.ICE, ice);
        typeChart.put(PokeTyping.DRAGON, dragon);
        typeChart.put(PokeTyping.DARK, dark);
        typeChart.put(PokeTyping.FAIRY, fairy);

        return typeChart.getOrDefault(attackType, Collections.emptyMap()).getOrDefault(defenderType, 1.0);

    }
}
