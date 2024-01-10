package org.example.teams;

import org.example.Pokemon.Pokemon;

import java.util.List;

public class Team {

    private List<Pokemon> pokemons;

    public Team(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
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
}
