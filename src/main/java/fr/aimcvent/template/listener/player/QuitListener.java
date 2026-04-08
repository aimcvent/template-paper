package fr.aimcvent.template.listener.player;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@Inject
public class QuitListener implements Listener {
    private final TemplateService templateService;
    private final PaperService paperService;

    public QuitListener(TemplateService templateService, PaperService paperService) {
        this.templateService = templateService;
        this.paperService = paperService;
    }

    @EventHandler
    private void on(PlayerQuitEvent event) {
        event.quitMessage(null);
        final Player player = this.templateService.players().of(event.getPlayer());
        this.templateService.players().remove(player);

        this.paperService.translations()
            .of(this.templateService, "event.player.quit")
            .add("player", player.displayName())
            .broadcast(this.templateService.players().translations());

        this.paperService.hotbars().clear(event.getPlayer());
    }
}
