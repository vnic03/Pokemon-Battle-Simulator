package org.example.pokemon.moveEffects;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;


public class ExplosionEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        user.takeDamage(1000);
        result.setMessage(user.getName() + " pulled a 'Team Rocket' and blasted off!");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
