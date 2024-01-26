package org.example.Pokemon.Effects;
import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Pokemon;


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
