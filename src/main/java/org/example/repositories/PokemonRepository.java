package org.example.repositories;

import org.example.pokemon.Ability;
import org.example.pokemon.Nature;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.pokemon.stats.Stats;

import java.util.*;

public class PokemonRepository {

    private final static Map<String, Pokemon> pokemonMap = new HashMap<>();
    private final static AbilityRepository AR = new AbilityRepository();

    static {
        pokemon("Pikachu", 25, Typing.ELECTRIC, new Stats(110, 75, 60, 70, 70, 110), "Static");

        pokemon("Cubone", 104, Typing.GROUND, new Stats(125, 70, 115, 60, 70, 55), "Battle Armor");

        pokemon("Snorlax", 143, Typing.NORMAL, new Stats(235, 130, 85, 85, 130, 50), "Thick Fat");

        pokemon("Bulbasaur", 1, Typing.GRASS, Typing.POISON, new Stats(120, 69, 69, 85, 85, 65), "Overgrow", "Chlorophyll");
        pokemon("Ivysaur", 2, Typing.GRASS, Typing.POISON, new Stats(135, 82, 83, 100, 100, 80), "Overgrow", "Chlorophyll");
        pokemon("Venusaur", 3, Typing.GRASS, Typing.POISON, new Stats(155, 102, 103, 120, 120, 100), "Overgrow", "Chlorophyll");

        pokemon("Charmander", 4, Typing.FIRE, new Stats(114, 72, 63, 80, 70, 85), "Blaze", "Solar Power");
        pokemon("Charmeleon", 5, Typing.FIRE, new Stats(133, 84, 78, 100, 85, 100), "Blaze", "Solar Power");
        pokemon("Charizard", 6, Typing.FIRE, Typing.FLYING, new Stats(153, 104, 98, 129, 105, 120), "Blaze", "Solar Power");

        pokemon("Squirtle", 7, Typing.WATER, new Stats(119, 68, 85, 70, 84, 63), "Torrent", "Rain Dish");
        pokemon("Wartortle", 8, Typing.WATER, new Stats(134, 83, 100, 85, 100, 78), "Torrent", "Rain Dish");
        pokemon("Blastoise", 9, Typing.WATER, new Stats(154, 103, 120, 105, 125, 98), "Torrent", "Rain Dish");

        // Machop
        // Machoke
        pokemon("Machamp", 68, Typing.FIGHTING, new Stats(165, 150, 100, 85, 105, 75), "Guts", "No Guard");

        // Gastly
        // Haunter
        pokemon("Gengar", 94, Typing.GHOST, Typing.POISON, new Stats(135, 85, 80, 150, 95, 130), "Cursed Body");

        pokemon("Scyther", 123, Typing.BUG, Typing.FLYING, new Stats(145, 130, 100, 75, 100, 125), "Swarm", "Technician");

        pokemon("Magmar", 126, Typing.FIRE, new Stats(140, 115, 77, 120, 105, 103), "Flame Body");

        pokemon("Pidgeot", 18, Typing.NORMAL, Typing.FLYING, new Stats(158, 100, 95, 90, 90, 121), "Keen Eye");

        pokemon("Mr.Mime", 122, Typing.PSYCHIC, Typing.FAIRY, new Stats(115, 65, 85, 120, 140, 110), "Filter", "Soundproof", "Technician");

        pokemon("Whismur", 293, Typing.NORMAL, new Stats(139, 71, 43, 71, 43, 48), "Soundproof");
        pokemon("Exploud", 295, Typing.NORMAL, new Stats(179, 111, 83, 111, 93, 88), "Soundproof", "Scrappy");

        pokemon("Tyranitar", 248, Typing.ROCK, Typing.DARK, new Stats(175, 154, 130, 115, 120, 81), "Sand Stream");

        pokemon("Gyarados", 130, Typing.WATER, Typing.FLYING, new Stats(170, 145, 99, 80, 120, 101), "Intimidate", "Moxie");

        pokemon("Metagross", 376, Typing.STEEL, Typing.PSYCHIC, new Stats(155, 155, 150,115, 110, 90), "Clear Body");

        pokemon("Lapras", 131, Typing.WATER, Typing.ICE, new Stats(205, 105, 100, 105, 115, 80), "Water Absorb", "Shell Armor", "Hydration");

        pokemon("Dragonite", 149, Typing.DRAGON, Typing.FLYING, new Stats(166, 154, 115, 120, 120, 100), "Inner Focus", "Multiscale");

        pokemon("Milotic", 350, Typing.WATER, new Stats(170, 80, 99, 120, 145, 101), "Marvel Scale", "Competitive");

        pokemon("Camerupt", 323, Typing.FIRE, Typing.GROUND, new Stats(145, 120, 90, 125, 90, 60), "Magma Armor", "Solid Rock", "Anger Point");
        pokemon("Sharpedo", 319, Typing.WATER, Typing.DARK, new Stats(145, 140, 60, 115, 60, 115), "Rough Skin", "Speed Boost");


        // Legendary Pokemon

        pokemon("Mew", 151, Typing.PSYCHIC, new Stats(175, 120, 120, 120, 120, 120), "Synchro");

        pokemon("Rayquaza", 384, Typing.DRAGON, Typing.FLYING, new Stats(180, 170, 110, 170, 110, 115), "Air Lock");
    }

    @SuppressWarnings("unused")
    public static List<String> getAllPokemonNames() {
        return new ArrayList<>(pokemonMap.keySet());
    }

    public static Pokemon getPokemon(String name) {
        return pokemonMap.get(name);
    }

    public static List<Pokemon> getAllPokemons() {
        return new ArrayList<>(pokemonMap.values());
    }

    private static void pokemon(
            String name, int dex, Typing typing, Stats stats, String... abilityNames)
    {
        List<Typing> typings = Collections.singletonList(typing);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        Pokemon pokemon = new Pokemon(
                name, dex, typings, stats, Nature.SERIOUS, abilities,
                spritePath(name, "front"), spritePath(name, "back"), spritePath(name, "icon"),
                new ArrayList<>()); // moves
        pokemonMap.put(name, pokemon);
    }

    private static void pokemon(
            String name, int dex, Typing typing1, Typing typing2, Stats stats, String... abilityNames)
    {
        List<Typing> typings = Arrays.asList(typing1, typing2);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        Pokemon pokemon = new Pokemon(
                name, dex, typings, stats, Nature.SERIOUS, abilities,
                spritePath(name, "front"), spritePath(name, "back"), spritePath(name, "icon"),
                new ArrayList<>()); // moves
        pokemonMap.put(name, pokemon);
    }

    private static String spritePath(String name, String type) {
        String formattedName = name.toLowerCase().replaceAll("\\W+", "_");
        return "/pokemonSprites/" + formattedName + "/" + type + ".png";
    }

    @SuppressWarnings("unused")
    public static int countPokemon() {
        return pokemonMap.size();
    }
}