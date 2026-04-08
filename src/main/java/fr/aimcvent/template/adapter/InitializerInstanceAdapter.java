package fr.aimcvent.template.adapter;

import fr.aimcvent.kernel.api.injector.injection.adapter.InstanceAdapter;
import fr.aimcvent.template.initializer.Initializer;

public class InitializerInstanceAdapter implements InstanceAdapter<Initializer> {
    @Override
    public Class<Initializer> type() {
        return Initializer.class;
    }

    @Override
    public void adapt(Initializer initializer) {
        initializer.initialize();
    }
}
