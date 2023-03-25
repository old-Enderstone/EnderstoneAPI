package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PropertyKeyRepository implements IRepository<String, Integer> {

    @Override
    public boolean hasKey(final String key) {
        return RestAPI.connector.query("select `id` from `propertyIdentifiers` where `label`=?;", ResultSet::next, key);
    }

    public int create(final String key) {
        return RestAPI.connector.query("insert into `propertyIdentifiers`(`label`) values (?) returning `id`;",
                                       rs -> {
                                            if(!rs.next()) return -1;
                                            return rs.getInt("id");
                                       },
                                       key);
    }

    @Override
    public void insert(final String key, final @Nullable Integer value) {
        throw new UnsupportedOperationException("Use PropertyKeyRepository.create(String) instead.");
    }

    @Override
    public void update(final String key, final Integer value) {
        throw new UnsupportedOperationException("Update not supported");
    }

    public String getIdentifier(final int id) {
        return RestAPI.connector.query("select `label` from `propertyIdentifiers` where `id`=?;", rs -> {
            if(!rs.next()) return null;
            return rs.getString("label");
        }, id);
    }

    @Override
    public Integer get(final String key) {
        return RestAPI.connector.query("select `id` from `propertyIdentifiers` where `label`=?;", rs -> {
            if(!rs.next()) return null;
            return rs.getInt("id");
        }, key);
    }

    @Override
    public void delete(final String key) {
        RestAPI.connector.update("delete from `propertyIdentifiers` where `label`=?;", key);
    }
}
