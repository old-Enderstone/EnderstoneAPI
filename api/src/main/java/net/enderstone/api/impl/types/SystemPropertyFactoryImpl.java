package net.enderstone.api.impl.types;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.impl.properties.*;
import net.enderstone.api.repository.SystemPropertyRepository;
import net.enderstone.api.types.ISystemPropertyFactory;

public class SystemPropertyFactoryImpl implements ISystemPropertyFactory {

    private final SystemPropertyRepository repository;

    public SystemPropertyFactoryImpl(SystemPropertyRepository repository) {
        this.repository = repository;
    }

    public IProperty<?> createEmpty(final SystemProperty property) {
        return createEmpty(property, repository);
    }

    @Override
    public IProperty<?> createEmpty(final SystemProperty property,
                                    final SystemPropertyRepository SystemPropertyRepository) {
        return switch(property.type) {
            case STRING -> new StringSystemPropertyImpl(property, null, SystemPropertyRepository);
            case BOOLEAN -> new BooleanSystemPropertyImpl(property, null, SystemPropertyRepository);
            case INTEGER -> new IntegerSystemPropertyImpl(property, null, SystemPropertyRepository);
            case LONG -> new LongSystemPropertyImpl(property, null, SystemPropertyRepository);
            case DOUBLE -> new DoubleSystemPropertyImpl(property, null, SystemPropertyRepository);
            case FLOAT -> new FloatSystemPropertyImpl(property, null, SystemPropertyRepository);
        };
    }

    public IProperty<?> createOfValue(final SystemProperty property, final String value) {
        return createOfValue(property, value, repository);
    }

    @Override
    public IProperty<?> createOfValue(final SystemProperty property,
                                      final String value,
                                      final SystemPropertyRepository SystemPropertyRepository) {
        return switch(property.type) {
            case STRING -> new StringSystemPropertyImpl(property, value, SystemPropertyRepository);
            case BOOLEAN -> new BooleanSystemPropertyImpl(property, Boolean.parseBoolean(value), SystemPropertyRepository);
            case INTEGER -> new IntegerSystemPropertyImpl(property, Integer.parseInt(value), SystemPropertyRepository);
            case LONG -> new LongSystemPropertyImpl(property, Long.parseLong(value), SystemPropertyRepository);
            case DOUBLE -> new DoubleSystemPropertyImpl(property, Double.parseDouble(value), SystemPropertyRepository);
            case FLOAT -> new FloatSystemPropertyImpl(property, Float.parseFloat(value), SystemPropertyRepository);
        };
    }
    
}
