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
    public boolean hasKey(UUID key) {
        return RestAPI.connector.query("select `bId` from `bundles` where `bId`=?;", ResultSet::next, key.toString());
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
        return RestAPI.connector.query("select `tKey` from `bundles` where `bId`=? limit 1;", rs -> {
            if(!rs.next()) return null;

            return new TranslationBundleItem(key, rs.getString("tKey"));
        }, key.toString());
    }

    /**
     * Returns a list of all translation keys belonging to the given bundle
     */
    public List<String> getTranslationsOfBundle(UUID key) {
        return RestAPI.connector.query("select `tKey` from `bundles` where `bId`=?;", rs -> {
            final List<String> translations = new ArrayList<>();

            while (rs.next()) {
                translations.add(rs.getString("tKey"));
            }

            return translations;
        }, key.toString());
    }

    @Override
    public void delete(UUID key) {
        RestAPI.connector.update("delete from `bundles` where `bId`=?;", key.toString());
    }
}
