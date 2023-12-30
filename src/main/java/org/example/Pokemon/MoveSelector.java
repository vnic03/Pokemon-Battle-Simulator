package org.example.Pokemon;

import java.util.Scanner;

public class MoveSelector {
    private Scanner scanner;

    public MoveSelector() {
        this.scanner = new Scanner(System.in);
    }

    public void selectMovesForPokemon(Pokemon pokemon) {
        System.out.println("Choose moves for " + pokemon.getName() + " (4 moves MAX) Type 'done' if you don't want to add 4 moves: ");

        while (pokemon.getMoves().size() < 4) {
            System.out.println("Move name (Move " + (pokemon.getMoves().size() + 1) + "): ");
            String moveName = scanner.nextLine();

            if ("done".equalsIgnoreCase(moveName) && !pokemon.getMoves().isEmpty()) {
                break;
            }

            Moves move = MovesRepository.getMoveByName(moveName);

            if (move != null) {
                pokemon.addMove(move);
            } else {
                System.out.println("Couldn't found " + moveName);
            }
        }
    }

    public static String getMoveNameByNumber(Pokemon pokemon, String input) {
        if (input.matches("[1-4]")) {
            int moveIndex = Integer.parseInt(input) - 1;

            if (moveIndex < pokemon.getMoves().size()) {
                return pokemon.getMoves().get(moveIndex).getName();
            } else {
                System.out.println("Invalid move Number");
            }
        }
        return input;
    }
}
