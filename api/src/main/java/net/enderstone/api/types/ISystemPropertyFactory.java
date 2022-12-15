package net.enderstone.api.types;

import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.repository.SystemPropertyRepository;

public interface ISystemPropertyFactory {

    IProperty<?> createEmpty(final SystemProperty property, final SystemPropertyRepository SystemPropertyRepository);

    IProperty<?> createOfValue(final SystemProperty property,
                               final String value,
                               final SystemPropertyRepository SystemPropertyRepository);
}
