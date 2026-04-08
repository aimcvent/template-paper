package fr.aimcvent.template.utils;

import fr.aimcvent.kernel.api.Kernel;
import fr.aimcvent.template.event.game.UpdateStateEvent;

public class Informations {
    private final Kernel kernel;

    private State state = State.LOADING;

    public Informations(Kernel kernel) {
        this.kernel = kernel;
    }

    public State state() {
        return this.state;
    }

    public void updateState(State state) {
        if (this.state != state) {
            this.state = state;
            this.kernel.events().call(new UpdateStateEvent(state));
        }
    }
}
