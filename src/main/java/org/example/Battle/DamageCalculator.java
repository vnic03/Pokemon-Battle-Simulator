package org.example.Battle;

import org.example.Pokemon.Moves;
import org.example.Pokemon.PokeTyping;
import org.example.Pokemon.Pokemon;
import org.example.Pokemon.Stats;

import java.util.List;

public class DamageCalculator {
    public static int calculateDamage(Pokemon attacker, Pokemon defender, Moves attack) {

        Stats attackStats = attacker.getStats();
        Stats defenderStats = defender.getStats();

        double typeAdvantage = getTypeAdvantage(attack.getType(), defender.getTyping());
        double randomFactor = 0.85 + Math.random() * 0.15;

        int damage = (int) ((((2 * attacker.getLevel() / 5 + 2) * attack.getPower() * attackStats.getAttack() /
                defenderStats.getDefense()) / 50 + 2) * typeAdvantage * randomFactor);
        return damage;
    }
    public static double getTypeAdvantage(PokeTyping attackType, List<PokeTyping> defenderTypes){

        return 1.0;
    }
}
