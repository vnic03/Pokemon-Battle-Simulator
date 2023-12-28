package org.example;

import org.example.Pokemon.*;
import FailedIdeas.PokemonCreator;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pokemon Fight");

        Moves move = MovesRepository.getMoveByName("Peck");
        Moves moves = MovesRepository.getMoveByName("Lick");

        System.out.println(move);
        System.out.println(moves);



    }
}