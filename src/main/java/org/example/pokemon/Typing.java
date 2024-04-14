package org.example.pokemon;

import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public enum Typing {

    NORMAL("normal.png"),
    FIGHTING("fighting.png"),
    FLYING("flying.png"),
    POISON("poison.png"),
    GROUND("ground.png"),
    ROCK("rock.png"),
    BUG("bug.png"),
    GHOST("ghost.png"),
    STEEL("steel.png"),
    FIRE("fire.png"),
    WATER("water.png"),
    GRASS("grass.png"),
    ELECTRIC("electric.png"),
    PSYCHIC("psychic.png"),
    ICE("ice.png"),
    DRAGON("dragon.png"),
    DARK("dark.png"),
    FAIRY("fairy.png");

    private final String imagePath;

    private static final Map<Typing, Image> IMAGE_CACHE = new EnumMap<>(Typing.class);

    static
    {
        for (Typing type: Typing.values()) {
            IMAGE_CACHE.put(type, loadImage(type.imagePath));
        }
    }

    Typing(String imagePath) {
        this.imagePath = "/types/" + imagePath;
    }

    private static Image loadImage(String path) {
        return new Image(Objects.requireNonNull(Typing.class.getResourceAsStream(path)));
    }

    public Image getImage() {
        return IMAGE_CACHE.get(this);
    }

    public static String format(List<Typing> typings) {
        return typings.stream()
                .map(typing -> "Typing." + typing.name())
                .collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
