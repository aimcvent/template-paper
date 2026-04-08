package fr.aimcvent.template.event.team;

import fr.aimcvent.kernel.api.event.CancellableEvent;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;

public class PlayerRequestLeaveTeamEvent extends PlayerTeamEvent implements CancellableEvent {
    private boolean cancelled;

    public PlayerRequestLeaveTeamEvent(Team team, Player player) {
        super(team, player);
    }

    @Override
    public boolean cancelled() {
        return this.cancelled;
    }

    @Override
    public void cancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
