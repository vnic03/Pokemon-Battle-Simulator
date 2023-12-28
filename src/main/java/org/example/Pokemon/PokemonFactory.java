package org.example.Pokemon;

import java.util.List;

public class PokemonFactory {

    public static Pokemon createPokemon(String name, List<PokeTyping> typings, int hp, int attack, int defense,int specialAttack, int specialDefense ,int speed, List<Moves> moves) {
        Stats stats = new Stats(hp, attack, defense, specialAttack, specialDefense, speed);
        return new Pokemon(name, typings, stats, moves);
    }
}
