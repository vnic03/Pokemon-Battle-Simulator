package org.example.screens;

import org.example.teams.Team;

@FunctionalInterface
public interface BattleStartListener {
    void onBattleStart(Team team1, Team team2);
}
