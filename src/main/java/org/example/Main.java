package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.Battle.BattleSimulator;
import org.example.Gui.EvDistribution;
import org.example.Pokemon.*;




public class Main extends Application {
    public static void main(String[] args) {

        launch(args);


        MoveSelector moveSelector = new MoveSelector();


        Pokemon one = PokemonRepository.getPokemon("Arcanine");
        EvDistribution.EvDistributionWindow(one);
        moveSelector.selectMovesForPokemon(one);

        Pokemon two = PokemonRepository.getPokemon("Blastoise");
        EvDistribution.EvDistributionWindow(two);
        moveSelector.selectMovesForPokemon(two);


        BattleSimulator.getInstance().simulateBattle(one, two);


        //System.out.println(MovesRepository.countAllMoves());


    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 300, 250);

        stage.setTitle("EV Distribution");
        stage.setScene(scene);
        stage.show();
    }
}