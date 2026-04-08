package fr.aimcvent.template.handler.player;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.event.player.PlayerLoadEvent;
import fr.aimcvent.template.sidebar.SidebarKey;

@Inject
public class LoadHandler implements KernelListener {
    private final PaperService paperService;

    public LoadHandler(PaperService paperService) {
        this.paperService = paperService;
    }

    @KernelEventHandler
    private void on(PlayerLoadEvent event) {
        this.paperService.sidebars().update(SidebarKey.PLAYERS);
    }
}
