package failedIdeas;

import org.example.pokemon.Moves;
import org.example.repositories.MovesRepository;
import org.example.pokemon.Nature;
import org.example.pokemon.Pokemon;

import java.util.Scanner;

public class MoveSelector {
    private final Scanner scanner;

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
                System.out.println("Couldn't find " + moveName);
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

    public static Nature chooseNature(String pokemonName) {

        Scanner scannerNature = new Scanner(System.in);

        Nature[] natures = Nature.values();

        System.out.println("Choose the Nature for "+ pokemonName + ":");
        System.out.println();

        for (int i = 0; i < natures.length; i++) {
            System.out.print(natures[i].name() + "\t");
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("Choose your Nature: ");
        String input = scannerNature.nextLine().toUpperCase();

        try {
            return Nature.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Nature, defaulting to SERIOUS for " + pokemonName);
            return Nature.SERIOUS;
        }
    }
}
