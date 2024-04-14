package org.example.repositories;

import org.example.pokemon.ability.Ability;
import org.example.pokemon.Nature;
import org.example.pokemon.Typing;
import org.example.pokemon.Pokemon;
import org.example.pokemon.stats.Stats;
import org.example.pokemon.ability.AbilityRepository;

import java.util.*;
import java.util.stream.Collectors;

public abstract class PokemonRepository {

    private final static Map<String, Pokemon> POKEMON = new HashMap<>();

    private final static AbilityRepository AR = new AbilityRepository();

    static {
         /*
            BUG
         */
        pokemon("Scyther", 123, Typing.BUG, Typing.FLYING, stats(145, 130, 100, 75, 100, 125), Ability.Name.SWARM, Ability.Name.TECHNICIAN);

        pokemon("Butterfree", 12, Typing.BUG, Typing.FLYING, stats(135, 65, 70, 110, 100, 90), Ability.Name.COMPOUND_EYES, Ability.Name.TINTED_LENS);

        pokemon("Beedrill", 15, Typing.BUG, Typing.POISON, stats(140, 110, 60, 65, 100, 95), Ability.Name.SWARM);

        pokemon("Ledian", 166, Typing.BUG, Typing.FLYING, stats(130, 55, 70, 75, 130, 105), Ability.Name.SWARM, Ability.Name.EARLY_BIRD);


         /*
            DARK
         */
        pokemon("Houndoom", 229, Typing.DARK, Typing.FIRE, stats(150, 110, 70, 130, 100, 115), Ability.Name.FLASH_FIRE);

        pokemon("Umbreon", 197, Typing.DARK, stats(170, 85, 130, 80, 150, 85), Ability.Name.SYNCHRONIZE, Ability.Name.INNER_FOCUS);


         /*
            DRAGON
         */
        pokemon("Dragonite", 149, Typing.DRAGON, Typing.FLYING, stats(166, 154, 115, 120, 120, 100), Ability.Name.INNER_FOCUS, Ability.Name.MULTISCALE);

        pokemon("Gible", 443, Typing.DRAGON, Typing.GROUND, stats(133, 90, 65, 60, 65, 62), Ability.Name.SAND_VEIL, Ability.Name.ROUGH_SKIN);
        pokemon("Gabite", 444, Typing.DRAGON, Typing.GROUND, stats(143, 110, 85, 70, 75, 102), Ability.Name.SAND_VEIL, Ability.Name.ROUGH_SKIN);
        pokemon("Garchomp", 445, Typing.DRAGON, Typing.GROUND, stats(183, 150, 115, 100, 105, 122), Ability.Name.SAND_VEIL, Ability.Name.ROUGH_SKIN);


         /*
            ELECTRIC
         */
        pokemon("Pikachu", 25, Typing.ELECTRIC, stats(110, 75, 60, 70, 70, 110), Ability.Name.STATIC, Ability.Name.LIGHTNING_ROD);
        pokemon("Raichu", 26, Typing.ELECTRIC, stats(135, 110, 75, 110, 100, 130), Ability.Name.STATIC, Ability.Name.LIGHTNING_ROD);

        pokemon("Zebstrika", 523, Typing.ELECTRIC, stats(150, 120, 83, 100, 83, 136), Ability.Name.LIGHTNING_ROD, Ability.Name.SAP_SIPPER);

        pokemon("Jolteon", 135, Typing.ELECTRIC, stats(140, 85, 80, 130, 115, 150), Ability.Name.VOLT_ABSORB);


         /*
            FAIRY
         */
        pokemon("Granbull", 210, Typing.FAIRY, stats(165, 140, 95, 80, 80, 65), Ability.Name.INTIMIDATE, Ability.Name.ROUGH_SKIN);

        pokemon("Clefairy", 35, Typing.FAIRY, stats(145, 65, 68, 80, 85, 55), Ability.Name.MAGIC_GUARD);

        pokemon("Sylveon", 700, Typing.FAIRY, stats(170, 85, 85, 130, 150, 80), Ability.Name.PIXILATE);


         /*
            FIGHTING
         */
        pokemon("Machop", 66, Typing.FIGHTING, stats(145, 100, 70, 55, 55, 55), Ability.Name.GUTS, Ability.Name.NO_GUARD);
        pokemon("Machoke", 67, Typing.FIGHTING, stats(155, 120, 90, 70, 80, 65), Ability.Name.GUTS, Ability.Name.NO_GUARD);
        pokemon("Machamp", 68, Typing.FIGHTING, stats(165, 150, 100, 85, 105, 75), Ability.Name.GUTS, Ability.Name.NO_GUARD);

        pokemon("Mankey", 56, Typing.FIGHTING, stats(115, 100, 55, 55, 65, 90), Ability.Name.ANGER_POINT);
        pokemon("Primeape", 57, Typing.FIGHTING, stats(140, 125, 80, 80, 90, 115), Ability.Name.ANGER_POINT);


         /*
            FIRE
         */
        pokemon("Charmander", 4, Typing.FIRE, stats(114, 72, 63, 80, 70, 85), Ability.Name.BLAZE, Ability.Name.SOLAR_POWER);
        pokemon("Charmeleon", 5, Typing.FIRE, stats(133, 84, 78, 100, 85, 100), Ability.Name.BLAZE, Ability.Name.SOLAR_POWER);
        pokemon("Charizard", 6, Typing.FIRE, Typing.FLYING, stats(153, 104, 98, 129, 105, 120), Ability.Name.BLAZE, Ability.Name.SOLAR_POWER);

        pokemon("Magmar", 126, Typing.FIRE, stats(140, 115, 77, 120, 105, 103), Ability.Name.FLAME_BODY);

        pokemon("Camerupt", 323, Typing.FIRE, Typing.GROUND, stats(145, 120, 90, 125, 90, 60), Ability.Name.MAGMA_ARMOR, Ability.Name.SOLID_ROCK, Ability.Name.ANGER_POINT);

        pokemon("Vulpix", 37, Typing.FIRE, stats(113, 61, 60, 70, 85, 85), Ability.Name.FLASH_FIRE, Ability.Name.DROUGHT);
        pokemon("Ninetales", 38, Typing.FIRE, stats(148, 96, 95, 101, 120, 120), Ability.Name.FLASH_FIRE, Ability.Name.DROUGHT);

        pokemon("Growlithe", 58, Typing.FIRE, stats(130, 90, 65, 90, 70, 80), Ability.Name.INTIMIDATE, Ability.Name.FLASH_FIRE);
        pokemon("Arcanine", 59, Typing.FIRE, stats(165, 130, 100, 120, 100, 115), Ability.Name.INTIMIDATE, Ability.Name.FLASH_FIRE);

        pokemon("Flareon", 136, Typing.FIRE, stats(140, 150, 80, 115, 130, 85), Ability.Name.FLASH_FIRE, Ability.Name.GUTS);


         /*
            FLYING
         */
        pokemon("Corviknight", 823, Typing.FLYING, Typing.STEEL, stats(173, 107, 125, 73, 105, 87), Ability.Name.MIRROR_ARMOR);


         /*
            GHOST
         */
        pokemon("Gastly", 92, Typing.GHOST, Typing.POISON, stats(105, 55, 50, 120, 55, 100), Ability.Name.LEVITATE);
        pokemon("Haunter", 93, Typing.GHOST, Typing.POISON, stats(120, 70, 65, 135, 75, 115), Ability.Name.LEVITATE);
        pokemon("Gengar", 94, Typing.GHOST, Typing.POISON, stats(135, 85, 80, 150, 95, 130), Ability.Name.CURSED_BODY);


         /*
            GRASS
         */
        pokemon("Bulbasaur", 1, Typing.GRASS, Typing.POISON, stats(120, 69, 69, 85, 85, 65), Ability.Name.OVERGROW, Ability.Name.CHLOROPHYLL);
        pokemon("Ivysaur", 2, Typing.GRASS, Typing.POISON, stats(135, 82, 83, 100, 100, 80),  Ability.Name.OVERGROW, Ability.Name.CHLOROPHYLL);
        pokemon("Venusaur", 3, Typing.GRASS, Typing.POISON, stats(155, 102, 103, 120, 120, 100),  Ability.Name.OVERGROW, Ability.Name.CHLOROPHYLL);

        pokemon("Exeggutor", 103, Typing.GRASS, Typing.PSYCHIC, stats(170, 115, 105, 145, 95, 75), Ability.Name.CHLOROPHYLL);

        pokemon("Leafeon", 470, Typing.GRASS, stats(140, 130, 150, 80, 85, 115), Ability.Name.CHLOROPHYLL);


         /*
            GROUND
         */
        pokemon("Cubone", 104, Typing.GROUND, stats(125, 70, 115, 60, 70, 55), Ability.Name.BATTLE_ARMOR);

        pokemon("Sandshrew", 27, Typing.GROUND, stats(125, 95, 105, 40, 50, 60), Ability.Name.SAND_VEIL, Ability.Name.SAND_RUSH);
        pokemon("Sandslash", 28, Typing.GROUND, stats(150, 120, 130, 65, 75, 85), Ability.Name.SAND_VEIL, Ability.Name.SAND_RUSH);


         /*
            ICE
         */
        pokemon("Jynx", 124, Typing.ICE, Typing.PSYCHIC, stats(140, 70,55, 135, 115, 115), Ability.Name.DRY_SKIN, Ability.Name.FOREWARN);

        pokemon("Glaceon", 471, Typing.ICE, stats(140, 80, 130, 150, 115, 85), Ability.Name.ICE_BODY);


         /*
            NORMAL
         */
        pokemon("Snorlax", 143, Typing.NORMAL, stats(235, 130, 85, 85, 130, 50), Ability.Name.THICK_FAT);

        pokemon("Pidgeot", 18, Typing.NORMAL, Typing.FLYING, stats(158, 100, 95, 90, 90, 121), Ability.Name.KEEN_EYE);

        pokemon("Whismur", 293, Typing.NORMAL, stats(139, 71, 43, 71, 43, 48), Ability.Name.SOUNDPROOF);
        pokemon("Exploud", 295, Typing.NORMAL, stats(179, 111, 83, 111, 93, 88), Ability.Name.SOUNDPROOF, Ability.Name.SCRAPPY);

        pokemon("Rattata", 19, Typing.NORMAL, stats(105, 76, 55, 45, 55, 92), Ability.Name.GUTS);
        pokemon("Raticate", 20, Typing.NORMAL, stats(130, 101, 80, 70, 90, 117), Ability.Name.GUTS);

        pokemon("Spearow", 21, Typing.NORMAL, Typing.FLYING, stats(115, 80, 50, 51, 51, 90), Ability.Name.KEEN_EYE);
        pokemon("Fearow", 22, Typing.NORMAL, Typing.FLYING, stats(140, 110, 85, 81, 81, 120), Ability.Name.KEEN_EYE);

        pokemon("Kangaskhan", 115, Typing.NORMAL, stats(180, 115, 100, 60, 100, 110), Ability.Name.SCRAPPY, Ability.Name.INNER_FOCUS);

        pokemon("Eevee", 133, Typing.NORMAL, stats(130, 75, 70, 65, 85, 75), Ability.Name.ADAPTABILITY);


         /*
            POISON
         */
        pokemon("Zubat", 41, Typing.POISON, Typing.FLYING, stats(115, 65, 55, 50, 60, 75), Ability.Name.INNER_FOCUS);
        pokemon("Golbat", 42, Typing.POISON, Typing.FLYING, stats(150, 100, 90, 85, 95, 110), Ability.Name.INNER_FOCUS);
        pokemon("Crobat", 169, Typing.POISON, Typing.FLYING, stats(160, 110, 100, 90, 100, 150), Ability.Name.INNER_FOCUS);

        pokemon("Ekans", 23, Typing.POISON, stats(110, 80, 64, 60, 74, 75), Ability.Name.INTIMIDATE);
        pokemon("Arbok", 24, Typing.POISON, stats(135, 115, 89, 85, 99, 100), Ability.Name.INTIMIDATE);

        pokemon("Nidoqueen", 31, Typing.POISON, Typing.GROUND, stats(165, 112, 107, 95, 105, 96), Ability.Name.POISON_POINT);
        pokemon("Nidoking", 34, Typing.POISON, Typing.GROUND, stats(156, 122, 97, 105, 95, 105), Ability.Name.POISON_POINT);

         /*
            PSYCHIC
         */
        pokemon("Mr.Mime", 122, Typing.PSYCHIC, Typing.FAIRY, stats(115, 65, 85, 120, 140, 110), Ability.Name.FILTER, Ability.Name.SOUNDPROOF, Ability.Name.TECHNICIAN);

        pokemon("Abra", 63, Typing.PSYCHIC, stats(100, 40, 35, 125, 75, 110), Ability.Name.SYNCHRONIZE, Ability.Name.INNER_FOCUS, Ability.Name.MAGIC_GUARD);
        pokemon("Kadabra", 64, Typing.PSYCHIC, stats(115, 55, 50, 140, 90, 125), Ability.Name.SYNCHRONIZE, Ability.Name.INNER_FOCUS, Ability.Name.MAGIC_GUARD);
        pokemon("Alakazam", 65, Typing.PSYCHIC, stats(130, 70, 65, 155, 115, 140), Ability.Name.SYNCHRONIZE, Ability.Name.INNER_FOCUS, Ability.Name.MAGIC_GUARD);

        pokemon("Espeon", 196, Typing.PSYCHIC, stats(140, 85, 80, 150, 115, 130), Ability.Name.SYNCHRONIZE);


         /*
            ROCK
         */
        pokemon("Tyranitar", 248, Typing.ROCK, Typing.DARK, stats(175, 154, 130, 115, 120, 81), Ability.Name.SAND_STREAM);

        pokemon("Kabutops", 141, Typing.ROCK, Typing.WATER, stats(135, 135, 125, 85, 90, 100), Ability.Name.SWIFT_SWIM, Ability.Name.BATTLE_ARMOR);

        pokemon("Geodude", 74, Typing.ROCK, Typing.GROUND, stats(115, 100, 120, 50, 50, 40), Ability.Name.STURDY);
        pokemon("Graveler", 75, Typing.ROCK, Typing.GROUND, stats(130, 115, 135, 65, 65, 55), Ability.Name.STURDY);
        pokemon("Golem", 76, Typing.ROCK, Typing.GROUND, stats(155, 140, 150, 75, 85, 65), Ability.Name.STURDY);


         /*
            STEEL
         */
        pokemon("Metagross", 376, Typing.STEEL, Typing.PSYCHIC, stats(155, 155, 150,115, 110, 90), Ability.Name.CLEAR_BODY);

        pokemon("Aggron", 306, Typing.STEEL, Typing.ROCK, stats(145, 130, 200, 80, 80, 70), Ability.Name.STURDY);

        pokemon("Steelix", 208, Typing.STEEL, Typing.GROUND, stats(150, 105, 220, 75, 85, 50), Ability.Name.STURDY);

        pokemon("Magnemite", 81, Typing.ELECTRIC, Typing.STEEL, stats(100, 55, 90, 115, 75, 65), Ability.Name.STURDY);
        pokemon("Magneton", 82, Typing.ELECTRIC, Typing.STEEL, stats(125, 80, 115, 140, 90, 90), Ability.Name.STURDY);
        pokemon("Magnezone", 462, Typing.ELECTRIC, Typing.STEEL, stats(145, 90, 135, 150, 110, 80), Ability.Name.STURDY);


        /*
            WATER
         */
        pokemon("Squirtle", 7, Typing.WATER, stats(119, 68, 85, 70, 84, 63), Ability.Name.TORRENT, Ability.Name.RAIN_DISH);
        pokemon("Wartortle", 8, Typing.WATER, stats(134, 83, 100, 85, 100, 78), Ability.Name.TORRENT, Ability.Name.RAIN_DISH);
        pokemon("Blastoise", 9, Typing.WATER, stats(154, 103, 120, 105, 125, 98), Ability.Name.TORRENT, Ability.Name.RAIN_DISH);

        pokemon("Gyarados", 130, Typing.WATER, Typing.FLYING, stats(170, 145, 99, 80, 120, 101), Ability.Name.INTIMIDATE, Ability.Name.MOXIE);

        pokemon("Lapras", 131, Typing.WATER, Typing.ICE, stats(205, 105, 100, 105, 115, 80), Ability.Name.WATER_ABSORB, Ability.Name.SHELL_ARMOR, Ability.Name.HYDRATION);

        pokemon("Milotic", 350, Typing.WATER, stats(170, 80, 99, 120, 145, 101), Ability.Name.MARVEL_SCALE, Ability.Name.COMPETITIVE);

        pokemon("Sharpedo", 319, Typing.WATER, Typing.DARK, stats(145, 140, 60, 115, 60, 115), Ability.Name.ROUGH_SKIN, Ability.Name.SPEED_BOOST);

        pokemon("Greninja", 658, Typing.WATER, Typing.DARK, stats(147, 115, 87, 123, 91, 142), Ability.Name.TORRENT);

        pokemon("Poliwag", 60, Typing.WATER, stats(115, 70, 60, 60, 60, 110), Ability.Name.WATER_ABSORB, Ability.Name.SWIFT_SWIM);
        pokemon("Poliwhirl", 61, Typing.WATER, stats(140, 85, 85, 70, 70, 110), Ability.Name.WATER_ABSORB, Ability.Name.SWIFT_SWIM);
        pokemon("Poliwrath", 62, Typing.WATER, Typing.FIGHTING, stats(165, 115, 115, 90, 110, 90), Ability.Name.WATER_ABSORB, Ability.Name.SWIFT_SWIM);

        pokemon("Vaporeon", 134, Typing.WATER, stats(205, 85, 80, 130, 115, 85), Ability.Name.WATER_ABSORB, Ability.Name.HYDRATION);

        pokemon("Psyduck", 54, Typing.WATER, stats(125, 72, 68, 85, 70, 75), Ability.Name.SWIFT_SWIM);
        pokemon("Golduck", 55, Typing.WATER, stats(155, 102, 98, 115, 100, 105), Ability.Name.SWIFT_SWIM);


        /*
            Legendary-Pokemon
         */
        pokemon("Mew", 1000, Typing.PSYCHIC, stats(175, 120, 120, 120, 120, 120), Ability.Name.SYNCHRONIZE);

        pokemon("Rayquaza", 1003, Typing.DRAGON, Typing.FLYING, stats(180, 170, 110, 170, 110, 115), Ability.Name.AIR_LOCK);

        pokemon("Kyogre", 1001, Typing.WATER, stats(175, 120,110, 170,160, 110), Ability.Name.DRIZZLE);
        pokemon("Groudon", 1002, Typing.GROUND, stats(175, 170, 160, 120, 110, 110), Ability.Name.DROUGHT);

        pokemon("Zekrom", 1644, Typing.DRAGON, Typing.ELECTRIC, stats(175, 170, 140, 140, 120, 110), Ability.Name.TERAVOLT);
        pokemon("Reshiram", 1643, Typing.DRAGON, Typing.FIRE, stats(175, 140, 120, 170, 140, 110), Ability.Name.TURBOBLAZE);
    }

