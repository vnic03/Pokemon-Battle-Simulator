package org.example.Pokemon.Effects;

import org.example.Battle.Weather;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

public interface MultiHitMoveEffect extends MoveEffectWithDamage {
    int applyMultiHitDamage(Pokemon user, Pokemon target, Moves move, Weather weather);
}
