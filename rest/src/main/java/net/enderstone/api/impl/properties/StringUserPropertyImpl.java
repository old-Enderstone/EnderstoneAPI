package net.enderstone.api.impl.properties;

import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;
import net.enderstone.api.repo.UserPropertyRepository;

import java.util.AbstractMap;
import java.util.UUID;

public class StringUserPropertyImpl extends StringUserProperty {

    private UserPropertyRepository repo;

    public StringUserPropertyImpl(UserProperty key, UUID owner, String value, UserPropertyRepository repo) {
        super(key, owner, value);
    }

    @Override
    public void set(String value) {
        super.set(value);
        repo.update(new AbstractMap.SimpleImmutableEntry<>(getOwner(), getKey()), this);
    }

    @Override
    public String get() {
        return super.get();
    }
}
