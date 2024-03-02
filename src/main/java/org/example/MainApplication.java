package org.example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.screens.TeamBuilder;
import org.example.battle.BattleLogic;
import org.example.screens.battleScene.BattleView;
import org.example.screens.battleScene.BeforeBattleAnimation;
import org.example.screens.firstPage.TeamBuilderButton;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class MainApplication extends Application {
    private final TabPane tabPane = new TabPane();
    private final StackPane rootPane = new StackPane();
    private Stage primaryStage;
    private BattleView battleView;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        primaryStage.setFullScreen(true);

        primaryStage.setMinWidth(1100);
        primaryStage.setMinHeight(800);

        Rectangle blackScreen = new Rectangle();
        blackScreen.setFill(Color.BLACK);
        blackScreen.widthProperty().bind(primaryStage.widthProperty());
        blackScreen.heightProperty().bind(primaryStage.heightProperty());

        Image background = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/mainBackground.jpg")));

        ImageView backgroundView = new ImageView(background);

        backgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        backgroundView.fitHeightProperty().bind(primaryStage.heightProperty());
        backgroundView.setPreserveRatio(false);

        Button teambuilderButton = TeamBuilderButton.createButton();
        teambuilderButton.getStyleClass().add("button");
        teambuilderButton.setOnAction(event -> openTeamBuilder());

        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(teambuilderButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        VBox.setMargin(teambuilderButton, new Insets(380, 0, 0, 0));

        rootPane.getChildren().add(backgroundView);

        rootPane.getChildren().add(tabPane);
        rootPane.getChildren().add(buttonBox);

        rootPane.getChildren().add(blackScreen);

        Scene scene = new Scene(rootPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/mainApplication.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokémon Battle Simulator by Nico");
        primaryStage.show();

        FadeTransition transition = new FadeTransition(Duration.seconds(3), blackScreen);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.setOnFinished(event -> rootPane.getChildren().remove(blackScreen));
        transition.play();

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Exit application");
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void openTeamBuilder() {
        TeamBuilder teamBuilder = new TeamBuilder(primaryStage);
        Scene teamBuilderScene = new Scene(teamBuilder.getBuilderView());
        primaryStage.setScene(teamBuilderScene);
        primaryStage.setFullScreen(true);

        teamBuilder.setBattleStartListener((team1, team2) -> {

            battleView = new BattleView(team1, team2);
            BattleLogic logic = new BattleLogic(team1, team2);

            battleView.setBattleLogic(logic);
            logic.setBattleView(battleView);

            Scene battleScene = battleView.createScene();

            BeforeBattleAnimation animation = new BeforeBattleAnimation(primaryStage, battleScene, team1,team2);

            Scene animationScene = new Scene(animation, primaryStage.getWidth(), primaryStage.getHeight());
            animationScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/battle_view_styles/bBAnimationStyles.css")).toExternalForm());
            primaryStage.setScene(animationScene);
            primaryStage.setFullScreen(true);

            animation.setOnAnimationFinished(() -> {
                battleView.loadTeams(team1, team2);
                primaryStage.setScene(battleScene);
                primaryStage.setFullScreen(true);
                logic.initiateRound();
            });
        });
    }
}
