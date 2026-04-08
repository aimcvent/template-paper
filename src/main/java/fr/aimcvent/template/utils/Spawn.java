package fr.aimcvent.template.utils;

import fr.aimcvent.template.player.Player;
import org.bukkit.Location;

import java.util.Collection;

public class Spawn {
    private final Location location;

    public Spawn(Location location) {
        this.location = location;
    }

    public Location of() {
        return this.location;
    }

    public void teleportTemplatePlayers(Collection<Player> players) {
        players.forEach(this::teleport);
    }

    public void teleportBukkitPlayers(Collection<org.bukkit.entity.Player> players) {
        players.forEach(this::teleport);
    }

    public void teleport(Player player) {
        player.bukkit().ifPresent(this::teleport);
    }

    public void teleport(org.bukkit.entity.Player player) {
        player.teleport(this.location);
    }
}
