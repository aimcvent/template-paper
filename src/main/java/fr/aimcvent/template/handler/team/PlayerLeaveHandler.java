package fr.aimcvent.template.handler.team;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.team.PlayerJoinTeamEvent;
import fr.aimcvent.template.player.Player;

import java.util.List;

@Inject
public class PlayerLeaveHandler implements KernelListener {
    private final PaperService paperService;
    private final TemplateService templateService;

    public PlayerLeaveHandler(PaperService paperService, TemplateService templateService) {
        this.paperService = paperService;
        this.templateService = templateService;
    }

    @KernelEventHandler
    private void on(PlayerJoinTeamEvent event) {
        final List<Player> players = event.team().players();
        players.remove(event.player());
        this.paperService.translations()
            .of(this.templateService, "event.team.leave")
            .add("player", event.player().displayName())
            .broadcast(this.templateService.players().translations(players));

        event.player().bukkit()
            .ifPresent(player -> this.paperService.translations()
                .of(this.templateService, "event.team.player.leave")
                .add("team", event.team().displayName())
                .send(player, event.player().aimcvent().translation())
            );
    }
}
