package org.example.repositories;

import org.example.pokemon.Ability;
import org.example.pokemon.Nature;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.pokemon.stats.Stats;

import java.util.*;
import java.util.stream.Collectors;

public class PokemonRepository {

    private final static Map<String, Pokemon> pokemonMap = new HashMap<>();
    private final static AbilityRepository AR = new AbilityRepository();

    static {

        // Bug
        pokemon("Scyther", 123, Typing.BUG, Typing.FLYING, stats(145, 130, 100, 75, 100, 125), "Swarm", "Technician");

        // Dark
        pokemon("Houndoom", 229, Typing.DARK, Typing.FIRE, stats(150, 110, 70, 130, 100, 115), "Flash Fire");

        // Dragon
        pokemon("Dragonite", 149, Typing.DRAGON, Typing.FLYING, stats(166, 154, 115, 120, 120, 100), "Inner Focus", "Multiscale");

        // Electric
        pokemon("Pikachu", 25, Typing.ELECTRIC, stats(110, 75, 60, 70, 70, 110), "Static");

        // Fairy
        pokemon("Granbull", 210, Typing.FAIRY, stats(165, 140, 95, 80, 80, 65), "Intimidate", "Rough Skin");

        // Fighting
        pokemon("Machamp", 68, Typing.FIGHTING, stats(165, 150, 100, 85, 105, 75), "Guts", "No Guard");

        // Fire
        pokemon("Charmander", 4, Typing.FIRE, stats(114, 72, 63, 80, 70, 85), "Blaze", "Solar Power");
        pokemon("Charmeleon", 5, Typing.FIRE, stats(133, 84, 78, 100, 85, 100), "Blaze", "Solar Power");
        pokemon("Charizard", 6, Typing.FIRE, Typing.FLYING, stats(153, 104, 98, 129, 105, 120), "Blaze", "Solar Power");

        pokemon("Magmar", 126, Typing.FIRE, stats(140, 115, 77, 120, 105, 103), "Flame Body");

        pokemon("Camerupt", 323, Typing.FIRE, Typing.GROUND, stats(145, 120, 90, 125, 90, 60), "Magma Armor", "Solid Rock", "Anger Point");

        // Flying

        // Ghost
        pokemon("Gengar", 94, Typing.GHOST, Typing.POISON, stats(135, 85, 80, 150, 95, 130), "Cursed Body");

        // Grass
        pokemon("Bulbasaur", 1, Typing.GRASS, Typing.POISON, stats(120, 69, 69, 85, 85, 65), "Overgrow", "Chlorophyll");
        pokemon("Ivysaur", 2, Typing.GRASS, Typing.POISON, stats(135, 82, 83, 100, 100, 80), "Overgrow", "Chlorophyll");
        pokemon("Venusaur", 3, Typing.GRASS, Typing.POISON, stats(155, 102, 103, 120, 120, 100), "Overgrow", "Chlorophyll");

        pokemon("Exeguttor", 103, Typing.GRASS, Typing.PSYCHIC, stats(170, 115, 105, 145, 95, 75), "Chlorophyll");

        // Ground
        pokemon("Cubone", 104, Typing.GROUND, stats(125, 70, 115, 60, 70, 55), "Battle Armor");

        // Ice
        pokemon("Jynx", 124, Typing.ICE, Typing.PSYCHIC, stats(140, 70,55, 135, 115, 115), "Dry Skin", "Fore Warn");

        // Normal
        pokemon("Snorlax", 143, Typing.NORMAL, stats(235, 130, 85, 85, 130, 50), "Thick Fat");

        pokemon("Pidgeot", 18, Typing.NORMAL, Typing.FLYING, stats(158, 100, 95, 90, 90, 121), "Keen Eye");

        pokemon("Whismur", 293, Typing.NORMAL, stats(139, 71, 43, 71, 43, 48), "Soundproof");
        pokemon("Exploud", 295, Typing.NORMAL, stats(179, 111, 83, 111, 93, 88), "Soundproof", "Scrappy");

        // Poison
        pokemon("Crobat", 169, Typing.POISON, Typing.FLYING, stats(160,110, 100, 90, 100, 150), "Inner Focus");

        // Psychic
        pokemon("Mr.Mime", 122, Typing.PSYCHIC, Typing.FAIRY, stats(115, 65, 85, 120, 140, 110), "Filter", "Soundproof", "Technician");

        // Rock
        pokemon("Tyranitar", 248, Typing.ROCK, Typing.DARK, stats(175, 154, 130, 115, 120, 81), "Sand Stream");

        // Steel
        pokemon("Metagross", 376, Typing.STEEL, Typing.PSYCHIC, stats(155, 155, 150,115, 110, 90), "Clear Body");

        // Water
        pokemon("Squirtle", 7, Typing.WATER, stats(119, 68, 85, 70, 84, 63), "Torrent", "Rain Dish");
        pokemon("Wartortle", 8, Typing.WATER, stats(134, 83, 100, 85, 100, 78), "Torrent", "Rain Dish");
        pokemon("Blastoise", 9, Typing.WATER, stats(154, 103, 120, 105, 125, 98), "Torrent", "Rain Dish");

        pokemon("Gyarados", 130, Typing.WATER, Typing.FLYING, stats(170, 145, 99, 80, 120, 101), "Intimidate", "Moxie");

        pokemon("Lapras", 131, Typing.WATER, Typing.ICE, stats(205, 105, 100, 105, 115, 80), "Water Absorb", "Shell Armor", "Hydration");

        pokemon("Milotic", 350, Typing.WATER, stats(170, 80, 99, 120, 145, 101), "Marvel Scale", "Competitive");

        pokemon("Sharpedo", 319, Typing.WATER, Typing.DARK, stats(145, 140, 60, 115, 60, 115), "Rough Skin", "Speed Boost");

        // Legendary Pokemon

        pokemon("Mew", 151, Typing.PSYCHIC, stats(175, 120, 120, 120, 120, 120), "Synchro");

        pokemon("Rayquaza", 384, Typing.DRAGON, Typing.FLYING, stats(180, 170, 110, 170, 110, 115), "Air Lock");

        pokemon("Kyogre", 382, Typing.WATER, stats(175, 120,110, 170,160, 110), "Drizzle");
        pokemon("Groudon", 383, Typing.GROUND, stats(175, 170, 160, 120, 110, 110), "Drought");
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
        return "/pokemon/" + formattedName + "/" + type + ".png";
    }

    private static Stats stats(int hp, int a, int d, int spA, int spD, int speed) {
        return new Stats(hp, a, d, spA, spD, speed);
    }

    @SuppressWarnings("unused")
    public static List<Pokemon> getAllPokemonByType(Typing type) {
        return pokemonMap.values().stream()
                .filter(pokemon -> pokemon.getTyping().contains(type))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    public static int countPokemon() {
        return pokemonMap.size();
    }
}