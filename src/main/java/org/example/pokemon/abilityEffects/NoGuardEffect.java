package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

public class NoGuardEffect implements AbilityEffectWithMove {

    @Override
    public void applyEffect(Pokemon pokemon) {}

    @Override
    public void applyEffect(Pokemon pokemon, Moves move) {
        if (pokemon.hasActiveAbility("No Guard")) {
            move.setAccuracy(100);
            // einbauen, dass auch moves auf das pokemon 100 % treffen
        }
    }
}
