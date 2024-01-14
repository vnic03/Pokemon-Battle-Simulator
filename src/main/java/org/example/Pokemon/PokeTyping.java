package org.example.Pokemon;

import javafx.scene.image.Image;
import java.util.Objects;

public enum PokeTyping {

    NORMAL("normal.png"),
    FIGHTING("fight.png"),
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

    PokeTyping(String imagePath) {
        this.imagePath = "/types/" + imagePath;
    }

    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(this.imagePath)));
    }
}
