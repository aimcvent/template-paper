package fr.aimcvent.template;

import fr.aimcvent.paper.api.PaperService;
import fr.aimcvent.kernel.api.Kernel;
import fr.aimcvent.kernel.api.configuration.Configuration;
import fr.aimcvent.kernel.api.configuration.WithConfiguration;
import fr.aimcvent.kernel.api.injector.annotation.Inject;
import fr.aimcvent.kernel.api.injector.annotation.Singleton;
import fr.aimcvent.kernel.api.logger.Logger;
import fr.aimcvent.kernel.api.service.Service;
import fr.aimcvent.kernel.api.settings.Settings;
import fr.aimcvent.kernel.api.settings.WithSettings;
import fr.aimcvent.player.api.PlayerService;
import fr.aimcvent.template.player.Players;
import fr.aimcvent.template.team.Teams;
import fr.aimcvent.template.utils.Informations;
import fr.aimcvent.template.utils.Locations;

@Inject
@Singleton
public class TemplateService implements Service, WithConfiguration, WithSettings {

    private final Kernel kernel;
    private final Informations informations;
    private final Players players;
    private final Teams teams;
    private final Locations locations;

    private TemplateService(Kernel kernel) {
        this.kernel = kernel;
        this.informations = new Informations(kernel);
        this.players = new Players(this, kernel);
        this.teams = new Teams(kernel.events());
        this.locations = new Locations(this);
    }

    @Override
    public String name() {
        return "template-api";
    }

    public String version() {
        return "v0.0.0";
    }

    @Override
    public Logger logger() {
        return this.kernel.loggers().of(this);
    }

    public Informations informations() {
        return this.informations;
    }

    public Players players() {
        return this.players;
    }

    public Teams teams() {
        return this.teams;
    }

    public Locations locations() {
        return this.locations;
    }

    @Override
    public Configuration config() {
        try {
            return this.kernel.configurations().load(this);
        } catch (Throwable throwable) {
            this.logger().error(throwable.getMessage(), throwable);
        }
        return null;
    }

    @Override
    public Settings settings() {
        try {
            return this.kernel.settings().of(this);
        } catch (Throwable throwable) {
            this.logger().error(throwable.getMessage(), throwable);
        }
        return null;
    }

    @Override
    public Class<? extends Service>[] dependencies() {
        return new Class[] {
            PaperService.class,
            PlayerService.class
        };
    }
}
