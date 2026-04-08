package fr.aimcvent.template.handler.kernel;

import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.event.kernel.KernelReadyEvent;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.injector.injection.Injector;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.utils.State;

@Inject
public class ReadyHandler implements KernelListener {
    private final TemplateService templateService;
    private final Injector injector;

    public ReadyHandler(TemplateService templateService, Injector injector) {
        this.templateService = templateService;
        this.injector = injector;
    }

    @KernelEventHandler
    private void on(KernelReadyEvent event) {
        this.injector.scanner().find("fr.aimcvent.template.initializer").inject();
        this.templateService.informations().updateState(State.LOBBY);
    }
}