    @SuppressWarnings("unused")
    public static List<String> getAllPokemonNames() {
        return POKEMON.keySet().stream().map(String::toLowerCase).collect(Collectors.toList());
    }

    public static Pokemon getPokemon(String name) {
        return POKEMON.get(name);
    }

    public static List<Pokemon> getAllPokemons() {
        return new ArrayList<>(POKEMON.values());
    }

    private static void pokemon(
            String name, int dex, Typing type, Stats stats, Ability.Name... abilityNames)
    {
        List<Typing> typing = Collections.singletonList(type);
        List<Ability> abilities = Arrays.stream(abilityNames).map(AR::getAbility).toList();

        Pokemon pokemon = new Pokemon(
                name, dex, typing, stats, Nature.SERIOUS, abilities,
                resourcePath(name, "front", "png"), resourcePath(name, "icon", "png"),
                resourcePath(name, "front", "gif"), resourcePath(name, "back", "gif"),
                resourcePath(name, "cry", "mp3"),
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
                resourcePath(name, "front", "png"), resourcePath(name, "icon", "png"),
                resourcePath(name, "front", "gif"), resourcePath(name, "back", "gif"),
                resourcePath(name, "cry", "mp3"),
                new ArrayList<>()); // moves

        POKEMON.put(name, pokemon);
    }

    private static String resourcePath(String name, String type, String format) {
        String formattedName = name.toLowerCase().replaceAll("\\W+", "_");
        return "/pokemon/" + formattedName + "/" + type + "." + format;
    }

    private static Stats stats(int hp, int a, int d, int spA, int spD, int speed) {
        return new Stats(hp, a, d, spA, spD, speed);
    }

    @SuppressWarnings("unused")
    public static List<Pokemon> getAllPokemonByType(Typing type) {
        return POKEMON.values().stream()
                .filter(pokemon -> pokemon.getTyping().contains(type))
                .collect(Collectors.toList());
    }

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