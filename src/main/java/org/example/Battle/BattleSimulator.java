package org.example.Battle;

import org.example.Pokemon.*;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Effects.MoveEffectWithDamage;
import org.example.Pokemon.Effects.MultiHitMoveEffect;

import java.util.Random;
import java.util.Scanner;

public class BattleSimulator {
    public void simulateBattle(Pokemon pokemon1, Pokemon pokemon2) {

        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (pokemon1.isAlive() && pokemon2.isAlive()) {

            System.out.println("Round " + round + ": ");
            System.out.println(pokemon1.getName() + " (" + pokemon1.getStats().getHp() + " HP) vs. " + pokemon2.getName() + " (" + pokemon2.getStats().getHp() + " HP)");

            Pokemon firstAttacker = determineFirstAttacker(pokemon1, pokemon2);
            Pokemon secondAttacker = (firstAttacker == pokemon1) ? pokemon2 : pokemon1;

            System.out.println(firstAttacker.getName() + ", choose your move: ");

            String firstMoveInput = scanner.nextLine();
            String firstMove = MoveSelector.getMoveNameByNumber(firstAttacker, firstMoveInput);

            Moves chosenMove = firstAttacker.chooseMoveByName(firstMove);
            if (chosenMove != null) {
                System.out.println(chosenMove.getName() + " PP remaining: " + chosenMove.getPp());
            }

            doAttack(firstAttacker, secondAttacker, firstMove);

            if (!secondAttacker.isAlive()) {
                System.out.println(firstAttacker.getName() + " wins!");
                break;
            }

            System.out.println(secondAttacker.getName() + ", choose your move: ");

            String secondMoveInput = scanner.nextLine();
            String secondMove = MoveSelector.getMoveNameByNumber(secondAttacker, secondMoveInput);

            Moves chosenMove2 = secondAttacker.chooseMoveByName(secondMove);
            if (chosenMove2 != null) {
                System.out.println(chosenMove2.getName() + " PP remaining: " + chosenMove2.getPp());
            }

            doAttack(secondAttacker, firstAttacker, secondMove);

            applyEndOfTurnEffects(pokemon1);
            applyEndOfTurnEffects(pokemon2);

            if (!firstAttacker.isAlive()) {
                System.out.println(secondAttacker.getName() + " wins!");
                break;
            }
            round++;

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

    private void doAttack(Pokemon attacker, Pokemon defender, String moveName) {

        if (!attacker.canAct()) {
            System.out.println(attacker.getName() + " is paralyzed and cannot act!");
            return;
        }

        Moves move = attacker.chooseMoveByName(moveName);

        if (move == null) {
            System.out.println("Move not found or invalid.");
            return;
        }

        move.useMove();

        if (!doesMoveHit(move, attacker, defender)) {
            System.out.println(attacker.getName() + " missed " + defender.getName());
            return;
        }

        boolean isDamageApplied = false;

        MoveEffect effect = move.getEffect();


        if (effect instanceof MultiHitMoveEffect) {
            MultiHitMoveEffect multiHitMoveEffect = (MultiHitMoveEffect) effect;
            int totalDamage = multiHitMoveEffect.applyMultiHitDamage(attacker, defender, move);
            System.out.println(attacker.getName() + " hits " + defender.getName() + " with " + move.getName() + " for " + totalDamage + " damage.");
            isDamageApplied = true;

        } else if (effect instanceof MoveEffectWithDamage) {
            MoveEffectWithDamage effectWithDamage = (MoveEffectWithDamage) effect;
            effectWithDamage.applyWithDamage(attacker, defender, move);
            isDamageApplied = true;

        } else {
            effect.apply(attacker, defender);
        }

        if (!isDamageApplied && move.getCategory() != MoveCategory.STATUS) {

            int damage = DamageCalculator.calculateDamage(attacker, defender, move);
            defender.takeDamage(damage);
            System.out.println(attacker.getName() + " hits " + defender.getName() + " with " + move.getName() + " for " + damage + " damage.");
        }
    }


    private boolean doesMoveHit(Moves move, Pokemon attacker, Pokemon defender) {
        Random random = new Random();

        int accuracy = move.getAccuracy();

        int hitRoll = random.nextInt(100) + 1;

        return hitRoll <= accuracy;
    }

    public void applyEndOfTurnEffects(Pokemon pokemon) {
        if (pokemon.isBurned()) {
            int burnDamage = pokemon.getStats().getMaxHp() / 16;
            pokemon.takeDamage(burnDamage);
            System.out.println(pokemon.getName() + " is hurt by it's burn!");
        }
        // more effects
    }
}
