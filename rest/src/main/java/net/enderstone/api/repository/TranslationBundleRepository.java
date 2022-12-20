package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.i18n.TranslationBundleItem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TranslationBundleRepository implements IRepository<UUID, TranslationBundleItem> {

    @Override
    public void setupDatabase() {
        RestAPI.connector.update("""
                CREATE TABLE `bundles` (
                  `bId` varchar(36) KEY NOT NULL,
                  `tKey` varchar(128) NOT NULL,
                  PRIMARY KEY(`bId`, `tKey`)
                );""");
    }

    @Override
    public boolean hasKey(UUID key) {
        final ResultSet rs = RestAPI.connector.query("select `bId` from `bundles` where `bId`=?;", key.toString());
        try {
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(UUID key, TranslationBundleItem value) {
        RestAPI.connector.update("insert into `bundles` values (?, ?);", key.toString(), value.getTranslationKey());
    }

    @Override
    public void update(UUID key, TranslationBundleItem value) {
        throw new UnsupportedOperationException("update not supported for translation bundles");
    }

    @Override
    public TranslationBundleItem get(UUID key) {
        final ResultSet rs = RestAPI.connector.query("select `tKey` from `bundles` where `bId`=? limit 1;", key.toString());
        try {
            if(!rs.next()) return null;

            return new TranslationBundleItem(key, rs.getString("tKey"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a list of all translation keys belonging to the given bundle
     */
    public List<String> getTranslationsOfBundle(UUID key) {
        final ResultSet rs = RestAPI.connector.query("select `tKey` from `bundöes` where `bId`=?;", key.toString());
        final List<String> translations = new ArrayList<>();

        try {
            while (rs.next()) {
                translations.add(rs.getString("tKey"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return translations;
    }

    @Override
    public void delete(UUID key) {
        RestAPI.connector.update("delete from `bundles` where `bId`=?;", key.toString());
    }
}
