package fr.aimcvent.template.command.converter;

import fr.aimcvent.paper.api.command.InputConverter;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Inject
public class PlayerConverter implements InputConverter<Player> {
    private final TemplateService templateService;

    private PlayerConverter(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public String name() {
        return TemplateConverterType.PLAYER;
    }

    @Override
    public Player convert(String input) {
        return this.templateService.players()
            .all()
            .stream()
            .filter(player -> player.name().equalsIgnoreCase(input))
            .findFirst()
            .orElse(null);
    }

    @Override
    public List<String> onComplete(String input) {
        final List<String> players = new ArrayList<>();
        return this.templateService.players()
            .all()
            .stream()
            .map(Player::name)
            .filter(name -> name.toLowerCase().startsWith(input.toLowerCase()))
            .collect(Collectors.toList());
    }
}
