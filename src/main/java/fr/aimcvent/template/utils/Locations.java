package fr.aimcvent.template.utils;

import fr.aimcvent.paper.api.denormalizer.LocationDenormalizer;
import fr.aimcvent.template.TemplateService;

public class Locations {
    private final TemplateService templateService;
    private Spawn spawn;

    public Locations(TemplateService templateService) {
        this.templateService = templateService;
    }

    public Spawn spawn() {
        if (this.spawn == null) {
            this.spawn = new Spawn(this.templateService.config().get(LocationDenormalizer.class, "spawn"));
        }
        return this.spawn;
    }
}
