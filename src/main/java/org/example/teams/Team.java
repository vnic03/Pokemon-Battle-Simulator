package org.example.teams;

import javafx.collections.ObservableList;
import org.example.pokemon.Moves;
import org.example.repositories.MovesRepository;
import org.example.pokemon.Pokemon;


import java.util.Objects;

public class Team {

    private ObservableList<Pokemon> pokemons;
    private int activePokemonIndex;

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
    public Moves getFirstAvailableMove() {
        for (Pokemon pokemon : pokemons) {
            for (Moves move : pokemon.getMoves()) {
                if (move.getCurrentPP() > 0) {
                    return move;
                }
            }
        }
        return MovesRepository.getMoveByName("Struggle");
    }
    public Pokemon getActivePokemon() {
        if (activePokemonIndex >= 0 && activePokemonIndex < pokemons.size()) {
            return pokemons.get(activePokemonIndex);
        }
        throw new NoActivePokemonException("No active Pokemon in team");
    }
    public boolean hasAlivePokemon() {
        return pokemons.stream().anyMatch(pokemon -> pokemon.getStats().getHp() > 0);
    }
    public boolean containsPokemon(Pokemon pokemon) {
        return pokemons.contains(pokemon);
    }
}
