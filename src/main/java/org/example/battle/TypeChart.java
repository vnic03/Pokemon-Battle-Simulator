package org.example.battle;

import org.example.pokemon.Typing;

import java.util.HashMap;
import java.util.Map;

public abstract class TypeChart {

    private static final Map<TypeKey, Double> effectiveness = new HashMap<>();

    private static boolean dataLoaded = false;

    private static void load()
    {
        if (!dataLoaded)
        {
            interaction(Typing.NORMAL, Typing.ROCK, 0.5);
            interaction(Typing.NORMAL, Typing.GHOST, 0.0);
            interaction(Typing.NORMAL, Typing.STEEL, 0.5);

            interaction(Typing.FIRE, Typing.GRASS, 2.0);
            interaction(Typing.FIRE, Typing.ICE, 2.0);
            interaction(Typing.FIRE, Typing.BUG, 2.0);
            interaction(Typing.FIRE, Typing.STEEL, 2.0);
            interaction(Typing.FIRE, Typing.WATER, 0.5);
            interaction(Typing.FIRE, Typing.ROCK, 0.5);
            interaction(Typing.FIRE, Typing.FIRE, 0.5);
            interaction(Typing.FIRE, Typing.DRAGON, 0.5);

            interaction(Typing.WATER, Typing.FIRE, 2.0);
            interaction(Typing.WATER, Typing.GROUND, 2.0);
            interaction(Typing.WATER, Typing.ROCK, 2.0);
            interaction(Typing.WATER, Typing.WATER, 0.5);
            interaction(Typing.WATER, Typing.GRASS, 0.5);
            interaction(Typing.WATER, Typing.DRAGON, 0.5);

            interaction(Typing.ELECTRIC, Typing.WATER, 2.0);
            interaction(Typing.ELECTRIC, Typing.FLYING, 2.0);
            interaction(Typing.ELECTRIC, Typing.GRASS, 0.5);
            interaction(Typing.ELECTRIC, Typing.ELECTRIC, 0.5);
            interaction(Typing.ELECTRIC, Typing.DRAGON, 0.5);
            interaction(Typing.ELECTRIC, Typing.GROUND, 0.0);

            interaction(Typing.GRASS, Typing.WATER, 2.0);
            interaction(Typing.GRASS, Typing.GROUND, 2.0);
            interaction(Typing.GRASS, Typing.ROCK, 2.0);
            interaction(Typing.GRASS, Typing.FIRE, 0.5);
            interaction(Typing.GRASS, Typing.GRASS, 0.5);
            interaction(Typing.GRASS, Typing.POISON, 0.5);
            interaction(Typing.GRASS, Typing.FLYING, 0.5);
            interaction(Typing.GRASS, Typing.BUG, 0.5);
            interaction(Typing.GRASS, Typing.DRAGON, 0.5);
            interaction(Typing.GRASS, Typing.STEEL, 0.5);

            interaction(Typing.ICE, Typing.GRASS, 2.0);
            interaction(Typing.ICE, Typing.GROUND, 2.0);
            interaction(Typing.ICE, Typing.FLYING, 2.0);
            interaction(Typing.ICE, Typing.DRAGON, 2.0);
            interaction(Typing.ICE, Typing.WATER, 0.5);
            interaction(Typing.ICE, Typing.ICE, 0.5);
            interaction(Typing.ICE, Typing.FIRE, 0.5);
            interaction(Typing.ICE, Typing.STEEL, 0.5);

            interaction(Typing.FIGHTING, Typing.NORMAL, 2.0);
            interaction(Typing.FIGHTING, Typing.ICE, 2.0);
            interaction(Typing.FIGHTING, Typing.ROCK, 2.0);
            interaction(Typing.FIGHTING, Typing.DARK, 2.0);
            interaction(Typing.FIGHTING, Typing.STEEL, 2.0);
            interaction(Typing.FIGHTING, Typing.GHOST, 0.0);
            interaction(Typing.FIGHTING, Typing.POISON, 0.5);
            interaction(Typing.FIGHTING, Typing.FLYING, 0.5);
            interaction(Typing.FIGHTING, Typing.PSYCHIC, 0.5);
            interaction(Typing.FIGHTING, Typing.BUG, 0.5);
            interaction(Typing.FIGHTING, Typing.FAIRY, 0.5);

            interaction(Typing.POISON, Typing.GRASS, 2.0);
            interaction(Typing.POISON, Typing.FAIRY, 2.0);
            interaction(Typing.POISON, Typing.POISON, 0.5);
            interaction(Typing.POISON, Typing.GROUND, 0.5);
            interaction(Typing.POISON, Typing.ROCK, 0.5);
            interaction(Typing.POISON, Typing.GHOST, 0.5);
            interaction(Typing.POISON, Typing.STEEL, 0.0);

            interaction(Typing.GROUND, Typing.FIRE, 2.0);
            interaction(Typing.GROUND, Typing.ELECTRIC, 2.0);
            interaction(Typing.GROUND, Typing.POISON, 2.0);
            interaction(Typing.GROUND, Typing.ROCK, 2.0);
            interaction(Typing.GROUND, Typing.STEEL, 2.0);
            interaction(Typing.GROUND, Typing.GRASS, 0.5);
            interaction(Typing.GROUND, Typing.BUG, 0.5);
            interaction(Typing.GROUND, Typing.FLYING, 0.0);

            interaction(Typing.FLYING, Typing.GRASS, 2.0);
            interaction(Typing.FLYING, Typing.FIGHTING, 2.0);
            interaction(Typing.FLYING, Typing.BUG, 2.0);
            interaction(Typing.FLYING, Typing.ELECTRIC, 0.5);
            interaction(Typing.FLYING, Typing.ROCK, 0.5);
            interaction(Typing.FLYING, Typing.STEEL, 0.5);

            interaction(Typing.PSYCHIC, Typing.FIGHTING, 2.0);
            interaction(Typing.PSYCHIC, Typing.POISON, 2.0);
            interaction(Typing.PSYCHIC, Typing.PSYCHIC, 0.5);
            interaction(Typing.PSYCHIC, Typing.STEEL, 0.5);
            interaction(Typing.PSYCHIC, Typing.DARK, 0.0);

            interaction(Typing.BUG, Typing.GRASS, 2.0);
            interaction(Typing.BUG, Typing.PSYCHIC, 2.0);
            interaction(Typing.BUG, Typing.DARK, 2.0);
            interaction(Typing.BUG, Typing.FIGHTING, 0.5);
            interaction(Typing.BUG, Typing.FLYING, 0.5);
            interaction(Typing.BUG, Typing.POISON, 0.5);
            interaction(Typing.BUG, Typing.GHOST, 0.5);
            interaction(Typing.BUG, Typing.STEEL, 0.5);
            interaction(Typing.BUG, Typing.FIRE, 0.5);
            interaction(Typing.BUG, Typing.FAIRY, 0.5);

            interaction(Typing.ROCK, Typing.FIRE, 2.0);
            interaction(Typing.ROCK, Typing.ICE, 2.0);
            interaction(Typing.ROCK, Typing.FLYING, 2.0);
            interaction(Typing.ROCK, Typing.BUG, 2.0);
            interaction(Typing.ROCK, Typing.FIGHTING, 0.5);
            interaction(Typing.ROCK, Typing.GROUND, 0.5);
            interaction(Typing.ROCK, Typing.STEEL, 0.5);

            interaction(Typing.GHOST, Typing.PSYCHIC, 2.0);
            interaction(Typing.GHOST, Typing.GHOST, 2.0);
            interaction(Typing.GHOST, Typing.DARK, 0.5);
            interaction(Typing.GHOST, Typing.NORMAL, 0.0);

            interaction(Typing.DRAGON, Typing.DRAGON, 2.0);
            interaction(Typing.DRAGON, Typing.STEEL, 0.5);
            interaction(Typing.DRAGON, Typing.FAIRY, 0.0);

            interaction(Typing.DARK, Typing.PSYCHIC, 2.0);
            interaction(Typing.DARK, Typing.GHOST, 2.0);
            interaction(Typing.DARK, Typing.FIGHTING, 0.5);
            interaction(Typing.DARK, Typing.DARK, 0.5);
            interaction(Typing.DARK, Typing.FAIRY, 0.5);

            interaction(Typing.STEEL, Typing.ICE, 2.0);
            interaction(Typing.STEEL, Typing.ROCK, 2.0);
            interaction(Typing.STEEL, Typing.FAIRY, 2.0);
            interaction(Typing.STEEL, Typing.STEEL, 0.5);
            interaction(Typing.STEEL, Typing.FIRE, 0.5);
            interaction(Typing.STEEL, Typing.WATER, 0.5);
            interaction(Typing.STEEL, Typing.ELECTRIC, 0.5);

            interaction(Typing.FAIRY, Typing.DRAGON, 2.0);
            interaction(Typing.FAIRY, Typing.FIGHTING, 2.0);
            interaction(Typing.FAIRY, Typing.DARK, 2.0);
            interaction(Typing.FAIRY, Typing.POISON, 0.5);
            interaction(Typing.FAIRY, Typing.STEEL, 0.5);
            interaction(Typing.FAIRY, Typing.FIRE, 0.5);

            dataLoaded = true;
        }
    }

    private static void interaction(Typing attacker, Typing defender, double multiplier) {
        effectiveness.put(new TypeKey(attacker, defender), multiplier);
    }

    public static double getEffectiveness(Typing attacker, Typing defender) {
        load();
        return effectiveness.getOrDefault(new TypeKey(attacker, defender), 1.0);
    }

    private record TypeKey(Typing attacker, Typing defender) {}
}
