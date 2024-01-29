package org.example.screens.battleScene;

import org.example.screens.battle.DamageCalculator;
import org.example.screens.battle.Weather;
import org.example.pokemon.moveEffects.MoveEffect;
import org.example.pokemon.moveEffects.MoveEffectWithDamage;
import org.example.pokemon.moveEffects.MultiHitMoveEffect;
import org.example.pokemon.MoveCategory;
import org.example.pokemon.Moves;
import org.example.pokemon.repositories.MovesRepository;
import org.example.pokemon.Pokemon;
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
                pokemon.calculateStatsIfNecessary();
            }
        }
        for (int i = 0; i < team2.getPokemons().size(); i++) {
            Pokemon pokemon = team2.getPokemons().get(i);
            if (pokemon != null) {
                pokemon.saveOriginalStats();
                pokemon.calculateStatsIfNecessary();
            }
        }
        startMoveSelectionTimer();
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
            checkAndPerformRound();
        }
    }
    public void checkAndPerformRound() {

        if (moveSelectedTeam1 && moveSelectedTeam2) {
            Pokemon firstAttacker = determineFirstAttacker(team1.getActivePokemon(), team2.getActivePokemon(),
                    selectedMoveTeam1.getEffect(), selectedMoveTeam2.getEffect());
            Pokemon secondAttacker = firstAttacker.equals(team1.getActivePokemon())
                    ? team2.getActivePokemon() : team1.getActivePokemon();

            Moves firstMove = firstAttacker.equals(team1.getActivePokemon()) ? selectedMoveTeam1 : selectedMoveTeam2;
            Moves secondMove=  firstAttacker.equals(team1.getActivePokemon()) ? selectedMoveTeam2 : selectedMoveTeam1;

            BattleRoundResult result1 = performAttack(firstAttacker, secondAttacker, firstMove);
            battleView.updateBattlelog(result1);
            if (secondAttacker.isAlive()) {
                BattleRoundResult result2 = performAttack(secondAttacker, firstAttacker, secondMove);
                battleView.updateBattlelog(result2);
            }
            resetRound();
            checkEndGame();
        }
    }
    private void resetRound() {
        moveSelectedTeam1 = false;
        moveSelectedTeam2 = false;
        startMoveSelectionTimer();
        battleView.showButtons();
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
            int totalDamage = multiHitMoveEffect.applyMultiHitDamage(attacker, defender, move, currentWeather, result);
            // show how many times the move hits
            isDamageApplied = true;
        } else if (effect instanceof MoveEffectWithDamage) {
            MoveEffectWithDamage effectWithDamage = (MoveEffectWithDamage) effect;
            effectWithDamage.applyWithDamage(attacker, defender, move, currentWeather, result);
            isDamageApplied = true;
        } else {
            effect.apply(attacker, defender,result);
            if (move.equals(MovesRepository.getMoveByName("Struggle"))) {
                isDamageApplied = true;
            }
        }
        if (!isDamageApplied && move.getCategory() != MoveCategory.STATUS) {
            int damage = DamageCalculator.calculateDamage(attacker, defender, move, currentWeather, result);
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

    private void startMoveSelectionTimer() {
        moveSelectionTimer = new Timer();
        moveSelectionTimer.schedule(new TimerTask() {
            private int remainingTime = ROUND_DURATION;
            @Override
            public void run() {
                if (remainingTime > 0) {
                    battleView.updateTimerTeam1(remainingTime);
                    battleView.updateTimerTeam2(remainingTime);
                    remainingTime--;
                } else {
                    cancel();
                    onTimeExpired();
                }
            }
        }, 0, 1000);
    }
    private void onTimeExpired() {
        boolean roundPerformed = false;

        if (!moveSelectedTeam1) {
            selectedMoveTeam1 = chooseDefaultMove(team1);
            moveSelectedTeam1 = true;
            roundPerformed = true;
        }
        if (!moveSelectedTeam2) {
            selectedMoveTeam2 = chooseDefaultMove(team2);
            moveSelectedTeam2 = true;
            roundPerformed = true;
        }
        if (roundPerformed) {
            checkAndPerformRound();
        }
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

    private void checkEndGame() {
        if (!team1.hasAlivePokemon() || !team2.hasAlivePokemon()) {
            endGame();
        }
    }
    public boolean areMovesSelected(boolean isTeam1) {
        return moveSelectedTeam1 ? isTeam1 : moveSelectedTeam2;
    }
}
