package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.jdbc.SQLStatement;
import net.enderstone.api.jdbc.SQLTransaction;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class TranslationRepository implements IMultipleKeyRepository<String, Locale, Translation> {

    @Override
    public boolean hasKey(Map.Entry<String, Locale> key) {
        return RestAPI.connector.query("select `tKey` from `translations` where `tKey`=? and tLocale=?;", ResultSet::next, key.getKey(), key.getValue().toString());
    }

    @Override
    public void insert(Map.Entry<String, Locale> key, Translation value) {
        RestAPI.connector.update("insert into `translations` values (?, ?, ?);", key.getKey(), key.getValue().toString(), value.getTranslation());
    }

    @Override
    public void update(Map.Entry<String, Locale> key, Translation value) {
        RestAPI.connector.update("update `translations` set `tValue`=? where `tKey`=? and `tLocale`=?;", value.getTranslation(), key.getKey(), key.getValue().toString());
    }

    public List<Translation> getAllTranslationOfBundle(Locale locale, UUID bundle) {
        return RestAPI.connector.query("select `t`.`tKey`, `t`.`tValue` from translations t inner join bundles b on b.tKey=t.tKey where b.`bId`=? and t.`tLocale`=?;", rs -> {
            final List<Translation> translations = new ArrayList<>();

            while(rs.next()) {
                translations.add(new Translation(rs.getString("tKey"), locale, rs.getString("tValue")));
            }

            return translations;
        }, bundle.toString(), locale.toString());
    }

    @Override
    public Translation get(Map.Entry<String, Locale> key) {
        return RestAPI.connector.query("select `tValue` from `translations` t where t.`tKey`=? and t.`tLocale`=?;", rs -> {
            if(!rs.next()) return null;

            return new Translation(key.getKey(), key.getValue(), rs.getString("tValue"));
        }, key.getKey(), key.getValue().toString());
    }

    @Override
    public void delete(Map.Entry<String, Locale> key) {
        final SQLTransaction transaction = RestAPI.connector.createEmptyTransaction()
                .withStatement(new SQLStatement("delete from `bundles` where `tKey`=?;", key.getKey()))
                .withStatement(new SQLStatement("delete from `translations` where `tKey`=?;", key.getKey()));
        transaction.transact();
    }
}
