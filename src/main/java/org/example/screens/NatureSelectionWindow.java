package org.example.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pokemon.Nature;

import java.util.function.Consumer;

public class NatureSelectionWindow {

    public static void display(String pokemonName, Consumer<Nature> nature) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Select Nature for " + pokemonName);
        window.setMinWidth(250);

        Label label = new Label("Choose a Nature:");

        ComboBox<Nature> natureComboBox = new ComboBox<>();

        natureComboBox.getItems().addAll(Nature.values());


        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            Nature selectedNature = natureComboBox.getValue();
            nature.accept(selectedNature);
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, natureComboBox, confirmButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
