package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.impl.EPlayerImpl;
import net.enderstone.api.service.UserPropertyService;
import net.enderstone.api.sql.SQLStatement;
import net.enderstone.api.sql.SQLTransaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerRepository implements IRepository<UUID, EPlayer> {

    private final UserPropertyService userPropertyService;

    public PlayerRepository(final UserPropertyService userPropertyService) {
        this.userPropertyService = userPropertyService;
    }

    @Override
    public boolean hasKey(UUID key) {
        ResultSet rs = RestAPI.connector.query("select `uId` from `Player` where uId=?;", key.toString());
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        ResultSet rs = RestAPI.connector.query("select `lastKnownName` from `Player` where `uId`=?;", key.toString());
        try {
            if(!rs.next()) return null;
            final String lastKnownName = rs.getString("lastKnownName");

            return new EPlayerImpl(key, lastKnownName, new ArrayList<>(), userPropertyService);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<UUID> nameToUUID(String name) {
        ResultSet rs = RestAPI.connector.query("select `uId` from `Player` where `lastKnownName`=?;", name);
        List<UUID> ids = new ArrayList<>();

        try {
            while(rs.next()) {
                final String uId = rs.getString("uId");
                ids.add(UUID.fromString(uId));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return ids;
    }

    @Override
    public void delete(UUID key) {
        final SQLTransaction transaction = RestAPI.connector.createEmptyTransaction()
                .withStatement(new SQLStatement("delete from `Property` where `uId`=?", key.toString()))
                .withStatement(new SQLStatement("delete from `Player` where `uId`=?;", key.toString()));
        transaction.transact();
    }
}
