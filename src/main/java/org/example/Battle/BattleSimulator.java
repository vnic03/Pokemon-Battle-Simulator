package org.example.Battle;

import org.example.Pokemon.*;

import java.util.Random;

public class BattleSimulator {
    public void simulateBattle(Pokemon pokemon1, Pokemon pokemon2){

        while (pokemon1.isAlive() && pokemon2.isAlive()) {

            Pokemon firstAttacker = determineFirstAttacker(pokemon1, pokemon2);
            Pokemon secondAttacker = (firstAttacker == pokemon1) ? pokemon2 : pokemon1;

            doAttack(firstAttacker, secondAttacker);
            if (!secondAttacker.isAlive()) {
                break;
            }

            doAttack(secondAttacker, firstAttacker);

            if (pokemon1.isAlive()) {
                System.out.println(pokemon1.getName() + " wins!");
            } else {
                System.out.println(pokemon2.getName() + " wins!");
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

    private void doAttack(Pokemon attacker, Pokemon defender){

        Moves move = MovesRepository.getMoveByName("Peck");

        if (move != null) {
            if (!doesMoveHit(move, attacker, defender)) {
                System.out.println(attacker.getName() + " missed " + defender.getName());
            }
        }
        int damage = DamageCalculator.calculateDamage( attacker, defender, move);

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
