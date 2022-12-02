package net.enderstone.api.repo;

import net.enderstone.api.Main;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.impl.properties.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserPropertyRepository implements IMultipleKeyRepository<UUID, UserProperty, IUserProperty<?>> {

    @Override
    public void setupDatabase() {
        Main.connector.updateBatch("""
                CREATE TABLE `User` (
                  `uId` varchar(36) PRIMARY KEY NOT NULL,
                  `lastKnownName` varchar(16)
                );
                """,
                """          
                CREATE TABLE `Property` (
                  `uId` varchar(36) NOT NULL,
                  `property` varchar(128) NOT NULL,
                  `value` varchar(1024),
                  PRIMARY KEY (`uId`, `property`)
                );
                """,
                """        
                CREATE TABLE `SystemProperty` (
                  `property` varchar(128) PRIMARY KEY NOT NULL,
                  `value` varchar(1024)
                );
                """,
                """
                ALTER TABLE `User` ADD FOREIGN KEY (`uId`) REFERENCES `Property` (`uId`);
                """);
    }

    /**
     * Returns a collection containing all properties stored in the database that belong to the given uuid.
     * The return value is never null, if there are no properties an empty collection will be returned
     */
    public Collection<IUserProperty<?>> getAllPropertiesByOwner(UUID owner) {
        ResultSet rs = Main.connector.query("select `property`, `value` from `Property` where uId=?;", owner);
        List<IUserProperty<?>> properties = new ArrayList<>();
        try {
            while(rs.next()) {
                String typeStr = rs.getString("property");
                String value = rs.getString("value");
                UserProperty property = UserProperty.valueOf(typeStr);

                properties.add(createProperty(owner, property, value));
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Override
    public boolean hasKey(final Map.Entry<UUID, UserProperty> key) {
        ResultSet rs = Main.connector.query("select `uId` from `Property` where `uId`=? and `property`=?;", key.getKey().toString(), key.getValue().toString());
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(final Map.Entry<UUID, UserProperty> key, final IUserProperty<?> value) {
        Main.connector.update("insert into `Property` values (?, ?, ?);", value.getOwner().toString(), value.getKey().toString(), value.get().toString());
    }

    @Override
    public void update(final Map.Entry<UUID, UserProperty> key, final IUserProperty<?> value) {
        Main.connector.update("update `Property` set `value`=? where `uId`=? and `property`=?;", value.get().toString(), value.getOwner().toString(), value.getKey().toString());
    }

    private IUserProperty<?> createProperty(UUID owner, UserProperty property, String value) {
        return switch(property.type) {
            case STRING -> new StringUserPropertyImpl(property, owner, value, this);
            case BOOLEAN -> new BooleanUserPropertyImpl(property, owner, Boolean.parseBoolean(value), this);
            case INTEGER -> new IntegerUserPropertyImpl(property, owner, Integer.parseInt(value), this);
            case LONG -> new LongUserPropertyImpl(property, owner, Long.parseLong(value), this);
            case DOUBLE -> new DoubleUserPropertyImpl(property, owner, Double.parseDouble(value), this);
            case FLOAT -> new FloatUserPropertyImpl(property, owner, Float.parseFloat(value), this);
        };
    }

    @Override
    public IUserProperty<?> get(final Map.Entry<UUID, UserProperty> key) {
        ResultSet rs = Main.connector.query("select `value` from `Property` where `uId`=? and `property`=?;", key.getKey().toString(), key.getValue().toString());
        try {
            if(!rs.next()) {
                return null;
            }

            String value = rs.getString("value");

            return createProperty(key.getKey(), key.getValue(), value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(final Map.Entry<UUID, UserProperty> key) {
        Main.connector.update("delete from `Property` where `uId`=? and `property`=?;", key.getKey().toString(), key.getValue().toString());
    }
}
