package fr.aimcvent.template.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlayerTeam {
    private final Player player;
    private final int index;

    private org.bukkit.scoreboard.Team bukkitTeam;
    private fr.aimcvent.template.team.Team templateTeam;

    public PlayerTeam(Player player, int index) {
        this.player = player;
        this.index = index;
    }

    public boolean isPresent() {
        return this.templateTeam != null;
    }

    public fr.aimcvent.template.team.Team of() {
        return this.templateTeam;
    }

    public org.bukkit.scoreboard.Team bukkit() {
        return this.bukkitTeam;
    }

    public void set(fr.aimcvent.template.team.Team team) {
        this.templateTeam = team;
        if (this.bukkitTeam != null) {
            this.bukkitTeam.unregister();
        }
        this.bukkitTeam = Bukkit.getScoreboardManager()
            .getMainScoreboard()
            .registerNewTeam((team != null ? team.identifier() : "z") + "_" + this.index);
        this.bukkitTeam.addEntry(this.player.name());
        this.bukkitTeam.setAllowFriendlyFire(false);
        this.bukkitTeam.setColor(team != null ? team.color() : ChatColor.RESET);
        this.update();
    }

    public void update() {
        this.bukkitTeam.prefix(
            this.templateTeam != null
                ? Component.text(this.templateTeam.prefix())
                : Component.empty()
        );
        this.bukkitTeam.suffix(Component.empty());
    }
}
