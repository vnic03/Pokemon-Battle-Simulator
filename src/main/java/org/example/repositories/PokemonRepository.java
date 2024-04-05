package org.example.repositories;

import org.example.pokemon.Ability;
import org.example.pokemon.Nature;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.pokemon.stats.Stats;
import org.example.pokemon.ability.AbilityRepository;
import org.example.repositories.api.PokeApiClient;

import java.util.*;
import java.util.stream.Collectors;

public abstract class PokemonRepository {

    private final static Map<String, Pokemon> POKEMON = new HashMap<>();
    private final static AbilityRepository AR = new AbilityRepository();

    static {

        // Bug
        pokemon("Scyther", 123, Typing.BUG, Typing.FLYING, stats(145, 130, 100, 75, 100, 125), Ability.Name.SWARM, Ability.Name.TECHNICIAN);

        // Dark
        pokemon("Houndoom", 229, Typing.DARK, Typing.FIRE, stats(150, 110, 70, 130, 100, 115), Ability.Name.FLASH_FIRE);

        // Dragon
        pokemon("Dragonite", 149, Typing.DRAGON, Typing.FLYING, stats(166, 154, 115, 120, 120, 100), Ability.Name.INNER_FOCUS, Ability.Name.MULTISCALE);

        // Electric
        pokemon("Pikachu", 25, Typing.ELECTRIC, stats(110, 75, 60, 70, 70, 110), Ability.Name.STATIC);

        // Fairy
        pokemon("Granbull", 210, Typing.FAIRY, stats(165, 140, 95, 80, 80, 65), Ability.Name.INTIMIDATE, Ability.Name.ROUGH_SKIN);

        // Fighting
        pokemon("Machamp", 68, Typing.FIGHTING, stats(165, 150, 100, 85, 105, 75), Ability.Name.GUTS, Ability.Name.NO_GUARD);

        // Fire
        pokemon("Charmander", 4, Typing.FIRE, stats(114, 72, 63, 80, 70, 85), Ability.Name.BLAZE, Ability.Name.SOLAR_POWER);
        pokemon("Charmeleon", 5, Typing.FIRE, stats(133, 84, 78, 100, 85, 100), Ability.Name.BLAZE, Ability.Name.SOLAR_POWER);
        pokemon("Charizard", 6, Typing.FIRE, Typing.FLYING, stats(153, 104, 98, 129, 105, 120), Ability.Name.BLAZE, Ability.Name.SOLAR_POWER);

        pokemon("Magmar", 126, Typing.FIRE, stats(140, 115, 77, 120, 105, 103), Ability.Name.FLAME_BODY);

        pokemon("Camerupt", 323, Typing.FIRE, Typing.GROUND, stats(145, 120, 90, 125, 90, 60), Ability.Name.MAGMA_ARMOR, Ability.Name.SOLID_ROCK, Ability.Name.ANGER_POINT);

        // Flying
        pokemon("Corviknight", 823, Typing.FLYING, Typing.STEEL, stats(173, 107, 125, 73, 105, 87), Ability.Name.MIRROR_ARMOR);

        // Ghost
        pokemon("Gengar", 94, Typing.GHOST, Typing.POISON, stats(135, 85, 80, 150, 95, 130), Ability.Name.CURSED_BODY);

        // Grass
        pokemon("Bulbasaur", 1, Typing.GRASS, Typing.POISON, stats(120, 69, 69, 85, 85, 65), Ability.Name.OVERGROW, Ability.Name.CHLOROPHYLL);
        pokemon("Ivysaur", 2, Typing.GRASS, Typing.POISON, stats(135, 82, 83, 100, 100, 80),  Ability.Name.OVERGROW, Ability.Name.CHLOROPHYLL);
        pokemon("Venusaur", 3, Typing.GRASS, Typing.POISON, stats(155, 102, 103, 120, 120, 100),  Ability.Name.OVERGROW, Ability.Name.CHLOROPHYLL);

        pokemon("Exeggutor", 103, Typing.GRASS, Typing.PSYCHIC, stats(170, 115, 105, 145, 95, 75), Ability.Name.CHLOROPHYLL);

        // Ground
        pokemon("Cubone", 104, Typing.GROUND, stats(125, 70, 115, 60, 70, 55), Ability.Name.BATTLE_ARMOR);

        // Ice
        pokemon("Jynx", 124, Typing.ICE, Typing.PSYCHIC, stats(140, 70,55, 135, 115, 115), Ability.Name.DRY_SKIN, Ability.Name.FOREWARN);

        // Normal
        pokemon("Snorlax", 143, Typing.NORMAL, stats(235, 130, 85, 85, 130, 50), Ability.Name.THICK_FAT);

        pokemon("Pidgeot", 18, Typing.NORMAL, Typing.FLYING, stats(158, 100, 95, 90, 90, 121), Ability.Name.KEEN_EYE);

        pokemon("Whismur", 293, Typing.NORMAL, stats(139, 71, 43, 71, 43, 48), Ability.Name.SOUNDPROOF);
        pokemon("Exploud", 295, Typing.NORMAL, stats(179, 111, 83, 111, 93, 88), Ability.Name.SOUNDPROOF, Ability.Name.SCRAPPY);

        // Poison
        pokemon("Crobat", 169, Typing.POISON, Typing.FLYING, stats(160, 110, 100, 90, 100, 150), Ability.Name.INNER_FOCUS);

        // Psychic
        pokemon("Mr.Mime", 122, Typing.PSYCHIC, Typing.FAIRY, stats(115, 65, 85, 120, 140, 110), Ability.Name.FILTER, Ability.Name.SOUNDPROOF, Ability.Name.TECHNICIAN);

        pokemon("Alakazam", 65, Typing.PSYCHIC, stats(130, 70, 65, 155, 115, 140), Ability.Name.SYNCHRONIZE, Ability.Name.INNER_FOCUS, Ability.Name.MAGIC_GUARD);

        // Rock
        pokemon("Tyranitar", 248, Typing.ROCK, Typing.DARK, stats(175, 154, 130, 115, 120, 81), Ability.Name.SAND_STREAM);

        // Steel
        pokemon("Metagross", 376, Typing.STEEL, Typing.PSYCHIC, stats(155, 155, 150,115, 110, 90), Ability.Name.CLEAR_BODY);

        pokemon("Aggron", 306, Typing.STEEL, Typing.ROCK, stats(145, 130, 200, 80, 80, 70), Ability.Name.STURDY);

        pokemon("Steelix", 208, Typing.STEEL, Typing.GROUND, stats(150, 105, 220, 75, 85, 50), Ability.Name.STURDY);

        // Water
        pokemon("Squirtle", 7, Typing.WATER, stats(119, 68, 85, 70, 84, 63), Ability.Name.TORRENT, Ability.Name.RAIN_DISH);
        pokemon("Wartortle", 8, Typing.WATER, stats(134, 83, 100, 85, 100, 78), Ability.Name.TORRENT, Ability.Name.RAIN_DISH);
        pokemon("Blastoise", 9, Typing.WATER, stats(154, 103, 120, 105, 125, 98), Ability.Name.TORRENT, Ability.Name.RAIN_DISH);

        pokemon("Gyarados", 130, Typing.WATER, Typing.FLYING, stats(170, 145, 99, 80, 120, 101), Ability.Name.INTIMIDATE, Ability.Name.MOXIE);

        pokemon("Lapras", 131, Typing.WATER, Typing.ICE, stats(205, 105, 100, 105, 115, 80), Ability.Name.WATER_ABSORB, Ability.Name.SHELL_ARMOR, Ability.Name.HYDRATION);

        pokemon("Milotic", 350, Typing.WATER, stats(170, 80, 99, 120, 145, 101), Ability.Name.MARVEL_SCALE, Ability.Name.COMPETITIVE);

        pokemon("Sharpedo", 319, Typing.WATER, Typing.DARK, stats(145, 140, 60, 115, 60, 115), Ability.Name.ROUGH_SKIN, Ability.Name.SPEED_BOOST);

        pokemon("Greninja", 658, Typing.WATER, Typing.DARK, stats(147, 115, 87, 123, 91, 142), Ability.Name.TORRENT);

        // Legendary Pokemon

        pokemon("Mew", 1000, Typing.PSYCHIC, stats(175, 120, 120, 120, 120, 120), Ability.Name.SYNCHRONIZE);

        pokemon("Rayquaza", 1003, Typing.DRAGON, Typing.FLYING, stats(180, 170, 110, 170, 110, 115), Ability.Name.AIR_LOCK);

        pokemon("Kyogre", 1001, Typing.WATER, stats(175, 120,110, 170,160, 110), Ability.Name.DRIZZLE);
        pokemon("Groudon", 1002, Typing.GROUND, stats(175, 170, 160, 120, 110, 110), Ability.Name.DROUGHT);
    }

