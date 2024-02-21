package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;

public class ThickFatEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon) {
    }

    @Override
    public void applyEffect(Pokemon pokemon, Moves move) {

        if (move.getType() == Typing.FIRE || move.getType() == Typing.ICE) {
            pokemon.setThickFatActive(true);
        }
    }
}
