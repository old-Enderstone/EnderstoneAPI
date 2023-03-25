package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.impl.EPlayerImpl;
import net.enderstone.api.service.PropertyService;
import net.enderstone.api.jdbc.SQLStatement;
import net.enderstone.api.jdbc.SQLTransaction;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerRepository implements IRepository<UUID, EPlayer> {

    private final PropertyService propertyService;

    public PlayerRepository(final PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public boolean hasKey(UUID key) {
        return RestAPI.connector.query("select `uId` from `Player` where uId=?;", ResultSet::next, key.toString());
    }

    @Override
    public void insert(UUID key, EPlayer value) {
        RestAPI.connector.update("insert into `Player` values (?, ?);", key.toString(), value.getLastKnownName());
    }

    @Override
    public void update(UUID key, EPlayer value) {
        RestAPI.connector.update("update `Player` set `lastKnownName`=? where `uId`=?;", value.getLastKnownName(), key.toString());
    }

    /**
     * Returns a EPlayer instance or null if no database entry is found, the property list of the EPlayer will be empty
     */
    @Override
    public EPlayer get(UUID key) {
        return RestAPI.connector.query("select `lastKnownName` from `Player` where `uId`=?;", rs -> {
            if(!rs.next()) return null;
            final String lastKnownName = rs.getString("lastKnownName");

            return new EPlayerImpl(key, lastKnownName, new ArrayList<>(), propertyService);
        }, key.toString());
    }

    public Collection<UUID> nameToUUID(String name) {
        return RestAPI.connector.query("select `uId` from `Player` where `lastKnownName`=?;", rs -> {
            final List<UUID> ids = new ArrayList<>();

            while(rs.next()) {
                final String uId = rs.getString("uId");
                ids.add(UUID.fromString(uId));
            }

            return ids;
        }, name);
    }

    @Override
    public void delete(UUID key) {
        final SQLTransaction transaction = RestAPI.connector.createEmptyTransaction()
                .withStatement(new SQLStatement("delete from `property` where `uId`=?", key.toString()))
                .withStatement(new SQLStatement("delete from `arrayProperty` where `uId`=?", key.toString()))
                .withStatement(new SQLStatement("delete from `Player` where `uId`=?;", key.toString()));
        transaction.transact();
    }
}
