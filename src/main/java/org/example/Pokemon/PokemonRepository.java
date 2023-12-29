package org.example.Pokemon;

import java.util.*;

public class PokemonRepository {

    private static Map<String, Pokemon> pokemonMap = new HashMap<>();

    static {
        pokemonMap.put("Pikachu", new Pokemon("Pikachu", Collections.singletonList(PokeTyping.ELECTRIC), new Stats(35, 55, 40, 50, 50, 90), new ArrayList<>()));
        pokemonMap.put("Cubone", new Pokemon("Cubone", Collections.singletonList(PokeTyping.GROUND), new Stats(50, 50, 95, 40, 50, 35), new ArrayList<>()));
        pokemonMap.put("Snorlax", new Pokemon("Snorlax", Collections.singletonList(PokeTyping.NORMAL), new Stats(160, 110, 65, 65, 110, 30), new ArrayList<>()));

        pokemonMap.put("Bulbasaur", new Pokemon("Bulbasaur", Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(45,49,49,65,65,45), new ArrayList<>()));
        pokemonMap.put("Ivysaur", new Pokemon("Ivysaur",Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(60,62,63,80,80,60), new ArrayList<>()));
        pokemonMap.put("Venusaur", new Pokemon("Venusaur", Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(80,82,83, 100,100,80), new ArrayList<>()));

        pokemonMap.put("Charmander", new Pokemon("Charmander", Collections.singletonList(PokeTyping.FIRE), new Stats(39,52,43,60,50,65), new ArrayList<>()));
        // Charmeleon
        pokemonMap.put("Charizard", new Pokemon("Charizard", Arrays.asList(PokeTyping.FIRE, PokeTyping.FLYING), new Stats(78,84,78,109,85,100), new ArrayList<>()));

        //Squirtle
        //Wartortle
        pokemonMap.put("Blastoise", new Pokemon("Blastoise", Collections.singletonList(PokeTyping.WATER), new Stats(79,83,100, 85, 105,78), new ArrayList<>()));

    }

    public static Pokemon getPokemon(String name) {
        return pokemonMap.get(name);
    }
}
