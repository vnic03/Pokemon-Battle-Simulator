package org.example.pokemon.repositories;

import org.example.pokemon.Ability;
import org.example.pokemon.Nature;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.pokemon.stats.Stats;

import java.util.*;

public class PokemonRepository {

    private final static Map<String, Pokemon> pokemonMap = new HashMap<>();
    private final static AbilityRepository AR = new AbilityRepository();

    static
    {
        pokemon("Pikachu", 25, Typing.ELECTRIC, new Stats(110, 75, 60, 70, 70, 110), new String[]{"Static"});

        pokemon("Cubone", 104, Typing.GROUND, new Stats(125, 70, 115, 60, 70, 55), new String[]{"Battle Armor"});

        pokemon("Snorlax", 143, Typing.NORMAL, new Stats(235, 130, 85, 85, 130, 50), new String[]{"Thick Fat"});

        pokemon("Bulbasaur", 1, Typing.GRASS, Typing.POISON, new Stats(120, 69, 69, 85, 85, 65), new String[]{"Overgrow", "Chlorophyll"});
        pokemon("Ivysaur", 2, Typing.GRASS, Typing.POISON, new Stats(135, 82, 83, 100, 100, 80), new String[]{"Overgrow", "Chlorophyll"});
        pokemon("Venusaur", 3, Typing.GRASS, Typing.POISON, new Stats(155, 102, 103, 120, 120, 100), new String[]{"Overgrow", "Chlorophyll"});

        pokemon("Charmander", 4, Typing.FIRE, new Stats(114, 72, 63, 80, 70, 85), new String[]{"Blaze", "Solar Power"});
        pokemon("Charmeleon", 5, Typing.FIRE, new Stats(133, 84, 78, 100, 85, 100), new String[]{"Blaze", "Solar Power"});
        pokemon("Charizard", 6, Typing.FIRE, Typing.FLYING, new Stats(153, 104, 98, 129, 105, 120), new String[]{"Blaze", "Solar Power"});

        //Squirtle
        //Wartortle
        pokemon("Blastoise", 9, Typing.WATER, new Stats(154, 103, 120, 105, 125, 98), new String[]{"Torrent"});

        // Machop
        // Machoke
        pokemon("Machamp", 68, Typing.FIGHTING, new Stats(165, 150, 100, 85, 105, 75), new String[]{"Guts", "No Guard"});

        // Gastly
        // Haunter
        pokemon("Gengar", 94, Typing.GHOST, Typing.POISON, new Stats(135, 85, 80, 150, 95, 130), new String[]{"Cursed Body"});

        pokemon("Scyther", 123, Typing.BUG, Typing.FLYING, new Stats(145, 130, 100, 75, 100, 125), new String[]{"Swarm", "Technician"});

        pokemon("Magmar", 126, Typing.FIRE, new Stats(140, 115, 77, 120, 105, 103), new String[]{"Flame Body"});

        pokemon("Pidgeot", 18, Typing.NORMAL, Typing.FLYING, new Stats(158, 100, 95, 90, 90, 121), new String[]{"Keen Eye"});

        pokemon("Mr.Mime", 122, Typing.PSYCHIC, Typing.FAIRY, new Stats(115, 65, 85, 120, 140, 110), new String[]{"Filter", "Soundproof", "Technician"});

        pokemon("Whismur", 293, Typing.NORMAL, new Stats(139, 71, 43, 71, 43, 48), new String[]{"Soundproof"});
        pokemon("Exploud", 295, Typing.NORMAL, new Stats(179, 111, 83, 111, 93, 88), new String[]{"Soundproof", "Scrappy"});

        pokemon("Tyranitar", 248, Typing.ROCK, Typing.DARK, new Stats(175, 154, 130, 115, 120, 81), new String[]{"Sand Stream"});

        pokemon("Gyarados", 130, Typing.WATER, Typing.FLYING, new Stats(170, 145, 99, 80, 120, 101), new String[]{"Intimidate", "Moxie"});


        // Legendary Pokemon

        pokemon("Mew", 151, Typing.PSYCHIC, new Stats(175, 120, 120, 120, 120, 120), new String[]{"Synchro"});

        pokemon("Rayquaza", 384, Typing.DRAGON, Typing.FLYING, new Stats(180, 170, 110, 170, 110, 115), new String[]{"Air Lock"});
    }
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
            String name, int dex, Typing typing, Stats stats, String[] abilityNames)
    {
        List<Typing> typings = Collections.singletonList(typing);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        String frontSprite = spritePath(name, "front");
        String backSprite = spritePath(name, "back");
        String iconSprite = spritePath(name, "icon");

        Pokemon pokemon = new Pokemon(
                name, dex, typings, stats, Nature.SERIOUS, abilities,
                frontSprite, backSprite, iconSprite,
                new ArrayList<>());
        pokemonMap.put(name, pokemon);
    }

    private static void pokemon(
            String name, int dex, Typing typing1, Typing typing2,Stats stats, String[] abilityNames)
    {
        List<Typing> typings = Arrays.asList(typing1, typing2);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        String frontSprite = spritePath(name, "front");
        String backSprite = spritePath(name, "back");
        String iconSprite = spritePath(name, "icon");

        Pokemon pokemon = new Pokemon(
                name, dex, typings, stats, Nature.SERIOUS, abilities,
                frontSprite, backSprite, iconSprite,
                new ArrayList<>());
        pokemonMap.put(name, pokemon);
    }

    private static String spritePath(String name, String type) {
        String formattedName = name.toLowerCase().replaceAll("\\W+", "_");
        return "/pokemonSprites/" + formattedName + "/" + type + ".png";
    }
}