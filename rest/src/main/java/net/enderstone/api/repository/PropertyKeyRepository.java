package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyKeyRepository implements IRepository<String, Integer> {

    @Override
    public boolean hasKey(final String key) {
        final ResultSet rs = RestAPI.connector.query("select `id` from `propertyIdentifiers` where `label`=?;", key);
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(final String key, final @Nullable Integer value) {
        RestAPI.connector.update("insert into `propertyIdentifiers` values (?, ?);", key, value);
    }

    @Override
    public void update(final String key, final Integer value) {
        RestAPI.connector.update("update `propertyIdentifiers` set `id`=? where `label`=?;", value, key);
    }

    public String getIdentifier(final int id) {
        final ResultSet rs = RestAPI.connector.query("select `label` from `propertyIdentifiers` where `id`=?;", id);
        try {
            if(!rs.next()) return null;
            return rs.getString("label");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer get(final String key) {
        final ResultSet rs = RestAPI.connector.query("select `id` from `propertyIdentifiers` where `label`=?;", key);
        try {
            if(!rs.next()) return null;
            return rs.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(final String key) {
        RestAPI.connector.update("delete from `propertyIdentifiers` where `label`=?;", key);
    }
}
