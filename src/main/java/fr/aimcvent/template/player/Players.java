package fr.aimcvent.template.player;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.Kernel;
import fr.aimcvent.kernel.api.translation.Translation;
import fr.aimcvent.player.api.PlayerService;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.player.PlayerUnloadEvent;
import fr.aimcvent.template.utils.State;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Players {
    private final Map<UUID, Player> playerMap = new HashMap<>();
    private final AtomicInteger teamIndex = new AtomicInteger();
    private final TemplateService templateService;
    private final Kernel kernel;

    public Players(TemplateService templateService, Kernel kernel) {
        this.templateService = templateService;
        this.kernel = kernel;
    }

    public List<Player> all() {
        return new ArrayList<>(this.playerMap.values());
    }

    public int count() {
        return this.playerMap.size();
    }

    public Player of(HumanEntity bukkitPlayer) {
        return this.playerMap.computeIfAbsent(
            bukkitPlayer.getUniqueId(),
            new PlayerCreatorFunction(
                this.kernel.services().of(PlayerService.class),
                this.kernel.services().of(PaperService.class),
                this.templateService,
                this.kernel,
                (org.bukkit.entity.Player) bukkitPlayer,
                this.teamIndex
            )
        );
    }

    public void remove(Player player) {
        if (
            this.playerMap.containsKey(player.id())
                && this.templateService.informations().state().equals(State.LOBBY)
        ) {
            this.kernel.events().call(new PlayerUnloadEvent(player));
            this.playerMap.remove(player.id());
        }
    }

    public Map<CommandSender, Translation> translations() {
        return this.translations(this.all());
    }

    public Map<CommandSender, Translation> translations(List<Player> players) {
        final Map<CommandSender, Translation> translationMap = new HashMap<>();
        players.forEach(
            player -> player.bukkit()
                .ifPresent(
                    bukkitPlayer -> translationMap.put(bukkitPlayer, player.aimcvent().translation())
                )
        );
        return translationMap;
    }

    public void reset(org.bukkit.entity.Player player, GameMode gameMode) {
        player.setGameMode(gameMode);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue());
        player.getInventory().setContents(new ItemStack[36]);
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.setItemOnCursor(null);
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if (inventory instanceof CraftingInventory) {
            ((CraftingInventory) inventory).setResult(null);
            ((CraftingInventory) inventory).setMatrix(new ItemStack[4]);
        }
        player.getActivePotionEffects()
            .forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.setSaturation(20);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.setTotalExperience(0);
        player.setLevel(0);
        player.setExp(0);
    }
}
