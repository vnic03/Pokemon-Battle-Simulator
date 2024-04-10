package org.example.pokemon.ability;

import java.util.Arrays;
import java.util.stream.Collectors;

public record Ability
        (Name name, String description, AbilityEffect effect)
{
    public enum Name {
    ADAPTABILITY,
AIR_LOCK,
ANGER_POINT,
BATTLE_ARMOR,
BLAZE,
CHLOROPHYLL,
CLEAR_BODY,
COMPETITIVE,
COMPOUND_EYES,
CURSED_BODY,
DRIZZLE,
DROUGHT,
DRY_SKIN,
FILTER,
FLAME_BODY,
FLASH_FIRE,
FOREWARN,
GUTS,
HYDRATION,
ICE_BODY,
INNER_FOCUS,
INTIMIDATE,
KEEN_EYE,
LEVITATE,
LIGHTNING_ROD,
MAGIC_GUARD,
MAGMA_ARMOR,
MARVEL_SCALE,
MIRROR_ARMOR,
MOXIE,
MULTISCALE,
NO_GUARD,
OVERGROW,
PIXILATE,
RAIN_DISH,
ROUGH_SKIN,
SAND_RUSH,
SAND_STREAM,
SAND_VEIL,
SAP_SIPPER,
SCRAPPY,
SHELL_ARMOR,
SOLAR_POWER,
SOLID_ROCK,
SOUNDPROOF,
SPEED_BOOST,
STATIC,
STURDY,
SWARM,
SWIFT_SWIM,
SYNCHRONIZE,
TECHNICIAN,
TERAVOLT,
THICK_FAT,
TINTED_LENS,
TORRENT,
TURBOBLAZE,
VOLT_ABSORB,
WATER_ABSORB,
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
