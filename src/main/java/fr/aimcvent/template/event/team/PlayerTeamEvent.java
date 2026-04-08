package fr.aimcvent.template.event.team;

import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;

public abstract class PlayerTeamEvent extends TeamEvent {
    private final Player player;

    protected PlayerTeamEvent(Team team, Player player) {
        super(team);
        this.player = player;
    }

    public Player player() {
        return this.player;
    }
}
