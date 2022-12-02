package net.enderstone.api.repo;

import net.enderstone.api.Main;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.util.Map;
import java.util.UUID;

public class UserPropertyRepository implements IMultipleKeyRepository<UUID, UserProperty, IUserProperty<?>> {

    @Override
    public void setupDatabase() {
        Main.connector.update("""
                CREATE TABLE `User` (
                  `uId` varchar(36) PRIMARY KEY NOT NULL,
                  `lastKnownName` varchar(16)
                );
                                
                CREATE TABLE `Property` (
                  `uId` varchar(36) NOT NULL,
                  `property` varchar(128) NOT NULL,
                  `value` varchar(1024),
                  PRIMARY KEY (`uId`, `property`)
                );
                                
                CREATE TABLE `SystemProperty` (
                  `property` varchar(128) PRIMARY KEY NOT NULL,
                  `value` varchar(1024)
                );
                                
                ALTER TABLE `User` ADD FOREIGN KEY (`uId`) REFERENCES `Property` (`uId`);
                """);
    }

    @Override
    public boolean hasKey(final Map.Entry<UUID, UserProperty> key) {
        return false;
    }

    @Override
    public void insert(final Map.Entry<UUID, UserProperty> key, final IUserProperty<?> value) {
        Main.connector.update("insert into `Property` values (?, ?, ?);", value.getOwner().toString(), value.getKey().toString(), value.get().toString());
    }

    @Override
    public void update(final Map.Entry<UUID, UserProperty> key, final IUserProperty<?> value) {
        Main.connector.update("update `Property` set `value`=? where `uId`=? and `property`=?;", value.get().toString(), value.getOwner().toString(), value.getKey().toString());
    }

    @Override
    public IUserProperty<?> get(final Map.Entry<UUID, UserProperty> key) {
        return null;
    }

    @Override
    public void delete(final Map.Entry<UUID, UserProperty> key) {
        Main.connector.update("delete from `Property` where `uId`=? and `property`=?;", key.getKey().toString(), key.getValue().toString());
    }
}
