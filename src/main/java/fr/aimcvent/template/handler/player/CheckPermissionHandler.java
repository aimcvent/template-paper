package fr.aimcvent.template.handler.player;

import fr.aimcvent.kernel.api.event.KernelEventHandler;
import fr.aimcvent.kernel.api.event.KernelListener;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.utils.Env;
import fr.aimcvent.kernel.api.utils.Environment;
import fr.aimcvent.player.api.event.PlayerCheckPermissionEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Inject
@Env({Environment.LOCAL, Environment.DEV})
public class CheckPermissionHandler implements KernelListener {
    @KernelEventHandler
    private void on(PlayerCheckPermissionEvent event) {
        final Player player = Bukkit.getPlayer(event.player().playerId());
        event.result(event.result() || (player != null && player.isOp()));
    }
}
