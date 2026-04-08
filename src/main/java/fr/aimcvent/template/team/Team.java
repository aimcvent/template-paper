package fr.aimcvent.template.team;

import fr.aimcvent.kernel.api.translation.Translation;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.utils.Spawn;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Team {
    private final Map<UUID, Player> playerMap = new HashMap<>();
    private final TemplateService templateService;
    private final String identifier;
    private final ItemStack icon;
    private final String prefix;
    private final String name;
    private final ChatColor color;
    private final Spawn spawn;

    public Team(
        TemplateService templateService,
        String identifier,
        ItemStack icon,
        String prefix,
        String name,
        ChatColor color,
        Spawn spawn
    ) {
        this.templateService = templateService;
        this.identifier = identifier;
        this.icon = icon;
        this.prefix = prefix;
        this.name = name;
        this.color = color;
        this.spawn = spawn;
    }

    public String identifier() {
        return this.identifier;
    }

    public String prefix() {
        return this.prefix;
    }

    public String name() {
        return this.name;
    }

    public String displayName() {
        return this.prefix + this.color.toString() + this.name;
    }

    public ItemStack icon() {
        return this.icon;
    }

    public Spawn spawn() {
        return this.spawn;
    }

    public ChatColor color() {
        return this.color;
    }

    public List<Player> players() {
        return new ArrayList<>(this.playerMap.values());
    }

    public int size() {
        return this.playerMap.size();
    }

    public boolean has(Player player) {
        return this.playerMap.containsKey(player.id());
    }

    public Map<CommandSender, Translation> translations() {
        return this.templateService.players().translations(this.players());
    }

    protected void add(Player player) {
        this.playerMap.put(player.id(), player);
    }

    protected void remove(Player player) {
        this.playerMap.remove(player.id());
    }
}
