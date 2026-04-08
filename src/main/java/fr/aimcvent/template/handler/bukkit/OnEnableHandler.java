package fr.aimcvent.template.handler.bukkit;

import fr.aimcvent.paper.api.event.BukkitOnEnableEvent;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.injector.injection.Injector;

@Inject
public class OnEnableHandler implements KernelListener {
    private final Injector injector;

    public OnEnableHandler(Injector injector) {
        this.injector = injector;
    }

    @KernelEventHandler
    private void on(BukkitOnEnableEvent event) {
        this.injector.scanner()
            .find(
                "fr.aimcvent.template.command",
                "fr.aimcvent.template.listener",
                "fr.aimcvent.template.sidebar"
            ).inject();
    }
}
