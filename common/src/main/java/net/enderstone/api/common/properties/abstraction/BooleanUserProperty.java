package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class BooleanUserProperty extends AbstractUserProperty<Boolean> {

    public BooleanUserProperty(UserProperty key, UUID owner, Boolean value) {
        super(key, owner, value);
    }

    public abstract Boolean toggle();

}
