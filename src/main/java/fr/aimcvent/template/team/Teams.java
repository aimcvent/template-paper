package fr.aimcvent.template.team;

import fr.aimcvent.kernel.api.event.Events;
import fr.aimcvent.template.event.team.PlayerJoinTeamEvent;
import fr.aimcvent.template.event.team.PlayerLeaveTeamEvent;
import fr.aimcvent.template.event.team.PlayerRequestJoinTeamEvent;
import fr.aimcvent.template.event.team.PlayerRequestLeaveTeamEvent;
import fr.aimcvent.template.player.Player;

import java.util.*;

public class Teams {
    private final Map<String, Team> teamMap = new HashMap<>();
    private final Events events;
    private Team spectator;

    public Teams(Events events) {
        this.events = events;
    }

    public List<Team> all() {
        return new ArrayList<>(this.teamMap.values());
    }

    public Optional<Team> of(String identifier) {
        return Optional.ofNullable(this.teamMap.get(identifier));
    }

    public Team spectator() {
        return this.spectator;
    }

    public int count() {
        return this.teamMap.size();
    }

    public boolean join(Team team, Player player) {
        if (
            team.has(player)
                || (player.team().isPresent() && !this.leave(player))
                || this.events.call(new PlayerRequestJoinTeamEvent(team, player)).cancelled()
        ) {
            return false;
        }
        team.add(player);
        player.team().set(team);
        this.events.call(new PlayerJoinTeamEvent(team, player));
        return true;
    }

    public boolean leave(Player player) {
        if (!player.team().isPresent()) {
            return false;
        }
        final Team team = player.team().of();
        if (this.events.call(new PlayerRequestLeaveTeamEvent(team, player)).cancelled()) {
            return false;
        }
        player.team().of().remove(player);
        player.team().set(null);
        this.events.call(new PlayerLeaveTeamEvent(team, player));
        return true;
    }

    public void register(Team team) {
        this.teamMap.put(team.identifier(), team);
    }

    public void registerSpectator(Team spectator) {
        this.spectator = spectator;
    }
}
