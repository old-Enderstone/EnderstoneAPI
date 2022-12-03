package net.enderstone.api.impl.types;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.impl.properties.*;
import net.enderstone.api.repository.UserPropertyRepository;
import net.enderstone.api.types.IUserPropertyFactory;

import java.util.UUID;

public class UserPropertyFactoryImpl implements IUserPropertyFactory {

    private final UserPropertyRepository repository;

    public UserPropertyFactoryImpl(UserPropertyRepository repository) {
        this.repository = repository;
    }

    public IUserProperty<?> createEmpty(final UUID owner, final UserProperty property) {
        return createEmpty(owner, property, repository);
    }

    @Override
    public IUserProperty<?> createEmpty(final UUID owner,
                                        final UserProperty property,
                                        final UserPropertyRepository userPropertyRepository) {
        return switch(property.type) {
            case STRING -> new StringUserPropertyImpl(property, owner, null, userPropertyRepository);
            case BOOLEAN -> new BooleanUserPropertyImpl(property, owner, null, userPropertyRepository);
            case INTEGER -> new IntegerUserPropertyImpl(property, owner, null, userPropertyRepository);
            case LONG -> new LongUserPropertyImpl(property, owner, null, userPropertyRepository);
            case DOUBLE -> new DoubleUserPropertyImpl(property, owner, null, userPropertyRepository);
            case FLOAT -> new FloatUserPropertyImpl(property, owner, null, userPropertyRepository);
        };
    }

    public IUserProperty<?> createOfValue(final UUID owner, final UserProperty property, final String value) {
        return createOfValue(owner, property, value, repository);
    }

    @Override
    public IUserProperty<?> createOfValue(final UUID owner,
                                          final UserProperty property,
                                          final String value,
                                          final UserPropertyRepository userPropertyRepository) {
        return switch(property.type) {
            case STRING -> new StringUserPropertyImpl(property, owner, value, userPropertyRepository);
            case BOOLEAN -> new BooleanUserPropertyImpl(property, owner, Boolean.parseBoolean(value), userPropertyRepository);
            case INTEGER -> new IntegerUserPropertyImpl(property, owner, Integer.parseInt(value), userPropertyRepository);
            case LONG -> new LongUserPropertyImpl(property, owner, Long.parseLong(value), userPropertyRepository);
            case DOUBLE -> new DoubleUserPropertyImpl(property, owner, Double.parseDouble(value), userPropertyRepository);
            case FLOAT -> new FloatUserPropertyImpl(property, owner, Float.parseFloat(value), userPropertyRepository);
        };
    }
}
