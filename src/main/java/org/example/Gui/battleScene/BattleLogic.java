package org.example.Gui.battleScene;

import org.example.Battle.DamageCalculator;
import org.example.Battle.Weather;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Effects.MoveEffectWithDamage;
import org.example.Pokemon.Effects.MultiHitMoveEffect;
import org.example.Pokemon.MoveCategory;
import org.example.Pokemon.Moves;
import org.example.Pokemon.MovesRepository;
import org.example.Pokemon.Pokemon;
import org.example.teams.Team;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BattleLogic {
    private Team team1;
    private Team team2;
    private static final int ROUND_DURATION = 60;
    private Weather currentWeather;
    private Moves selectedMoveTeam1;
    private Moves selectedMoveTeam2;
    private Timer moveSelectionTimer;
    private boolean moveSelectedTeam1 = false;
    private boolean moveSelectedTeam2 = false;
    private BattleView battleView;

    public BattleLogic(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.currentWeather = Weather.NONE;
    }
    public void initiateRound() {
        startRound();
        battleView.showNewRoundStarted();
    }
    public void startRound() {

        moveSelectedTeam1 = false;
        moveSelectedTeam2 = false;

        for (int i = 0; i < team1.getPokemons().size(); i++) {
            Pokemon pokemon = team1.getPokemons().get(i);
            if (pokemon != null) {
               pokemon.saveOriginalStats();
               pokemon.getStats().calculateFinalStats(pokemon);
            }
        }
        for (int i = 0; i < team2.getPokemons().size(); i++) {
            Pokemon pokemon = team2.getPokemons().get(i);
            if (pokemon != null) {
                pokemon.saveOriginalStats();
                pokemon.getStats().calculateFinalStats(pokemon);
            }
        }
        startMoveSelectionTimer();
    }
    public void performRound() {
        Pokemon activePokemonTeam1 = team1.getActivePokemon();
        Pokemon activePokemonTeam2 = team2.getActivePokemon();

        BattleRoundResult resultTeam1 = performAttack(activePokemonTeam1, activePokemonTeam2, selectedMoveTeam1);
        battleView.updateBattlelog(resultTeam1);
        BattleRoundResult resultTeam2 = performAttack(activePokemonTeam2, activePokemonTeam1, selectedMoveTeam2);
        battleView.updateBattlelog(resultTeam2);

        // update UI

        if (!team1.hasAlivePokemon() || !team2.hasAlivePokemon()) {
            endGame();
        }
    }


    private BattleRoundResult performAttack(Pokemon attacker, Pokemon defender, Moves move) {
        BattleRoundResult result = new BattleRoundResult("", 0, false, false);
        if (!attacker.canAct(result)) {
            return result;
        }
        if (!move.useMove()) {
            if (attacker.allMovesOutOfPP()) {
                move = MovesRepository.getMoveByName("Struggle");
                result.setMessage(attacker.getName() + " struggled. . .");
            } else {
                result.setMessage(move.getName() + " has no pp left");
                return result;
            }
        }
        move.setAttacker(attacker);

        if (!doesMoveHit(move, attacker, defender)) {
            result.setMessage(attacker.getName() + " missed " + defender.getName());
            return result;
        }
        boolean isDamageApplied = false;

        MoveEffect effect = move.getEffect();

        if (effect instanceof MultiHitMoveEffect multiHitMoveEffect) {
            int totalDamage = multiHitMoveEffect.applyMultiHitDamage(attacker, defender, move, currentWeather);
            // show how many times the move hits
            isDamageApplied = true;
        } else if (effect instanceof MoveEffectWithDamage) {
            MoveEffectWithDamage effectWithDamage = (MoveEffectWithDamage) effect;
            effectWithDamage.applyWithDamage(attacker, defender, move, currentWeather);
            isDamageApplied = true;
        } else {
            effect.apply(attacker, defender);
            if (move.equals(MovesRepository.getMoveByName("Struggle"))) {
                isDamageApplied = true;
            }
        }
        if (!isDamageApplied && move.getCategory() != MoveCategory.STATUS) {
            int damage = DamageCalculator.calculateDamage(attacker, defender, move, currentWeather);
            defender.takeDamage(damage);
            result.setDamageDealt(damage);
            result.setMessage(attacker.getName() + " hits " + defender.getName() + " with " + move.getName() + " for " + damage + " Damage.");
            result.setWasSuccessful(true);
        }
        if (!defender.isAlive()) {
            result.setDidFaint(true);
        }
        return result;
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

    private void endGame() {
        // logic for winning and playing again with the same team
        //if not playing again {
        for (int i = 0; i < team1.getPokemons().size(); i++) {
            team1.getPokemons().get(i).resetStats();
        }
        for (int i = 0; i < team2.getPokemons().size(); i++) {
            team2.getPokemons().get(i).resetStats();
        }
    }
    private void startMoveSelectionTimer(){
        moveSelectionTimer = new Timer();
        moveSelectionTimer.schedule(new TimerTask() {
            private int remainingTimeTeam1 = ROUND_DURATION ;
            private int remainingTimeTeam2 = ROUND_DURATION ;
            @Override
            public void run() {
                if (remainingTimeTeam1 > 0) {
                    battleView.updateTimerTeam1(remainingTimeTeam1--);
                } else {onTimeExpired(); }
                if (remainingTimeTeam2 > 0) {
                    battleView.updateTimerTeam2(remainingTimeTeam2--);
                } else {onTimeExpired(); }
                if (remainingTimeTeam1 <= 0 && remainingTimeTeam2 <= 0) {
                    cancel();
                }
            }
        }, 0, 1000);
    }
    public void receivedMoveSelection(Moves move, Team team) {
        if (team.equals(team1)) {
            selectedMoveTeam1 = move;
            moveSelectedTeam1 = true;
        } else if (team.equals(team2)) {
            selectedMoveTeam2 = move;
            moveSelectedTeam2 = true;
        }
        if (moveSelectedTeam1 && moveSelectedTeam2) {
            moveSelectionTimer.cancel();
            performRound();
            moveSelectedTeam1 = false;
            moveSelectedTeam2 = false;
        }
    }
    private void onTimeExpired(){
        if (!moveSelectedTeam1) {
            selectedMoveTeam1 = chooseDefaultMove(team1);
        }
        if (!moveSelectedTeam2) {
            selectedMoveTeam2 = chooseDefaultMove(team2);
        }
        performRound();
    }
    private Moves chooseDefaultMove(Team team) {
        return team.getFirstAvailableMove();
    }

    public BattleRoundResult processMoveSelection(Moves move, Pokemon activePokemon) {
        BattleRoundResult result = new BattleRoundResult("", 0, false, false);

        if (!activePokemon.canAct(result)) {
            return result;
        }
        return result;
    }
    private boolean doesMoveHit(Moves move, Pokemon attacker, Pokemon defender) {
        Random random = new Random();
        int accuracy = move.getAccuracy();
        int hitRoll = random.nextInt(100) + 1;
        return hitRoll <= accuracy;
    }

    public void setBattleView(BattleView battleView) {
        this.battleView = battleView;
    }
}
