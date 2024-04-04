package org.example.pokemon.move_effects.status_condition;

import org.example.pokemon.Typing;
import org.example.pokemon.move_effects.MoveEffect;
import org.example.screens.battleScene.BattleRoundResult;
import org.example.pokemon.Pokemon;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class SleepEffect extends MoveEffect {

    private static final int MAX_SLEEP_TURNS = 3;

    private final Optional<Set<Typing>> immuneTypes;

    public SleepEffect() {
        this.immuneTypes = Optional.empty();
    }

    public SleepEffect(Set<Typing> immuneTypes) {
        this.immuneTypes = Optional.of(immuneTypes);
    }

    @Override
    public void apply(Pokemon user, Pokemon target, BattleRoundResult result) {
        if (target.hasStatusCondition()) {
            result.setMessage(target.getName() + " can't be affected!");
            return;
        }
        if (immuneTypes.isPresent() && immuneTypes.get().stream().anyMatch(target.getTyping()::contains)) {
            result.setMessage(target.getName() + " can't be affected!");
            return;
        }
        int sleepTurns = new Random().nextInt(MAX_SLEEP_TURNS) + 1;
        target.setAsleep(true, sleepTurns);
        result.setMessage(target.getName() + " is fast asleep!");
    }
}
