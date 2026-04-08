package fr.aimcvent.template.handler.game;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.game.UpdateStateEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Inject
public class UpdateStateHandler implements KernelListener {
    private final PaperService paperService;
    private final TemplateService templateService;

    public UpdateStateHandler(PaperService paperService, TemplateService templateService) {
        this.paperService = paperService;
        this.templateService = templateService;
    }

    @KernelEventHandler
    private void on(UpdateStateEvent event) {
        this.paperService.sidebars().clear();
        final String key = "sidebar." + event.state().name().toLowerCase();
        if (this.templateService.config().has(key)) {
            final List<String> list = this.templateService.config().get(key);
            final Map<Integer, String> sidebarConfig = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                sidebarConfig.put(i, list.get(i));
            }
            this.paperService.sidebars().apply(sidebarConfig);
            this.paperService.sidebars().reload();
        }
    }
}
