package net.enderstone.api.impl;

import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.service.PropertyService;

import java.util.Collection;
import java.util.UUID;

public class EPlayerImpl extends EPlayer {

    private transient final PropertyService propertyService;

    public EPlayerImpl(final UUID id,
                       final String lastKnownName,
                       final Collection<AbstractProperty<?>> properties,
                       final PropertyService propertyService) {
        super(id, lastKnownName, properties);

        this.propertyService = propertyService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AbstractProperty<T> getProperty(final PropertyKey<T> key) {
        for(AbstractProperty<?> property : super.properties) {
            if(property.getKey() == key) return (AbstractProperty<T>) property;
        }

        final AbstractProperty<T> property = propertyService.getProperty(key, super.id);
        super.properties.add(property);
        return property;
    }
}
