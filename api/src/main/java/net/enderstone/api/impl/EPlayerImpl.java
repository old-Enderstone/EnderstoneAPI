package net.enderstone.api.impl;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.EnderStoneAPIImpl;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.Collection;
import java.util.UUID;

public class EPlayerImpl extends EPlayer {

    public EPlayerImpl(UUID id, String lastKnownName, Collection<IUserProperty<?>> properties) {
        super(id, lastKnownName, properties);
    }

    @Override
    public void setLastKnownName(String lastKnownName) {
        super.setLastKnownName(lastKnownName);
        ((EnderStoneAPIImpl)EnderStoneAPI.getInstance()).getPlayerRepository().update(this);
    }

    @Override
    public IUserProperty<?> getProperty(UserProperty property) {
        for(IUserProperty<?> prop : super.properties) {
            if(prop.getKey().equals(property)) return prop;
        }

        final IUserProperty<?> prop = EnderStoneAPI.getInstance().getUserPropertyFactory().createEmpty(super.id, property);
        super.properties.add(prop);
        return prop;
    }
}
