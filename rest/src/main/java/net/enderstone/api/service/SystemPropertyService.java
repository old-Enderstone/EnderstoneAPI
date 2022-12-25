package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.impl.properties.*;
import net.enderstone.api.repository.SystemPropertyRepository;

import java.util.Collection;

public class SystemPropertyService extends GlobalBean {

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

    public Collection<IProperty<?>> getAllProperties() {
        return repository.getAllProperties();
    }

    public IProperty<?> getProperty(SystemProperty property) {
        return repository.get(property);
    }

}
