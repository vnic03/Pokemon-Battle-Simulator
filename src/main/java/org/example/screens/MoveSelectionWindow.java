package org.example.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.pokemon.repositories.MovesRepository;
import org.example.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public class MoveSelectionWindow {

    public static void display(Pokemon pokemon) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Select Moves for " + pokemon.getName());
        window.setMinWidth(500);
        window.setMaxHeight(600);

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);

        Label instructions = new Label("Choose up to 4 moves:");
        mainLayout.getChildren().add(instructions);

        HBox typesLayout = new HBox(10);
        typesLayout.setAlignment(Pos.CENTER);


        Map<Typing, List<String>> movesByType = new HashMap<>();

        for (Moves move : MovesRepository.getAllMoves()) {
            movesByType.computeIfAbsent(move.getType(), k -> new ArrayList<>()).add(move.getName());
        }

        ListView<String> currentMovesList = new ListView<>();
        currentMovesList.setPrefHeight(100);

        Consumer<Void> updateCurrentMovesDisplay = (v) -> {
            currentMovesList.getItems().clear();
            currentMovesList.getItems()
                    .addAll(pokemon.getMoves().stream().map(Moves::getName).toList());
        };

        updateCurrentMovesDisplay.accept(null);


        for (Map.Entry<Typing, List<String>> entry : movesByType.entrySet()) {

            VBox typeLayout = new VBox(5);
            typeLayout.setAlignment(Pos.CENTER);

            Label typeLabel = new Label(entry.getKey().toString());
            typeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: lightblue;");

            ListView<String> movesList = new ListView<>();
            movesList.getItems().addAll(entry.getValue());
            movesList.setPrefHeight(150);
            movesList.setPrefWidth(120);


            Button addButton = new Button("Add Move");

            addButton.setOnAction(e -> {
                String selectedMove = movesList.getSelectionModel().getSelectedItem();
                if (selectedMove != null && pokemon.getMoves().size() < 4) {
                    Moves move = MovesRepository.getMoveByName(selectedMove);
                    if (move != null) {
                        pokemon.addMove(move);
                        updateCurrentMovesDisplay.accept(null);
                    }
                }
            });

            typeLayout.getChildren().addAll(typeLabel, movesList, addButton);
            typesLayout.getChildren().add(typeLayout);
        }

        ScrollPane scrollPane = new ScrollPane(typesLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200);

        mainLayout.getChildren().add(scrollPane);

        Label currentMovesLabel = new Label("Current Moves: ");
        mainLayout.getChildren().addAll(currentMovesLabel, currentMovesList);

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> window.close());
        mainLayout.getChildren().add(doneButton);

        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.showAndWait();
    }
}
