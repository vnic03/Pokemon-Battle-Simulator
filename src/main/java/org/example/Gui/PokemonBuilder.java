package org.example.Gui;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.example.Pokemon.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PokemonBuilder {

    private VBox pokemonBuilderLayout = new VBox(10);
    private Pokemon pokemon;
    private TabPane tabPane = new TabPane();


    public PokemonBuilder(Pokemon pokemon) {
        this.pokemon = pokemon;
        initializeLayout();
        pokemonBuilderLayout.getStylesheets().add(getClass().getResource("/teamBuilderStyle.css").toExternalForm());
    }

    private void initializeLayout() {

        Tab detailsTab = new Tab("Details", createDetailsPane());
        Tab movesTab = new Tab("Moves", creatMovesPane());
        Tab evsTab = new Tab("EVs", createEvPane());

        tabPane.getTabs().addAll(detailsTab, movesTab, evsTab);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        pokemonBuilderLayout.getChildren().add(tabPane);
    }

    private ImageView createPokemonImage(String spritePath) {
        Image image = new Image(spritePath);
        ImageView imageView = new ImageView(image);

        final int width = 150;
        final int height = 150;
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);

        Rectangle clip = new Rectangle(
                width, height
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);


        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(2.0);
        shadow.setOffsetY(2.0);
        shadow.setColor(Color.color(0.4, 0.5, 0.5));

        imageView.setEffect(shadow);

        imageView.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 10;");
        // style css
        return imageView;
    }

    private GridPane createDetailsPane() {
        GridPane detailsPane = new GridPane();

        detailsPane.setHgap(10);
        detailsPane.setVgap(10);
        detailsPane.setPadding(new Insets(10, 10, 10, 10));

        ImageView pokemonSprite = createPokemonImage(pokemon.getSpritePath());
        detailsPane.add(pokemonSprite, 0, 0, 1, 2);


        Label pokemonName = new Label(pokemon.getName());
        pokemonName.setFont(Font.font("Arial", FontWeight.BOLD ,14));
        GridPane.setHalignment(pokemonName, HPos.LEFT);
        detailsPane.add(pokemonName, 0, 0);


        HBox typeBox = new HBox(5);
        for (PokeTyping type : pokemon.getTyping()) {
            Label typeLabel = new Label(type.toString());
            typeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
            typeBox.getChildren().add(typeLabel);
        }
        detailsPane.add(typeBox, 1, 2,2,1);


        Label levelLabel = new Label("Level: 50"); // level is always 50 rn
        levelLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        GridPane.setHalignment(levelLabel, HPos.RIGHT);
        detailsPane.add(levelLabel, 1, 0);


        Label genderLabel = new Label("Gender: ");
        genderLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll(Gender.MALE, Gender.FEMALE, Gender.NONE);
        genderComboBox.setValue(Gender.NONE); // standard

        genderComboBox.setCellFactory(lv -> new ListCell<Gender>() {
            @Override
            protected void updateItem(Gender item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getSymbol());
                    setStyle(item.getStyledSymbol());
                }
            }
        });

        genderComboBox.setOnAction(event -> {
            Gender selectedGender = genderComboBox.getValue();

            pokemon.setGender(selectedGender);
        });

        GridPane.setHalignment(genderComboBox, HPos.RIGHT);
        detailsPane.add(genderLabel, 1, 1);
        detailsPane.add(genderComboBox, 2, 1);



        Label abilityLabel = new Label("Ability: ");
        abilityLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        ComboBox<Ability> abilityComboBox = new ComboBox<>();
        abilityComboBox.getItems().addAll(pokemon.getAbilities());
        abilityComboBox.setCellFactory(lv -> new ListCell<Ability>() {

            protected void updateItem(Ability item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(item.getName());
                    Tooltip tooltip = new Tooltip(item.getDescription());
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(300);
                    setTooltip(tooltip);
                }
            }
        });

        abilityComboBox.setOnAction(event -> {
            Ability selectedAbility = abilityComboBox.getValue();
            pokemon.setActiveAbility(selectedAbility);
        });

        detailsPane.add(abilityLabel, 1, 3);
        detailsPane.add(abilityComboBox, 2, 3);

        return detailsPane;
    }

    private VBox creatMovesPane() {
        VBox movesPane = new VBox(10);
        movesPane.setPadding(new Insets(10));
        List<Moves> selectedMoves = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            TextField moveInput = new TextField();
            moveInput.setPromptText("Enter Move " + (i + 1));
            moveInput.getStyleClass().add("move-input");

            ListView<Moves> movesList = new ListView<>();
            movesList.getStyleClass().add("moves-list");

            movesList.setCellFactory(param -> new ListCell<Moves>() {
                @Override
                protected void updateItem(Moves move, boolean empty) {
                    super.updateItem(move, empty);
                    if (empty || move == null) {
                        setText(null);
                    } else {
                        setText(move.getName() + " - Power: " + move.getPower() + ", Type: " + move.getType());
                    }
                }
            });

            moveInput.textProperty().addListener((observable, oldValue, newValue) -> {
                updateMovesList(movesList, newValue);
            });

            movesList.setOnMouseClicked(event -> {
                Moves selectedMove = movesList.getSelectionModel().getSelectedItem();
                if (selectedMove != null && !selectedMoves.contains(selectedMove)) {
                    selectedMoves.add(selectedMove);
                    moveInput.setText(selectedMove.getName());
                    moveInput.setStyle("-fx-text-fill: green;");
                }
            });

            movesPane.getChildren().addAll(moveInput, movesList);
        }
        return movesPane;
    }

    private void updateMovesList(ListView<Moves> movesList, String searchText) {
        List<Moves> filteredMoves = MovesRepository.getAllMoves().stream()
                .filter(move -> move.getName().toLowerCase().startsWith(searchText.toLowerCase()))
                .collect(Collectors.toList());

        movesList.setItems(FXCollections.observableArrayList(filteredMoves));
    }

    private VBox createEvPane() {
        return null;
    }

    public VBox getView() {
        return pokemonBuilderLayout;
    }
}
