package org.example.Gui;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import org.example.Pokemon.Moves;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;
import org.example.Pokemon.PokemonRepository;
import org.example.teams.Team;
import org.jetbrains.annotations.NotNull;


import java.util.*;

public class TeamBuilder {

    private final VBox teamBuilderLayout = new VBox(10);
    private final HBox[] team1Slots = new HBox[6];
    private final HBox[] team2Slots = new HBox[6];
    private BattleStartListener battleStartListener;
    private final Stage stage;
    private final TabPane tabPane = new TabPane();
    private Team team1, team2;
    private final VBox team1Box = new VBox(10);
    private final VBox team2Box = new VBox(10);
    private final HBox teamsContainer = new HBox(20);
    private final Button startBattleButton;
    private final VBox startButtonBox = new VBox();
    private final Region spacer;
    private final BooleanProperty isEditingPokemon = new SimpleBooleanProperty(false);


    public TeamBuilder(Stage stage) {
        this.stage = stage;

        createNewTeam();
        initializeLayout();

        teamsContainer.setAlignment(Pos.CENTER);

        HBox.setHgrow(team1Box, Priority.ALWAYS);
        HBox.setHgrow(team2Box, Priority.ALWAYS);

        spacer = new Region();

        startBattleButton = new Button("Start Battle");
        startBattleButton.getStyleClass().add("start-battle-button");

        setupStartBattleButton();

        startButtonBox.setAlignment(Pos.CENTER);
        startButtonBox.setPadding(new Insets(120, 0, 20, 0));
        startButtonBox.getChildren().addAll(spacer, startBattleButton);

        teamsContainer.getChildren().addAll(team1Box, team2Box);
        teamBuilderLayout.getChildren().addAll(teamsContainer, startButtonBox, tabPane);
    }

    private void setupStartBattleButton() {
        startBattleButton.setDisable(true);
        startBattleButton.setMinWidth(200);
        startBattleButton.setMinHeight(50);
        startBattleButton.setStyle("-fx-font-size: 20px;");

        startBattleButton.managedProperty().bind(startBattleButton.visibleProperty());
        startButtonBox.managedProperty().bind(startBattleButton.visibleProperty());

        startBattleButton.setOnAction(e -> {
            if (battleStartListener != null) {
                battleStartListener.onBattleStart(team1, team2);
            }
        });

        startBattleButton.visibleProperty().bind(isEditingPokemon.not().and(Bindings.createBooleanBinding(
                () -> !team1.getPokemons().isEmpty() && !team2.getPokemons().isEmpty() &&
                team1.hasAtLeastOnePokemon() && team2.hasAtLeastOnePokemon(),
                team1.getPokemons(),
                team2.getPokemons())
        ));

        spacer.visibleProperty().bind(startBattleButton.visibleProperty());
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

        ImageView pokemonIcon = new ImageView();
        pokemonIcon.setFitWidth(40);
        pokemonIcon.setFitHeight(45);
        pokemonIcon.setVisible(false);

        Label nameLabel = new Label("Empty Slot");
        nameLabel.setOnMouseClicked(event -> choosePokemonForSlot(index, isTeam1));

        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> openPokemonEditor(index, isTeam1));
        editButton.setDisable(true);

        slot.getChildren().addAll(pokemonIcon,nameLabel, editButton);

