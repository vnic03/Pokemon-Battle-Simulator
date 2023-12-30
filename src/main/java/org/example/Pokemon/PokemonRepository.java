package org.example.Pokemon;

import java.util.*;

public class PokemonRepository {

    private static Map<String, Pokemon> pokemonMap = new HashMap<>();


    static {

        pokemonMap.put("Pikachu", new Pokemon("Pikachu", Collections.singletonList(PokeTyping.ELECTRIC), new Stats(110, 75, 60, 70, 70, 110),null ,new ArrayList<>()));

        pokemonMap.put("Cubone", new Pokemon("Cubone", Collections.singletonList(PokeTyping.GROUND), new Stats(125, 70, 115, 60, 70, 55),null ,new ArrayList<>()));
        pokemonMap.put("Snorlax", new Pokemon("Snorlax", Collections.singletonList(PokeTyping.NORMAL), new Stats(235, 130, 85, 85, 130, 50),null ,new ArrayList<>()));

        pokemonMap.put("Bulbasaur", new Pokemon("Bulbasaur", Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(120,69,69,85,85,65),null ,new ArrayList<>()));
        pokemonMap.put("Ivysaur", new Pokemon("Ivysaur",Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(135,82,83,100,100,80), null,new ArrayList<>()));
        pokemonMap.put("Venusaur", new Pokemon("Venusaur", Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(155,102,103, 120,120,100),null ,new ArrayList<>()));

        pokemonMap.put("Charmander", new Pokemon("Charmander", Collections.singletonList(PokeTyping.FIRE), new Stats(114,72,63,80,70,85),null ,new ArrayList<>()));
        // Charmeleon
        pokemonMap.put("Charizard", new Pokemon("Charizard", Arrays.asList(PokeTyping.FIRE, PokeTyping.FLYING), new Stats(153,104,98,129,105,120),null ,new ArrayList<>()));

        //Squirtle
        //Wartortle
        pokemonMap.put("Blastoise", new Pokemon("Blastoise", Collections.singletonList(PokeTyping.WATER), new Stats(154,103,120, 105, 125,98),null ,new ArrayList<>()));

        pokemonMap.put("Growlithe", new Pokemon("Growlithe", Collections.singletonList(PokeTyping.FIRE), new Stats(130,90,65,90,70,80),null ,new ArrayList<>()));
        pokemonMap.put("Arcanine", new Pokemon("Arcanine", Collections.singletonList(PokeTyping.FIRE), new Stats(165,130,100,120,100,115),null ,new ArrayList<>()));

        // Machop
        // Machoke
        pokemonMap.put("Machamp", new Pokemon("Machamp", Collections.singletonList(PokeTyping.FIGHTING), new Stats(165, 150,100,85,105, 75),null ,new ArrayList<>()));

        // Gastly
        // Haunter
        pokemonMap.put("Gengar", new Pokemon("Gengar", Arrays.asList(PokeTyping.GHOST, PokeTyping.POISON), new Stats(135, 85, 80,150,95,130),null ,new ArrayList<>()));

        // Koffing
        pokemonMap.put("Weezing", new Pokemon("Weezing", Collections.singletonList(PokeTyping.POISON), new Stats(140,110, 140, 105, 90, 80),null ,new ArrayList<>()));
    }

    public static Pokemon getPokemon(String name) {
        Pokemon pokemon = pokemonMap.get(name);

        if (pokemon.getNature() == null) {
            pokemon.setNature(MoveSelector.chooseNature(name));
        }
        return pokemon;
    }


}
