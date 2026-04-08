package fr.aimcvent.template.event.player;

import fr.aimcvent.template.player.Player;

public class PlayerUnloadEvent extends PlayerEvent {
    public PlayerUnloadEvent(Player player) {
        super(player);
    }
}
