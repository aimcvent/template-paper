package fr.aimcvent.template.hotbar;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.paper.api.hotbar.Item;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;
import fr.aimcvent.template.utils.State;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TeamItem implements Item {
    private final TemplateService templateService;
    private final PaperService paperService;
    private final Player player;

    public TeamItem(TemplateService templateService, PaperService paperService, Player player) {
        this.templateService = templateService;
        this.paperService = paperService;
        this.player = player;
    }

    @Override
    public ItemStack icon() {
        final Team team = this.player.team().of();
        return this.paperService.items()
            .of(team != null ? team.icon().getType() : Material.WHITE_WOOL)
            .name(
                this.paperService.translations()
                    .of(this.templateService, "inventory.team.title")
                    .translate(this.player.aimcvent().translation())
            )
            .build();
    }

    @Override
    public void interact(org.bukkit.entity.Player player) {
        if (this.templateService.informations().state().equals(State.LOBBY)) {
            player.performCommand("team open");
        }
    }
}
