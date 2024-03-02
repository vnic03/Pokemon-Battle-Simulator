package org.example.pokemon.ability_effects;

import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class GutsEffect implements AbilityEffect {

    private boolean isActivated = false;

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {
        if (pokemon.hasStatusCondition() && !isActivated) {
            int originalAttack = pokemon.getStats().getAttack();
            pokemon.getStats().setAttack((int) (originalAttack * 1.5));
            isActivated = true;
        } else if (!pokemon.hasStatusCondition() && isActivated) {
            pokemon.resetAttack();
            isActivated = false;
        }
    }
}
