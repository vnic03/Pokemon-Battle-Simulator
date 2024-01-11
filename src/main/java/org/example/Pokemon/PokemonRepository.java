package org.example.Pokemon;

import java.util.*;

public class PokemonRepository {

    private final static Map<String, Pokemon> pokemonMap = new HashMap<>();
    private final static AbilityRepository aR = new AbilityRepository();


    static {

        pokemonMap.put("Pikachu", new Pokemon("Pikachu", Collections.singletonList(PokeTyping.ELECTRIC), new Stats(110, 75, 60, 70, 70, 110),Nature.SERIOUS, Arrays.asList(aR.getAbility("Static")),"/sprites/Pikachu.jpg" ,new ArrayList<>()));


        pokemonMap.put("Cubone", new Pokemon("Cubone", Collections.singletonList(PokeTyping.GROUND), new Stats(125, 70, 115, 60, 70, 55),Nature.SERIOUS, Arrays.asList(aR.getAbility("Battle Armor")),"/sprites/Cubone.png" ,new ArrayList<>()));
        pokemonMap.put("Snorlax", new Pokemon("Snorlax", Collections.singletonList(PokeTyping.NORMAL), new Stats(235, 130, 85, 85, 130, 50),Nature.SERIOUS, Arrays.asList(aR.getAbility("Thick Fat")) ,"/sprites/Snorlax.jpg" ,new ArrayList<>()));


        pokemonMap.put("Bulbasaur", new Pokemon("Bulbasaur", Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(120,69,69,85,85,65),Nature.SERIOUS, Arrays.asList(aR.getAbility("Overgrow"), aR.getAbility("Chlorophyll")),"/sprites/Bulbasaur.png" ,new ArrayList<>()));
        pokemonMap.put("Ivysaur", new Pokemon("Ivysaur",Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(135,82,83,100,100,80), Nature.SERIOUS,  Arrays.asList(aR.getAbility("Overgrow"), aR.getAbility("Chlorophyll")),"/sprites/Ivysaur.png" ,new ArrayList<>()));
        pokemonMap.put("Venusaur", new Pokemon("Venusaur", Arrays.asList(PokeTyping.GRASS, PokeTyping.POISON), new Stats(155,102,103, 120,120,100),Nature.SERIOUS,  Arrays.asList(aR.getAbility("Overgrow"), aR.getAbility("Chlorophyll")),"/sprites/Venusaur.png" ,new ArrayList<>()));


        pokemonMap.put("Charmander", new Pokemon("Charmander", Collections.singletonList(PokeTyping.FIRE), new Stats(114,72,63,80,70,85),Nature.SERIOUS, Arrays.asList(aR.getAbility("Blaze"), aR.getAbility("Solar Power")),"/sprites/Charmander.jpg" ,new ArrayList<>()));
        pokemonMap.put("Charmeleon", new Pokemon("Charmeleon", Collections.singletonList(PokeTyping.FIRE), new Stats(133, 84,78,100,85,100), Nature.SERIOUS, Arrays.asList(aR.getAbility("Blaze"), aR.getAbility("Solar Power")),"/sprites/charmeleon.jpg", new ArrayList<>()));
        pokemonMap.put("Charizard", new Pokemon("Charizard", Arrays.asList(PokeTyping.FIRE, PokeTyping.FLYING), new Stats(153,104,98,129,105,120),Nature.SERIOUS, Arrays.asList(aR.getAbility("Blaze"), aR.getAbility("Solar Power")),"/sprites/Charizard.png",new ArrayList<>()));
        /*
        //Squirtle
        //Wartortle
        pokemonMap.put("Blastoise", new Pokemon("Blastoise", Collections.singletonList(PokeTyping.WATER), new Stats(154,103,120, 105, 125,98),Nature.SERIOUS ,new ArrayList<>()));

        pokemonMap.put("Growlithe", new Pokemon("Growlithe", Collections.singletonList(PokeTyping.FIRE), new Stats(130,90,65,90,70,80),Nature.SERIOUS ,new ArrayList<>()));
        pokemonMap.put("Arcanine", new Pokemon("Arcanine", Collections.singletonList(PokeTyping.FIRE), new Stats(165,130,100,120,100,115),Nature.SERIOUS ,new ArrayList<>()));

        // Machop
        // Machoke
        pokemonMap.put("Machamp", new Pokemon("Machamp", Collections.singletonList(PokeTyping.FIGHTING), new Stats(165, 150,100,85,105, 75),Nature.SERIOUS ,new ArrayList<>()));

        // Gastly
        // Haunter
        */
        pokemonMap.put("Gengar", new Pokemon("Gengar", Arrays.asList(PokeTyping.GHOST, PokeTyping.POISON), new Stats(135, 85, 80,150,95,130),Nature.SERIOUS, Arrays.asList(aR.getAbility("Cursed Body")),"/sprites/Gengar.jpg" ,new ArrayList<>()));

        // Koffing
        //pokemonMap.put("Weezing", new Pokemon("Weezing", Collections.singletonList(PokeTyping.POISON), new Stats(140,110, 140, 105, 90, 80),Nature.SERIOUS ,new ArrayList<>()));


    }

    public static List<String> getAllPokemonNames() {
        return new ArrayList<>(pokemonMap.keySet());
    }

    public static Pokemon getPokemon(String name) {
        return pokemonMap.get(name);
    }
}
