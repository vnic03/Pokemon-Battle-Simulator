package org.example.teams;

import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.pokemon.Moves;
import org.example.repositories.MovesRepository;
import org.example.pokemon.Pokemon;


import java.util.Objects;

public class Team {

    private static final Logger LOGGER = LogManager.getLogger(Team.class);

    private ObservableList<Pokemon> pokemons;
    private int activePokemonIndex = -1;

    public Team(ObservableList<Pokemon> pokemons) {
        setPokemons(pokemons);
        this.activePokemonIndex = 0;
    }

    public ObservableList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(ObservableList<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public void addPokemon(Pokemon pokemon) {
        if (pokemon == null) {
            LOGGER.warn("Check Pokemon");
            throw new IllegalArgumentException("Pokémon is null");
        }
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
        if (index < 0 || index >= pokemons.size() || pokemons.get(index).getStats().getHp() <= 0) {
            LOGGER.warn("Check Pokemon-Index: {}", index);
            throw new IllegalArgumentException("Invalid index or the selected Pokémon is not able to battle.");
        }
        this.activePokemonIndex = index;
    }

    public Moves getFirstAvailableMove() {
        if (activePokemonIndex >= 0 && activePokemonIndex < pokemons.size()) {

            return pokemons.get(activePokemonIndex).getMoves().stream()
                    .filter(move -> move.getCurrentPP() > 0)
                    .findFirst()
                    .orElse(MovesRepository.getMoveByName("Struggle"));

        } else {
            return MovesRepository.getMoveByName("Struggle");
        }
    }

    public Pokemon getActivePokemon() {
        if (activePokemonIndex >= 0 && activePokemonIndex < pokemons.size()) {
            return pokemons.get(activePokemonIndex);
        } else {
            LOGGER.warn("Check Active Pokemon(Index) {}", activePokemonIndex);
            throw new NoActivePokemonException("No active Pokemon in team");
        }
    }

    public boolean hasAlivePokemon() {
        return pokemons.stream()
                .filter(Objects::nonNull)
                .anyMatch(pokemon -> pokemon.getStats().getHp() > 0);
    }

    public boolean containsPokemon(Pokemon pokemon) {
        return pokemons.contains(pokemon);
    }
}
