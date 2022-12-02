package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.SystemProperty;

public abstract class BooleanProperty extends AbstractSystemProperty<Boolean> {

    public BooleanProperty(SystemProperty key, Boolean value) {
        super(key, value);
    }

    public abstract Boolean toggle();

}
