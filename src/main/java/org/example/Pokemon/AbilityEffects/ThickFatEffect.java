package org.example.Pokemon.AbilityEffects;

import org.example.Pokemon.Moves;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

public class ThickFatEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon) {
    }

    @Override
    public void applyEffect(Pokemon pokemon, Moves move) {

        if (move.getType() == PokeTyping.FIRE || move.getType() == PokeTyping.ICE) {
            pokemon.setThickFatActive(true);
        }
    }
}
