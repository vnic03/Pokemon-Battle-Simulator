package org.example.Battle;

import org.example.Pokemon.*;

import java.util.Random;
import java.util.Scanner;

public class BattleSimulator {
    public void simulateBattle(Pokemon pokemon1, Pokemon pokemon2){

        Scanner scanner = new Scanner(System.in);

        while (pokemon1.isAlive() && pokemon2.isAlive()) {

            Pokemon firstAttacker = determineFirstAttacker(pokemon1, pokemon2);
            Pokemon secondAttacker = (firstAttacker == pokemon1) ? pokemon2 : pokemon1;

            System.out.println(firstAttacker.getName() + ", choose your move: ");

            String firstMove = scanner.nextLine();
            doAttack(firstAttacker, secondAttacker, firstMove);
            if (!secondAttacker.isAlive()) {
                System.out.println(firstAttacker.getName() + " wins!");
                break;
            }

            System.out.println(secondAttacker.getName() + ", choose your move: ");
            String secondMove = scanner.nextLine();
            doAttack(secondAttacker, firstAttacker, secondMove);

            if (!firstAttacker.isAlive()) {
                System.out.println(secondAttacker.getName() + " wins!");
                break;
            }

        }
    }
    private Pokemon determineFirstAttacker(Pokemon pokemon1, Pokemon pokemon2) {

        int pokemonSpeed1 = pokemon1.getStats().getSpeed();
        int pokemonSpeed2 = pokemon2.getStats().getSpeed();

        if (pokemonSpeed1 > pokemonSpeed2) {
            return pokemon1;
        } else if (pokemonSpeed2 > pokemonSpeed1) {
            return pokemon2;
        } else {
            return Math.random() < 0.5 ? pokemon1 : pokemon2;
        }
    }

    private void doAttack(Pokemon attacker, Pokemon defender, String moveName){

        Moves move = attacker.chooseMoveByName(moveName);

        if (move != null) {
            if (!doesMoveHit(move, attacker, defender)) {
                System.out.println(attacker.getName() + " missed " + defender.getName());
            }
        }
        int damage = DamageCalculator.calculateDamage(attacker, defender, move);

        defender.takeDamage(damage);

        System.out.println(attacker.getName() + " hits " + defender.getName() + " with " + move.getName() + " for " + damage + " damage.");

    }
    private boolean doesMoveHit(Moves move, Pokemon attacker, Pokemon defender) {
        Random random = new Random();

        int accuracy = move.getAccuracy();

        int hitRoll = random.nextInt(100) + 1;

        return hitRoll <= accuracy;
    }
}
