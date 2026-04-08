package fr.aimcvent.template.event.player;

import fr.aimcvent.template.player.Player;

public class PlayerLoadEvent extends PlayerEvent {
    public PlayerLoadEvent(Player player) {
        super(player);
    }
}
