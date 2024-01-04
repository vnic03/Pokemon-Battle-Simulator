package org.example.Pokemon.Effects;
import org.example.Pokemon.Pokemon;


public class ExplosionEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target) {
        user.takeDamage(1000);
        System.out.println(user.getName() + " didn't wanna be around anymore :(");
    }
}
