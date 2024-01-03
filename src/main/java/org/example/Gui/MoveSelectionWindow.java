package org.example.Gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.Pokemon.Moves;
import org.example.Pokemon.MovesRepository;
import org.example.Pokemon.Pokemon;

import java.util.List;

public class MoveSelectionWindow {

    public static void display(Pokemon pokemon) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Select Moves for " + pokemon.getName());
        window.setMinWidth(300);

        VBox layout = new VBox(10);
        Label instructions = new Label("Choose up to 4 moves:");
        layout.getChildren().add(instructions);

        ListView<String> movesList = new ListView<>();

        movesList.getItems().addAll(MovesRepository.getAllMoveNames());

        Button addButton = new Button("Add Move");

        addButton.setOnAction(e -> {
            String selectedMove = movesList.getSelectionModel().getSelectedItem();
            if (selectedMove != null && pokemon.getMoves().size() < 4) {
                Moves move = MovesRepository.getMoveByName(selectedMove);
                if (move != null) {
                    pokemon.addMove(move);
                }
            }
        });

        Button doneButton = new Button("Done");
        doneButton.setOnAction(e -> window.close());

        layout.getChildren().addAll(movesList, addButton, doneButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
