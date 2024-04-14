package org.example;

public final class Constants {

    private Constants() { }

    public static final int HTTP_STATUS_OK = 200;

    public static final int HTTP_STATUS_NOT_FOUND = 404;

    public static final String PATH_TO_ABILITIES_JSON =
            "src/main/java/org/example/pokemon/ability/abilities.json";

    public static final String PATH_TO_ABILITY_RECORD =
            "src/main/java/org/example/pokemon/ability/Ability.java";

    public static final String PATH_TO_POKEMON_REPOSITORY =
            "src/main/java/org/example/repositories/PokemonRepository.java";

    public static final String PATH_TO_MOVES_REPOSITORY =
            "src/main/java/org/example/repositories/MovesRepository.java";

    public static final String PATH_TO_POKEMON_RESOURCE =
            "src/main/resources/pokemon/";

    public static final String POKE_API_BASE_URL =
            "https://pokeapi.co/api/v2/pokemon/";

    public static final String POKE_API_ABILITY_URL =
            "https://pokeapi.co/api/v2/ability/";

    public static final String POKE_API_MOVE_URL =
            "https://pokeapi.co/api/v2/move/";

    public static final String SCRIPT_PATH_POKEMON =
            "scripts/WritePokemon.groovy";

    public static final String SCRIPT_PATH_ABILITIES =
            "scripts/UpdateAbilityEnum.groovy";

    public static final String SCRIPT_PATH_MOVES =
            "scripts/WriteMove.groovy";

    public static final int DEFAULT_BUFFER_SIZE = 4096;
}
