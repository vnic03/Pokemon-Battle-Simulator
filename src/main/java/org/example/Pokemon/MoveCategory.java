package org.example.Pokemon;

import javafx.scene.image.Image;
import java.util.Objects;


public enum MoveCategory {
    PHYSICAL("physical_move_icon.png"),
    SPECIAL("special_move_icon.png"),
    STATUS("status_move_icon.png");

    private final String imagePath;

    MoveCategory(String imagePath) {
        this.imagePath = "/moveCategory/" + imagePath;
    }
    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream(this.imagePath)));
    }
}
