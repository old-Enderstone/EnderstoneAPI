package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PropertyRepository implements IMultipleKeyRepository<Integer, UUID, String> {

    @Override
    public boolean hasKey(final Map.Entry<Integer, UUID> key) {
        return RestAPI.connector.query("select `id` from `property` where `id`=? and `uId`=?;",
                                       ResultSet::next,
                                       key.getKey(),
                                       key.getValue() == null ? "NULL": key.toString());
    }

    @Override
    public void insert(final Map.Entry<Integer, UUID> key, final String value) {
        RestAPI.connector.update("replace into `property` values (?, ?, ?);", key.getKey(), key.getValue() == null ? "NULL": key.getValue().toString(), value);
    }

    @Override
    public void update(final Map.Entry<Integer, UUID> key, final String value) {
        RestAPI.connector.update("replace into `property` values (?, ?, ?)", key.getKey(), key.getValue() == null ? "NULL": key.getValue().toString(), value);
    }

    public HashMap<Integer, String> getAllByOwner(final @Nullable UUID owner) {
        return RestAPI.connector.query("select `id`, `value` from `property` where `uId`=?;", rs -> {
            final HashMap<Integer, String> map = new HashMap<>();

            while(rs.next()) {
                map.put(rs.getInt("id"), rs.getString("value"));
            }

            return map;
        }, owner == null ? "NULL": owner.toString());
    }

    @Override
    public String get(final Map.Entry<Integer, UUID> key) {
        return RestAPI.connector.query("select `value` from `property` where `id`=? and `uId`=?;", rs -> {
            if(!rs.next()) return null;

            return rs.getString("value");
        }, key.getKey(), key.getValue() == null ? "NULL": key.getValue().toString());
    }

    @Override
    public void delete(final Map.Entry<Integer, UUID> key) {
        RestAPI.connector.update("delete from `property` where `id`=? and `uId`=?;", key.getKey(), key.getValue() == null ? "NULL": key.getValue().toString());
    }
}
