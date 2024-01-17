package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.example.Gui.ChoosePokemon;
import org.example.Pokemon.MovesRepository;
import org.example.Pokemon.Pokemon;
import org.example.Pokemon.PokemonRepository;

public class Main{


    public static void main(String[] args) {
        Application.launch(ChoosePokemon.class, args);
    }
}