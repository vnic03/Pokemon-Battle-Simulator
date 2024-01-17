package org.example.Gui;

import org.example.Pokemon.Pokemon;

public interface PokemonEditListener {
    void onPokemonEdited(Pokemon pokemon, int index, boolean isTeam1);
}
