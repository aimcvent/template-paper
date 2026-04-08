package fr.aimcvent.template.sidebar;

import fr.aimcvent.paper.api.sidebar.SidebarLine;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;

@Inject
public class WhitespaceSidebarLine implements SidebarLine {
    @Override
    public String key() {
        return SidebarKey.WHITESPACE;
    }

    @Override
    public Optional<String> apply(UUID playerId, int index) {
        return Optional.of(
            ChatColor.translateAlternateColorCodes(
                '&',
                "&" + Integer.toHexString(index) + " "
            )
        );
    }
}
