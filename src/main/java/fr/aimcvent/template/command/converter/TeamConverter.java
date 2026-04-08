package fr.aimcvent.template.command.converter;

import fr.aimcvent.paper.api.command.InputConverter;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.template.TemplateService;
import fr.aimcvent.template.team.Team;

import java.util.List;
import java.util.stream.Collectors;

@Inject
public class TeamConverter implements InputConverter<Team> {
    private final TemplateService templateService;

    private TeamConverter(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public String name() {
        return TemplateConverterType.TEAM;
    }

    @Override
    public Team convert(String input) {
        final List<Team> teams = this.templateService.teams().all();
        teams.add(this.templateService.teams().spectator());
        for (Team team : teams) {
            if (team.identifier().equalsIgnoreCase(input)) {
                return team;
            }
        }
        return null;
    }

    @Override
    public List<String> onComplete(String input) {
        final List<Team> teams = this.templateService.teams().all();
        teams.add(this.templateService.teams().spectator());
        return teams.stream()
            .map(Team::identifier)
            .filter(team -> team.startsWith(input.toLowerCase()))
            .collect(Collectors.toList());
    }
}
