package net.enderstone.api.service;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.impl.properties.*;
import net.enderstone.api.repo.UserPropertyRepository;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.UUID;

public class UserPropertyService {

    private final UserPropertyRepository repository;

    public UserPropertyService(final UserPropertyRepository repository) {
        this.repository = repository;
    }

    private IUserProperty<?> createProperty(final UUID owner, final UserProperty property) {
        return switch(property.type) {
            case STRING -> new StringUserPropertyImpl(property, owner, null, repository);
            case BOOLEAN -> new BooleanUserPropertyImpl(property, owner, null, repository);
            case INTEGER -> new IntegerUserPropertyImpl(property, owner, null, repository);
            case LONG -> new LongUserPropertyImpl(property, owner, null, repository);
            case DOUBLE -> new DoubleUserPropertyImpl(property, owner, null, repository);
            case FLOAT -> new FloatUserPropertyImpl(property, owner, null, repository);
        };
    }

    public Collection<IUserProperty<?>> getAllUserProperties(final UUID user) {
        return repository.getAllPropertiesByOwner(user);
    }

    public IUserProperty<?> getUserProperty(final UUID user, final UserProperty property) {
        final IUserProperty<?> value = repository.get(new AbstractMap.SimpleImmutableEntry<>(user, property));
        if(value == null) return createProperty(user, property);
        return value;
    }

    public void deleteUserProperty(final UUID user, final UserProperty property) {
        repository.delete(new AbstractMap.SimpleEntry<>(user, property));
    }

}
