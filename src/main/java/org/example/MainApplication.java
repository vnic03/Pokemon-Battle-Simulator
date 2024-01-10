package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Gui.TeamBuilder;
import org.example.Gui.firstPage.BattleButton;
import org.example.Gui.firstPage.TeamBuilderButton;

public class MainApplication extends Application {

    private final TabPane tabPane = new TabPane();
    private final StackPane rootPane = new StackPane();

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        Image background = new Image(getClass().getResourceAsStream("/mainBackground.jpg"));

        ImageView backgroundView = new ImageView(background);

        backgroundView.setFitWidth(1100);
        backgroundView.setFitHeight(800);
        backgroundView.setPreserveRatio(false);

        Button teambuilderButton = TeamBuilderButton.createButton();
        teambuilderButton.getStyleClass().add("button");
        teambuilderButton.setOnAction(event -> openTeamBuilder());

        BattleButton bBC = new BattleButton(this);
        Button battleButton = bBC.createButton();
        battleButton.getStyleClass().add("button");

        VBox buttonBox = new VBox(10);
        buttonBox.getChildren().addAll(teambuilderButton, battleButton);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        VBox.setMargin(teambuilderButton, new Insets(300, 0, 0, 0));


        rootPane.getChildren().add(backgroundView);

        rootPane.getChildren().add(tabPane);
        rootPane.getChildren().add(buttonBox);



        Scene scene = new Scene(rootPane,  1100, 800);
        scene.getStylesheets().add(getClass().getResource("/mainApplication.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokémon Battle Simulator by Nico");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void setMainContent(Node node) {
        rootPane.getChildren().setAll(node);
    }

    private void openTeamBuilder() {
        TeamBuilder teamBuilder = new TeamBuilder(primaryStage);
        Scene teamBuilderScene = new Scene(teamBuilder.getBuilderView(), 1100, 800);
        primaryStage.setScene(teamBuilderScene);
    }

}



