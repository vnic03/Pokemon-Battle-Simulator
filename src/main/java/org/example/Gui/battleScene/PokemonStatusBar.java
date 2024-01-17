package org.example.Gui.battleScene;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Pokemon.Pokemon;

public class PokemonStatusBar extends HBox {

    private Label nameLabel;
    private Label levelLabel;
    private Label genderLabel;
    private ProgressBar hpBar;
    private Label hpLabel;

    public PokemonStatusBar(Pokemon pokemon) {

        nameLabel = new Label(pokemon.getName());
        levelLabel = new Label("Lvl: " + pokemon.getLevel());
        genderLabel = new Label(pokemon.getGender().getStyledSymbol());
        hpBar = new ProgressBar(pokemon.getStats().getHp() / (double) pokemon.getStats().getHp());
        hpLabel = new Label(pokemon.getStats().getHp() + " / " + pokemon.getStats().getMaxHp());

        this.getChildren().addAll(nameLabel, levelLabel, genderLabel, hpBar, hpLabel);

        this.getStylesheets().add(getClass().getResource("/battleViewStyles/battleViewStyle.css").toExternalForm());
        nameLabel.getStyleClass().add("pokemon-name");
        levelLabel.getStyleClass().add("pokemon-level");
        genderLabel.getStyleClass().add("pokemon-gender");
        hpBar.getStyleClass().add("pokemon-hp-bar");
        hpLabel.getStyleClass().add("pokemon-hp-label");

        VBox leftSide = new VBox(nameLabel, levelLabel);
        VBox rightSide = new VBox(genderLabel, hpBar, hpLabel);
        this.getChildren().setAll(leftSide, rightSide);

        leftSide.setSpacing(5);
        rightSide.setSpacing(2);
        this.setSpacing(20);

    }
}
