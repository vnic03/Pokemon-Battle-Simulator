package org.example.screens.battleScene;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.pokemon.Pokemon;
import org.example.teams.Team;

import java.util.List;
import java.util.Objects;

public class BeforeBattleAnimation extends AnchorPane {

    private static final int TEAM_SIZE = 6;

    private final Image unown = new Image(
            Objects.requireNonNull(getClass().getResourceAsStream("/pokemon/unown/icon.png")));

    private final Stage stage;
    private final Scene nextScene;
    private Runnable onAnimationFinished;

    public BeforeBattleAnimation(Stage stage, Scene nextScene, Team team1, Team team2) {

        this.stage = stage;
        this.nextScene = nextScene;

        this.getStyleClass().add("battle-background");

        HBox team1Box = imageDisplay(team1.getPokemons(), true);

        HBox team2Box = imageDisplay(team2.getPokemons(), false);

        AnchorPane.setBottomAnchor(team1Box, 10.0);
        AnchorPane.setLeftAnchor(team1Box, 10.0);

        AnchorPane.setTopAnchor(team2Box, 10.0);
        AnchorPane.setRightAnchor(team2Box, 10.0);

        this.getChildren().addAll(team1Box, team2Box);

        Label fightLabel = new Label("FIGHT");
        fightLabel.getStyleClass().add("fight-label");
        fightLabel.setOpacity(0);

        StackPane.setAlignment(fightLabel, Pos.CENTER);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), fightLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.seconds(1));

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), fightLabel);
        fadeOut.setFromValue(0);
        fadeOut.setToValue(1);

        SequentialTransition fightAnimation = new SequentialTransition(fightLabel, fadeIn, stay, fadeOut);

        fightAnimation.setOnFinished(event -> {
            if (onAnimationFinished != null) {
                onAnimationFinished.run();
            } else {
                System.out.println("NO CALLBACK");
                stage.setScene(nextScene);
            }
        });

        this.getChildren().add(fightLabel);

        fightAnimation.play();
    }

    public void setOnAnimationFinished(Runnable onAnimationFinished) {
        this.onAnimationFinished = onAnimationFinished;
    }

    private HBox imageDisplay(List<Pokemon> pokemons, boolean isTeam1) {

        HBox teamBox = new HBox(20);

        if (isTeam1) {
            teamBox.setAlignment(Pos.BOTTOM_LEFT);
        } else {
            teamBox.setAlignment(Pos.TOP_RIGHT);
        }

        for (int i = 0; i < TEAM_SIZE; i++) {
            Image pokemonImage;

            if (i < pokemons.size() && pokemons.get(i) != null) {
                pokemonImage = pokemons.get(i).getIconSprite();
            } else {
                pokemonImage = unown;
            }
            ImageView icon = new ImageView(pokemonImage);

            icon.setFitWidth(120);
            icon.setFitHeight(120);
            icon.setPreserveRatio(true);

            teamBox.getChildren().add(icon);
        }
        return teamBox;
    }
}
