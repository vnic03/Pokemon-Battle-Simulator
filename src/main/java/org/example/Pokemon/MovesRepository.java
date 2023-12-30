package org.example.Pokemon;

import org.example.Pokemon.Effects.*;

import java.util.ArrayList;
import java.util.List;

public class MovesRepository {


    public static List<Moves> getAllMoves() {

        MoveEffect nothing = new NoEffect();

        MoveEffect May_paralyze_the_target = new MayParalyzeEffect();

        MoveEffect paralyzes_opponent = new ParalyzeEffect();

        MoveEffectWithDamage absorbs_hp = new AbsorbEffect();

        MoveEffect may_cause_flinching = new MayFlinchEffect();

        MoveEffect hits_2_5_times = new HitsMoreTimesEffect();


        List<Moves> allMoves = new ArrayList<>();

        // nothing

        allMoves.add(new Moves("Cut",PokeTyping.NORMAL, MoveCategory.PHYSICAL, 50, 95, 30, nothing));
        allMoves.add(new Moves("Drill Peck", PokeTyping.FLYING, MoveCategory.PHYSICAL, 80, 100,20, nothing));
        allMoves.add(new Moves("Egg Bomb", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 100, 75,10, nothing));
        allMoves.add(new Moves("Horn Attack", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 65, 100,25, nothing));
        allMoves.add(new Moves("Hydro Pump", PokeTyping.WATER, MoveCategory.SPECIAL, 110, 80,5, nothing));
        allMoves.add(new Moves("Mega Kick", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 120, 75,5, nothing));
        allMoves.add(new Moves("Mega Punch", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 80, 85,20, nothing));
        allMoves.add(new Moves("Peck", PokeTyping.FLYING, MoveCategory.PHYSICAL, 35, 100,35, nothing));
        allMoves.add(new Moves("Pound", PokeTyping.NORMAL, MoveCategory.PHYSICAL,40, 100,35, nothing));
        allMoves.add(new Moves("Rock Throw", PokeTyping.ROCK, MoveCategory.PHYSICAL, 50, 90,15, nothing));
        allMoves.add(new Moves("Scratch", PokeTyping.NORMAL,MoveCategory.PHYSICAL, 40,100,35, nothing));
        allMoves.add(new Moves("Slam", PokeTyping.NORMAL,MoveCategory.PHYSICAL,80,75,20,nothing));
        allMoves.add(new Moves("Strenght", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 80,100,15, nothing));
        allMoves.add(new Moves("Tackle", PokeTyping.NORMAL,MoveCategory.PHYSICAL,40,100,35,nothing));
        allMoves.add(new Moves("Vine Whip", PokeTyping.GRASS, MoveCategory.PHYSICAL,45,100,25,nothing));
        allMoves.add(new Moves("Vise Grip", PokeTyping.NORMAL,MoveCategory.PHYSICAL,55,100,30,nothing));
        allMoves.add(new Moves("Water Gun", PokeTyping.WATER, MoveCategory.SPECIAL, 40, 100, 25, nothing));
        allMoves.add(new Moves("Wing Attack", PokeTyping.FLYING, MoveCategory.PHYSICAL, 60, 100, 35, nothing));
        allMoves.add(new Moves("Aqua Tail", PokeTyping.WATER, MoveCategory.PHYSICAL, 90,90,10, nothing));
        allMoves.add(new Moves("Dragon Claw", PokeTyping.DRAGON, MoveCategory.PHYSICAL, 80,100,15, nothing));
        allMoves.add(new Moves("Dragon Pulse", PokeTyping.DRAGON, MoveCategory.SPECIAL, 85,100,10,nothing));
        allMoves.add(new Moves("Fairy Wind", PokeTyping.FAIRY, MoveCategory.SPECIAL, 40,100, 30, nothing));
        allMoves.add(new Moves("Hyper Voice", PokeTyping.NORMAL, MoveCategory.SPECIAL, 90,100,10,nothing));



        // may paralyze the target

        allMoves.add(new Moves("Thunder Shock", PokeTyping.ELECTRIC, MoveCategory.SPECIAL, 40, 100, 30, May_paralyze_the_target));
        allMoves.add(new Moves("Thunder Bolt", PokeTyping.ELECTRIC, MoveCategory.SPECIAL, 90, 100, 15, May_paralyze_the_target));
        allMoves.add(new Moves("Thunder Punch", PokeTyping.ELECTRIC, MoveCategory.PHYSICAL, 75,100,15,May_paralyze_the_target));
        allMoves.add(new Moves("Thunder", PokeTyping.ELECTRIC, MoveCategory.SPECIAL, 110,70, 10, May_paralyze_the_target));
        allMoves.add(new Moves("Lick", PokeTyping.GHOST, MoveCategory.PHYSICAL,30,100,30,May_paralyze_the_target));
        allMoves.add(new Moves("Body Slam", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 85, 100,15, May_paralyze_the_target));
        allMoves.add(new Moves("Dragon Breath", PokeTyping.DRAGON, MoveCategory.SPECIAL, 60,100,20, May_paralyze_the_target));


        // absorbs HP

        allMoves.add(new Moves("Absorb", PokeTyping.GRASS, MoveCategory.SPECIAL, 20, 100, 25, absorbs_hp));
        allMoves.add(new Moves("Mega Drain", PokeTyping.GRASS, MoveCategory.SPECIAL, 40, 100, 15, absorbs_hp));
        allMoves.add(new Moves("Leech Life", PokeTyping.BUG, MoveCategory.PHYSICAL, 80, 100, 10, absorbs_hp));
        allMoves.add(new Moves("Giga Drain", PokeTyping.GRASS, MoveCategory.SPECIAL, 75,100,15, absorbs_hp));
        allMoves.add(new Moves("Bitter Blade", PokeTyping.FIRE, MoveCategory.PHYSICAL, 90,100,10, absorbs_hp));
        allMoves.add(new Moves("Drain Punch", PokeTyping.FIGHTING, MoveCategory.PHYSICAL, 70,100,10, absorbs_hp));
        allMoves.add(new Moves("Horn Leech", PokeTyping.GRASS, MoveCategory.PHYSICAL, 75,100, 10, absorbs_hp));


        // causes flinching (maybe)

        allMoves.add(new Moves("Bite", PokeTyping.DARK, MoveCategory.PHYSICAL, 60, 100, 25, may_cause_flinching));
        allMoves.add(new Moves("Bone Club", PokeTyping.GROUND, MoveCategory.PHYSICAL, 65,85,20,may_cause_flinching));
        allMoves.add(new Moves("Headbutt", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 70, 100, 15, may_cause_flinching));
        allMoves.add(new Moves("Hyper Fang", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 80,90,15, may_cause_flinching));
        allMoves.add(new Moves("Rock Slide", PokeTyping.ROCK, MoveCategory.PHYSICAL, 75, 90, 10, may_cause_flinching));
        allMoves.add(new Moves("Rolling Kick", PokeTyping.FIGHTING, MoveCategory.PHYSICAL, 60,85,15,may_cause_flinching));
        allMoves.add(new Moves("Stomp", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 65, 100, 20, may_cause_flinching));
        allMoves.add(new Moves("Waterfall", PokeTyping.WATER ,MoveCategory.PHYSICAL, 80,100,14,may_cause_flinching));

        // hits multiple times

        allMoves.add(new Moves("Arm thrust", PokeTyping.FIGHTING, MoveCategory.PHYSICAL, 15, 100, 20, hits_2_5_times));
        allMoves.add(new Moves("Barrage", PokeTyping.NORMAL, MoveCategory.PHYSICAL, 15, 85, 20, hits_2_5_times));
        allMoves.add(new Moves("Bullet Seed", PokeTyping.GRASS, MoveCategory.PHYSICAL, 25, 100, 30, hits_2_5_times));
        allMoves.add(new Moves("Icicle Spear", PokeTyping.ICE, MoveCategory.PHYSICAL, 25,100,20, hits_2_5_times));
        allMoves.add(new Moves("Pin Missile", PokeTyping.BUG, MoveCategory.PHYSICAL, 25, 95, 20, hits_2_5_times));
        allMoves.add(new Moves("Water Shuriken", PokeTyping.WATER, MoveCategory.SPECIAL, 15, 100, 20, hits_2_5_times));

        // paralyzes opponent

        allMoves.add(new Moves("Glare", PokeTyping.NORMAL, MoveCategory.STATUS, 0, 100, 30, paralyzes_opponent));
        allMoves.add(new Moves("Stun Spore", PokeTyping.GRASS, MoveCategory.STATUS, 0, 75, 30, paralyzes_opponent));
        allMoves.add(new Moves("Thunder Wave", PokeTyping.ELECTRIC, MoveCategory.STATUS, 0, 90, 20, paralyzes_opponent));
        allMoves.add(new Moves("Nuzzle", PokeTyping.ELECTRIC, MoveCategory.PHYSICAL, 20, 100, 20, paralyzes_opponent));
        allMoves.add(new Moves("Zap Canon", PokeTyping.ELECTRIC, MoveCategory.SPECIAL, 120, 50, 5, paralyzes_opponent));

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
}
