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
        if(key.getValue() == null) {
            final ResultSet rs = RestAPI.connector.query("select `id` from `property` where `id`=? and `uId`='NULL';", key.getKey());
            try {
                return rs.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        final ResultSet rs = RestAPI.connector.query("select `id` from `property` where `id`=? and `uId`=?;", key.getKey(), key.getValue().toString());
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(final Map.Entry<Integer, UUID> key, final String value) {
        RestAPI.connector.update("insert into `property` values (?, ?, ?);", key.getKey(), key.getValue().toString(), value);
    }

    @Override
    public void update(final Map.Entry<Integer, UUID> key, final String value) {
        if(key.getValue() == null) {
            RestAPI.connector.update("replace into `property` values (?, 'NULL', ?);", key.getKey(), value);
            return;
        }
        RestAPI.connector.update("replace into `property` values (?, ?, ?);", key.getKey(), key.getValue().toString(), value);
    }

    public HashMap<Integer, String> getAllByOwner(final @Nullable UUID owner) {
        final HashMap<Integer, String> map = new HashMap<>();
        final ResultSet rs;
        if(owner == null) {
            rs = RestAPI.connector.query("select `id`, `value` from `property` where `uId`='NULL';");
        } else {
            rs = RestAPI.connector.query("select `id`, `value` from `property` where `uId`=?;", owner.toString());
        }

        try {
            while(rs.next()) {
                final String value = rs.getString("value");
                map.put(rs.getInt("id"), value.equals("NULL") ? null: value);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

    @Override
    public String get(final Map.Entry<Integer, UUID> key) {
        if(key.getValue() == null) {
            final ResultSet rs = RestAPI.connector.query("select `value` from `property` where `id`=? and `uId`='NULL';", key.getKey());
            try {
                if(!rs.next()) return null;

                final String value = rs.getString("value");
                return value.equals("NULL") ? null: value;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        final ResultSet rs = RestAPI.connector.query("select `value` from `property` where `id`=? and `uId`=?;", key.getKey(), key.getValue().toString());
        try {
            if(!rs.next()) return null;

            final String value = rs.getString("value");
            return value.equals("NULL") ? null: value;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(final Map.Entry<Integer, UUID> key) {
        if(key.getValue() == null) {
            RestAPI.connector.update("delete from `property` where `id`=? and `uId`='NULL';", key.getKey());
            return;
        }
        RestAPI.connector.update("delete from `property` where `id`=? and `uId`=?;", key.getKey(), key.getValue().toString());
    }
}
