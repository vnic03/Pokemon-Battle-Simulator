package org.example.Gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Pokemon.Pokemon;
import org.example.Pokemon.Stats;

import java.util.HashMap;
import java.util.Map;

public class EvConfigWindow {

    private final static int MAX_EVS = 252;
    private static final int TOTAl_EVS = 508;

    private final Pokemon pokemon;
    private Stage stage;
    private Label remainingEvsLabel;
    private Button submitButton;
    private Slider hpSlider, attackSlider, defenseSlider, spAttackSlider, spDefenseSlider, speedSlider;
    private ProgressBar hpProgressBar, attackProgressBar, defenseProgressBar, spAttackProgressBar, spDefenseProgressBar, speedProgressBar;
    private TextField hpTextField, attackTextField, defenseTextField, spAttackTextField, spDefenseTextField, speedTextField;


    private Label hpBaseLabel, attackBaseLabel, defenseBaseLabel, spABaseLabel, spDBaseLabel, speedBaseLabel;
    private Label hpFinalLabel, attackFinalLabel, defenseFinalLabel, spAFinalLabel, spDFinalLabel, speedFinalLabel;


    private final Map<String, Integer> baseStats;

    public EvConfigWindow(Pokemon pokemon) {
        this.pokemon = pokemon;
        this.baseStats = getPokemonBaseStats(this.pokemon);
        createAndShowStage();
    }

    public static Map<String, Integer> getPokemonBaseStats(Pokemon pokemon) {
        Stats baseStats = pokemon.getStats();
        Map<String, Integer> statsMap = new HashMap<>();

        statsMap.put("HP", baseStats.getMaxHp());
        statsMap.put("Attack", baseStats.getAttack());
        statsMap.put("Defense", baseStats.getDefense());
        statsMap.put("Sp.Atk", baseStats.getSpecialAttack());
        statsMap.put("Sp.Def", baseStats.getSpecialDefense());
        statsMap.put("Speed", baseStats.getSpeed());

        return statsMap;
    }

    private void createAndShowStage() {

        stage = new Stage();

        stage.setTitle("Select Evs for: " + pokemon.getName());

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


        Scene scene = new Scene(grid, 630, 260);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.show();
    }

