package net.enderstone.api.service;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.impl.properties.*;
import net.enderstone.api.repository.SystemPropertyRepository;

public class SystemPropertyService {

    private final SystemPropertyRepository repository;

    public SystemPropertyService(final SystemPropertyRepository repository) {
        this.repository = repository;
    }

    private IProperty<?> createProperty(final SystemProperty property) {
        return switch(property.type) {
            case STRING -> new StringSystemPropertyImpl(property, null, repository);
            case BOOLEAN -> new BooleanSystemPropertyImpl(property, null, repository);
            case INTEGER -> new IntegerSystemPropertyImpl(property, null, repository);
            case LONG -> new LongSystemPropertyImpl(property, null, repository);
            case DOUBLE -> new DoubleSystemPropertyImpl(property, null, repository);
            case FLOAT -> new FloatSystemPropertyImpl(property, null, repository);
        };
    }

    public IProperty<?> getProperty(SystemProperty property) {
        final IProperty<?> propertyHandle = repository.get(property);
        if(propertyHandle != null) return propertyHandle;
        return createProperty(property);
    }

}