package org.example.pokemon.effects.move_effects.special;
import org.example.pokemon.effects.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;


public class ExplosionEffect extends MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        user.takeDamage(1000);
        result.setMessage(user.getName() + " pulled a 'Team Rocket' and blasted off!");
    }
}