    private Slider createSlider() {

        Slider slider = new Slider(0, MAX_EVS, 0);

        slider.setMajorTickUnit(4);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setBlockIncrement(4);

        slider.getStyleClass().add("slider");

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

    private void updateTotalEvs() {

        int totalEvs = (int) (hpSlider.getValue() + attackSlider.getValue() + defenseSlider.getValue() +
                spAttackSlider.getValue() + spDefenseSlider.getValue() + speedSlider.getValue());


        int remainingEvs = TOTAl_EVS - totalEvs;

        remainingEvsLabel.setText("Remaining Evs: " + remainingEvs);

        boolean limit = totalEvs > TOTAl_EVS;

        hpSlider.setDisable(limit && hpSlider.getValue() == 0);
        attackSlider.setDisable(limit && attackSlider.getValue() == 0);
        defenseSlider.setDisable(limit && defenseSlider.getValue() == 0);
        spAttackSlider.setDisable(limit && spAttackSlider.getValue() == 0);
        spDefenseSlider.setDisable(limit && spDefenseSlider.getValue() == 0);
        speedSlider.setDisable(limit && speedSlider.getValue() == 0);


        if (limit) {
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

        hpProgressBar = new ProgressBar(0);
        attackProgressBar = new ProgressBar(0);
        defenseProgressBar = new ProgressBar(0);
        spAttackProgressBar = new ProgressBar(0);
        spDefenseProgressBar = new ProgressBar(0);
        speedProgressBar = new ProgressBar(0);

        hpTextField = new TextField();
        hpTextField.getStyleClass().add("text-field");
        hpTextField.setPrefWidth(40);
        hpTextField.setMinWidth(Control.USE_PREF_SIZE);
        hpTextField.setMaxWidth(Control.USE_PREF_SIZE);

        attackTextField = new TextField();
        attackTextField.getStyleClass().add("text-field");
        attackTextField.setPrefWidth(40);
        attackTextField.setMinWidth(Control.USE_PREF_SIZE);
        attackTextField.setMaxWidth(Control.USE_PREF_SIZE);

        defenseTextField = new TextField();
        defenseTextField.getStyleClass().add("text-field");
        defenseTextField.setPrefWidth(40);
        defenseTextField.setMinWidth(Control.USE_PREF_SIZE);
        defenseTextField.setMaxWidth(Control.USE_PREF_SIZE);

        spAttackTextField = new TextField();
        spAttackTextField.getStyleClass().add("text-field");
        spAttackTextField.setPrefWidth(40);
        spAttackTextField.setMinWidth(Control.USE_PREF_SIZE);
        spAttackTextField.setMaxWidth(Control.USE_PREF_SIZE);

        spDefenseTextField = new TextField();
        spDefenseTextField.getStyleClass().add("text-field");
        spDefenseTextField.setPrefWidth(40);
        spDefenseTextField.setMinWidth(Control.USE_PREF_SIZE);
        spDefenseTextField.setMaxWidth(Control.USE_PREF_SIZE);

        speedTextField = new TextField();
        speedTextField.getStyleClass().add("text-field");
        speedTextField.setPrefWidth(40);
        speedTextField.setMinWidth(Control.USE_PREF_SIZE);
        speedTextField.setMaxWidth(Control.USE_PREF_SIZE);

        hpBaseLabel = new Label(String.valueOf(baseStats.get("HP")));
        attackBaseLabel = new Label(String.valueOf(baseStats.get("Attack")));
        defenseBaseLabel = new Label(String.valueOf(baseStats.get("Defense")));
        spABaseLabel = new Label(String.valueOf(baseStats.get("Sp.Atk")));
        spDBaseLabel = new Label(String.valueOf(baseStats.get("Sp.Def")));
        speedBaseLabel = new Label(String.valueOf(baseStats.get("Speed")));

        hpFinalLabel = new Label();
        attackFinalLabel = new Label();
        defenseFinalLabel = new Label();
        spAFinalLabel = new Label();
        spDFinalLabel = new Label();
        speedFinalLabel = new Label();

        hpProgressBar.setProgress(baseStats.get("HP") / 255.0);
        attackProgressBar.setProgress(baseStats.get("Attack") / 255.0);
        defenseProgressBar.setProgress(baseStats.get("Defense") / 255.0);
        spAttackProgressBar.setProgress(baseStats.get("Sp.Atk") / 255.0);
        spDefenseProgressBar.setProgress(baseStats.get("Sp.Def") / 255.0);
        speedProgressBar.setProgress(baseStats.get("Speed") / 255.0);


        setupSliderTextFieldBinding(hpSlider, hpTextField, hpProgressBar, hpBaseLabel, hpFinalLabel,"HP");
        setupSliderTextFieldBinding(attackSlider, attackTextField, attackProgressBar, attackBaseLabel, attackFinalLabel,"Attack");
        setupSliderTextFieldBinding(defenseSlider, defenseTextField, defenseProgressBar, defenseBaseLabel, defenseFinalLabel,"Defense");
        setupSliderTextFieldBinding(spAttackSlider, spAttackTextField, spAttackProgressBar, spABaseLabel,spAFinalLabel ,"Sp.Atk");
        setupSliderTextFieldBinding(spDefenseSlider, spDefenseTextField, spDefenseProgressBar, spDBaseLabel, spDFinalLabel,"SP.Def");
        setupSliderTextFieldBinding(speedSlider, speedTextField, speedProgressBar, speedBaseLabel, speedFinalLabel,"Speed");

        setUpTextFieldListeners();

        textFieldOnFocusLost(hpTextField, hpSlider);
        textFieldOnFocusLost(attackTextField, attackSlider);
        textFieldOnFocusLost(defenseTextField, defenseSlider);
        textFieldOnFocusLost(spAttackTextField, spAttackSlider);
        textFieldOnFocusLost(spDefenseTextField, spDefenseSlider);
        textFieldOnFocusLost(speedTextField, speedSlider);


        remainingEvsLabel = new Label("Remaining EVs: 508");
        submitButton = new Button("Assign EVs");

        submitButton.getStyleClass().add("button");
    }

    private void setupSliderTextFieldBinding(Slider slider, TextField textField, ProgressBar progressBar,Label baselabel, Label finalLabel, String statName) {

        slider.valueProperty().addListener((obs, oldval, newVal) -> {
            textField.setText(String.format("%d", newVal.intValue()));
            progressBar.setProgress(newVal.doubleValue() / MAX_EVS);
            updateTotalEvs();
            updateFinalStatsLabel(finalLabel, statName, newVal.intValue());
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


    private void addToGrid(GridPane grid) {

        grid.add(new Label("Base"), 1, 0);

        grid.add(new Label("HP           "), 0, 1);
        grid.add(new Label("Attack       "), 0, 2);
        grid.add(new Label("Defense      "), 0, 3);
        grid.add(new Label("Sp.Atk       "), 0, 4);
        grid.add(new Label("Sp.Def       "), 0, 5);
        grid.add(new Label("Speed        "), 0, 6);

        grid.add(hpBaseLabel, 1,1);
        grid.add(attackBaseLabel, 1,2);
        grid.add(defenseBaseLabel, 1,3);
        grid.add(spABaseLabel, 1,4);
        grid.add(spDBaseLabel, 1,5);
        grid.add(speedBaseLabel, 1,6);


        grid.add(hpProgressBar, 2, 1);
        grid.add(attackProgressBar, 2, 2);
        grid.add(defenseProgressBar, 2, 3);
        grid.add(spAttackProgressBar, 2, 4);
        grid.add(spDefenseProgressBar, 2, 5);
        grid.add(speedProgressBar, 2, 6);

        grid.add(hpSlider, 4, 1);
        grid.add(attackSlider, 4, 2);
        grid.add(defenseSlider, 4, 3);
        grid.add(spAttackSlider, 4, 4);
        grid.add(spDefenseSlider, 4, 5);
        grid.add(speedSlider, 4, 6);

        grid.add(hpTextField, 5, 1);
        grid.add(attackTextField, 5, 2);
        grid.add(defenseTextField, 5, 3);
        grid.add(spAttackTextField, 5, 4);
        grid.add(spDefenseTextField, 5, 5);
        grid.add(speedTextField, 5, 6);

        grid.add(new Label("Result"), 6, 0);

        grid.add(hpFinalLabel, 6, 1);
        grid.add(attackFinalLabel, 6, 2);
        grid.add(defenseFinalLabel, 6, 3);
        grid.add(spAFinalLabel, 6, 4);
        grid.add(spDFinalLabel, 6, 5);
        grid.add(speedFinalLabel, 6, 6);


        grid.add(remainingEvsLabel, 0, 7, 2, 1);
        grid.add(submitButton, 4, 7, 2, 1);
        GridPane.setHalignment(remainingEvsLabel, HPos.RIGHT);
        GridPane.setHalignment(submitButton, HPos.RIGHT);
    }

    private void updateFinalStatsLabel(Label finalLabel, String statName, int evValue) {
        int baseValue = baseStats.get(statName);

        int increasedValue = evValue > 3 ? increasedStats(evValue) + 1 : 0;

        int finalValue = baseValue + increasedValue;
        finalLabel.setText(String.valueOf(finalValue));
    }

    private int increasedStats(int evs) {
        return evs / 8;
    }


    private void textFieldOnFocusLost(TextField textField, Slider slider) {

        textField.focusedProperty().addListener((observable, odlValue, newValue) ->{

            if (!newValue) {
                String text = textField.getText();
                if (!text.isEmpty() && isValidEVInput(text)) {
                    int value = Integer.parseInt(text);
                    if (value <= slider.getMax() && (canAssignMoreEVs() || value <slider.getValue())) {
                        slider.setValue(value);
                    } else {
                        textField.setText(String.valueOf((int) slider.getValue()));
                    }
                } else {
                    textField.setText(String.valueOf((int) slider.getValue()));
                }
                updateTotalEvs();
            }
        });
    }

    private void setUpTextFieldListeners() {
        hpTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isValidEVInput(newVal) || !canAssignMoreEVs()) {
                hpTextField.setText(oldVal);
            }
        });
    }
    private boolean isValidEVInput(String input) {
        try {
            int value = Integer.parseInt(input);
            return value >= 0 && value <= MAX_EVS;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean canAssignMoreEVs() {
        int totalEvs = (int) (hpSlider.getValue() + attackSlider.getValue() + defenseSlider.getValue() +
                spAttackSlider.getValue() + spDefenseSlider.getValue() + speedSlider.getValue());
        return totalEvs <= TOTAl_EVS;

    }


}
