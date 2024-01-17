package org.example.Gui.battleScene;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import org.example.Pokemon.Gender;
import org.example.Pokemon.Pokemon;

public class PokemonStatusBar extends StackPane {
    private static final double POLYGON_WIDTH = 420;
    private static final double POLYGON_HEIGHT = 80;
    private Label nameLabel;
    private Label levelLabel;
    private Label genderLabel;
    private ProgressBar hpBar;
    private Label hpLabel;

    public PokemonStatusBar(Pokemon pokemon) {
        nameLabel = new Label(" " + pokemon.getName());
        levelLabel = new Label(" Lv." + pokemon.getLevel());

        Gender gender = pokemon.getGender();
        genderLabel = new Label(gender.getSymbol());
        genderLabel.setStyle("-fx-text-fill: " + gender.getColor() + ";");

        hpBar = new ProgressBar(pokemon.getStats().getHp() / (double) pokemon.getStats().getHp());
        hpLabel = new Label("HP: " + pokemon.getStats().getHp() + " / " + pokemon.getStats().getMaxHp());

        Pane statusBarShapeContainer = new Pane();
        Polygon statusBarShape = createStatusBarShape();
        statusBarShape.getStyleClass().add("pokemon-status-bar-shape");
        statusBarShapeContainer.getChildren().add(statusBarShape);

        VBox leftSide = new VBox(nameLabel, levelLabel);
        VBox rightSide = new VBox(genderLabel, hpBar, hpLabel);
        rightSide.setAlignment(Pos.CENTER);

        leftSide.setSpacing(5);
        rightSide.setSpacing(2);

        HBox contentBox = new HBox(leftSide, rightSide);
        contentBox.setSpacing(20);
        contentBox.setLayoutX(10); // ggf. anpassen für die X-Position
        contentBox.setLayoutY(10); // ggf. anpassen für die Y-Position


        this.getStylesheets().add(getClass().getResource("/battleViewStyles/battleViewStyle.css").toExternalForm());
        nameLabel.getStyleClass().add("pokemon-name");
        levelLabel.getStyleClass().add("pokemon-level");
        genderLabel.getStyleClass().add("pokemon-gender");
        hpBar.getStyleClass().add("pokemon-hp-bar");
        hpLabel.getStyleClass().add("pokemon-hp-label");

        statusBarShapeContainer.setMinSize(POLYGON_WIDTH, POLYGON_HEIGHT);
        statusBarShapeContainer.setMaxSize(POLYGON_WIDTH, POLYGON_HEIGHT);
        statusBarShapeContainer.setPrefSize(POLYGON_WIDTH, POLYGON_HEIGHT);

        statusBarShapeContainer.getChildren().add(contentBox);

        this.getChildren().addAll(statusBarShapeContainer);
    }

    private Polygon createStatusBarShape() {
        Polygon polygon = new Polygon();
        double angle = 20;

        polygon.getPoints().addAll(new Double[]{
                0.0, 0.0,
                POLYGON_WIDTH - angle, 0.0,
                POLYGON_WIDTH, POLYGON_HEIGHT,
                angle, POLYGON_HEIGHT
        });
        return polygon;
    }
}
