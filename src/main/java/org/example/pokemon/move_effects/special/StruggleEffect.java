package org.example.pokemon.move_effects.special;

import org.example.pokemon.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

public class StruggleEffect extends MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {

        int struggleDamage = calculateStruggleDamage(user);
        target.takeDamage(struggleDamage);
        result.setMessage(user.getName() + " hits " + target.getName() + " with Struggle for " + struggleDamage + " damage !");

        int recoilDamage = (int) (struggleDamage * 1.5);
        user.takeDamage(recoilDamage);
        result.setMessage(user.getName() + " is hit with recoil for " + recoilDamage + " damage !");
    }

    private int calculateStruggleDamage(Pokemon user) {
        return user.getStats().getAttack() / 2;
    }
}
