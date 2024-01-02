package org.example.Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.Battle.BattleSimulator;
import org.example.Pokemon.MoveSelector;
import org.example.Pokemon.Pokemon;
import org.example.Pokemon.PokemonRepository;


public class ChoosePokemon extends Application {

    private Stage primaryStage;

    private ComboBox<String> pokemon1ComboBox;
    private ComboBox<String> pokemon2ComboBox;

    @Override
    public void start(Stage stage) throws Exception {

        this.primaryStage = stage;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Label label1 = new Label("Pokemon 1: ");
        ComboBox<String> pokemon1ComboBox = new ComboBox<>();
        pokemon1ComboBox.getItems().addAll(PokemonRepository.getAllPokemonNames());
        grid.add(label1, 0, 0);
        grid.add(pokemon1ComboBox, 1, 0);


        Label label2 = new Label("Pokemon 2:");
        ComboBox<String> pokemon2ComboBox = new ComboBox<>();
        pokemon2ComboBox.getItems().addAll(PokemonRepository.getAllPokemonNames());
        grid.add(label2, 0, 1);
        grid.add(pokemon2ComboBox, 1, 1);

        Button startBattleButton = new Button("Start Battle");
        grid.add(startBattleButton, 1, 2);

        pokemon1ComboBox.setOnAction(e -> openEvConfigWindow(pokemon1ComboBox.getValue()));
        pokemon2ComboBox.setOnAction(e -> openEvConfigWindow(pokemon2ComboBox.getValue()));

        startBattleButton.setOnAction(e -> {

            String pokemon1Name = pokemon1ComboBox.getValue();
            String pokemon2Name = pokemon2ComboBox.getValue();

            if (pokemon1Name != null && pokemon2Name != null) {
                Pokemon pokemon1 = PokemonRepository.getPokemon(pokemon1Name);
                Pokemon pokemon2 = PokemonRepository.getPokemon(pokemon2Name);


                // ob die Pokémon bereits EVs zugewiesen bekommen haben

                // pokemon bereits moves ausgewahlt
                MoveSelector moveSelector = new MoveSelector();
                moveSelector.selectMovesForPokemon(pokemon1);
                moveSelector.selectMovesForPokemon(pokemon2);

                BattleSimulator.getInstance().simulateBattle(pokemon1, pokemon2);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Choose 2 Pokemon for Battle");
                alert.setHeaderText(null);
                alert.showAndWait();
            }

        });

        primaryStage.setScene(new Scene(grid, 400, 200));
        primaryStage.setTitle("Pokémon Battle Setup");
        primaryStage.show();

    }

    private void openEvConfigWindow(String pokemonName) {
        if (pokemonName != null) {
            Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);
            new EvConfigWindow(pokemon); // EvDistributionFx
        }
    }
}
