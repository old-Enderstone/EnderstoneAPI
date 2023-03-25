package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.tests.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.security.cert.CertificateParsingException;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PropertyRepositoryTest { // TODO: test system properties, e.g. Properties with a null UUID

    private PropertyRepository repository;

    public static final UUID PLAYER_A = UUID.randomUUID();
    public static final UUID PLAYER_B = UUID.randomUUID();

    public static final int PROP_A = 1;
    public static final int PROP_B = 2;

    public static final Map.Entry<Integer, UUID> KEY_A = new SimpleEntry<>(PROP_A, PLAYER_A);
    public static final Map.Entry<Integer, UUID> KEY_B = new SimpleEntry<>(PROP_B, PLAYER_A);
    public static final Map.Entry<Integer, UUID> KEY_C = new SimpleEntry<>(PROP_A, PLAYER_B);

    @BeforeAll
    void setup() {
        TestUtil.before();
        repository = new PropertyRepository();

        RestAPI.connector.update("insert into `propertyIdentifiers`(label) values ('prop_a'), ('prop_b');");
        RestAPI.connector.update(
                "insert into `Player` values (?, ?), (?, ?);",
                PLAYER_A,
                "p_a",
                PLAYER_B,
                "p_b"
        );
    }

    @AfterAll
    void cleanUp() {
        TestUtil.after();
    }

    @Test
    @Order(1)
    void test1() {
        final boolean res1 = repository.hasKey(KEY_A);
        final boolean res2 = repository.hasKey(KEY_B);
        final boolean res3 = repository.hasKey(KEY_C);

        repository.insert(KEY_A, "val1");

        final boolean res4 = repository.hasKey(KEY_A);
        final boolean res5 = repository.hasKey(KEY_B);
        final boolean res6 = repository.hasKey(KEY_C);

        repository.insert(KEY_B, "val2");
        repository.insert(KEY_C, null);

        final boolean res7 = repository.hasKey(KEY_A);
        final boolean res8 = repository.hasKey(KEY_B);
        final boolean res9 = repository.hasKey(KEY_C);

        assertFalse(res1);
        assertFalse(res2);
        assertFalse(res3);
        assertTrue(res4);
        assertFalse(res5);
        assertFalse(res6);
        assertTrue(res7);
        assertTrue(res8);
        assertTrue(res9);
    }

    @Test
    @Order(2)
    void get() {
        final String res1 = repository.get(KEY_A);
        final String res2 = repository.get(KEY_B);
        final String res3 = repository.get(KEY_C);

        assertNotNull(res1);
        assertNotNull(res2);
        assertNull(res3);
        assertEquals("val1", res1);
        assertEquals("val2", res2);
    }

    @Test
    @Order(3)
    void update() {
        repository.update(KEY_A, "val3");
        final String res1 = repository.get(KEY_A);

        assertNotNull(res1);
        assertEquals("val3", res1);
    }

    @Test
    @Order(4)
    void delete() {
        final boolean res1 = repository.hasKey(KEY_A);
        final boolean res2 = repository.hasKey(KEY_B);
        final boolean res3 = repository.hasKey(KEY_C);

        repository.delete(KEY_A);

        final boolean res4 = repository.hasKey(KEY_A);
        final boolean res5 = repository.hasKey(KEY_B);
        final boolean res6 = repository.hasKey(KEY_C);

        assertTrue(res1);
        assertTrue(res2);
        assertTrue(res3);
        assertFalse(res4);
        assertTrue(res5);
        assertTrue(res6);
    }

}
