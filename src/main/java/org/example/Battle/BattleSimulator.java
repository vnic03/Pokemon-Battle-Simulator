package org.example.Battle;

import org.example.Pokemon.*;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Effects.MoveEffectWithDamage;
import org.example.Pokemon.Effects.MultiHitMoveEffect;

import java.util.Random;
import java.util.Scanner;

public class BattleSimulator {

    private static BattleSimulator instance = new BattleSimulator();

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    private Weather currentWeather;
    private int weatherDuration;

    private BattleSimulator(){}

    public static BattleSimulator getInstance() {
        return instance;
    }


    public void simulateBattle(Pokemon pokemon1, Pokemon pokemon2) {

        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;

        pokemon1.saveOriginalStats();
        pokemon2.saveOriginalStats();

        setWeather(Weather.NONE);

        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (pokemon1.isAlive() && pokemon2.isAlive()) {

            System.out.println("Round " + round + ": ");
            System.out.println(pokemon1.getName() + " (" + pokemon1.getStats().getHp() + " HP) vs. " + pokemon2.getName() + " (" + pokemon2.getStats().getHp() + " HP)");

            if (currentWeather != Weather.NONE) {
                weatherDuration++;
                if (weatherDuration >= 5) {
                    setWeather(Weather.NONE);
                }
            }

            System.out.println("Current weather: " + currentWeather);

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

        pokemon1.resetStats();
        pokemon2.resetStats();
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
            int totalDamage = multiHitMoveEffect.applyMultiHitDamage(attacker, defender, move, currentWeather);
            System.out.println(attacker.getName() + " hits " + defender.getName() + " with " + move.getName() + " for " + totalDamage + " damage.");
            isDamageApplied = true;

        } else if (effect instanceof MoveEffectWithDamage) {
            MoveEffectWithDamage effectWithDamage = (MoveEffectWithDamage) effect;
            effectWithDamage.applyWithDamage(attacker, defender, move, currentWeather);
            isDamageApplied = true;

        } else {
            effect.apply(attacker, defender);
        }

        if (!isDamageApplied && move.getCategory() != MoveCategory.STATUS) {

            int damage = DamageCalculator.calculateDamage(attacker, defender, move, currentWeather);
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

        int originalSpDefense = pokemon.getStats().getSpecialDefense();
        int originalDefense = pokemon.getStats().getDefense();

        applyWeatherEffects(pokemon);

        switch (currentWeather) {
            case SANDSTORM:
                applySandstormDamage(pokemon);
                if (pokemon.getTyping().contains(PokeTyping.ROCK)) {
                    pokemon.getStats().setSpecialDefense((int) (pokemon.getStats().getSpecialDefense() * 1.5));
                }
                break;
            case SNOW:
                if (pokemon.getTyping().contains(PokeTyping.ICE)) {
                    pokemon.getStats().setDefense((int) (pokemon.getStats().getDefense() * 1.5));
                }
                break;
        }

        if (pokemon.isBurned()) {
            int burnDamage = pokemon.getStats().getMaxHp() / 16;
            pokemon.takeDamage(burnDamage);
            System.out.println(pokemon.getName() + " is hurt by it's burn!");
        }

        if (pokemon.isPoisoned()) {
            int poisonDamage = pokemon.getStats().getMaxHp() / 8;
            pokemon.takeDamage(poisonDamage);
            System.out.println(pokemon.getName() + " is hurt by poison !");
        }

        if (pokemon.isBadlyPoisoned()) {
            int badlyPoisonDamage = pokemon.getStats().getMaxHp() / 16 * pokemon.getBadlyPoisonedTurns();
            pokemon.takeDamage(badlyPoisonDamage);
            System.out.println(pokemon.getName() + " is hurt by bad poison !");
        }

        pokemon.decrementSleepTurns();

        // more effects

        pokemon.getStats().setSpecialDefense(originalSpDefense);
        pokemon.getStats().setDefense(originalDefense);
    }

    private void applyWeatherEffects(Pokemon pokemon) {
        switch (currentWeather) {
            case SANDSTORM:
                applySandstormDamage(pokemon);
                if (pokemon.getTyping().contains(PokeTyping.ROCK)) {
                    pokemon.getStats().setSpecialDefense((int) (pokemon.getStats().getSpecialDefense() * 1.5));
                }
                break;
            case SNOW:
                if (pokemon.getTyping().contains(PokeTyping.ICE)) {
                    pokemon.getStats().setDefense((int) (pokemon.getStats().getDefense() * 1.5));
                }
                break;
        }
    }

    private void applySandstormDamage(Pokemon pokemon) {
        if (!pokemon.getTyping().contains(PokeTyping.ROCK) &&
                !pokemon.getTyping().contains(PokeTyping.GROUND) &&
                !pokemon.getTyping().contains(PokeTyping.STEEL)) {

            int damage = pokemon.getStats().getMaxHp() / 16;
            pokemon.takeDamage(damage);
            System.out.println(pokemon.getName() + " took damage(" + damage +") from Sand");
        }
    }

    public void setWeather(Weather updatedWeather) {
        currentWeather = updatedWeather;
        weatherDuration = 0;
        System.out.println(getWeatherMessage(updatedWeather));
    }

    private void resetWeatherEffects(Pokemon pokemon) {
        pokemon.resetStats();
    }

    private String getWeatherMessage(Weather weather) {
        switch (weather) {
            case SUN -> {
                return "The sunlight turned harsh!";
            }
            case RAIN -> {
                return "It started to rain!";
            }
            case SANDSTORM -> {
                return "A sandstorm kicked up!";
            }
            case SNOW -> {
                return "It started to snow!";
            }
            case NONE -> {
                return "The weather cleared up !";
            }
            default -> {
                return "The weather changed !";
            }
        }
    }


}
