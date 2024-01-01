package org.example.Pokemon.Effects;

import org.example.Pokemon.Pokemon;

public class StruggleEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target) {

        int struggleDamage = calculateStruggleDamage(user);
        target.takeDamage(struggleDamage);
        System.out.println(user.getName() + " hits " + target.getName() + " with Struggle for " + struggleDamage + " damage !");

        int recoilDamage = (int) (struggleDamage * 1.5);
        user.takeDamage(recoilDamage);
        System.out.println(user.getName() + " is hit with recoil for " + recoilDamage + " damage !");

    }

    private int calculateStruggleDamage(Pokemon user) {
        return user.getStats().getAttack() / 2;
    }
}
