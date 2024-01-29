package org.example.pokemon.abilityEffects;

import org.example.pokemon.Moves;
import org.example.pokemon.PokeTyping;
import org.example.pokemon.Pokemon;

public class StarterEffect implements AbilityEffect {

    @Override
    public void applyEffect(Pokemon pokemon) {

        int lowHp = (int) (pokemon.getStats().getMaxHp() * 0.33);

        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(PokeTyping.FIRE)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == PokeTyping.FIRE) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }

        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(PokeTyping.WATER)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == PokeTyping.WATER) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }

        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(PokeTyping.GRASS)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == PokeTyping.GRASS) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }
        if (pokemon.getStats().getHp() <= lowHp && pokemon.getTyping().contains(PokeTyping.BUG)) {

            for (Moves move : pokemon.getMoves()) {
                if (move.getType() == PokeTyping.BUG) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }
    }
}
