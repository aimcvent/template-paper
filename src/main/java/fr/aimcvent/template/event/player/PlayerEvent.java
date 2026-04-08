package fr.aimcvent.template.event.player;

import fr.aimcvent.kernel.api.event.Event;
import fr.aimcvent.template.player.Player;

public abstract class PlayerEvent implements Event {
    private final Player player;

    protected PlayerEvent(Player player) {
        this.player = player;
    }

    public Player player() {
        return this.player;
    }
}
