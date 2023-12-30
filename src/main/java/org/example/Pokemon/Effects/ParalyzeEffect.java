package org.example.Pokemon.Effects;

import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

public class ParalyzeEffect implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target) {
        if (target.getTyping().contains(PokeTyping.ELECTRIC) || target.getTyping().contains(PokeTyping.GROUND)) {
            System.out.println(target.getName() + " can't be paralyzed!");
            return;
        }
        if (target.isParalyzed()) {
            System.out.println(target.getName() + " is already paralyzed!");
            return;
        }
        target.setParalyzed(true);
        System.out.println(target.getName() + " got paralyzed!");
    }
}
