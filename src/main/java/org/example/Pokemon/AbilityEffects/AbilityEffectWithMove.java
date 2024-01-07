package org.example.Pokemon.AbilityEffects;

import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

public interface AbilityEffectWithMove extends AbilityEffect{
    void applyEffect(Pokemon pokemon, Moves move);
}
