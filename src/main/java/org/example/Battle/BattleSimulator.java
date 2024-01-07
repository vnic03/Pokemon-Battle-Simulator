package org.example.Battle;

import org.example.Pokemon.*;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Effects.MoveEffectWithDamage;
import org.example.Pokemon.Effects.MultiHitMoveEffect;

import java.util.Random;
import java.util.Scanner;

public class BattleSimulator {

    private final static BattleSimulator instance = new BattleSimulator();

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    private Weather currentWeather = Weather.NONE;
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

        pokemon1.getStats().calculateFinalStats(pokemon1);
        pokemon2.getStats().calculateFinalStats(pokemon2);

        System.out.println("/=====================! BATTLE BEGINS  !============================/");

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

            String firstMoveInput = promptForMove(pokemon1, scanner);
            String firstMoveName = MoveSelector.getMoveNameByNumber(pokemon1, firstMoveInput);
            MoveEffect firstMove = MovesRepository.getMoveEffectByName(firstMoveName);

            String secondMoveInput = promptForMove(pokemon2, scanner);
            String secondMoveName = MoveSelector.getMoveNameByNumber(pokemon2, secondMoveInput);
            MoveEffect secondMove = MovesRepository.getMoveEffectByName(secondMoveName);

            Pokemon firstAttacker = determineFirstAttacker(pokemon1, pokemon2, firstMove, secondMove);
            Pokemon secondAttacker = (firstAttacker == pokemon1) ? pokemon2 : pokemon1;


            doAttack(firstAttacker, secondAttacker, MoveSelector.getMoveNameByNumber(firstAttacker, firstMoveName));

            if (secondAttacker.isAlive()) {
                doAttack(secondAttacker, firstAttacker, MoveSelector.getMoveNameByNumber(secondAttacker, secondMoveName));
            } else {
                System.out.println(secondAttacker.getName() + " has fainted !");
                System.out.println(firstAttacker.getName() + " WINS !");
                break;
            }


            if (pokemon1.isAlive()) {
                applyEndOfTurnEffects(pokemon1);
            }
            if (pokemon2.isAlive()) {
                applyEndOfTurnEffects(pokemon2);
            }


            if (!firstAttacker.isAlive()) {
                System.out.println(firstAttacker.getName() + " has fainted ");
                System.out.println(secondAttacker.getName() + " WINS !");
                break;
            }
            round++;

        }

        System.out.println("/===============! The Battle is OVER !===================/");

        pokemon1.resetStats();
        pokemon2.resetStats();

        resetPokemonMoves(pokemon1);
        resetPokemonMoves(pokemon2);
    }

    private Pokemon determineFirstAttacker(Pokemon pokemon1, Pokemon pokemon2, MoveEffect move1, MoveEffect move2) {

        int priority1 = move1.getPriority();
        int priority2 = move2.getPriority();

        if (priority1 > priority2) {
            return pokemon1;
        } else if (priority2 > priority1) {
            return pokemon2;

        } else {

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
    }

    public boolean doesTargetActAfterAttacker(Pokemon attacker, Pokemon target, MoveEffect move1, MoveEffect move2) {

        int priority1 = move1.getPriority();
        int priority2 = move2.getPriority();

        if (priority1 > priority2) {
            return true;
        } else if (priority2 > priority1) {
            return false;

        } else {

            int attackerSpeed = attacker.getStats().getSpeed();
            int targetSpeed = target.getStats().getSpeed();

            if (attackerSpeed > targetSpeed) {
                return true;
            } else if (targetSpeed > attackerSpeed) {
                return false;
            } else {
                return Math.random() < 0.5;
            }
        }
    }

    private void doAttack(Pokemon attacker, Pokemon defender, String moveName) {

        if (!attacker.canAct()) {
            return;
        }

        Moves move = attacker.chooseMoveByName(moveName);

        if (move == null || !move.useMove()) {
            if (attacker.allMovesOutOfPP()) {
                System.out.println(attacker.getName() + " has no moves left !");
                move = MovesRepository.getMoveByName("Struggle");
            } else {
                System.out.println("Move has no PP");
                return;
            }
        }

        move.setAttacker(attacker);

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
            if (move.getName().equalsIgnoreCase("Sunny Day")) {
                System.out.println(attacker.getName() + " used Sunny Day !");
            } else if (move.getName().equalsIgnoreCase("Rain Dance")) {
                System.out.println(attacker.getName() + " used Rain Dance !");
            } else if (move.getName().equalsIgnoreCase("SandStorm")) {
                System.out.println(attacker.getName() + " used Sandstorm !");
            } else if (move.getName().equalsIgnoreCase("Snowscape")) {
                System.out.println(attacker.getName() + " used Snowscape !");
            }

            effect.apply(attacker, defender);

            if (moveName.equalsIgnoreCase("Struggle")) {
                isDamageApplied = true;
            }

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

        if (pokemon.hasActiveAbility("Solar Power") && currentWeather == Weather.SUN) {
            int solarPowerDamage = (int) (pokemon.getStats().getMaxHp() * 0.1);
            pokemon.takeDamage(solarPowerDamage);
            System.out.println(pokemon.getName() + " is hurt by the Sun");
        }

        // more effects

        pokemon.getStats().setSpecialDefense(originalSpDefense);
        pokemon.getStats().setDefense(originalDefense);
    }

    private void applyWeatherEffects(Pokemon pokemon) {

        if (currentWeather == null) {
            return;
        }

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
            System.out.println(pokemon.getName() + " took damage (" + damage +") from Sand");
        }
    }

    public void setWeather(Weather updatedWeather) {

        if (currentWeather != updatedWeather) {
            currentWeather = updatedWeather;
            weatherDuration = 0;
            System.out.println(getWeatherMessage(updatedWeather));
        }
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

    private String promptForMove(Pokemon pokemon, Scanner scanner) {

        while (true) {
            System.out.println(pokemon.getName() + ", choose your move: ");

            for (int i = 0; i < pokemon.getMoves().size(); i++) {
                System.out.println((i + 1) + ": " + pokemon.getMoves().get(i).getName()+ " Type: " + pokemon.getMoves().get(i).getType() + " - PP  " + pokemon.getMoves().get(i).getCurrentPP());
            }

            String moveInput = scanner.nextLine();

            if (isMoveInputValid(moveInput, pokemon)) {
                return moveInput;
            } else {
                System.out.println("Invalid move. Try again !");
            }
        }
    }

    private boolean isMoveInputValid(String input, Pokemon pokemon) {
        try {
            int moveIndex = Integer.parseInt(input) - 1;
            return moveIndex >= 0 && moveIndex < pokemon.getMoves().size();
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void resetPokemonMoves(Pokemon pokemon) {
        for (Moves move : pokemon.getMoves()) {
            move.resetPower();
        }
    }
}
