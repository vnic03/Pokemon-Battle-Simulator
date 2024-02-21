package org.example.pokemon;

import javafx.scene.image.Image;
import java.util.Objects;

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
    FAIRY("contest_cute.png");

    private final String imagePath;

    Typing(String imagePath) {
        this.imagePath = "/types/" + imagePath;
    }

    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(this.imagePath)));
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
