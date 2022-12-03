package net.enderstone.api.repo;

import net.enderstone.api.Main;
import net.enderstone.api.common.Player;
import net.enderstone.api.impl.PlayerImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class PlayerRepository implements IRepository<UUID, Player> {

    @Override
    public void setupDatabase() {
        Main.connector.update("""
                CREATE TABLE `Player` (
                  `uId` varchar(36) PRIMARY KEY NOT NULL,
                  `lastKnownName` varchar(16)
                );
                """);
    }

    @Override
    public boolean hasKey(UUID key) {
        ResultSet rs = Main.connector.query("select `uId` from `Player` where uId=?;", key.toString());
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(UUID key, Player value) {
        Main.connector.update("insert into `Player` values (?, ?);", key.toString(), value.getLastKnownName());
    }

    @Override
    public void update(UUID key, Player value) {
        Main.connector.update("update `Player` set `lastKnownName`=? where `uId`=?;", value.getLastKnownName(), key.toString());
    }

    /**
     * Returns a player instance or null if no database entry is found, the property list of the player will be empty
     */
    @Override
    public Player get(UUID key) {
        ResultSet rs = Main.connector.query("select `lastKnownName` from `Player` where `uId`=?;", key.toString());
        try {
            if(!rs.next()) return null;
            final String lastKnownName = rs.getString("lastKnownString");

            return new PlayerImpl(key, lastKnownName, new ArrayList<>());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<UUID> nameToUUID(String name) {
        ResultSet rs = Main.connector.query("select `uId` from `Player` where `lastKnownName`=?;", name);
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
        Main.connector.update("delete from `Player` where `uId`=?;", key.toString());
    }
}
