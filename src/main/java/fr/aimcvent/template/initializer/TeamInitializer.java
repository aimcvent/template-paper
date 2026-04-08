package fr.aimcvent.template.initializer;

import fr.aimcvent.paper.api.denormalizer.ItemStackDenormalizer;
import fr.aimcvent.paper.api.denormalizer.LocationDenormalizer;
import fr.aimcvent.kernel.api.Kernel;
import fr.aimcvent.kernel.api.configuration.Denormalizer;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.team.Team;
import fr.aimcvent.template.utils.Spawn;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Inject
public class TeamInitializer implements Initializer {
    private final TemplateService templateService;
    private final Kernel kernel;

    public TeamInitializer(TemplateService templateService, Kernel kernel) {
        this.templateService = templateService;
        this.kernel = kernel;
    }

    @Override
    public void initialize() {
        this.templateService.teams().registerSpectator(
            this.create(
                "team.spec",
                this.templateService.config().get("spectator", new HashMap<>())
            )
        );

        this.templateService.config()
            .<List<Map<String, Object>>>get("teams", new ArrayList<>())
            .forEach(teamConfiguration -> {
                this.templateService.teams().register(this.create(null, teamConfiguration));
            });
    }

    private Team create(String identifier, Map<String, Object> teamConfiguration) {
        final String teamIdentifier = teamConfiguration.getOrDefault("identifier", identifier).toString();
        final String prefix = ChatColor.translateAlternateColorCodes(
            '&',
            teamConfiguration.getOrDefault("prefix", "&f").toString()
        );
        final Denormalizer<Location, Map<String, Object>> locationDenormalizer = this.kernel.denormalizers()
            .of(LocationDenormalizer.class);

        final Spawn spawn = new Spawn(
            teamConfiguration.containsKey("spawn")
                ? locationDenormalizer.denormalize((Map<String, Object>) teamConfiguration.get("spawn"))
                : this.templateService.locations().spawn().of()
        );

        final Denormalizer<ItemStack, Object> itemStackDenormalizer = this.kernel.denormalizers()
            .of(ItemStackDenormalizer.class);

        final ItemStack icon = teamConfiguration.containsKey("icon")
            ? itemStackDenormalizer.denormalize(teamConfiguration.get("icon"))
            : new ItemStack(Material.WHITE_WOOL);

        return new Team(
            this.templateService,
            teamIdentifier,
            icon,
            prefix,
            teamConfiguration.getOrDefault("name", teamIdentifier).toString(),
            ChatColor.valueOf(
                teamConfiguration.getOrDefault("color", "white")
                    .toString()
                    .toUpperCase()
            ),
            spawn
        );
    }
}
