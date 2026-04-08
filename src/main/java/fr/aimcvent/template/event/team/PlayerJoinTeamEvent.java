package fr.aimcvent.template.event.team;

import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;

public class PlayerJoinTeamEvent extends PlayerTeamEvent {
    public PlayerJoinTeamEvent(Team team, Player player) {
        super(team, player);
    }
}
