package fr.aimcvent.template.command;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.paper.api.command.ConfigurationBuilder;
import fr.aimcvent.paper.api.command.ConverterType;
import fr.aimcvent.paper.api.command.Executor;
import fr.aimcvent.paper.api.command.Input;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.command.converter.TemplateConverterType;
import fr.aimcvent.template.inventory.TeamReloadInventory;
import fr.aimcvent.template.player.Player;
import fr.aimcvent.template.team.Team;
import fr.aimcvent.template.utils.Permissions;
import fr.aimcvent.template.utils.State;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

@Inject
public class TeamExecutor implements Executor {
    private final TemplateService templateService;
    private final PaperService paperService;

    public TeamExecutor(TemplateService templateService, PaperService paperService) {
        this.templateService = templateService;
        this.paperService = paperService;
    }

    @Override
    public void configuration(ConfigurationBuilder configuration) {
        configuration.name("team")
            .description("Choose a team")
            .usage("/team open")
            .add(
                configuration.argument()
                    .name("open")
                    .optional(),

                configuration.chain(
                    configuration.argument()
                        .name("join")
                        .optional(),
                    configuration.argument()
                        .name("team")
                        .converter(TemplateConverterType.TEAM),
                    configuration.argument()
                        .name("players")
                        .converter(ConverterType.ARRAY)
                ),

                configuration.argument()
                    .name("quit")
                    .optional()
                    .append(
                        configuration.argument()
                            .name("players")
                            .converter(ConverterType.ARRAY)
                    )
            );
    }

    @Override
    public boolean execute(CommandSender sender, Input input) {
        if (
            !(sender instanceof org.bukkit.entity.Player bukkitPlayer)
                || !this.templateService.informations().state().equals(State.LOBBY)
        ) {
            return false;
        }
        final Player player = this.templateService.players().of(bukkitPlayer);
        if (
            input.hasArgument("open")
                || (!input.hasArgument("join") && !input.hasArgument("quit"))
        ) {
            this.paperService.inventories()
                .builderOf(bukkitPlayer)
                .title(
                    this.paperService.translations()
                        .of(this.templateService, "inventory.team.title")
                        .translate(player.aimcvent().translation())
                )
                .lines(1)
                .reload(new TeamReloadInventory(this.paperService, this.templateService))
                .build()
                .open();
            return true;
        }

        if (!player.aimcvent().hasPermission(Permissions.COMMAND_MANAGE_TEAM)) {
            return false;
        }

        final Set<Player> players = new HashSet<>();
        final boolean quit = input.hasArgument("quit");
        for (String playerName : input.<String[]>argumentOf("players")) {
            final org.bukkit.entity.Player bukkitTarget = Bukkit.getPlayer(playerName);
            if (bukkitTarget != null) {
                final Player target = this.templateService.players().of(bukkitTarget);
                if (quit && !target.team().isPresent()) {
                    continue;
                }
                players.add(target);
            }
        }
        if (players.isEmpty()) {
            return false;
        }
        final Team team = input.argumentOf("team");
        if (input.hasArgument("join")) {
            players.forEach(target -> this.templateService.teams().join(team, target));
            return true;
        }
        players.forEach(target -> this.templateService.teams().leave(target));
        return true;
    }
}
