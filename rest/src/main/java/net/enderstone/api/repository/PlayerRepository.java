package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.Player;
import net.enderstone.api.impl.PlayerImpl;
import net.enderstone.api.service.UserPropertyService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerRepository implements IRepository<UUID, Player> {

    private final UserPropertyService userPropertyService;

    public PlayerRepository(final UserPropertyService userPropertyService) {
        this.userPropertyService = userPropertyService;
    }

    @Override
    public void setupDatabase() {
        RestAPI.connector.updateBatch("""
                CREATE TABLE `Player` (
                  `uId` varchar(36) PRIMARY KEY NOT NULL,
                  `lastKnownName` varchar(16)
                );
                """,
                """
                ALTER TABLE `Property` ADD FOREIGN KEY (`uId`) REFERENCES `Player` (`uId`);
                """);
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
    public void insert(UUID key, Player value) {
        RestAPI.connector.update("insert into `Player` values (?, ?);", key.toString(), value.getLastKnownName());
    }

    @Override
    public void update(UUID key, Player value) {
        RestAPI.connector.update("update `Player` set `lastKnownName`=? where `uId`=?;", value.getLastKnownName(), key.toString());
    }

    /**
     * Returns a player instance or null if no database entry is found, the property list of the player will be empty
     */
    @Override
    public Player get(UUID key) {
        ResultSet rs = RestAPI.connector.query("select `lastKnownName` from `Player` where `uId`=?;", key.toString());
        try {
            if(!rs.next()) return null;
            final String lastKnownName = rs.getString("lastKnownName");

            return new PlayerImpl(key, lastKnownName, new ArrayList<>(), userPropertyService);
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
        RestAPI.connector.update("delete from `Player` where `uId`=?;", key.toString());
    }
}
