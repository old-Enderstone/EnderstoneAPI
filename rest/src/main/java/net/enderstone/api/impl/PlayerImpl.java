package net.enderstone.api.impl;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.Player;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.service.UserPropertyService;

import java.util.Collection;
import java.util.UUID;

public class PlayerImpl extends Player {

    private transient final UserPropertyService userPropertyService;

    public PlayerImpl(final UUID id,
                      final String lastKnownName,
                      final Collection<IUserProperty<?>> properties,
                      final UserPropertyService userPropertyService) {
        super(id, lastKnownName, properties);

        this.userPropertyService = userPropertyService;
    }

    @Override
    public IUserProperty<?> getProperty(UserProperty property) {
        for(IUserProperty<?> prop : super.properties) {
            if(prop.getKey().equals(property)) return prop;
        }

        final IUserProperty<?> prop = userPropertyService.getUserProperty(super.id, property);
        super.properties.add(prop);
        return prop;
    }
}
