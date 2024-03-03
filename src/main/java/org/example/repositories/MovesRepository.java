package org.example.repositories;

import org.example.pokemon.MoveCategory;
import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.battle.Weather;
import org.example.pokemon.move_effects.*;
import org.example.pokemon.move_effects.priority.PriorityOne;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MovesRepository {

    private static final List<Moves> MOVES = new ArrayList<>();

    static { initializeMoves(); }

    public static void initializeMoves() {

        MoveEffect nothing = new NoEffect();

        MoveEffect struggleEffect = new StruggleEffect();

        MoveEffect May_paralyze_the_target = new MayParalyzeEffect();

        MoveEffect paralyzes_opponent = new ParalyzeEffect();

        MoveEffectWithDamage absorbs_hp = new AbsorbEffect();

        MoveEffect may_cause_flinching_10 = new MayFlinchEffect(0.10);
        MoveEffect may_cause_flinching_20 = new MayFlinchEffect(0.20);
        MoveEffect may_cause_flinching_30 = new MayFlinchEffect(0.30);

        MoveEffect hits_2_5_times = new HitsMoreTimesEffect();

        MoveEffect burns_opponent = new BurnEffect();

        MoveEffect may_burn_opponent = new MayBurnEffect();

        MoveEffect poisons_opponent = new PoisonEffect();

        MoveEffect badly_poisons_opponent = new BadlyPoisonedEffect();

        MoveEffect may_poison_opponent = new MayPoisonEffect();

        MoveEffect may_freeze_opponent = new MayFreezeEffect();

        MoveEffect puts_opponent_to_sleep = new SleepEffect();

        MoveEffect puts_opponent_to_sleep_no_grass = new SleepPowderEffect();

        MoveEffect makes_it_sunny = new ChangeWeatherEffect(Weather.SUN);

        MoveEffect makes_it_rain = new ChangeWeatherEffect(Weather.RAIN);

        MoveEffect creates_a_sandStorm = new ChangeWeatherEffect(Weather.SANDSTORM);

        MoveEffect snow = new ChangeWeatherEffect(Weather.SNOW);

        MoveEffect user_faints = new ExplosionEffect();

        MoveEffect priority_one = new PriorityOne();


        // no Effect

        move("Cut", Typing.NORMAL, MoveCategory.PHYSICAL, 50, 95, 30, nothing);
        move("Drill Peck", Typing.FLYING, MoveCategory.PHYSICAL, 80, 100, 20, nothing);
        move("Egg Bomb", Typing.NORMAL, MoveCategory.PHYSICAL, 100, 75, 10, nothing);
        move("Horn Attack", Typing.NORMAL, MoveCategory.PHYSICAL, 65, 100, 25, nothing);
        move("Hydro Pump", Typing.WATER, MoveCategory.SPECIAL, 110, 80, 5, nothing);
        move("Mega Kick", Typing.NORMAL, MoveCategory.PHYSICAL, 120, 75, 5, nothing);
        move("Mega Punch", Typing.NORMAL, MoveCategory.PHYSICAL, 80, 85, 20, nothing);
        move("Peck", Typing.FLYING, MoveCategory.PHYSICAL, 35, 100, 35, nothing);
        move("Pound", Typing.NORMAL, MoveCategory.PHYSICAL, 40, 100, 35, nothing);
        move("Rock Throw", Typing.ROCK, MoveCategory.PHYSICAL, 50, 90, 15, nothing);
        move("Scratch", Typing.NORMAL, MoveCategory.PHYSICAL, 40, 100, 35, nothing);
        move("Slam", Typing.NORMAL, MoveCategory.PHYSICAL, 80, 75, 20, nothing);
        move("Strenght", Typing.NORMAL, MoveCategory.PHYSICAL, 80, 100, 15, nothing);
        move("Tackle", Typing.NORMAL, MoveCategory.PHYSICAL, 40, 100, 35, nothing);
        move("Vine Whip", Typing.GRASS, MoveCategory.PHYSICAL, 45, 100, 25, nothing);
        move("Vise Grip", Typing.NORMAL, MoveCategory.PHYSICAL, 55, 100, 30, nothing);
        move("Water Gun", Typing.WATER, MoveCategory.SPECIAL, 40, 100, 25, nothing);
        move("Wing Attack", Typing.FLYING, MoveCategory.PHYSICAL, 60, 100, 35, nothing);
        move("Aqua Tail", Typing.WATER, MoveCategory.PHYSICAL, 90, 90, 10, nothing);
        move("Dragon Claw", Typing.DRAGON, MoveCategory.PHYSICAL, 80, 100, 15, nothing);
        move("Dragon Pulse", Typing.DRAGON, MoveCategory.SPECIAL, 85, 100, 10, nothing);
        move("Fairy Wind", Typing.FAIRY, MoveCategory.SPECIAL, 40, 100, 30, nothing);
        move("Hyper Voice", Typing.NORMAL, MoveCategory.SPECIAL, 90, 100, 10, nothing);


        // may paralyze the target

        move("Thunder Shock", Typing.ELECTRIC, MoveCategory.SPECIAL, 40, 100, 30, May_paralyze_the_target);
        move("Thunder Bolt", Typing.ELECTRIC, MoveCategory.SPECIAL, 90, 100, 15, May_paralyze_the_target);
        move("Thunder Punch", Typing.ELECTRIC, MoveCategory.PHYSICAL, 75, 100, 15, May_paralyze_the_target);
        move("Thunder", Typing.ELECTRIC, MoveCategory.SPECIAL, 110, 70, 10, May_paralyze_the_target);
        move("Lick", Typing.GHOST, MoveCategory.PHYSICAL, 30, 100, 30, May_paralyze_the_target);
        move("Body Slam", Typing.NORMAL, MoveCategory.PHYSICAL, 85, 100, 15, May_paralyze_the_target);
        move("Dragon Breath", Typing.DRAGON, MoveCategory.SPECIAL, 60, 100, 20, May_paralyze_the_target);


        // absorbs HP

        move("Absorb", Typing.GRASS, MoveCategory.SPECIAL, 20, 100, 25, absorbs_hp);
        move("Mega Drain", Typing.GRASS, MoveCategory.SPECIAL, 40, 100, 15, absorbs_hp);
        move("Leech Life", Typing.BUG, MoveCategory.PHYSICAL, 80, 100, 10, absorbs_hp);
        move("Giga Drain", Typing.GRASS, MoveCategory.SPECIAL, 75, 100, 15, absorbs_hp);
        move("Bitter Blade", Typing.FIRE, MoveCategory.PHYSICAL, 90, 100, 10, absorbs_hp);
        move("Drain Punch", Typing.FIGHTING, MoveCategory.PHYSICAL, 70, 100, 10, absorbs_hp);
        move("Horn Leech", Typing.GRASS, MoveCategory.PHYSICAL, 75, 100, 10, absorbs_hp);


        // causes flinching (maybe)

        move("Bite", Typing.DARK, MoveCategory.PHYSICAL, 60, 100, 25, may_cause_flinching_30);
        move("Bone Club", Typing.GROUND, MoveCategory.PHYSICAL, 65, 85, 20, may_cause_flinching_10);
        move("Headbutt", Typing.NORMAL, MoveCategory.PHYSICAL, 70, 100, 15, may_cause_flinching_30);
        move("Rock Slide", Typing.ROCK, MoveCategory.PHYSICAL, 75, 90, 10, may_cause_flinching_30);
        move("Rolling Kick", Typing.FIGHTING, MoveCategory.PHYSICAL, 60, 85, 15, may_cause_flinching_30);
        move("Stomp", Typing.NORMAL, MoveCategory.PHYSICAL, 65, 100, 20, may_cause_flinching_30);
        move("Waterfall", Typing.WATER, MoveCategory.PHYSICAL, 80, 100, 14, may_cause_flinching_20);
        move("Air Slash", Typing.FLYING, MoveCategory.SPECIAL, 75, 95, 15, may_cause_flinching_30);
        move("Extrasensory", Typing.PSYCHIC, MoveCategory.SPECIAL, 80, 100, 10, may_cause_flinching_10);
        move("Iron Head", Typing.STEEL, MoveCategory.PHYSICAL, 80, 100, 15, may_cause_flinching_30);
        move("Icicle crash", Typing.ICE, MoveCategory.PHYSICAL, 85, 90, 10, may_cause_flinching_30);


        // hits multiple times

        move("Arm thrust", Typing.FIGHTING, MoveCategory.PHYSICAL, 15, 100, 20, hits_2_5_times);
        move("Barrage", Typing.NORMAL, MoveCategory.PHYSICAL, 15, 85, 20, hits_2_5_times);
        move("Bullet Seed", Typing.GRASS, MoveCategory.PHYSICAL, 25, 100, 30, hits_2_5_times);
        move("Icicle Spear", Typing.ICE, MoveCategory.PHYSICAL, 25, 100, 20, hits_2_5_times);
        move("Pin Missile", Typing.BUG, MoveCategory.PHYSICAL, 25, 95, 20, hits_2_5_times);
        move("Water Shuriken", Typing.WATER, MoveCategory.SPECIAL, 15, 100, 20, hits_2_5_times);

        // paralyzes opponent

        move("Glare", Typing.NORMAL, MoveCategory.STATUS, 100, 30, paralyzes_opponent);
        move("Stun Spore", Typing.GRASS, MoveCategory.STATUS, 75, 30, paralyzes_opponent);
        move("Thunder Wave", Typing.ELECTRIC, MoveCategory.STATUS, 90, 20, paralyzes_opponent);
        move("Nuzzle", Typing.ELECTRIC, MoveCategory.PHYSICAL, 20, 100, 20, paralyzes_opponent);
        move("Zap Canon", Typing.ELECTRIC, MoveCategory.SPECIAL, 120, 50, 5, paralyzes_opponent);

        // burns opponent

        move("Will-O-Wisp", Typing.FIRE, MoveCategory.STATUS, 85, 15, burns_opponent);
        move("Inferno", Typing.FIRE, MoveCategory.SPECIAL, 100, 50, 5, burns_opponent);

        // may burn opponent

        move("Blue Flare", Typing.FIRE, MoveCategory.SPECIAL, 130, 85, 5, may_burn_opponent);
        move("Ember", Typing.FIRE, MoveCategory.SPECIAL, 40, 100, 25, may_burn_opponent);
        move("Heat Wave", Typing.FIRE, MoveCategory.SPECIAL, 95, 90, 10, may_burn_opponent);
        move("Scald", Typing.WATER, MoveCategory.SPECIAL, 80, 100, 15, may_burn_opponent);
        move("Flamethrower", Typing.FIRE, MoveCategory.SPECIAL, 90, 100, 15, may_burn_opponent);
        move("Fire Punch", Typing.FIRE, MoveCategory.PHYSICAL, 75, 100, 15, may_burn_opponent);

        // poisons opponent

        move("Poison Gas", Typing.POISON, MoveCategory.STATUS, 90, 40, poisons_opponent);
        move("Poison Powder", Typing.POISON, MoveCategory.STATUS, 75, 30, poisons_opponent);

        // badly poisons opponent

        move("Toxic", Typing.POISON, MoveCategory.STATUS, 90, 10, badly_poisons_opponent);

        // may poison opponent

        move("Gunk Shot", Typing.POISON, MoveCategory.PHYSICAL, 120, 80, 5, may_poison_opponent);
        move("Poison Jab", Typing.POISON, MoveCategory.PHYSICAL, 80, 100, 20, may_poison_opponent);
        move("Poison Sting", Typing.POISON, MoveCategory.PHYSICAL, 15, 100, 30, may_poison_opponent);
        move("Sludge", Typing.POISON, MoveCategory.SPECIAL, 65, 100, 20, may_poison_opponent);
        move("Sludge Bomb", Typing.POISON, MoveCategory.SPECIAL, 90, 100, 10, may_poison_opponent);
        move("Sludge Wave", Typing.POISON, MoveCategory.SPECIAL, 95, 100, 10, may_poison_opponent);
        move("Smog", Typing.POISON, MoveCategory.SPECIAL, 30, 70, 20, may_poison_opponent);

        // may freeze opponent

        move("Blizzard", Typing.ICE, MoveCategory.SPECIAL, 110, 70, 5, may_freeze_opponent);
        move("Ice Beam", Typing.ICE, MoveCategory.SPECIAL, 90, 100, 10, may_freeze_opponent);
        move("Ice Punch", Typing.ICE, MoveCategory.PHYSICAL, 75, 100, 15, may_freeze_opponent);
        move("Powder Snow", Typing.ICE, MoveCategory.SPECIAL, 40, 100, 25, may_freeze_opponent);

        // puts opponent to sleep

        move("Hypnosis", Typing.PSYCHIC, MoveCategory.STATUS, 60, 20, puts_opponent_to_sleep);
        move("Grass whistle", Typing.GRASS, MoveCategory.STATUS, 55, 15, puts_opponent_to_sleep);


        // puts opponent to sleep (powder moves)

        move("Sleep Powder", Typing.GRASS, MoveCategory.STATUS, 75, 15, puts_opponent_to_sleep_no_grass);
        move("Spore", Typing.GRASS, MoveCategory.STATUS, 100, 15, puts_opponent_to_sleep_no_grass);


        // Weather Moves

        move("Sunny Day", Typing.FIRE, MoveCategory.STATUS, 100, 5, makes_it_sunny);
        move("Rain Dance", Typing.WATER, MoveCategory.STATUS, 100, 5, makes_it_rain);
        move("SandStorm", Typing.ROCK, MoveCategory.STATUS, 100, 10, creates_a_sandStorm);
        move("Snowscape", Typing.ICE, MoveCategory.STATUS, 100, 10, snow);

        // user faints

        move("Explosion", Typing.NORMAL, MoveCategory.PHYSICAL, 250, 100, 5, user_faints);

        // Priority Moves

        move("Quick Attack", Typing.NORMAL, MoveCategory.PHYSICAL, 40, 100, 30, priority_one);
        move("Extreme Speed", Typing.NORMAL, MoveCategory.PHYSICAL, 80, 100, 5, priority_one);
        move("Mach Punch", Typing.FIGHTING, MoveCategory.PHYSICAL, 40, 100, 20, priority_one);


        // struggle
        move("Struggle", Typing.NORMAL, MoveCategory.PHYSICAL, 50, 100, 1000, struggleEffect);
    }

    public static void move(
            String name, Typing type, MoveCategory category, int power, int accuracy, int pp, MoveEffect effect)
    {
        MOVES.add(new Moves(name, type, category, power, accuracy, pp, effect));
    }

    public static void move(
            String name, Typing type, MoveCategory category, int accuracy, int pp, MoveEffect effect
    ) {
        MOVES.add(new Moves(name, type, category, 0, accuracy, pp, effect));
    }

    public static List<Moves> getMoves() {
        return MOVES;
    }

    public static Moves getMoveByName(String moveName) {
        for (Moves move : getMoves()) {
            if (move.getName().equalsIgnoreCase(moveName)){
                return move;
            }
        }
        return null;
    }

    @SuppressWarnings("unused")
    public static int countAllMoves() {
        List<Moves> allMoves = getMoves();
        return allMoves.size();
    }

    @SuppressWarnings("unused")
    public static List<String> getAllMoveNames() {
        return getMoves().stream()
                .map(Moves::getName)
                .collect(Collectors.toList());
    }

    @SuppressWarnings("unused")
    public static MoveEffect getMoveEffectByName(String moveName) {
        Moves move = getMoveByName(moveName);

        if (move != null) {
            return move.getEffect();
        } else  {
            System.out.println("move not found"); return null;
        }
    }
}