    @SuppressWarnings("unused")
    public static List<String> getAllPokemonNames() {
        return new ArrayList<>(POKEMON.keySet());
    }

    public static Pokemon getPokemon(String name) {
        return POKEMON.get(name);
    }

    public static List<Pokemon> getAllPokemons() {
        return new ArrayList<>(POKEMON.values());
    }

    private static void pokemon(
            String name, int dex, Typing typing, Stats stats, Ability.Name... abilityNames)
    {
        List<Typing> typings = Collections.singletonList(typing);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        Pokemon pokemon = new Pokemon(
                name, dex, typings, stats, Nature.SERIOUS, abilities,
                spritePath(name, "front"), spritePath(name, "back"), spritePath(name, "icon"),
                new ArrayList<>()); // moves

        POKEMON.put(name, pokemon);
    }

    private static void pokemon(
            String name, int dex, Typing typing1, Typing typing2, Stats stats, Ability.Name... abilityNames)
    {
        List<Typing> typings = Arrays.asList(typing1, typing2);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        Pokemon pokemon = new Pokemon(
                name, dex, typings, stats, Nature.SERIOUS, abilities,
                spritePath(name, "front"), spritePath(name, "back"), spritePath(name, "icon"),
                new ArrayList<>()); // moves

        POKEMON.put(name, pokemon);
    }

    private static String spritePath(String name, String type) {
        String formattedName = name.toLowerCase().replaceAll("\\W+", "_");
        return "/pokemon/" + formattedName + "/" + type + ".png";
    }

    private static Stats stats(int hp, int a, int d, int spA, int spD, int speed) {
        return new Stats(hp, a, d, spA, spD, speed);
    }

    // Creates the sprites for a Pokémon
    public static void main(String[] args) {
        final String pokemon = "";
        String data = PokeApiClient.fetchData(pokemon);
        PokeApiClient.createSprites(data, pokemon);
    }

    @SuppressWarnings("unused")
    public static List<Pokemon> getAllPokemonByType(Typing type) {
        return POKEMON.values().stream()
                .filter(pokemon -> pokemon.getTyping().contains(type))
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    public static int countPokemon() {
        return POKEMON.size();
    }

    @SuppressWarnings("unused")
    public static void display()
    {
        POKEMON.values().stream().sorted(Comparator.comparingInt(Pokemon::getPokeDex))
                .forEach(pokemon -> System.out.println(pokemon.getPokeDex() + ": " + pokemon.getName()));
    }
}