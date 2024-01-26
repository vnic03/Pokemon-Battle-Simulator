package org.example.Pokemon.Effects;

import org.example.Battle.Weather;
import org.example.Gui.battleScene.BattleRoundResult;
import org.example.Pokemon.Moves;
import org.example.Pokemon.Pokemon;

public interface MoveEffectWithDamage extends MoveEffect {
    void applyWithDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result);
}
