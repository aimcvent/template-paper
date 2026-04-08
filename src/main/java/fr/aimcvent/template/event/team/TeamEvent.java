package fr.aimcvent.template.event.team;

import fr.aimcvent.kernel.api.event.Event;
import fr.aimcvent.template.team.Team;

public abstract class TeamEvent implements Event {
    private final Team team;

    protected TeamEvent(Team team) {
        this.team = team;
    }

    public Team team() {
        return this.team;
    }
}
