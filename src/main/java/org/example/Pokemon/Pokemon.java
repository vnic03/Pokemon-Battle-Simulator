package org.example.Pokemon;

import java.util.List;

public class Pokemon {
    String name;
    private List<PokeTyping> typing;
    private final int level = 50;
    private Stats stats;
    private List<Moves> moves;

    public Pokemon(String name, List<PokeTyping> typing, Stats stats, List<Moves> moves){
        this.name = name;
        this.typing = typing;
        this.stats = stats;
        this.moves = moves;

    }

    public String getName(){
        return name;
    }
    public int getLevel() {
        return level;
    }

    public List<PokeTyping> getTyping(){
        return typing;
    }

    public Stats getStats(){
        return stats;
    }
    public List<Moves> getMoves() {
        return moves;
    }
}