        return slot;
    }

    private void choosePokemonForSlot(int index, boolean isTeam1) {
        List<String> allPokemons = PokemonRepository.getAllPokemonNames();

        Stage selection = new Stage();
        VBox layout = new VBox(10);
        Scene scene = new Scene(layout, 500, 300);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(FXCollections.observableArrayList(allPokemons));

        comboBox.setCellFactory(lv -> new ListCell<>() {
            private final ImageView imageView = new ImageView();
            private final Label typeLabel = new Label();

            @Override
            protected void updateItem(String pokemonName, boolean empty) {
                super.updateItem(pokemonName, empty);

                if (empty || pokemonName == null) {
                    setGraphic(null);
                } else {
                    Pokemon pokemon = PokemonRepository.getPokemon(pokemonName);

                    Image image = pokemon.getFrontSprite();
                    String spritePath = pokemon.getFrontSprite().toString();

                    if (image == null) {
                        System.err.println("Image not found: " + spritePath);
                        setGraphic(null);
                    } else {
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
            updateStartBattleButton();
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

                ImageView pokemonIcon = (ImageView) slot.getChildren().get(0);
                Image iconImage = pokemon.getIconSprite();
                ImageView iconView = createPokemonIconView(iconImage);
                pokemonIcon.setImage(iconView.getImage());
                pokemonIcon.setViewport(iconView.getViewport());
                pokemonIcon.setFitWidth(iconView.getFitWidth());
                pokemonIcon.setFitHeight(iconView.getFitHeight());
                pokemonIcon.setVisible(true);

                HBox typeIconBox;
                if (slot.getChildren().size() > 2 && slot.getChildren().get(1) instanceof HBox) {
                    slot.getChildren().remove(1);
                }

                typeIconBox = new HBox(5);
                for (PokeTyping typing : pokemon.getTyping()) {
                    ImageView typeIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/types/" + typing.name().toLowerCase() + ".png")).toExternalForm()));
                    typeIcon.setFitWidth(45);
                    typeIcon.setFitHeight(25);
                    typeIconBox.getChildren().add(typeIcon);
                }

                Label nameLabel = findLabelInSlot(slot);
                if (nameLabel != null) {
                    nameLabel.setText(pokemonName);
                }

                Button editButton = findButtonInSlot(slot);
                if (editButton != null) {
                    editButton.setDisable(false);
                }
                slot.getChildren().add(1, typeIconBox);

                updateStartBattleButton();
            }
        }
    }

    private void openPokemonEditor(int index, boolean isTeam1) {

        isEditingPokemon.set(true);

        updateStartBattleButton();

        Team targetTeam = isTeam1 ? team1 : team2;

        if (targetTeam.getPokemons().size() <= index) return;

        Pokemon pokemonToEdit = isTeam1 ? team1.getPokemons().get(index) : team2.getPokemons().get(index);

        if (pokemonToEdit == null) {
            return;
        }

        final Tab pokemonBuilderTab = tabPane.getTabs().stream().filter(
                t -> t.getText().equals("E")).findFirst().orElseGet(() -> {

            PokemonBuilder pokemonBuilder = new PokemonBuilder(pokemonToEdit, index, isTeam1);
            Tab newTab = new Tab("E", pokemonBuilder.getView());
            newTab.setUserData(pokemonBuilder);
            newTab.getStyleClass().add("edit-pokemon-tab");
            tabPane.getTabs().add(newTab);

            return newTab;
        });


        tabPane.getSelectionModel().select(pokemonBuilderTab);

        boolean doneButtonExists = ((VBox) pokemonBuilderTab.getContent()).getChildren().stream()
                .anyMatch(node -> node instanceof Button && ((Button) node).getText().equals("Done"));

        if (!doneButtonExists) {
            Button doneButton = new Button("Done");

            doneButton.setOnAction(e -> finishEditingPokemon(index, isTeam1, pokemonBuilderTab));
            ((VBox) pokemonBuilderTab.getContent()).getChildren().add(doneButton);
        }
    }

    private void finishEditingPokemon(int index, boolean isTeam1, Tab pokemonBuilderTab) {

        isEditingPokemon.set(false);
        updateStartBattleButton();

        if (pokemonBuilderTab.getUserData() instanceof PokemonBuilder) {
            PokemonBuilder pokemonBuilder = (PokemonBuilder) pokemonBuilderTab.getUserData();
            List<Moves> selectedMoves = pokemonBuilder.getSelectedMoves();

            selectedMoves.forEach(move -> System.out.println(move.getName()));

            Pokemon editedPokemon = isTeam1 ? team1.getPokemons().get(index) : team2.getPokemons().get(index);

            editedPokemon.clearMoves();

            for (Moves move : selectedMoves) {
                if (move != null) {
                    editedPokemon.addMove(move);
                }
            }
            pokemonBuilder.saveSelectedMoves();
            updatePokemonSlot(index, editedPokemon.getName(), isTeam1);
        }

        tabPane.getTabs().remove(pokemonBuilderTab);
        tabPane.getSelectionModel().selectFirst();
    }

    private void createNewTeam() {

        ObservableList<Pokemon> team1Pokemons = FXCollections.observableArrayList();
        ObservableList<Pokemon> team2Pokemons = FXCollections.observableArrayList();


        for (int i = 0; i < 6; i++) {
            team1Pokemons.add(null);
            team2Pokemons.add(null);
        }
        team1 = new Team(team1Pokemons);
        team2 = new Team(team2Pokemons);
    }

    private void updateStartBattleButton() {
        boolean team1HasPokemon = team1.getPokemons().stream().anyMatch(Objects::nonNull);
        boolean team2HasPokemon = team2.getPokemons().stream().anyMatch(Objects::nonNull);
        startBattleButton.setDisable(!(team1HasPokemon && team2HasPokemon));
    }

    public VBox getBuilderView() {
        teamBuilderLayout.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/teamBuilderStyle.css")).toExternalForm());
        return teamBuilderLayout;
    }

    public void setBattleStartListener(BattleStartListener listener) {
        this.battleStartListener = listener;
    }

    public static ImageView createPokemonIconView(Image pokemonImage) {
        ImageView imageView = new ImageView(pokemonImage);

        double width = pokemonImage.getWidth();
        double height = pokemonImage.getHeight() / 2;

        Rectangle2D viewport = new Rectangle2D(0, 0, width, height);
        imageView.setViewport(viewport);

        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    private Label findLabelInSlot(HBox slot) {
        for (Node node : slot.getChildren()) {
            if (node instanceof Label) {
                return (Label) node;
            }
        }
        return null;
    }
    private Button findButtonInSlot(HBox slot) {
        for (Node node : slot.getChildren()) {
            if (node instanceof Button) {
                return (Button) node;
            }
        }
        return null;
    }
}
