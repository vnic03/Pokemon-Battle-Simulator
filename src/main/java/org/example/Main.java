package org.example;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.*;


public class Main {
    public static void main(String[] args) {

        MoveSelector moveSelector = new MoveSelector();


        Pokemon one = PokemonRepository.getPokemon("Charizard");
        moveSelector.selectMovesForPokemon(one);


        Pokemon two = PokemonRepository.getPokemon("Venusaur");
        moveSelector.selectMovesForPokemon(two);



        BattleSimulator.getInstance().simulateBattle(one, two);


        //System.out.println(MovesRepository.countAllMoves());

    }
}