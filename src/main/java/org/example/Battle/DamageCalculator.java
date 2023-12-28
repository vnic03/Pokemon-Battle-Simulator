package org.example.Battle;

import org.example.Pokemon.*;

import java.util.List;

public class DamageCalculator {
    public static int calculateDamage(Pokemon attacker, Pokemon defender, Moves attack) {

        Stats attackStats = attacker.getStats();
        Stats defenderStats = defender.getStats();

        double typeAdvantage = getTypeAdvantage(attack.getType(), defender.getTyping());
        double randomFactor = 0.85 + Math.random() * 0.15;

        int attackStat;
        int defenseStat;

        if (attack.getCategory() == MoveCategory.PHYSICAL) {
            attackStat = attackStats.getAttack();
            defenseStat = defenderStats.getDefense();
        } else if (attack.getCategory() == MoveCategory.SPECIAL) {
            attackStat = attackStats.getSpecialAttack();
            defenseStat = defenderStats.getSpecialDefense();
        } else {
            throw new IllegalArgumentException(attack.getCategory() + " does not exist!");
        }

        int damage = (int) ((((2 * attacker.getLevel() / 5 + 2) * attack.getPower() * attackStat /
                defenseStat) / 50 + 2) * typeAdvantage * randomFactor);
        return damage;
    }
    public static double getTypeAdvantage(PokeTyping attackType, List<PokeTyping> defenderTypes){

        return 1.0;
    }
}
