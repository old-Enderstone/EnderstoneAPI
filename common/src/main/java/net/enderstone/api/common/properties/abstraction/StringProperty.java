package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.SystemProperty;

public abstract class StringProperty extends AbstractSystemProperty<String> {

    public StringProperty(SystemProperty key, String value) {
        super(key, value);
    }
}
