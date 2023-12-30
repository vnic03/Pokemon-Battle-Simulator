package org.example;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.*;


public class Main {
    public static void main(String[] args) {

        MoveSelector moveSelector = new MoveSelector();
        BattleSimulator battle = new BattleSimulator();

        Pokemon one = PokemonRepository.getPokemon("Gengar");
        Pokemon two = PokemonRepository.getPokemon("Charizard");

        moveSelector.selectMovesForPokemon(one);
        moveSelector.selectMovesForPokemon(two);

        battle.simulateBattle(one, two);
    }
}