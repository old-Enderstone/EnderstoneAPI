package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class StringUserProperty extends AbstractUserProperty<String> {

    public StringUserProperty(UserProperty key, UUID owner, String value) {
        super(key, owner, value);
    }
}
