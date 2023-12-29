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

        Stats tragosso_stats = new Stats(50, 50, 95, 40, 50, 35);

        List<Moves> tragosso_moves = new ArrayList<>();

        tragosso_moves.add(MovesRepository.getMoveByName("Rock Throw"));
        tragosso_moves.add(MovesRepository.getMoveByName("Tackle"));
        tragosso_moves.add(MovesRepository.getMoveByName("Mega Punch"));

        Pokemon tragosso = new Pokemon("Tragosso", Collections.singletonList(PokeTyping.GROUND), tragosso_stats, tragosso_moves);

        Stats relaxo_stats = new Stats(160, 110, 65, 65, 110, 30);

        List<Moves> relaxo_moves = new ArrayList<>();

        relaxo_moves.add(MovesRepository.getMoveByName("Body Slam"));
        relaxo_moves.add(MovesRepository.getMoveByName("Tackle"));
        relaxo_moves.add(MovesRepository.getMoveByName("Thunder Punch"));

        Pokemon relaxo = new Pokemon("Relaxo", Collections.singletonList(PokeTyping.NORMAL), relaxo_stats, relaxo_moves);

        BattleSimulator battle = new BattleSimulator();

        battle.simulateBattle(tragosso, relaxo);

        Pokemon t = PokemonRepository.getPokemon("Relaxo"); // test




    }
}