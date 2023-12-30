package org.example.Pokemon.Effects;

import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

public interface MoveEffect {
    void apply(Pokemon user, Pokemon target);
}
