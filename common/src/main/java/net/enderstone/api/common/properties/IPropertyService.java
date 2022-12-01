package net.enderstone.api.common.properties;

import java.util.Collection;
import java.util.UUID;

public interface IPropertyService {

    IProperty<?> getProperty(final SystemProperty property);

    IUserProperty<?> getUserProperty(final UUID user, final UserProperty property);
    Collection<IUserProperty<?>> getUserProperties(final UUID user);

    void registerProperty(final IProperty<?> property);

    void registerUserProperty(final IUserProperty<?> property);

}
