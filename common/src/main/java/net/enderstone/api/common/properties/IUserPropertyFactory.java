package net.enderstone.api.common.properties;

import java.util.UUID;

public interface IUserPropertyFactory {

    <T> IUserProperty<T> create(UserProperty property, UUID owner, T value);

}
