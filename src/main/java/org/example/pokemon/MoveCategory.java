package org.example.pokemon;

import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;


public enum MoveCategory {
    PHYSICAL("physical_move_icon.png"),
    SPECIAL("special_move_icon.png"),
    STATUS("status_move_icon.png");

    private final String imagePath;

    private static final Map<MoveCategory, Image> IMAGE_CACHE = new EnumMap<>(MoveCategory.class);

    static
    {
        for (MoveCategory category : MoveCategory.values()) {
            IMAGE_CACHE.put(category, loadImage(category.imagePath));
        }
    }

    MoveCategory(String imagePath) {
        this.imagePath = "/move_categories/" + imagePath;
    }

    public static Image loadImage(String path) {
        return new Image(Objects.requireNonNull(MoveCategory.class.getResourceAsStream(path)));
    }

    public Image getImage() {
        return IMAGE_CACHE.get(this);
    }
}
