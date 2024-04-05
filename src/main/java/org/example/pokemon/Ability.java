package org.example.pokemon;

import org.example.pokemon.ability.AbilityEffect;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Ability
        (Name name, String description, AbilityEffect effect)
{
    public enum Name {
    FLAME_BODY,
    WATER_ABSORB,
    ROUGH_SKIN,
    STURDY,
    MOXIE,
    MAGIC_GUARD,
    SCRAPPY,
    SOLAR_POWER,
    ANGER_POINT,
    SOUNDPROOF,
    INTIMIDATE,
    SAND_STREAM,
    BLAZE,
    KEEN_EYE,
    NO_GUARD,
    FOREWARN,
    SPEED_BOOST,
    GUTS,
    INNER_FOCUS,
    SYNCHRONIZE,
    STATIC,
    TORRENT,
    CHLOROPHYLL,
    TECHNICIAN,
    SHELL_ARMOR,
    RAIN_DISH,
    CLEAR_BODY,
    REGENERATOR,
    SOLID_ROCK,
    DRIZZLE,
    MIRROR_ARMOR,
    FILTER,
    FLASH_FIRE,
    MARVEL_SCALE,
    SWARM,
    OVERGROW,
    DROUGHT,
    HYDRATION,
    MAGMA_ARMOR,
    BATTLE_ARMOR,
    CURSED_BODY,
    DRY_SKIN,
    COMPETITIVE,
    AIR_LOCK,
    MULTISCALE,
    THICK_FAT,
};

    // Converts the names from the Poke-Api like this: flame-body -> FLAME_BODY
    public static Ability.Name convert(String name) {
        return Ability.Name.valueOf(name.toUpperCase().replace("-", "_"));
    }

    // Converts the enum like this: FLAME_BODY -> Flame Body
    public static String convert(Ability.Name name) {
        return Arrays.stream(name.name().split("_"))
                .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
