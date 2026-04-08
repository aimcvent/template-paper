package fr.aimcvent.template.inventory.item;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.paper.api.inventory.Inventory;
import fr.aimcvent.paper.api.translation.Translator;
import fr.aimcvent.kernel.api.translation.Translation;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class TeamItem extends AbstractSoundItem {
    private final TemplateService templateService;
    private final PaperService paperService;
    private final Player player;
    private final Team team;

    public TeamItem(TemplateService templateService, PaperService paperService, Player player, Team team) {
        this.templateService = templateService;
        this.paperService = paperService;
        this.player = player;
        this.team = team;
    }

    @Override
    public ItemStack icon() {
        final Translator translator = this.paperService.translations()
            .of(this.templateService, "inventory.team.lore");
        final Translation translation = this.player.aimcvent().translation();

        return this.paperService.items()
            .of(this.team.icon().getType(), 1)
            .name(this.team.name())
            .lore(
                this.team.players().stream()
                    .map(p -> translator.add("player", p.name()).translate(translation))
                    .collect(Collectors.toList())
            )
            .build();
    }

    @Override
    public boolean onClick(Inventory inventory, ClickType clickType) {
        final String path;
        if (clickType.isLeftClick()) {
            if (this.templateService.teams().join(this.team, this.player)) {
                inventory.player().closeInventory();
                this.paperService.hotbars().update(inventory.player());
                return true;
            }
            path = "inventory.team.join.error";
        } else {
            path = this.team.equals(this.player.team().of())
                    && this.templateService.teams().leave(this.player)
                ? null
                : "inventory.team.leave.error";
            inventory.update();
        }

        if (path != null) {
            this.paperService.translations()
                .of(this.templateService, path)
                .add("team", this.team.displayName())
                .send(inventory.player(), this.player.aimcvent().translation());
        }

        this.paperService.hotbars().update(inventory.player());
        return path == null;
    }
}
