package org.example.Pokemon.Effects.Priority;

import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Pokemon;

public class PriorityFour implements MoveEffect {

    @Override
    public void apply(Pokemon user, Pokemon target) {
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
