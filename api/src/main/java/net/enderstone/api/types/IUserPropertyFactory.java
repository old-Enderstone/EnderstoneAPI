package net.enderstone.api.types;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.repository.UserPropertyRepository;

import java.util.UUID;

public interface IUserPropertyFactory {

    IUserProperty<?> createEmpty(final UUID owner, final UserProperty property);

    IUserProperty<?> createEmpty(final UUID owner,
                                 final UserProperty property,
                                 final UserPropertyRepository userPropertyRepository);

    IUserProperty<?> createOfValue(final UUID owner, final UserProperty property, final String value);

    IUserProperty<?> createOfValue(final UUID owner,
                                   final UserProperty property,
                                   final String value,
                                   final UserPropertyRepository userPropertyRepository);

}
