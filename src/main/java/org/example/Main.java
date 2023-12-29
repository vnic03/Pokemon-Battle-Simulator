package org.example;

import org.example.Battle.BattleSimulator;
import org.example.Pokemon.*;
import FailedIdeas.PokemonCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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