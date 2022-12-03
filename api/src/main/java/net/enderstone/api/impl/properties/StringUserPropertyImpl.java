package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;

public class StringUserPropertyImpl extends StringUserProperty {

    private final UserPropertyRepository repository;

    public StringUserPropertyImpl(UserProperty key, UUID owner, String value, UserPropertyRepository repository) {
        super(key, owner, value);
        this.repository = repository;
    }

    @Override
    public void set(String value) {
        super.set(value);
        repository.setValue(getOwner(), getKey(), value);
    }
}
