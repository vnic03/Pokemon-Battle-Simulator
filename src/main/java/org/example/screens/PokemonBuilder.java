package org.example.screens;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import org.example.pokemon.*;
import org.example.repositories.MovesRepository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PokemonBuilder {

    private final VBox pokemonBuilderLayout = new VBox(10);
    private final Pokemon pokemon;
    private final TabPane tabPane = new TabPane();
    private ComboBox<Moves>[] moveComboBoxes;
    private PokemonEditListener editListener;
    private final int pokemonIndex;
    private final boolean isTeam1;
    private TextField nicknameField;

    //Ev Configuration

    private static final int MAX_EVS = 252;
    private static final int TOTAl_EVS = 508;
    private Slider hpSlider, attackSlider, defenseSlider, spAttackSlider, spDefenseSlider, speedSlider;
    private ProgressBar hpProgressBar, attackProgressBar, defenseProgressBar, spAttackProgressBar, spDefenseProgressBar, speedProgressBar;
    private TextField hpTextField, attackTextField, defenseTextField, spAttackTextField, spDefenseTextField, speedTextField;
    private Label hpBaseLabel, attackBaseLabel, defenseBaseLabel, spABaseLabel, spDBaseLabel, speedBaseLabel;
    private Label hpFinalLabel, attackFinalLabel, defenseFinalLabel, spAFinalLabel, spDFinalLabel, speedFinalLabel;
    private Label remainingEvsLabel;
    private Button submitButton;


    public PokemonBuilder(Pokemon pokemon, int pokemonIndex, boolean isTeam1) {
        this.pokemon = pokemon;
        this.pokemonIndex = pokemonIndex;
        this.isTeam1 = isTeam1;
        initializeLayout();
        pokemonBuilderLayout.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/teamBuilderStyle.css")).toExternalForm());
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

        Rectangle clip = new Rectangle(width, height);
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

        detailsPane.setHgap(20);
        detailsPane.setVgap(20);
        detailsPane.setPadding(new Insets(10, 10, 10, 10));

        detailsPane.setAlignment(Pos.CENTER);

        ImageView pokemonSprite = createPokemonImage(pokemon.getFrontSprite().getUrl());
        detailsPane.add(pokemonSprite, 0, 0, 1, 2);


        Label pokemonName = new Label(pokemon.getName());
        pokemonName.getStyleClass().add("pokemon-name-label");
        pokemonName.setFont(Font.font("Arial", FontWeight.BOLD ,14));
        GridPane.setHalignment(pokemonName, HPos.LEFT);
        detailsPane.add(pokemonName, 0, 2);


        HBox typeBox = new HBox(5);
        for (Typing type : pokemon.getTyping()) {
            Image typeImage = new Image(Objects.requireNonNull(
                    getClass().getResource(
                            "/types/" + type.name().toLowerCase() + ".png")).toExternalForm());
            ImageView typeIconView = new ImageView(typeImage);
            typeIconView.setFitWidth(50);
            typeIconView.setFitHeight(30);
            typeBox.getChildren().add(typeIconView);
        }
        detailsPane.add(typeBox, 1, 2,2,1);


        Label levelLabel = new Label("Level: 50"); // level is always 50 rn, not changeable
        levelLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        GridPane.setHalignment(levelLabel, HPos.RIGHT);
        detailsPane.add(levelLabel, 1, 0);

        Label genderLabel = new Label("Gender: ");
        genderLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        ComboBox<Gender> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll(Gender.MALE, Gender.FEMALE, Gender.NONE);
        genderComboBox.setValue(Gender.NONE); // standard

        genderComboBox.setCellFactory(lv -> new ListCell<>() {
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
        abilityComboBox.setCellFactory(lv -> new ListCell<>() {

            protected void updateItem(Ability item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setTooltip(null);
                } else {
                    setText(Arrays.stream(item.name().split(" "))
                            .map(w -> w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase())
                            .collect(Collectors.joining(" ")));

                    Tooltip tooltip = new Tooltip(item.description());
                    tooltip.setWrapText(true);
                    tooltip.setMaxWidth(300);
                    setTooltip(tooltip);
                }
            }
        });

        abilityComboBox.setOnAction(event -> {
            Ability selectedAbility = abilityComboBox.getValue();
            if (selectedAbility != null) {
                pokemon.setActiveAbility(selectedAbility);
            } else {
                List<Ability> abilities = pokemon.getAbilities();
                if (!abilities.isEmpty()) {
                    Random random = new Random();
                    int index = random.nextInt(abilities.size());
                    Ability randomAbility = abilities.get(index);
                    pokemon.setActiveAbility(randomAbility);
                    abilityComboBox.setValue(randomAbility);
                }
            }
        });

        nicknameField = new TextField();
        nicknameField.setPromptText("nickname ?");
        nicknameField.setText(pokemon.getName());
        GridPane.setHalignment(nicknameField, HPos.LEFT);

        nicknameField.setOnAction(event -> {
            String nickname = nicknameField.getText().trim();
            if (nickname.isEmpty()) {
                pokemon.setNickname(nickname);
                pokemonName.setText(pokemon.getName());
            }
        });

        detailsPane.add(abilityLabel, 1, 3);
        detailsPane.add(abilityComboBox, 2, 3);
        detailsPane.add(nicknameField, 1, 4);

        return detailsPane;
    }
    public String getNickName() {
        return nicknameField.getText().trim();
    }


    // Moves Pane

    private ScrollPane creatMovesPane() {
        VBox movesPane = new VBox(10);
        movesPane.setPadding(new Insets(10));
        movesPane.setAlignment(Pos.CENTER);

        moveComboBoxes = new ComboBox[4];

        for (int i = 0; i < moveComboBoxes.length; i++) {
            final int index = i;
            moveComboBoxes[index] = createComboBox();

            moveComboBoxes[index].setEditable(true);
            moveComboBoxes[index].getStyleClass().add("combo-box");
            moveComboBoxes[index].setPrefWidth(300);
            moveComboBoxes[index].setMaxWidth(Double.MAX_VALUE);
            moveComboBoxes[index].setVisibleRowCount(3);

            //moveComboBoxes[index].hide();

            FilteredList<Moves> filteredMoves = new FilteredList<>(
                    FXCollections.observableArrayList(MovesRepository.getMoves()), p -> true);

            moveComboBoxes[index].setItems(filteredMoves);

            moveComboBoxes[index].setConverter(new StringConverter<>() {
                @Override
                public String toString(Moves move) {
                    return move != null ? move.getName() : "";
                }

                @Override
                public Moves fromString(String string) {
                    if (string == null || string.trim().isEmpty()) {
                        return null;
                    }

                    Moves move = MovesRepository.getMoves().stream()
                            .filter(m -> m.getName().equalsIgnoreCase(string.trim()))
                            .findFirst()
                            .orElse(null);

                    System.out.println("From String: " + string + " -> Move: " + move);

                    return move;
                }
            });

            //moveComboBoxes[index].setItems(filteredMoves);

            final boolean[] isUpdating = {false};

            moveComboBoxes[index].getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !isUpdating[0]) {
                    isUpdating[0] = true;
                    moveComboBoxes[index].getEditor().setText(newValue.getName());
                    isUpdating[0] = false;
                }
            });

            final boolean[] isShowing = {false};

            moveComboBoxes[index].getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                if (!isUpdating[0] && (newValue == null || !newValue.equals(oldValue))) {
                    isUpdating[0] = true;
                    Task<Void> filterTask = new Task<>() {
                        @Override
                        protected Void call() {
                            Predicate<Moves> filter = move -> {
                                if (newValue == null || newValue.isEmpty()) {
                                    return true;
                                }
                                return move.getName().toLowerCase().startsWith(newValue.toLowerCase());
                            };
                            Platform.runLater(() -> {
                                filteredMoves.setPredicate(filter);
                                if (!filteredMoves.isEmpty()) {
                                    moveComboBoxes[index].show();
                                }
                            });
                            return null;
                        }
                    };
                    filterTask.setOnSucceeded(e -> isUpdating[0] = false);
                    new Thread(filterTask).start();
                }
            });
            moveComboBoxes[index].valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null && !isUpdating[0]) {
                    isUpdating[0] = true;
                    Platform.runLater(() -> {
                        moveComboBoxes[index].getEditor().setText(newValue.getName());
                        isUpdating[0] = false;
                    });
                }
            });
            moveComboBoxes[index].setOnAction(event -> {
                Moves selectedMove = moveComboBoxes[index].getValue();
                if (selectedMove != null && !isUpdating[0]) {
                    isUpdating[0] = true;
                    moveComboBoxes[index].getEditor().setText(selectedMove.getName());
                    isUpdating[0] = false;
                }
            });
            moveComboBoxes[index].setOnHidden(event -> isShowing[0] = false);

            movesPane.getChildren().add(moveComboBoxes[index]);
        }

        ScrollPane movesScrollPane = new ScrollPane(movesPane);
        movesScrollPane.setFitToWidth(true);

        return movesScrollPane;
    }

    private ComboBox<Moves> createComboBox() {
        ComboBox<Moves> moveComboBox = new ComboBox<>();
        moveComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Moves move, boolean empty) {
                super.updateItem(move, empty);
                if (empty || move == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(5);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    ImageView typeImage = new ImageView(move.getType().getImage());
                    typeImage.setFitHeight(28);
                    typeImage.setFitWidth(50);

                    ImageView categoryImage = new ImageView(move.getCategory().getImage());
                    categoryImage.setFitHeight(61);
                    categoryImage.setFitWidth(55);

                    Label nameLabel = new Label(move.getName());

                    Label categorylabel = new Label("Cat: ");
                    Label powerLabel = new Label("Power: " + (move.getPower() != 0 ? move.getPower() : "-"));
                    Label accuracyLabel = new Label("Acc: " + move.getAccuracy());
                    Label ppLabel = new Label("PP: " + move.getPp());

                    hBox.getChildren().addAll(nameLabel, typeImage,categorylabel ,categoryImage, powerLabel, accuracyLabel, ppLabel);

                    setGraphic(hBox);
                    setText(null);
                }
            }
        });
        moveComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Moves move, boolean empty) {
                super.updateItem(move, empty);
                if (empty || move == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(move.getName());
                    ImageView typeImage = new ImageView(move.getType().getImage());
                    typeImage.setFitHeight(28);
                    typeImage.setFitWidth(50);
                    setGraphic(typeImage);
                }
            }
        });
        return moveComboBox;
    }
    public List<Moves> getSelectedMoves() {
        List<Moves> selectedMoves = new ArrayList<>();
        for (ComboBox<Moves> comboBox : moveComboBoxes) {
            Moves move = comboBox.getValue();
            if (move != null) {
                selectedMoves.add(move);
            }
        }
        return selectedMoves;
    }
    public void saveSelectedMoves() {
        List<Moves> selectedMoves = getSelectedMoves();

        while (selectedMoves.size() < 4) {
            Moves randomMove = getRandomMove();
            if (!selectedMoves.contains(randomMove)) {
                selectedMoves.add(randomMove);
            }
        }
        pokemon.setMoves(selectedMoves);
        if (editListener != null) {
            editListener.onPokemonEdited(pokemon, pokemonIndex, isTeam1);
        }
        updateMoveComboBoxes();
    }
    private Moves getRandomMove() {
        List<Moves> allMoves = MovesRepository.getMoves();
        int random = new Random().nextInt(allMoves.size());
        return allMoves.get(random);
    }
    private void updateMoveComboBoxes() {
        List<Moves> moves = pokemon.getMoves();
        for (int i = 0; i < moveComboBoxes.length; i++) {
            moveComboBoxes[i].setValue(moves.get(i));
        }
    }


    //Ev Pane

    private VBox createEvPane() {
        VBox evPane = new VBox(10);
        evPane.getStyleClass().add("ev-pane");

        evPane.minWidthProperty().set(600);

        initializeEvComponents(pokemon);

        HBox box1 = new HBox(15, hpBaseLabel, hpProgressBar, hpTextField, hpSlider, hpFinalLabel);
        HBox box2 = new HBox(15, attackBaseLabel, attackProgressBar, attackTextField, attackSlider, attackFinalLabel);
        HBox box3 = new HBox(15, defenseBaseLabel, defenseProgressBar, defenseTextField, defenseSlider,defenseFinalLabel, remainingEvsLabel);
        HBox box4 = new HBox(15, spABaseLabel, spAttackProgressBar, spAttackTextField, spAttackSlider, spAFinalLabel);
        HBox box5 = new HBox(15, spDBaseLabel, spDefenseProgressBar, spDefenseTextField, spDefenseSlider, spDFinalLabel);
        HBox box6 = new HBox(15, speedBaseLabel, speedProgressBar, speedTextField, speedSlider, speedFinalLabel, submitButton);

        HBox.setHgrow(hpSlider, Priority.ALWAYS);
        HBox.setHgrow(attackSlider, Priority.ALWAYS);
        HBox.setHgrow(defenseSlider, Priority.ALWAYS);
        HBox.setHgrow(spAttackSlider, Priority.ALWAYS);
        HBox.setHgrow(spDefenseSlider, Priority.ALWAYS);
        HBox.setHgrow(speedSlider, Priority.ALWAYS);

        HBox.setHgrow(hpProgressBar, Priority.ALWAYS);
        HBox.setHgrow(attackProgressBar, Priority.ALWAYS);
        HBox.setHgrow(defenseProgressBar, Priority.ALWAYS);
        HBox.setHgrow(spDefenseProgressBar, Priority.ALWAYS);
        HBox.setHgrow(spAttackProgressBar, Priority.ALWAYS);
        HBox.setHgrow(speedProgressBar, Priority.ALWAYS);

        evPane.getChildren().addAll(box1, box2, box3, box4, box5, box6);

        ScrollPane scrollPane = new ScrollPane(evPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        return evPane;
    }

    private void initializeEvComponents(Pokemon pokemon) {
        Map<String, Integer> baseStats = Pokemon.getPokemonBaseStats(pokemon);

        // Stat Labels

        hpBaseLabel = new Label(String.valueOf(pokemon.getStats().getMaxHp()));
        hpBaseLabel.getStyleClass().addAll("label", "stat-label");

        attackBaseLabel = new Label(String.valueOf(pokemon.getStats().getAttack()));
        attackBaseLabel.getStyleClass().addAll("label", "stat-label");

        defenseBaseLabel = new Label(String.valueOf(pokemon.getStats().getDefense()));
        defenseBaseLabel.getStyleClass().addAll("label", "stat-label");

        spABaseLabel = new Label(String.valueOf(pokemon.getStats().getSpecialAttack()));
        spABaseLabel.getStyleClass().addAll("label", "stat-label");

        spDBaseLabel = new Label(String.valueOf(pokemon.getStats().getSpecialDefense()));
        spDBaseLabel.getStyleClass().addAll("label", "stat-label");

        speedBaseLabel = new Label(String.valueOf(pokemon.getStats().getSpeed()));
        speedBaseLabel.getStyleClass().addAll("label", "stat-label");


        // Evs Result labels

        hpFinalLabel = new Label();
        hpFinalLabel.getStyleClass().addAll("label", "final-stat-label");

        attackFinalLabel = new Label();
        attackFinalLabel.getStyleClass().addAll("label", "final-stat-label");

        defenseFinalLabel = new Label();
        defenseFinalLabel.getStyleClass().addAll("label", "final-stat-label");

        spAFinalLabel = new Label();
        spAFinalLabel.getStyleClass().addAll("label", "final-stat-label");

        spDFinalLabel = new Label();
        spDFinalLabel.getStyleClass().addAll("label", "final-stat-label");

        speedFinalLabel = new Label();
        speedFinalLabel.getStyleClass().addAll("label", "final-stat-label");

        // Sliders

        hpSlider = createSlider();
        hpSlider.getStyleClass().add("ev-slider");
        hpSlider.setMaxWidth(500);

        attackSlider = createSlider();
        attackSlider.getStyleClass().add("ev-slider");
        attackSlider.setMaxWidth(500);

        defenseSlider = createSlider();
        defenseSlider.getStyleClass().add("ev-slider");
        defenseSlider.setMaxWidth(500);

        spAttackSlider = createSlider();
        spAttackSlider.getStyleClass().add("ev-slider");
        spAttackSlider.setMaxWidth(500);

        spDefenseSlider = createSlider();
        spDefenseSlider.getStyleClass().add("ev-slider");
        spDefenseSlider.setMaxWidth(500);

        speedSlider = createSlider();
        speedSlider.getStyleClass().add("ev-slider");
        speedSlider.setMaxWidth(500);

        // TextFields

        hpTextField = new TextField("0");
        hpTextField.getStyleClass().add("ev-text-field");

        attackTextField = new TextField("0");
        attackTextField.getStyleClass().add("ev-text-field");

        defenseTextField = new TextField("0");
        defenseTextField.getStyleClass().add("ev-text-field");

        spAttackTextField = new TextField("0");
        spAttackTextField.getStyleClass().add("ev-text-field");

        spDefenseTextField = new TextField("0");
        spDefenseTextField.getStyleClass().add("ev-text-field");

        speedTextField = new TextField("0");
        speedTextField.getStyleClass().add("ev-text-field");

        // Progress Bars

        hpProgressBar = new ProgressBar(0);
        hpProgressBar.getStyleClass().add("ev-progress-bar");
        hpProgressBar.setMaxWidth(400);

        attackProgressBar = new ProgressBar(0);
        attackProgressBar.getStyleClass().add("ev-progress-bar");
        attackProgressBar.setMaxWidth(400);

        defenseProgressBar = new ProgressBar(0);
        defenseProgressBar.getStyleClass().add("ev-progress-bar");
        defenseProgressBar.setMaxWidth(400);

        spAttackProgressBar = new ProgressBar(0);
        spAttackProgressBar.getStyleClass().add("ev-progress-bar");
        spAttackProgressBar.setMaxWidth(400);

        spDefenseProgressBar = new ProgressBar(0);
        spDefenseProgressBar.getStyleClass().add("ev-progress-bar");
        spDefenseProgressBar.setMaxWidth(400);

        speedProgressBar = new ProgressBar(0);
        speedProgressBar.getStyleClass().add("ev-progress-bar");
        speedProgressBar.setMaxWidth(400);


        hpProgressBar.setProgress(baseStats.get("HP") / 255.0);
        attackProgressBar.setProgress(baseStats.get("Attack") / 255.0);
        defenseProgressBar.setProgress(baseStats.get("Defense") / 255.0);
        spAttackProgressBar.setProgress(baseStats.get("Sp.Atk") / 255.0);
        spDefenseProgressBar.setProgress(baseStats.get("Sp.Def") / 255.0);
        speedProgressBar.setProgress(baseStats.get("Speed") / 255.0);

        setupSliderTextFieldBinding(hpSlider, hpTextField,hpProgressBar, hpBaseLabel, hpFinalLabel,"HP");
        setupSliderTextFieldBinding(attackSlider, attackTextField, attackProgressBar,attackBaseLabel, attackFinalLabel,"Attack");
        setupSliderTextFieldBinding(defenseSlider, defenseTextField, defenseProgressBar,defenseBaseLabel, defenseFinalLabel,"Defense");
        setupSliderTextFieldBinding(spAttackSlider, spAttackTextField,spAttackProgressBar,spABaseLabel, spAFinalLabel,"Sp.Atk");
        setupSliderTextFieldBinding(spDefenseSlider, spDefenseTextField, spDefenseProgressBar ,spDBaseLabel, spDFinalLabel,"Sp.Def");
        setupSliderTextFieldBinding(speedSlider, speedTextField, speedProgressBar,speedBaseLabel, speedFinalLabel,"Speed");

        remainingEvsLabel = new Label("Remaining EVs: " + TOTAl_EVS);

        submitButton = new Button("Assign Evs");
        submitButton.getStyleClass().add("ev-submit-button");

        submitButton.setOnAction(event -> submitEvConfiguration());

        updateProgressBars();
    }

    private Slider createSlider() {
        Slider slider = new Slider(0, MAX_EVS, 0);

        slider.setMajorTickUnit(4);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(4);

        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int roundedValue = (int) (4 * (Math.round(newValue.doubleValue() / 4)));
            if (roundedValue != newValue.intValue()) {
                slider.setValue(roundedValue);
            }
        });
        return slider;
    }

    private void updateProgressBars() {
        hpProgressBar.setProgress((double) pokemon.getEVs()[0] / MAX_EVS);
        attackProgressBar.setProgress((double) pokemon.getEVs()[1] / MAX_EVS);
        defenseProgressBar.setProgress((double) pokemon.getEVs()[2] / MAX_EVS);
        spAttackProgressBar.setProgress((double) pokemon.getEVs()[3] / MAX_EVS);
        spDefenseProgressBar.setProgress((double) pokemon.getEVs()[4] / MAX_EVS);
        speedProgressBar.setProgress((double) pokemon.getEVs()[5] / MAX_EVS);
    }

    private void submitEvConfiguration() {

        try {
            int hpEvs = parseEvValue(hpTextField.getText());
            int attackEvs =parseEvValue(attackTextField.getText());
            int defenseEvs = parseEvValue(defenseTextField.getText());
            int spAttackEvs = parseEvValue(spAttackTextField.getText());
            int spDefenseEvs = parseEvValue(spDefenseTextField.getText());
            int speedEvs = parseEvValue(speedTextField.getText());

            int totalEvs = hpEvs + attackEvs + defenseEvs + spAttackEvs + spDefenseEvs + speedEvs;

            if (totalEvs <= TOTAl_EVS &&
                    hpEvs <= MAX_EVS && attackEvs <= MAX_EVS && defenseEvs <= MAX_EVS &&
                    spAttackEvs <= MAX_EVS && spDefenseEvs <= MAX_EVS && speedEvs <= MAX_EVS) {

                pokemon.setEvs(hpEvs, attackEvs, defenseEvs, spAttackEvs, spDefenseEvs, speedEvs);
                pokemon.calculateStatsIfNecessary();

                updateProgressBars();
                displayFinalStat();

                if (editListener != null) {
                    editListener.onPokemonEdited(pokemon, pokemonIndex, isTeam1);
                }
            }
        } catch (NumberFormatException e) {/* something */}
    }
    private int parseEvValue(String text) {
        int value = Integer.parseInt(text);
        return Math.min(MAX_EVS, Math.max(value, 0));
    }

    private void setupSliderTextFieldBinding(Slider slider, TextField textField, ProgressBar progressBar,Label baselabel, Label finalLabel, String statName) {

        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            textField.setText(String.format("%d", newVal.intValue()));
            progressBar.setProgress(newVal.doubleValue() / MAX_EVS);
            updateTotalEvs();
            updateFinalStatsLabel(finalLabel, statName);
        });
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    int value = Integer.parseInt(textField.getText());
                    value = Math.min(MAX_EVS, Math.max(value, 0));
                    slider.setValue(value);
                    updateTotalEvs();
                } catch (NumberFormatException e) {
                    textField.setText(String.format("%d", (int) slider.getValue()));
                }
            }
        });
    }

    private void updateTotalEvs() {
        int totalEvs = (int) (hpSlider.getValue() + attackSlider.getValue() + defenseSlider.getValue() +
                spAttackSlider.getValue() + spDefenseSlider.getValue() + speedSlider.getValue());

        int remainingEvs = TOTAl_EVS - totalEvs;

        remainingEvsLabel.setText("Remaining Evs: " + remainingEvs);

        boolean limit = totalEvs > TOTAl_EVS;

        hpSlider.setDisable(limit);
        attackSlider.setDisable(limit);
        defenseSlider.setDisable(limit);
        spAttackSlider.setDisable(limit);
        spDefenseSlider.setDisable(limit);
        speedSlider.setDisable(limit);


        if (limit) {
            remainingEvsLabel.setTextFill(Color.RED);
            submitButton.setDisable(true);

        } else {
            remainingEvsLabel.setTextFill(Color.BLUE);
            submitButton.setDisable(false);
        }
    }
    private void displayFinalStat() {

        updateFinalStatsLabel(hpFinalLabel, "HP");
        updateFinalStatsLabel(attackFinalLabel, "Attack");
        updateFinalStatsLabel(defenseFinalLabel, "Defense");
        updateFinalStatsLabel(spAFinalLabel, "Sp.Atk");
        updateFinalStatsLabel(spDFinalLabel, "Sp.Def");
        updateFinalStatsLabel(speedFinalLabel, "Speed");
    }

    private void updateFinalStatsLabel(Label finalLabel, String statName) {
        Map<String, Integer> finalStats = pokemon.getStats().getFinalStats();

        if (finalStats.containsKey(statName) && finalStats.get(statName) != null) {
            int value = finalStats.get(statName);
            finalLabel.setText(String.valueOf(value));
        } else {
            finalLabel.setText("N/A");
        }
    }

    public void setEditListener(PokemonEditListener listener) {
        this.editListener = listener;
    }

    public VBox getView() {
        return pokemonBuilderLayout;
    }
}
