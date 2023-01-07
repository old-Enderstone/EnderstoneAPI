package net.enderstone.api.impl;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.EnderStoneAPIImpl;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;

import java.util.Collection;
import java.util.UUID;

public class EPlayerImpl extends EPlayer {

    public EPlayerImpl(UUID id, String lastKnownName, Collection<AbstractProperty<?>> properties) {
        super(id, lastKnownName, properties);
    }

    @Override
    public void setLastKnownName(String lastKnownName) {
        super.setLastKnownName(lastKnownName);
        ((EnderStoneAPIImpl)EnderStoneAPI.getInstance()).getPlayerRepository().update(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> AbstractProperty<T> getProperty(final PropertyKey<T> propertyKey) {
        for(AbstractProperty<?> property : super.properties) {
            if(property.getKey() == propertyKey) return (AbstractProperty<T>) property;
        }

        final AbstractProperty<T> property = propertyKey.supplier().apply(propertyKey);
        property.setOwner(getId());
        super.properties.add(property);
        return property;
    }
}
