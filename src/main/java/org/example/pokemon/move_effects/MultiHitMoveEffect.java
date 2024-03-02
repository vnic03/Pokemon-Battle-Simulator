package org.example.pokemon.move_effects;

import org.example.screens.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;

public interface MultiHitMoveEffect extends MoveEffectWithDamage {
    int applyMultiHitDamage(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result);
}
