package org.example.screens;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.screens.battle.BattleSimulator;
import org.example.pokemon.Pokemon;
import org.example.pokemon.repositories.PokemonRepository;
import javafx.scene.image.Image;

import java.io.InputStream;


public class ChoosePokemon extends Application {

    private Stage primaryStage;

    private ComboBox<String> pokemon1ComboBox;
    private ComboBox<String> pokemon2ComboBox;

    @Override
    public void start(Stage stage) throws Exception {

        this.primaryStage = stage;

        GridPane grid = new GridPane();
        grid.getStyleClass().add("grid-pane");

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        Label label1 = new Label("Pokemon 1: ");
        label1.getStyleClass().add("label");

        ComboBox<String> pokemon1ComboBox = new ComboBox<>();
        pokemon1ComboBox.getStyleClass().add("combo-box");

        pokemon1ComboBox.getItems().addAll(PokemonRepository.getAllPokemonNames());

        pokemon1ComboBox.setCellFactory(lv -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            private Label typeLabel = new Label();

            @Override
            protected void updateItem(String pokemonName, boolean empty) {
                super.updateItem(pokemonName, empty);

                if (empty || pokemonName == null) {
                    setGraphic(null);

                } else {
                    Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);
                    String spritePath = pokemon.getFrontSprite().getUrl();

                    InputStream is = getClass().getResourceAsStream(spritePath);

                    if (is == null) {
                        System.err.println("Image not found: " + spritePath);
                        setGraphic(null);
                    } else {
                        Image image = new Image(is);
                        imageView.setImage(image);
                        imageView.setFitWidth(60);
                        imageView.setFitHeight(60);
                        imageView.setPreserveRatio(true);

                        typeLabel.setText(pokemon.getTypeString());

                        HBox hBox = new HBox(imageView, new Label(pokemonName), typeLabel);

                        hBox.setSpacing(10);
                        setGraphic(hBox);
                    }
                }
            }
        });

        pokemon1ComboBox.setVisibleRowCount(5);

        grid.add(label1, 0, 0);
        grid.add(pokemon1ComboBox, 1, 0);


        Label label2 = new Label("Pokemon 2:");
        label2.getStyleClass().add("label");

        ComboBox<String> pokemon2ComboBox = new ComboBox<>();
        pokemon2ComboBox.getStyleClass().add("combo-box");

        pokemon2ComboBox.getItems().addAll(PokemonRepository.getAllPokemonNames());

        pokemon2ComboBox.setCellFactory(lv -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            private Label typeLabel = new Label();

            @Override
            protected void updateItem(String pokemonName, boolean empty) {
                super.updateItem(pokemonName, empty);

                if (empty || pokemonName == null) {
                    setGraphic(null);

                } else {
                    Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);
                    String spritePath = pokemon.getFrontSprite().getUrl();

                    InputStream is = getClass().getResourceAsStream(spritePath);

                    if (is == null) {
                        System.err.println("Image not found: " + spritePath);
                        setGraphic(null);
                    } else {
                        Image image = new Image(is);
                        imageView.setImage(image);
                        imageView.setFitWidth(60);
                        imageView.setFitHeight(60);
                        imageView.setPreserveRatio(true);

                        typeLabel.setText(pokemon.getTypeString());

                        HBox hBox = new HBox(imageView, new Label(pokemonName), typeLabel);

                        hBox.setSpacing(10);
                        setGraphic(hBox);
                    }
                }
            }
        });

        pokemon2ComboBox.setVisibleRowCount(5);

        grid.add(label2, 0, 1);
        grid.add(pokemon2ComboBox, 1, 1);

        Button startBattleButton = new Button("Start Battle");
        startBattleButton.getStyleClass().add("button");
        grid.add(startBattleButton, 1, 2);

        pokemon1ComboBox.setOnAction(e -> {
            String pokemonName = pokemon1ComboBox.getValue();

            if (pokemonName != null) {
                Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);

                NatureSelectionWindow.display(pokemon.getName(), (nature -> {
                    pokemon.setNature(nature);
                    openEvConfigWindow(pokemonName);

                    MoveSelectionWindow.display(pokemon);
                }));
            }
        });


        pokemon2ComboBox.setOnAction(e -> {
            String pokemonName = pokemon2ComboBox.getValue();

            if ( pokemonName != null) {
                Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);

                NatureSelectionWindow.display(pokemon.getName(), (nature -> {
                    pokemon.setNature(nature);
                    openEvConfigWindow(pokemonName);

                    MoveSelectionWindow.display(pokemon);
                }));
            }
        });


        startBattleButton.setOnAction(e -> {

            String pokemon1Name = pokemon1ComboBox.getValue();
            String pokemon2Name = pokemon2ComboBox.getValue();

            if (pokemon1Name != null && pokemon2Name != null) {

                Pokemon pokemon1 = PokemonRepository.getPokemon(pokemon1Name);
                Pokemon pokemon2 = PokemonRepository.getPokemon(pokemon2Name);

                BattleSimulator.getInstance().simulateBattle(pokemon1, pokemon2);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Choose 2 Pokemon for Battle");
                alert.setHeaderText(null);
                alert.showAndWait();
            }

        });

        Scene scene = new Scene(grid, 600, 200);
        scene.getStylesheets().add(getClass().getResource("/choosePokemonStyle.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Pokémon Battle Setup");
        primaryStage.show();

    }

    private void openEvConfigWindow(String pokemonName) {
        if (pokemonName != null) {
            Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);
            new EvConfigWindow(pokemon);
        }
    }
}
