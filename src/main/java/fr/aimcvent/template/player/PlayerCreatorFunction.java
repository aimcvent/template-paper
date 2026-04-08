package fr.aimcvent.template.player;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.Kernel;
import fr.aimcvent.player.api.PlayerService;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.event.player.PlayerLoadEvent;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

class PlayerCreatorFunction implements Function<UUID, Player> {
    private final PlayerService playerService;
    private final PaperService paperService;
    private final TemplateService templateService;
    private final Kernel kernel;
    private final org.bukkit.entity.Player bukkitPlayer;
    private final AtomicInteger teamIndex;

    protected PlayerCreatorFunction(
        PlayerService playerService,
        PaperService paperService,
        TemplateService templateService,
        Kernel kernel,
        org.bukkit.entity.Player bukkitPlayer,
        AtomicInteger teamIndex
    ) {
        this.playerService = playerService;
        this.paperService = paperService;
        this.templateService = templateService;
        this.kernel = kernel;
        this.bukkitPlayer = bukkitPlayer;
        this.teamIndex = teamIndex;
    }

    @Override
    public Player apply(UUID uuid) {
        final Player player = new Player(
            playerService,
            bukkitPlayer.getUniqueId(),
            bukkitPlayer.getName(),
            this.teamIndex.incrementAndGet(),
            paperService.sidebars()
                .create(
                    bukkitPlayer,
                    paperService.translations()
                        .of(this.templateService, "sidebar.title")
                        .translate(
                            playerService.load(bukkitPlayer.getUniqueId(), bukkitPlayer.getName())
                                .translation()
                        )
                )
        );
        this.kernel.events().call(new PlayerLoadEvent(player));
        return player;
    }
}
