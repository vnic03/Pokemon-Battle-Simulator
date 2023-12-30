package org.example.Pokemon;

public enum Nature {
    ADAMANT(Stat.ATTACK, Stat.SPECIAL_ATTACK),
    NAUGHTY(Stat.ATTACK, Stat.SPECIAL_DEFENSE),
    BRAVE(Stat.ATTACK, Stat.SPEED),
    LONELY(Stat.ATTACK, Stat.DEFENSE),
    BOLD(Stat.DEFENSE, Stat.ATTACK),
    IMPISH(Stat.DEFENSE, Stat.SPECIAL_ATTACK),
    LAX(Stat.DEFENSE, Stat.SPECIAL_DEFENSE),
    RELAXED(Stat.DEFENSE, Stat.SPEED),
    MODEST(Stat.SPECIAL_ATTACK, Stat.ATTACK),
    MILD(Stat.SPECIAL_ATTACK, Stat.DEFENSE),
    RASH(Stat.SPECIAL_ATTACK, Stat.SPECIAL_DEFENSE),
    QUIET(Stat.SPECIAL_ATTACK, Stat.SPEED),
    CALM(Stat.SPECIAL_DEFENSE, Stat.ATTACK),
    GENTLE(Stat.SPECIAL_DEFENSE, Stat.DEFENSE),
    CAREFUL(Stat.SPECIAL_DEFENSE, Stat.SPECIAL_ATTACK),
    SASSY(Stat.SPECIAL_DEFENSE, Stat.SPEED),
    TIMID(Stat.SPEED, Stat.ATTACK),
    HASTY(Stat.SPEED, Stat.DEFENSE),
    JOLLY(Stat.SPEED, Stat.SPECIAL_ATTACK),
    NAIVE(Stat.SPEED, Stat.SPECIAL_DEFENSE),
    HARDY(null, null),
    DOCILE(null, null),
    BASHFUL(null, null),
    QUIRKY(null, null),
    SERIOUS(null, null);

    private final Stat increasedStat;
    private final Stat decreasedStat;

    Nature(Stat increasedStat, Stat decreasedStat) {
        this.increasedStat = increasedStat;
        this.decreasedStat = decreasedStat;
    }

    public Stat getIncreasedStat() {
        return increasedStat;
    }

    public Stat getDecreasedStat() {
        return decreasedStat;
    }

}
