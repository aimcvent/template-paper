package fr.aimcvent.template.listener.player;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.translation.Translation;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.hotbar.TeamItem;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.utils.State;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@Inject
public class JoinListener implements Listener {
    private final TemplateService templateService;
    private final PaperService paperService;

    public JoinListener(TemplateService templateService, PaperService paperService) {
        this.templateService = templateService;
        this.paperService = paperService;
    }

    @EventHandler
    private void on(PlayerJoinEvent event) {
        event.joinMessage(null);

        final Player player = this.templateService.players().of(event.getPlayer());
        final Translation translation = player.aimcvent().translation();

        this.paperService.tabListOf(this.templateService)
            .header("player_list.header")
            .footer("player_list.footer")
            .send(event.getPlayer(), translation);

        if (this.templateService.informations().state().equals(State.LOBBY)) {
            this.templateService.players().reset(event.getPlayer(), GameMode.ADVENTURE);
            this.templateService.locations().spawn().teleport(event.getPlayer());

            this.paperService.hotbars()
                .register(
                    event.getPlayer(),
                    4,
                    new TeamItem(this.templateService, this.paperService, player)
                );
            this.paperService.hotbars().update(event.getPlayer());
        }

        this.paperService.translations()
            .of(this.templateService, "event.player.join")
            .add("player", player.displayName())
            .broadcast(this.templateService.players().translations());
    }
}
