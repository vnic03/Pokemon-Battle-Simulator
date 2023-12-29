package org.example;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.*;


public class Main {
    public static void main(String[] args) {

        MoveSelector moveSelector = new MoveSelector();
        BattleSimulator battle = new BattleSimulator();

        Pokemon one = PokemonRepository.getPokemon("Pikachu");
        Pokemon two = PokemonRepository.getPokemon("Blastoise");

        moveSelector.selectMovesForPokemon(one);
        moveSelector.selectMovesForPokemon(two);

        battle.simulateBattle(one, two);
    }
}