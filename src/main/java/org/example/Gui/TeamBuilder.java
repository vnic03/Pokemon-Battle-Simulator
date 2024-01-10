package org.example.Gui;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.Pokemon.Pokemon;
import org.example.Pokemon.PokemonRepository;
import org.example.teams.Team;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TeamBuilder {

    private VBox teamBuilderLayout = new VBox(10);
    private HBox[] team1Slots = new HBox[6];
    private HBox[] team2Slots = new HBox[6];
    private Stage stage;
    private TabPane tabPane = new TabPane();
    private Team team1, team2;
    private VBox team1Box = new VBox(10);
    private VBox team2Box = new VBox(10);
    private HBox teamsContainer = new HBox(20);


    public TeamBuilder(Stage stage) {
        this.stage = stage;

        createNewTeam();
        initializeLayout();

        team1Box.setPrefWidth(550);
        team2Box.setPrefWidth(550);


        teamsContainer.getChildren().addAll(team1Box, team2Box);
        teamBuilderLayout.getChildren().addAll(teamsContainer, tabPane);
    }

    private void initializeLayout() {
        Label team1Label = new Label("Team 1");
        team1Box.getChildren().add(team1Label);
        team1Box.getStyleClass().add("team-box");

        for (int i = 0; i < team1Slots.length; i++) {
            team1Slots[i] = createPokemonSlot(i, true);
            team1Box.getChildren().add(team1Slots[i]);
        }

        Label team2Label = new Label("Team 2");
        team2Box.getChildren().add(team2Label);
        team2Box.getStyleClass().add("team-box");

        for (int i = 0; i < team2Slots.length; i++) {
            team2Slots[i] = createPokemonSlot(i, false);
            team2Box.getChildren().add(team2Slots[i]);
        }
    }

    private HBox createPokemonSlot(int index, boolean isTeam1) {

        HBox slot = new HBox(10);
        slot.getStyleClass().add("pokemon-slot");
        slot.setPadding(new Insets(5,5,5,5));

        Label nameLabel = new Label("Empty Slot");
        nameLabel.setOnMouseClicked(event -> choosePokemonForSlot(index, isTeam1));

        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> openPokemonEditor(index, isTeam1));
        editButton.setDisable(true);

        slot.getChildren().addAll(nameLabel, editButton);

        return slot;
    }

    private void choosePokemonForSlot(int index, boolean isTeam1) {
        List<String> allPokemons = PokemonRepository.getAllPokemonNames();

        Stage selection = new Stage();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 500, 300);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(allPokemons));

        comboBox.setCellFactory(lv -> new ListCell<String>() {
            private ImageView imageView = new ImageView();
            private Label typeLabel = new Label();

            @Override
            protected void updateItem(String pokemonName, boolean empty) {
                super.updateItem(pokemonName, empty);

                if (empty || pokemonName == null) {
                    setGraphic(null);
                } else {
                    Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);
                    String spritePath = pokemon.getSpritePath();

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

        Button selectButton = new Button("Select");
        selectButton.setOnAction(e -> {
            String selectedPokemonName = comboBox.getSelectionModel().getSelectedItem();
            updatePokemonSlot(index, selectedPokemonName, isTeam1);
            selection.close();
        });

        layout.getChildren().addAll(comboBox, selectButton);
        selection.setScene(scene);
        selection.setTitle("Choose a Pokemon");
        selection.showAndWait();
    }

    private void updatePokemonSlot(int index, String pokemonName, boolean isTeam1) {
        if (pokemonName != null) {

            Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);

            if (pokemon != null) {

                Team targetTeam = isTeam1 ? team1 : team2;
                targetTeam.getPokemons().set(index, pokemon);

                HBox[] targetSlots = isTeam1 ? team1Slots : team2Slots;

                HBox slot = targetSlots[index];
                Label nameLabel = (Label) slot.getChildren().get(0);
                nameLabel.setText(pokemonName);
                Button editButton = (Button) slot.getChildren().get(1);
                editButton.setDisable(false);
            }
        }
    }

    private void openPokemonEditor(int index, boolean isTeam1) {
        Team targetTeam = isTeam1 ? team1 : team2;

        if (targetTeam.getPokemons().size() <= index) return;

        Pokemon pokemon = targetTeam.getPokemons().get(index);

        if (pokemon == null) {
            return;
        }

        PokemonBuilder pokemonBuilder = new PokemonBuilder(pokemon);

        Stage popUpStage = new Stage();
        popUpStage.initModality(Modality.WINDOW_MODAL);
        popUpStage.initOwner(stage);

        Scene popUpScene = new Scene(pokemonBuilder.getView());
        popUpStage.setScene(popUpScene);

        popUpStage.setTitle("Edit Pokemon");
        popUpStage.showAndWait();
    }

    private void createNewTeam() {
        team1 = new Team(new ArrayList<>());
        team2 = new Team(new ArrayList<>());

        for (int i = 0; i < 6; i++) {
            team1.getPokemons().add(null);
            team2.getPokemons().add(null);
        }
    }

    public VBox getBuilderView() {
        teamBuilderLayout.getStylesheets().add(getClass().getResource("/teamBuilderStyle.css").toExternalForm());
        return teamBuilderLayout;
    }
}
