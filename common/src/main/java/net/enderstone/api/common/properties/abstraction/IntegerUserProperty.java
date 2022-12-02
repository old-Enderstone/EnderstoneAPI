package net.enderstone.api.common.properties.abstraction;

import net.enderstone.api.common.properties.UserProperty;

import java.util.UUID;

public abstract class IntegerUserProperty extends AbstractUserProperty<Integer> {

    public IntegerUserProperty(UserProperty key, UUID owner, Integer value) {
        super(key, owner, value);
    }

    public abstract Integer add(Integer value);

    public abstract Integer subtract(Integer value);

    public abstract Integer multiply(Integer value);

    public abstract Integer divide(Integer value);

}
