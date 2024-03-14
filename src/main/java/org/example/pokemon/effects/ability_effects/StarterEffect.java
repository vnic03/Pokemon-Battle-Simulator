package org.example.pokemon.effects.ability_effects;

import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.screens.battleScene.BattleRoundResult;

public class StarterEffect implements AbilityEffect {

    @Override
    public void applyEffect(Pokemon pokemon, BattleRoundResult result) {

        int lowHp = (int) (pokemon.getStats().getMaxHp() * 0.33);

        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(Typing.FIRE)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == Typing.FIRE) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }

        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(Typing.WATER)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == Typing.WATER) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }

        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(Typing.GRASS)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == Typing.GRASS) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }
        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(Typing.BUG)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == Typing.BUG) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }
    }
}
