package org.example.teams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.Pokemon.Pokemon;

import java.util.List;
import java.util.Objects;

public class Team {

    private ObservableList<Pokemon> pokemons = FXCollections.observableArrayList();
    private int activePokemonIndex = 0;

    public Team(ObservableList<Pokemon> pokemons) {
        this.pokemons = pokemons;
        this.activePokemonIndex = 0;
    }

    public ObservableList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ObservableList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public void addPokemon(Pokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    public void removePokemon(Pokemon pokemon) {
        this.pokemons.remove(pokemon);
    }

    public boolean hasAtLeastOnePokemon() {
        return pokemons.stream().anyMatch(Objects::nonNull);
    }
    public int getActivePokemonIndex() {
        return this.activePokemonIndex;
    }
    public void setActivePokemonIndex(int index) {
        if (index >= 0 && index < pokemons.size()) {
            this.activePokemonIndex = index;
        }
    }


}
