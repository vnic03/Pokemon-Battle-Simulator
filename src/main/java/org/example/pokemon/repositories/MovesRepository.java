package org.example.pokemon.repositories;

import org.example.pokemon.MoveCategory;
import org.example.pokemon.Moves;
import org.example.pokemon.Typing;
import org.example.screens.battle.Weather;
import org.example.pokemon.moveEffects.*;
import org.example.pokemon.moveEffects.priority.PriorityOne;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovesRepository {


    public static List<Moves> getAllMoves() {

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



        List<Moves> allMoves = new ArrayList<>();

        // struggle

        allMoves.add(new Moves("Struggle", Typing.NORMAL, MoveCategory.PHYSICAL, 50, 100 ,1000, struggleEffect));

        // nothing

        allMoves.add(new Moves("Cut", Typing.NORMAL, MoveCategory.PHYSICAL, 50, 95, 30, nothing));
        allMoves.add(new Moves("Drill Peck", Typing.FLYING, MoveCategory.PHYSICAL, 80, 100,20, nothing));
        allMoves.add(new Moves("Egg Bomb", Typing.NORMAL, MoveCategory.PHYSICAL, 100, 75,10, nothing));
        allMoves.add(new Moves("Horn Attack", Typing.NORMAL, MoveCategory.PHYSICAL, 65, 100,25, nothing));
        allMoves.add(new Moves("Hydro Pump", Typing.WATER, MoveCategory.SPECIAL, 110, 80,5, nothing));
        allMoves.add(new Moves("Mega Kick", Typing.NORMAL, MoveCategory.PHYSICAL, 120, 75,5, nothing));
        allMoves.add(new Moves("Mega Punch", Typing.NORMAL, MoveCategory.PHYSICAL, 80, 85,20, nothing));
        allMoves.add(new Moves("Peck", Typing.FLYING, MoveCategory.PHYSICAL, 35, 100,35, nothing));
        allMoves.add(new Moves("Pound", Typing.NORMAL, MoveCategory.PHYSICAL,40, 100,35, nothing));
        allMoves.add(new Moves("Rock Throw", Typing.ROCK, MoveCategory.PHYSICAL, 50, 90,15, nothing));
        allMoves.add(new Moves("Scratch", Typing.NORMAL,MoveCategory.PHYSICAL, 40,100,35, nothing));
        allMoves.add(new Moves("Slam", Typing.NORMAL,MoveCategory.PHYSICAL,80,75,20,nothing));
        allMoves.add(new Moves("Strenght", Typing.NORMAL, MoveCategory.PHYSICAL, 80,100,15, nothing));
        allMoves.add(new Moves("Tackle", Typing.NORMAL,MoveCategory.PHYSICAL,40,100,35,nothing));
        allMoves.add(new Moves("Vine Whip", Typing.GRASS, MoveCategory.PHYSICAL,45,100,25,nothing));
        allMoves.add(new Moves("Vise Grip", Typing.NORMAL,MoveCategory.PHYSICAL,55,100,30,nothing));
        allMoves.add(new Moves("Water Gun", Typing.WATER, MoveCategory.SPECIAL, 40, 100, 25, nothing));
        allMoves.add(new Moves("Wing Attack", Typing.FLYING, MoveCategory.PHYSICAL, 60, 100, 35, nothing));
        allMoves.add(new Moves("Aqua Tail", Typing.WATER, MoveCategory.PHYSICAL, 90,90,10, nothing));
        allMoves.add(new Moves("Dragon Claw", Typing.DRAGON, MoveCategory.PHYSICAL, 80,100,15, nothing));
        allMoves.add(new Moves("Dragon Pulse", Typing.DRAGON, MoveCategory.SPECIAL, 85,100,10,nothing));
        allMoves.add(new Moves("Fairy Wind", Typing.FAIRY, MoveCategory.SPECIAL, 40,100, 30, nothing));
        allMoves.add(new Moves("Hyper Voice", Typing.NORMAL, MoveCategory.SPECIAL, 90,100,10,nothing));



        // may paralyze the target

        allMoves.add(new Moves("Thunder Shock", Typing.ELECTRIC, MoveCategory.SPECIAL, 40, 100, 30, May_paralyze_the_target));
        allMoves.add(new Moves("Thunder Bolt", Typing.ELECTRIC, MoveCategory.SPECIAL, 90, 100, 15, May_paralyze_the_target));
        allMoves.add(new Moves("Thunder Punch", Typing.ELECTRIC, MoveCategory.PHYSICAL, 75,100,15,May_paralyze_the_target));
        allMoves.add(new Moves("Thunder", Typing.ELECTRIC, MoveCategory.SPECIAL, 110,70, 10, May_paralyze_the_target));
        allMoves.add(new Moves("Lick", Typing.GHOST, MoveCategory.PHYSICAL,30,100,30,May_paralyze_the_target));
        allMoves.add(new Moves("Body Slam", Typing.NORMAL, MoveCategory.PHYSICAL, 85, 100,15, May_paralyze_the_target));
        allMoves.add(new Moves("Dragon Breath", Typing.DRAGON, MoveCategory.SPECIAL, 60,100,20, May_paralyze_the_target));


        // absorbs HP

        allMoves.add(new Moves("Absorb", Typing.GRASS, MoveCategory.SPECIAL, 20, 100, 25, absorbs_hp));
        allMoves.add(new Moves("Mega Drain", Typing.GRASS, MoveCategory.SPECIAL, 40, 100, 15, absorbs_hp));
        allMoves.add(new Moves("Leech Life", Typing.BUG, MoveCategory.PHYSICAL, 80, 100, 10, absorbs_hp));
        allMoves.add(new Moves("Giga Drain", Typing.GRASS, MoveCategory.SPECIAL, 75,100,15, absorbs_hp));
        allMoves.add(new Moves("Bitter Blade", Typing.FIRE, MoveCategory.PHYSICAL, 90,100,10, absorbs_hp));
        allMoves.add(new Moves("Drain Punch", Typing.FIGHTING, MoveCategory.PHYSICAL, 70,100,10, absorbs_hp));
        allMoves.add(new Moves("Horn Leech", Typing.GRASS, MoveCategory.PHYSICAL, 75,100, 10, absorbs_hp));


        // causes flinching (maybe)

        allMoves.add(new Moves("Bite", Typing.DARK, MoveCategory.PHYSICAL, 60, 100, 25, may_cause_flinching_30));
        allMoves.add(new Moves("Bone Club", Typing.GROUND, MoveCategory.PHYSICAL, 65,85,20,may_cause_flinching_10));
        allMoves.add(new Moves("Headbutt", Typing.NORMAL, MoveCategory.PHYSICAL, 70, 100, 15, may_cause_flinching_30));
        allMoves.add(new Moves("Rock Slide", Typing.ROCK, MoveCategory.PHYSICAL, 75, 90, 10, may_cause_flinching_30));
        allMoves.add(new Moves("Rolling Kick", Typing.FIGHTING, MoveCategory.PHYSICAL, 60,85,15,may_cause_flinching_30));
        allMoves.add(new Moves("Stomp", Typing.NORMAL, MoveCategory.PHYSICAL, 65, 100, 20, may_cause_flinching_30));
        allMoves.add(new Moves("Waterfall", Typing.WATER, MoveCategory.PHYSICAL, 80, 100, 14,may_cause_flinching_20));
        allMoves.add(new Moves("Air Slash", Typing.FLYING ,MoveCategory.SPECIAL, 75, 95, 15, may_cause_flinching_30));
        allMoves.add(new Moves("Extrasensory", Typing.PSYCHIC ,MoveCategory.SPECIAL, 80, 100, 10, may_cause_flinching_10));
        allMoves.add(new Moves("Iron Head", Typing.STEEL, MoveCategory.PHYSICAL, 80,100,15,may_cause_flinching_30));
        allMoves.add(new Moves("Icicle crash", Typing.ICE, MoveCategory.PHYSICAL, 85, 90, 10, may_cause_flinching_30));




        // hits multiple times

        allMoves.add(new Moves("Arm thrust", Typing.FIGHTING, MoveCategory.PHYSICAL, 15, 100, 20, hits_2_5_times));
        allMoves.add(new Moves("Barrage", Typing.NORMAL, MoveCategory.PHYSICAL, 15, 85, 20, hits_2_5_times));
        allMoves.add(new Moves("Bullet Seed", Typing.GRASS, MoveCategory.PHYSICAL, 25, 100, 30, hits_2_5_times));
        allMoves.add(new Moves("Icicle Spear", Typing.ICE, MoveCategory.PHYSICAL, 25,100,20, hits_2_5_times));
        allMoves.add(new Moves("Pin Missile", Typing.BUG, MoveCategory.PHYSICAL, 25, 95, 20, hits_2_5_times));
        allMoves.add(new Moves("Water Shuriken", Typing.WATER, MoveCategory.SPECIAL, 15, 100, 20, hits_2_5_times));

        // paralyzes opponent

        allMoves.add(new Moves("Glare", Typing.NORMAL, MoveCategory.STATUS, 0, 100, 30, paralyzes_opponent));
        allMoves.add(new Moves("Stun Spore", Typing.GRASS, MoveCategory.STATUS, 0, 75, 30, paralyzes_opponent));
        allMoves.add(new Moves("Thunder Wave", Typing.ELECTRIC, MoveCategory.STATUS, 0, 90, 20, paralyzes_opponent));
        allMoves.add(new Moves("Nuzzle", Typing.ELECTRIC, MoveCategory.PHYSICAL, 20, 100, 20, paralyzes_opponent));
        allMoves.add(new Moves("Zap Canon", Typing.ELECTRIC, MoveCategory.SPECIAL, 120, 50, 5, paralyzes_opponent));

        // burns opponent

        allMoves.add(new Moves("Will-O-Wisp", Typing.FIRE, MoveCategory.STATUS, 0, 85, 15 ,burns_opponent));
        allMoves.add(new Moves("Inferno", Typing.FIRE, MoveCategory.SPECIAL, 100,50,5,burns_opponent));

        // may burn opponent

        allMoves.add(new Moves("Blue Flare", Typing.FIRE, MoveCategory.SPECIAL, 130,85,5 ,may_burn_opponent));
        allMoves.add(new Moves("Ember", Typing.FIRE, MoveCategory.SPECIAL, 40, 100, 25, may_burn_opponent));
        allMoves.add(new Moves("Heat Wave", Typing.FIRE, MoveCategory.SPECIAL, 95, 90, 10, may_burn_opponent));
        allMoves.add(new Moves("Scald", Typing.WATER, MoveCategory.SPECIAL, 80, 100, 15, may_burn_opponent));
        allMoves.add(new Moves("Flamethrower", Typing.FIRE, MoveCategory.SPECIAL, 90, 100, 15, may_burn_opponent));
        allMoves.add(new Moves("Fire Punch", Typing.FIRE, MoveCategory.PHYSICAL, 75, 100, 15, may_burn_opponent));

        // poisons opponent

        allMoves.add(new Moves("Poison Gas", Typing.POISON, MoveCategory.STATUS, 0, 90, 40, poisons_opponent));
        allMoves.add(new Moves("Poison Powder", Typing.POISON, MoveCategory.STATUS, 0, 75, 30, poisons_opponent));

        // badly poisons opponent

        allMoves.add(new Moves("Toxic", Typing.POISON, MoveCategory.STATUS, 0, 90, 10, badly_poisons_opponent));

        // may poison opponent

        allMoves.add(new Moves("Gunk Shot", Typing.POISON, MoveCategory.PHYSICAL, 120, 80, 5, may_poison_opponent));
        allMoves.add(new Moves("Poison Jab", Typing.POISON, MoveCategory.PHYSICAL, 80, 100, 20, may_poison_opponent));
        allMoves.add(new Moves("Poison Sting", Typing.POISON, MoveCategory.PHYSICAL, 15, 100, 30, may_poison_opponent));
        allMoves.add(new Moves("Sludge", Typing.POISON, MoveCategory.SPECIAL, 65,100,20,may_poison_opponent));
        allMoves.add(new Moves("Sludge Bomb", Typing.POISON, MoveCategory.SPECIAL, 90, 100, 10, may_poison_opponent));
        allMoves.add(new Moves("Sludge Wave", Typing.POISON, MoveCategory.SPECIAL, 95,100, 10, may_poison_opponent));
        allMoves.add(new Moves("Smog", Typing.POISON, MoveCategory.SPECIAL, 30,70,20,may_poison_opponent));

        // may freeze opponent

        allMoves.add(new Moves("Blizzard", Typing.ICE, MoveCategory.SPECIAL, 110, 70, 5, may_freeze_opponent));
        allMoves.add(new Moves("Ice Beam", Typing.ICE, MoveCategory.SPECIAL, 90,100,10,may_freeze_opponent));
        allMoves.add(new Moves("Ice Punch", Typing.ICE, MoveCategory.PHYSICAL, 75,100, 15,may_freeze_opponent));
        allMoves.add(new Moves("Powder Snow", Typing.ICE, MoveCategory.SPECIAL, 40, 100, 25, may_freeze_opponent ));

        // puts opponent to sleep

        allMoves.add(new Moves("Hypnosis", Typing.PSYCHIC, MoveCategory.STATUS, 0, 60, 20, puts_opponent_to_sleep));
        allMoves.add(new Moves("Grass whistle", Typing.GRASS, MoveCategory.STATUS, 0, 55, 15, puts_opponent_to_sleep));


        // puts opponent to sleep (powder moves)

        allMoves.add(new Moves("Sleep Powder", Typing.GRASS, MoveCategory.STATUS, 0, 75, 15, puts_opponent_to_sleep_no_grass));
        allMoves.add(new Moves("Spore", Typing.GRASS, MoveCategory.STATUS, 0, 100, 15, puts_opponent_to_sleep_no_grass));


        // Weather Moves

        allMoves.add(new Moves("Sunny Day", Typing.FIRE, MoveCategory.STATUS, 0, 100, 5, makes_it_sunny));
        allMoves.add(new Moves("Rain Dance", Typing.WATER, MoveCategory.STATUS, 0, 100 ,5, makes_it_rain));
        allMoves.add(new Moves("SandStorm", Typing.ROCK, MoveCategory.STATUS, 0, 100, 10, creates_a_sandStorm));
        allMoves.add(new Moves("Snowscape", Typing.ICE, MoveCategory.STATUS, 0, 100, 10, snow));

        // user faints

        allMoves.add(new Moves("Explosion", Typing.NORMAL, MoveCategory.PHYSICAL, 250, 100, 5, user_faints));

        // Priority Moves

        allMoves.add(new Moves("Quick Attack", Typing.NORMAL, MoveCategory.PHYSICAL, 40, 100 ,30, priority_one));
        allMoves.add(new Moves("Extreme Speed", Typing.NORMAL, MoveCategory.PHYSICAL, 80, 100, 5, priority_one));
        allMoves.add(new Moves("Mach Punch", Typing.FIGHTING, MoveCategory.PHYSICAL, 40, 100, 20, priority_one));


        return allMoves;
    }

    public static Moves getMoveByName(String moveName) {
        for (Moves move : getAllMoves()) {
            if (move.getName().equalsIgnoreCase(moveName)){
                return move;
            }
        }
        return null;
    }

    public static int countAllMoves() {
        List<Moves> allMoves = getAllMoves();
        return allMoves.size();
    }

    public static List<String> getAllMoveNames() {
        return getAllMoves().stream()
                .map(Moves::getName)
                .collect(Collectors.toList());
    }

    public static MoveEffect getMoveEffectByName(String moveName) {
        Moves move = getMoveByName(moveName);

        if (move != null) {
            return move.getEffect();
        } else  {
            System.out.println("move not found"); return null;
        }
    }




}
