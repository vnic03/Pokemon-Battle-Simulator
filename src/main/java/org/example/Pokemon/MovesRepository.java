package org.example.Pokemon;

import org.example.Pokemon.Effects.AbsorbEffect;
import org.example.Pokemon.Effects.MoveEffect;
import org.example.Pokemon.Effects.NoEffect;

import java.util.ArrayList;
import java.util.List;

public class MovesRepository {


    public static List<Moves> getAllMoves() {

        MoveEffect nothing = new NoEffect();

        List<Moves> allMoves = new ArrayList<>();

        allMoves.add(new Moves("Cut",PokeTyping.NORMAL, MoveCategory.ATTACKING, 50, 95, 30, nothing));
        allMoves.add(new Moves("Drill Peck", PokeTyping.FLYING, MoveCategory.ATTACKING, 80, 100,20, nothing));
        allMoves.add(new Moves("Egg Bomb", PokeTyping.NORMAL, MoveCategory.ATTACKING, 100, 75,10, nothing));
        allMoves.add(new Moves("Horn Attack", PokeTyping.NORMAL, MoveCategory.ATTACKING, 65, 100,25, nothing));
        allMoves.add(new Moves("Hydro Pump", PokeTyping.WATER, MoveCategory.ATTACKING, 110, 80,5, nothing));
        allMoves.add(new Moves("Mega Kick", PokeTyping.NORMAL, MoveCategory.ATTACKING, 120, 75,5, nothing));
        allMoves.add(new Moves("Mega Punch", PokeTyping.NORMAL, MoveCategory.ATTACKING, 80, 85,20, nothing));
        allMoves.add(new Moves("Peck", PokeTyping.FLYING, MoveCategory.ATTACKING, 35, 100,35, nothing));
        allMoves.add(new Moves("Pound", PokeTyping.NORMAL, MoveCategory.ATTACKING,40, 100,35, nothing));
        allMoves.add(new Moves("Rock Throw", PokeTyping.ROCK, MoveCategory.ATTACKING, 50, 90,15, nothing));
        allMoves.add(new Moves("Scratch", PokeTyping.NORMAL,MoveCategory.ATTACKING, 40,100,35, nothing));
        allMoves.add(new Moves("Slam", PokeTyping.NORMAL,MoveCategory.ATTACKING,80,75,20,nothing));
        allMoves.add(new Moves("Strenght", PokeTyping.NORMAL, MoveCategory.ATTACKING, 80,100,15, nothing));
        allMoves.add(new Moves("Tackle", PokeTyping.NORMAL,MoveCategory.ATTACKING,40,100,35,nothing));
        allMoves.add(new Moves("Vine Whip", PokeTyping.GRASS, MoveCategory.ATTACKING,45,100,25,nothing));
        allMoves.add(new Moves("Vise Grip", PokeTyping.NORMAL,MoveCategory.ATTACKING,55,100,30,nothing));
        allMoves.add(new Moves("Water Gun", PokeTyping.WATER, MoveCategory.ATTACKING, 40, 100, 25, nothing));
        allMoves.add(new Moves("Wing Attack", PokeTyping.FLYING, MoveCategory.ATTACKING, 60, 100, 35, nothing));






        return allMoves;
    }

    public static Moves getMoveByName(String moveName){
        for (Moves move : getAllMoves()) {
            if (move.getName().equalsIgnoreCase(moveName)){
                return move;
            }
        }
        return null;
    }
}
