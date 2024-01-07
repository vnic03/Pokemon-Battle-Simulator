package org.example.Pokemon.AbilityEffects;

import org.example.Pokemon.Moves;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;

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
    }
}
