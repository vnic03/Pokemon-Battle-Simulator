package org.example.teams;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.Pokemon.Pokemon;

import java.util.List;
import java.util.Objects;

public class Team {

    private List<Pokemon> pokemons;
    private ObservableList<Pokemon> observablePokemons = FXCollections.observableArrayList();

    public Team(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public ObservableList<Pokemon> getObservablePokemons() {
        return observablePokemons;
    }


    public void setPokemons(List<Pokemon> pokemons) {
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
}
