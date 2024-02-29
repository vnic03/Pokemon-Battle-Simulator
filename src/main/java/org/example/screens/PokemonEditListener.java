package org.example.screens;

import org.example.pokemon.Pokemon;

@FunctionalInterface
public interface PokemonEditListener {
    void onPokemonEdited(Pokemon pokemon, int index, boolean isTeam1);
}
