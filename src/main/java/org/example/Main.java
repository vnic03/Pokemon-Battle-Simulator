package org.example;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.*;


public class Main {
    public static void main(String[] args) {

        /*

        MoveSelector moveSelector = new MoveSelector();



        moveSelector.selectMovesForPokemon(one);


        Pokemon two = PokemonRepository.getPokemon("Venusaur");
        moveSelector.selectMovesForPokemon(two);



        BattleSimulator.getInstance().simulateBattle(one, two);


        //System.out.println(MovesRepository.countAllMoves());

         */
        Pokemon one = PokemonRepository.getPokemon("Charizard");
        System.out.println(one);
        Pokemon p = PokemonRepository.getPokemon("Weezing");
        System.out.println(p);
        Pokemon t = PokemonRepository.getPokemon("Pikachu");
        System.out.println(t);
    }
}