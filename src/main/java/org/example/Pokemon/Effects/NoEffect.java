package org.example.Pokemon.Effects;

import org.example.Pokemon.Pokemon;

public class NoEffect implements MoveEffect{
    @Override
    public void apply(Pokemon user, Pokemon target) {
        // no effect
    }
}
