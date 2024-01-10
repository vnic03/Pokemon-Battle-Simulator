package org.example.Gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.example.Pokemon.Nature;

import java.util.function.Consumer;

public class NatureSelection {

    private Consumer<Nature> natureConsumer;
    private ComboBox<Nature> natureComboBox;
    private VBox layout;

    public NatureSelection(Consumer<Nature> natureConsumer) {
        this.natureConsumer = natureConsumer;
        initializeLayout();
    }

    private void initializeLayout() {
        Label label = new Label("Choose a Nature:");
        natureComboBox = new ComboBox<>();
        natureComboBox.getItems().addAll(Nature.values());

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            Nature selectedNature = natureComboBox.getValue();
            natureConsumer.accept(selectedNature);
        });

        layout = new VBox(10, label, natureComboBox, confirmButton);
        layout.setAlignment(Pos.CENTER);
    }

    public VBox getView() {
        return layout;
    }
}
