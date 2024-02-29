package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.Pokemon;
import org.example.pokemon.repositories.PokemonRepository;
import org.example.screens.battle.Weather;
import org.example.screens.battleScene.BattleRoundResult;

public interface AbilityEffectWithMove extends AbilityEffect{
    void applyEffect(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result);
}
