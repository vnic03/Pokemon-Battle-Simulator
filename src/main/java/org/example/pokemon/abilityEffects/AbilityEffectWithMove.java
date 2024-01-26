package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

public interface AbilityEffectWithMove extends AbilityEffect{
    void applyEffect(Pokemon pokemon, Moves move);
}
