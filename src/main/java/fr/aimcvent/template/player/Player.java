package fr.aimcvent.template.player;

import fr.aimcvent.paper.api.sidebar.Sidebar;
import fr.aimcvent.player.api.PlayerService;
import fr.aimcvent.player.api.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;

public class Player {
    private final PlayerService playerService;
    private final PlayerTeam team;
    private final UUID id;
    private final String name;
    private final Sidebar sidebar;

    protected Player(PlayerService playerService, UUID id, String name, int teamIndex, Sidebar sidebar) {
        this.playerService = playerService;
        this.id = id;
        this.name = name;
        this.team = new PlayerTeam(this, teamIndex);
        this.sidebar = sidebar;
    }

    public UUID id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String displayName() {
        final Rank rank = this.aimcvent().rank();
        return ChatColor.translateAlternateColorCodes(
            '&',
            rank.bukkitPrefix()
                + this.name
                + rank.bukkitSuffix()
        );
    }

    public Optional<org.bukkit.entity.Player> bukkit() {
        return Optional.ofNullable(Bukkit.getPlayer(this.id));
    }

    public fr.aimcvent.player.api.Player aimcvent() {
        return this.playerService.load(this.id, this.name);
    }

    public PlayerTeam team() {
        return this.team;
    }

    public Sidebar sidebar() {
        return this.sidebar;
    }
}
