package org.example.Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Pokemon.Pokemon;
import javafx.scene.control.*;
import org.example.Pokemon.PokemonRepository;




public class EvDistributionFx extends Application {

    private static String currentPokemonName;
    private Pokemon pokemon;
    private Stage primaryStage;
    private Label remainingEvsLabel;
    private Button submitButton;
    private Slider hpSlider, attackSlider, defenseSlider, spAttackSlider, spDefenseSlider, speedSlider;
    private TextField hpTextField, attackTextField, defenseTextField, spAttackTextField, spDefenseTextField, speedTextField;


    public static void setCurrentPokemonName(String pokemonName) {
        currentPokemonName = pokemonName;
    }

    public void setPokemon(String pokemonName) {
        this.pokemon = PokemonRepository.getPokemon(pokemonName);
        if (primaryStage != null) {
            Platform.runLater(() -> {
                primaryStage.setTitle("Ev distribution for: " + pokemon.getName());
                updateUIWithPokemon();
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        this.pokemon = PokemonRepository.getPokemon(currentPokemonName);


        if (pokemon != null) {
            primaryStage.setTitle("Ev distribution for: " + pokemon.getName());
        } else {
            primaryStage.setTitle("EV distribution");
        }


        GridPane grid = new GridPane();

        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        initializeComponents();

        addToGrid(grid);

        submitButton.setOnAction(event -> {

            try {
                int hpEvs = Integer.parseInt(hpTextField.getText());
                int attackEvs = Integer.parseInt(attackTextField.getText());
                int defenseEvs = Integer.parseInt(defenseTextField.getText());
                int spAttackEvs = Integer.parseInt(spAttackTextField.getText());
                int spDefenseEvs = Integer.parseInt(spDefenseTextField.getText());
                int speedEvs = Integer.parseInt(speedTextField.getText());

                pokemon.setEvs(hpEvs, attackEvs, defenseEvs, spAttackEvs, spDefenseEvs, speedEvs);

                pokemon.getStats().calculateFinalStats(pokemon);

                Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "EVs successfully assigned!");
                confirmation.setHeaderText(null);
                confirmation.showAndWait();

                Stage stage = (Stage) submitButton.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException e) {

                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers for EVs.");
                errorAlert.setHeaderText(null);
                errorAlert.showAndWait();
            }

        });


        Scene scene = new Scene(grid, 500, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.show();

    }

    private Slider createSlider() {

        Slider slider = new Slider(0, 252, 0);

        slider.getStyleClass().add("slider");

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        slider.setMinorTickCount(5);
        slider.setBlockIncrement(1);
        return slider;
    }

    private void updateTotalEvs() {

        int totalEvs = (int) (hpSlider.getValue() + attackSlider.getValue() + defenseSlider.getValue() +
                spAttackSlider.getValue() + spDefenseSlider.getValue() + speedSlider.getValue());

        boolean validEvs = totalEvs <= 508;

        int remainingEvs = 508 - totalEvs;

        remainingEvsLabel.setText("Remaining Evs: " + remainingEvs);

        if (remainingEvs < 0 || !validEvs) {
            remainingEvsLabel.setTextFill(Color.RED);
            submitButton.setDisable(true);

        } else {
            remainingEvsLabel.setTextFill(Color.BLUE);
            submitButton.setDisable(false);
        }
    }

    private void initializeComponents() {
        hpSlider = createSlider();
        attackSlider = createSlider();
        defenseSlider = createSlider();
        spAttackSlider = createSlider();
        spDefenseSlider = createSlider();
        speedSlider = createSlider();

        hpTextField = new TextField("0");
        hpTextField.getStyleClass().add("text-field");

        attackTextField = new TextField("0");
        attackTextField.getStyleClass().add("text-field");

        defenseTextField = new TextField("0");
        defenseTextField.getStyleClass().add("text-field");

        spAttackTextField = new TextField("0");
        spAttackTextField.getStyleClass().add("text-field");

        spDefenseTextField = new TextField("0");
        spDefenseTextField.getStyleClass().add("text-field");

        speedTextField = new TextField("0");
        speedTextField.getStyleClass().add("text-field");

        setupSliderTextFieldBinding(hpSlider, hpTextField);
        setupSliderTextFieldBinding(attackSlider, attackTextField);
        setupSliderTextFieldBinding(defenseSlider, defenseTextField);
        setupSliderTextFieldBinding(spAttackSlider, spAttackTextField);
        setupSliderTextFieldBinding(spDefenseSlider, spDefenseTextField);
        setupSliderTextFieldBinding(speedSlider, speedTextField);

        remainingEvsLabel = new Label("Remaining EVs: 508");
        submitButton = new Button("Assign EVs");

        submitButton.getStyleClass().add("button");

    }

    private void setupSliderTextFieldBinding(Slider slider, TextField textField) {

        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            textField.setText(String.format("%d", newVal.intValue()));
            updateTotalEvs();
        });


        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                try {
                    int value = Integer.parseInt(textField.getText());
                    value = Math.min(252, Math.max(value, 0));
                    slider.setValue(value);
                    updateTotalEvs();
                } catch (NumberFormatException e) {
                    textField.setText(String.format("%d", (int) slider.getValue()));
                }
            }
        });
    }


    private void addToGrid(GridPane grid) {
        Label hp = new Label("HP EVs: ");
        hp.getStyleClass().add("label");

        grid.add(hp, 0, 0);
        grid.add(hpSlider, 1, 0);
        grid.add(hpTextField, 2, 0);

        Label attack = new Label("Attack EVs: ");
        attack.getStyleClass().add("label");

        grid.add(attack, 0,1);
        grid.add(attackSlider, 1, 1);
        grid.add(attackTextField, 2, 1);

        Label defense = new Label("Defense EVs: ");
        defense.getStyleClass().add("label");

        grid.add(defense, 0, 2);
        grid.add(defenseSlider, 1, 2);
        grid.add(defenseTextField, 2, 2);

        Label spAttack = new Label("Sp.Attack EVs: ");
        spAttack.getStyleClass().add("label");

        grid.add(spAttack, 0, 3);
        grid.add(spAttackSlider, 1, 3);
        grid.add(spAttackTextField, 2, 3);

        Label spDefense = new Label("Sp.Defense EVs: ");
        spDefense.getStyleClass().add("label");

        grid.add(spDefense, 0, 4);
        grid.add(spDefenseSlider, 1, 4);
        grid.add(spDefenseTextField, 2, 4);

        Label speed = new Label("Speed EVs: ");
        speed.getStyleClass().add("label");

        grid.add(speed, 0, 5);
        grid.add(speedSlider, 1, 5);
        grid.add(speedTextField, 2, 5);

        grid.add(remainingEvsLabel, 0, 6, 3, 1);

        GridPane.setHalignment(remainingEvsLabel, HPos.CENTER);
        grid.add(submitButton, 1, 7, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
    }

    private void updateUIWithPokemon() {

        if (pokemon != null) {

            hpSlider.setValue(pokemon.getEVs()[0]);
            attackSlider.setValue(pokemon.getEVs()[1]);
            defenseSlider.setValue(pokemon.getEVs()[2]);
            spAttackSlider.setValue(pokemon.getEVs()[3]);
            spDefenseSlider.setValue(pokemon.getEVs()[4]);
            speedSlider.setValue(pokemon.getEVs()[5]);

            hpTextField.setText(String.valueOf(pokemon.getEVs()[0]));
            attackTextField.setText(String.valueOf(pokemon.getEVs()[1]));
            defenseTextField.setText(String.valueOf(pokemon.getEVs()[2]));
            spAttackTextField.setText(String.valueOf(pokemon.getEVs()[3]));
            spDefenseTextField.setText(String.valueOf(pokemon.getEVs()[4]));
            speedTextField.setText(String.valueOf(pokemon.getEVs()[5]));
        }
    }

}
