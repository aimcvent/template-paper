package fr.aimcvent.template.handler.player;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.player.api.PlayerService;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.player.PlayerUnloadEvent;
import fr.aimcvent.template.sidebar.SidebarKey;

@Inject
public class UnloadHandler implements KernelListener {
    private final TemplateService templateService;
    private final PaperService paperService;
    private final PlayerService playerService;

    public UnloadHandler(TemplateService templateService, PaperService paperService, PlayerService playerService) {
        this.templateService = templateService;
        this.paperService = paperService;
        this.playerService = playerService;
    }

    @KernelEventHandler
    private void on(PlayerUnloadEvent event) {
        this.templateService.teams().leave(event.player());
        event.player()
            .bukkit()
            .ifPresent(player -> this.paperService.sidebars().destroy(player));
        this.playerService.unload(event.player().aimcvent(), false);
        this.paperService.sidebars().update(SidebarKey.PLAYERS);
    }
}
