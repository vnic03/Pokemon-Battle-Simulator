package org.example;

import org.example.Pokemon.Moves;
import org.example.Pokemon.MovesRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("Pokemon Fight");

        Moves move = MovesRepository.getMoveByName("Peck");

        System.out.println(move);


    }
}