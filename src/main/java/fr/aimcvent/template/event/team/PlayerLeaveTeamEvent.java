package fr.aimcvent.template.event.team;

import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;

public class PlayerLeaveTeamEvent extends PlayerTeamEvent {
    public PlayerLeaveTeamEvent(Team team, Player player) {
        super(team, player);
    }
}
