package org.example.pokemon.ability;

import org.example.Constants;
import org.example.battle.DamageCalculator;
import org.example.battle.TypeChart;
import org.example.battle.Weather;
import org.example.pokemon.*;
import org.example.screens.battleScene.BattleRoundResult;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class AbilityRepository {

    private final Map<Ability.Name, Ability> abilities;

    private final EffectHandler handler = new EffectHandler();

    public AbilityRepository() {
        abilities = new HashMap<>();
        createEffects();
        init();
    }

    public Ability getAbility(Ability.Name name) {
        return abilities.get(name);
    }

    public void init() {
        final Map<String, String> data = loadFromJson(Constants.PATH_TO_ABILITIES_JSON);

        for (Map.Entry<String, String> entry : data.entrySet()) {

            Ability.Name name = Ability.convert(entry.getKey());
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
        handler.registerEffect(List.of(
                Ability.Name.BLAZE, Ability.Name.OVERGROW, Ability.Name.TORRENT, Ability.Name.SWARM),
                this::starterEffect);

        handler.registerEffect(Ability.Name.CHLOROPHYLL, (user, target, move, weather, result) -> {
            if (weather == Weather.SUN) user.getStats().setSpeed(user.getStats().getSpeed() * 2);
            else user.resetStats();
        });

        handler.registerEffect(Ability.Name.CURSED_BODY, (user, target, move, weather, result) ->{
            final double ACTIVATION_CHANCE = 0.3;
            final int DISABLE_DURATION = 3;
            if (new Random().nextDouble() < ACTIVATION_CHANCE) {
                Pokemon attacker = move.getAttacker();
                attacker.disableMove(move, DISABLE_DURATION);
                result.setMessage(attacker.getName() + "'s " + move.getName() + " was disabled by Cursed Body!");
            }
        });

        handler.registerEffect(Ability.Name.FLAME_BODY, (user, target, move, weather, result) -> {
            final double BURN_CHANCE = 0.3;
            if (move.getCategory() == MoveCategory.PHYSICAL) {
                if (new Random().nextDouble() < BURN_CHANCE) move.getAttacker().setBurned(true);
            }
        });

        handler.registerEffect(Ability.Name.STATIC, (user, target, move, weather, result) -> {
            final double PARALYSIS_CHANCE = 0.3;
            if (move.getCategory() == MoveCategory.PHYSICAL) {
                if (new Random().nextDouble() < PARALYSIS_CHANCE) move.getAttacker().setParalyzed(true);
            }
        });

        handler.registerEffect(Ability.Name.GUTS, (user, target, move, weather, result) -> {
            if (user.hasStatusCondition() && !user.getEffectHandler().isGutsActive()) {
                user.getStats().setAttack((int) (user.getStats().getAttack() * 1.5));
                user.getEffectHandler().activateGuts(true);
            } else if (!user.hasStatusCondition() && user.getEffectHandler().isGutsActive()) {
                user.resetAttack();
                user.getEffectHandler().activateGuts(false);
            }
        });

        handler.registerEffect(Ability.Name.HYDRATION, (user, target, move, weather, result) -> {
            if (result.getCurrentWeather() == Weather.RAIN) user.clearStatusCondition();
        });

        handler.registerEffect(Ability.Name.MAGMA_ARMOR, (user, target, move, weather, result) -> {
            if (user.isFrozen()) user.clearStatusCondition();
            result.setMessage(user.getName() + " is protected by Magma Armor");
        });

        handler.registerEffect(Ability.Name.MARVEL_SCALE, (user, target, move, weather, result) -> {
            if (user.hasStatusCondition()) user.getStats().setDefense((int) (user.getStats().getDefense() * 1.5));
            else user.resetStats();
        });

        handler.registerEffect(Ability.Name.MULTISCALE, (user, target, move, weather, result) -> {
            if (target.getStats().getHp() == target.getStats().getMaxHp()) {
                int damage = (DamageCalculator.calculateDamage(user, target, move, weather, result)) / 2;
                user.takeDamage(damage);
            }
        });

        handler.registerEffect(Ability.Name.NO_GUARD, (user, target, move, weather, result) -> {
            // TODO: implement that moves also hit the user (pokemon) 100%
            if (user.hasActiveAbility(Ability.Name.NO_GUARD)) move.setAccuracy(100);
        });

        handler.registerEffect(Ability.Name.SOLAR_POWER, (user, target, move, weather, result) -> {
            if (weather == Weather.SUN) user.getStats().setSpecialAttack((int) (user.getStats().getSpecialAttack() * 1.5));
            // TODO: implement passive damage in Battle Logic
        });


        handler.registerEffect(Ability.Name.THICK_FAT, (user, target, move, weather, result) -> {
            if (move.getType() == Typing.FIRE || move.getType() == Typing.ICE) user.getEffectHandler().activateThickFat(true);
        });

        // Prevents-Crits-Effect implemented in Damage Calculator
        handler.registerEffect(List.of(Ability.Name.BATTLE_ARMOR, Ability.Name.SHELL_ARMOR), (user, target, move, weather, result) -> { });

        handler.registerEffect(Ability.Name.WATER_ABSORB, (user, target, move, weather, result) -> {
            if (move.getType() == Typing.WATER) {
                user.heal((int) (user.getStats().getMaxHp() / 4.0));
                result.setMessage(user.getName() + ": Water Absorb!");
                // TODO: user takes no damage
            }
        });

        handler.registerEffect(Ability.Name.TECHNICIAN, (user, target, move, weather, result) -> {
            if (move.getPower() <= 60) move.setPower((int) (move.getPower() * 1.5));
        });

        handler.registerEffect(Ability.Name.SYNCHRONIZE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.STURDY, (user, target, move, weather, result) -> {
            if (target.getStats().getHp() == target.getStats().getMaxHp()) {
                int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
                if (target.getStats().getHp() - damage <= 0) {
                    user.takeDamage(target.getStats().getHp() - 1);
                    result.setMessage(target.getName() + " survived thanks to Sturdy!");
                }
            }
        });

        handler.registerEffect(Ability.Name.SPEED_BOOST, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.SOUNDPROOF, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.SCRAPPY, (user, target, move, weather, result) -> {
            if (target.getTyping().contains(Typing.GHOST)) {
                if (move.getType() == Typing.NORMAL || move.getType() == Typing.FIGHTING) {
                    double original = TypeChart.getEffectiveness(move.getType(), Typing.GHOST);
                    result.setEffectiveness(original > 0 ? original : 1.0);
                }
            }
        });

        handler.registerEffect(Ability.Name.ROUGH_SKIN, (user, target, move, weather, result) -> {
            if (move.getCategory() == MoveCategory.PHYSICAL) {
                move.getAttacker().takeDamage(move.getAttacker().getStats().getMaxHp() / 8);
                result.setMessage(move.getAttacker().getName() + " was hurt by " + target.getName() + "'s Rough Skin!");
            }
        });

        handler.registerEffect(Ability.Name.RAIN_DISH, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.MOXIE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.MAGIC_GUARD, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.KEEN_EYE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.INTIMIDATE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.INNER_FOCUS, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.FOREWARN, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.FLASH_FIRE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.DRY_SKIN, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(List.of(Ability.Name.FILTER, Ability.Name.SOLID_ROCK), (user, target, move, weather, result) -> {
            // TODO: Damage-Reduction-Effect
        });

        handler.registerEffect(Ability.Name.COMPETITIVE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.CLEAR_BODY, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.ANGER_POINT, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.AIR_LOCK, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.MIRROR_ARMOR, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.LIGHTNING_ROD, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.SAP_SIPPER, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.SWIFT_SWIM, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.SAND_VEIL, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.SAND_RUSH, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.ADAPTABILITY, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.ICE_BODY, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.PIXILATE, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.VOLT_ABSORB, (user, target, move, weather, result) -> {
            // TODO
        });

        handler.registerEffect(Ability.Name.LEVITATE, (user, target, move, weather, result) -> {
            if (move.getType() == Typing.GROUND) {
                user.takeDamage(0);
                result.setDamageDealt(0);
                result.setMessage(user.getName() + "Levitate!");
            }
        });

        handler.registerEffect(Ability.Name.COMPOUND_EYES, (user, target, move, weather, result) -> {
            // TODO: Not for ONE-HIT-KO moves (need to be implemented first)
            user.getMoves().forEach(m -> m.setAccuracy((int) (m.getAccuracy() * 1.3)));
        });

        handler.registerEffect(Ability.Name.TINTED_LENS, (user, target, move, weather, result) -> {
            int damage = DamageCalculator.calculateDamage(user, target, move, weather, result);
            if (result.getEffectiveness() < 1.0 && result.getEffectiveness() > 0) damage *= 2;
            target.takeDamage(damage);
        });

        handler.registerEffect(List.of(Ability.Name.TERAVOLT, Ability.Name.TURBOBLAZE),
                (user, target, move, weather, result) -> {
                // TODO
        });

        handler.registerEffect(List.of(
                Ability.Name.DROUGHT, Ability.Name.DRIZZLE, Ability.Name.SAND_STREAM), this::weatherEffect);
    }

    private void weatherEffect(Pokemon user, Pokemon target, Moves move, Weather weather, BattleRoundResult result)
    {
        // TODO
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
