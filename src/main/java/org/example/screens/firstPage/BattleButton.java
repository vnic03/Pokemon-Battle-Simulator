package org.example.screens.firstPage;

import javafx.scene.control.Button;
import org.example.screens.ChoosePokemonX;
import org.example.MainApplication;

public class BattleButton {

    private final MainApplication mainApp;

    public BattleButton(MainApplication mainApp) {
        this.mainApp = mainApp;
    }

    public Button createButton() {

        Button battleButton = new Button("Battle");

        battleButton.setOnAction(e -> {
            ChoosePokemonX choosePokemon = new ChoosePokemonX();
            mainApp.setMainContent(choosePokemon.getView());
        });

        return battleButton;

    }
}
