package fr.aimcvent.template.event.game;

import fr.aimcvent.kernel.api.event.Event;
import fr.aimcvent.template.utils.State;

public record UpdateStateEvent(State state) implements Event {
}
