package org.example.pokemon.ability;

import org.example.battle.DamageCalculator;
import org.example.battle.Weather;
import org.example.pokemon.*;
import org.example.repositories.api.PokeApiClient;
import org.example.screens.battleScene.BattleRoundResult;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AbilityRepository {

    private final Map<String, Ability> abilities;

    private final EffectHandler handler = new EffectHandler();

    public AbilityRepository() {
        abilities = new HashMap<>();
        createEffects();
        init();
    }

    public static void main(String[] args) {
        PokeApiClient.saveAbilities("");
    }

    public Ability getAbility(String name) {
        return abilities.get(name);
    }

    public void init() {
        final Map<String, String> data = loadFromJson(
                "src/main/java/org/example/pokemon/ability/abilities.json");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String name = entry.getKey().replace("-", " ");
            String description = entry.getValue();
            AbilityEffect effect = handler.getEffect(name);

            if (!abilities.containsKey(name)) {
                abilities.put(name, new Ability(name, description, effect));

            } else System.out.println("Ability " + name + " already exists.");
        }
    }

    public Map<String, String> loadFromJson(String path) {
        Map<String, String> abilities = new HashMap<>();
        try {
            String text = new String(Files.readAllBytes(Paths.get(path)));
            JSONObject obj = new JSONObject(text);

            for(String key : obj.keySet()) {
                abilities.put(key, obj.getString(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abilities;
    }

    private void createEffects() {
        handler.registerEffect(List.of("blaze", "overgrow", "torrent", "swarm"), this::starterEffect);

        handler.registerEffect("chlorophyll", (user, target, move, weather, result) -> {
            if (weather == Weather.SUN) user.getStats().setSpeed(user.getStats().getSpeed() * 2);
            else user.resetStats();
        });

        handler.registerEffect("cursed body", (user, target, move, weather, result) ->{
            final double ACTIVATION_CHANCE = 0.3;
            final int DISABLE_DURATION = 3;
            if (new Random().nextDouble() < ACTIVATION_CHANCE) {
                Pokemon attacker = move.getAttacker();
                attacker.disableMove(move, DISABLE_DURATION);
                result.setMessage(attacker.getName() + "'s " + move.getName() + " was disabled by Cursed Body!");
            }
        });

        handler.registerEffect("flame body", (user, target, move, weather, result) -> {
            final double BURN_CHANCE = 0.3;
            if (move.getCategory() == MoveCategory.PHYSICAL) {
                if (new Random().nextDouble() < BURN_CHANCE) move.getAttacker().setBurned(true);
            }
        });

        handler.registerEffect("static", (user, target, move, weather, result) -> {
            final double PARALYSIS_CHANCE = 0.3;
            if (move.getCategory() == MoveCategory.PHYSICAL) {
                if (new Random().nextDouble() < PARALYSIS_CHANCE) move.getAttacker().setParalyzed(true);
            }
        });

        handler.registerEffect("guts", (user, target, move, weather, result) -> {
            if (user.hasStatusCondition() && !user.isGutsActivated()) {
                user.getStats().setAttack((int) (user.getStats().getAttack() * 1.5));
                user.setGutsActivated(true);
            } else if (!user.hasStatusCondition() && user.isGutsActivated()) {
                user.resetAttack();
                user.setGutsActivated(false);
            }
        });

        handler.registerEffect("hydration", (user, target, move, weather, result) -> {
            if (result.getCurrentWeather() == Weather.RAIN) user.clearStatusCondition();
        });

        handler.registerEffect("magma armor", (user, target, move, weather, result) -> {
            if (user.isFrozen()) user.clearStatusCondition();
            result.setMessage(user.getName() + " is protected by Magma Armor");
        });

        handler.registerEffect("marvel scale", (user, target, move, weather, result) -> {
            if (user.hasStatusCondition()) user.getStats().setDefense((int) (user.getStats().getDefense() * 1.5));
            else user.resetStats();
        });

        handler.registerEffect("multiscale", (user, target, move, weather, result) -> {
            if (target.getStats().getHp() == target.getStats().getMaxHp()) {
                int damage = (DamageCalculator.calculateDamage(user, target, move, weather, result)) / 2;
                user.takeDamage(damage);
            }
        });

        handler.registerEffect("no guard", (user, target, move, weather, result) -> {
            // TODO: implement that moves also hit the user (pokemon) 100%
            if (user.hasActiveAbility("No Guard")) move.setAccuracy(100);
        });

        handler.registerEffect("solar power", (user, target, move, weather, result) -> {
            if (weather == Weather.SUN) user.getStats().setSpecialAttack((int) (user.getStats().getSpecialAttack() * 1.5));
            // TODO: implement passive damage in Battle Logic
        });


        handler.registerEffect("thick fat", (user, target, move, weather, result) -> {
            if (move.getType() == Typing.FIRE || move.getType() == Typing.ICE) user.setThickFatActive(true);
        });

        // Prevents-Crits-Effect implemented in Damage Calculator
        handler.registerEffect(List.of("battle armor", "shell armor"), (user, target, move, weather, result) -> { });

        handler.registerEffect("water absorb", (user, target, move, weather, result) -> {
            if (move.getType() == Typing.WATER) {
                user.heal((int) (user.getStats().getMaxHp() / 4.0));
                result.setMessage(user.getName() + ": Water Absorb!");
                // TODO: user takes no damage
            }
        });

        handler.registerEffect("technician", (user, target, move, weather, result) -> {
            if (move.getPower() <= 60) move.setPower((int) (move.getPower() * 1.5));
        });

        handler.registerEffect("synchronize", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("sturdy", (user, target, move, weather, result) -> {
            if (target.getStats().getHp() == target.getStats().getMaxHp()) {
                int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
                if (target.getStats().getHp() - damage <= 0) {
                    user.takeDamage(target.getStats().getHp() - 1);
                    result.setMessage(target.getName() + " survived thanks to Sturdy!");
                }
            }
        });

        handler.registerEffect("speed boost", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("soundproof", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("scrappy", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("rough skin", (user, target, move, weather, result) -> {
            if (move.getCategory() == MoveCategory.PHYSICAL) {
                move.getAttacker().takeDamage(move.getAttacker().getStats().getMaxHp() / 8);
                result.setMessage(move.getAttacker().getName() + " was hurt by " + target.getName() + "'s Rough Skin!");
            }
        });

        handler.registerEffect("rain dish", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("moxie", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("magic guard", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("keen eye", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("intimidate", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("inner focus", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("forewarn", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("flash fire", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("dry skin", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(List.of("filter", "solid"), (user, target, move, weather, result) -> {
            // TODO: Damage-Reduction-Effect
        });

        handler.registerEffect("competitive", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("clear body", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("anger point", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect("air lock", (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(List.of("drought", "drizzle", "sand stream"), (user, target, move, weather, result) -> {
            // TODO: Weather-Effect
        });
    }

    private void starterEffect(
            Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result)
    {
        starterEffectHelper(user, Typing.FIRE);
        starterEffectHelper(user, Typing.WATER);
        starterEffectHelper(user, Typing.GRASS);
        starterEffectHelper(user, Typing.BUG);
    }

    private void starterEffectHelper(Pokemon user, Typing type) {
        int lowHp = (int) (user.getStats().getMaxHp() * 0.33);
        if (user.getStats().getHp() <= lowHp && user.getTyping().contains(type)) {
            for (Moves move : user.getMoves()) {
                if (move.getType() == type) {
                    move.saveOriginalPower();
                    move.setPower((int) (move.getPower() * 1.5));
                }
            }
        }
    }
}
