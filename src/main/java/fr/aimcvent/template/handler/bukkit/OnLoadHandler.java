package fr.aimcvent.template.handler.bukkit;

import fr.aimcvent.paper.api.event.BukkitOnLoadEvent;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.injector.injection.Injector;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.adapter.InitializerInstanceAdapter;

@Inject
public class OnLoadHandler implements KernelListener {
    private final TemplateService templateService;
    private final Injector injector;

    public OnLoadHandler(TemplateService templateService, Injector injector) {
        this.templateService = templateService;
        this.injector = injector;
    }

    @KernelEventHandler
    private void on(BukkitOnLoadEvent event) {
        this.injector.adapters()
            .add(new InitializerInstanceAdapter());

        event.service().translations()
            .addParameter("prefix", event.service().translations().of(this.templateService, "prefix"))
            .addParameter("version", this.templateService.version());
    }
}
