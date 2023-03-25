package net.enderstone.api.jdbc;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.tests.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConnectorTest {

    @BeforeAll
    void setup() {
        TestUtil.before();
    }

    @AfterAll
    void cleanUp() {
        TestUtil.after();
    }

    @Test
    void update() {
        final int res = RestAPI.connector.update("insert into `translations` values (?, ?, ?)", "key", Locale.GERMAN.toString(), "value");

        assertEquals(1, res);
    }

    @Test
    void query() {
        final Translation res = RestAPI.connector.query("select * from `translations` where `tKey`=? and `tLocale`=?;", rs -> {
            if(!rs.next()) return null;

            return new Translation(rs.getString("tKey"), new Locale(rs.getString("tLocale")), rs.getString("tValue"));
        }, "key", "de");

        assertNotNull(res);
        assertEquals(res.getKey(), "key");
        assertEquals(res.getLocale(), Locale.GERMAN);
        assertEquals(res.getTranslation(), "value");
    }

}
