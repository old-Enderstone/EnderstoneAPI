package net.enderstone.api.impl;

import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.service.UserPropertyService;

import java.util.Collection;
import java.util.UUID;

public class EPlayerImpl extends EPlayer {

    private transient final UserPropertyService userPropertyService;

    public EPlayerImpl(final UUID id,
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
