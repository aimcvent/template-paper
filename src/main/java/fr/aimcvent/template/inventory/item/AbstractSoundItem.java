package fr.aimcvent.template.inventory.item;

import fr.aimcvent.paper.api.inventory.Inventory;
import fr.aimcvent.paper.api.inventory.Item;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public abstract class AbstractSoundItem implements Item {
    @Override
    public void click(Inventory inventory, ClickType clickType) {
        this.playSound(inventory.player(), this.onClick(inventory, clickType));
    }

    protected void playSound(Player player, boolean result) {
        player.playSound(
            player.getLocation(),
            result ? Sound.ENTITY_EXPERIENCE_ORB_PICKUP : Sound.ENTITY_GHAST_DEATH,
            1f,
            1f
        );
    }

    public abstract boolean onClick(Inventory inventory, ClickType clickType);
}
