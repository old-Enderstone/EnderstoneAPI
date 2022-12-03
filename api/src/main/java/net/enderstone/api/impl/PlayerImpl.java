package net.enderstone.api.impl;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.Player;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.Collection;
import java.util.UUID;

public class PlayerImpl extends Player {

    public PlayerImpl(UUID id, String lastKnownName, Collection<IUserProperty<?>> properties) {
        super(id, lastKnownName, properties);
    }

    @Override
    public IUserProperty<?> getProperty(UserProperty property) {
        for(IUserProperty<?> prop : super.properties) {
            if(prop.getKey().equals(property)) return prop;
        }

        final IUserProperty<?> prop = EnderStoneAPI.getInstance().createProperty(super.id, property);
        super.properties.add(prop);
        return prop;
    }
}
