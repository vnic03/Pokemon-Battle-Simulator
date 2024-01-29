package org.example.pokemon.moveEffects;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;


public class ExplosionEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        user.takeDamage(1000);
        System.out.println(user.getName() + " didn't wanna be around anymore :(");
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
