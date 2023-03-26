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

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PropertyRepositoryTest {

    private PropertyRepository repository;

    public static final UUID PLAYER_A = UUID.randomUUID();
    public static final UUID PLAYER_B = UUID.randomUUID();

    public static final int PROP_A = 1;
    public static final int PROP_B = 2;

    public static final Map.Entry<Integer, UUID> KEY_A = new SimpleEntry<>(PROP_A, PLAYER_A);
    public static final Map.Entry<Integer, UUID> KEY_B = new SimpleEntry<>(PROP_B, PLAYER_A);
    public static final Map.Entry<Integer, UUID> KEY_C = new SimpleEntry<>(PROP_A, PLAYER_B);
    // System/Server property
    public static final Map.Entry<Integer, UUID> KEY_D = new SimpleEntry<>(PROP_A, null);

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
        final boolean res01 = repository.hasKey(KEY_A);
        final boolean res02 = repository.hasKey(KEY_B);
        final boolean res03 = repository.hasKey(KEY_C);
        final boolean res04 = repository.hasKey(KEY_D);

        repository.insert(KEY_A, "val1");

        final boolean res05 = repository.hasKey(KEY_A);
        final boolean res06 = repository.hasKey(KEY_B);
        final boolean res07 = repository.hasKey(KEY_C);
        final boolean res08 = repository.hasKey(KEY_D);

        repository.insert(KEY_B, "val2");
        repository.insert(KEY_C, null);
        repository.insert(KEY_D, "val3");

        final boolean res09 = repository.hasKey(KEY_A);
        final boolean res10 = repository.hasKey(KEY_B);
        final boolean res11 = repository.hasKey(KEY_C);
        final boolean res12 = repository.hasKey(KEY_D);

        assertFalse(res01);
        assertFalse(res02);
        assertFalse(res03);
        assertFalse(res04);
        assertTrue(res05);
        assertFalse(res06);
        assertFalse(res07);
        assertFalse(res08);
        assertTrue(res09);
        assertTrue(res10);
        assertTrue(res11);
        assertTrue(res12);
    }

    @Test
    @Order(2)
    void get() {
        final String res1 = repository.get(KEY_A);
        final String res2 = repository.get(KEY_B);
        final String res3 = repository.get(KEY_C);
        final String res4 = repository.get(KEY_D);

        assertNotNull(res1);
        assertNotNull(res2);
        assertNull(res3);
        assertNotNull(res4);
        assertEquals("val1", res1);
        assertEquals("val2", res2);
        assertEquals("val3", res4);
    }

    @Test
    @Order(3)
    void update() {
        repository.update(KEY_A, "val3");
        repository.update(KEY_D, "val4");

        final String res1 = repository.get(KEY_A);
        final String res2 = repository.get(KEY_D);

        assertNotNull(res1);
        assertNotNull(res2);
        assertEquals("val3", res1);
        assertEquals("val4", res2);
    }

    @Test
    @Order(4)
    void delete() {
        final boolean res1 = repository.hasKey(KEY_A);
        final boolean res2 = repository.hasKey(KEY_B);
        final boolean res3 = repository.hasKey(KEY_C);
        final boolean res4 = repository.hasKey(KEY_D);

        repository.delete(KEY_A);

        final boolean res5 = repository.hasKey(KEY_A);
        final boolean res6 = repository.hasKey(KEY_B);
        final boolean res7 = repository.hasKey(KEY_C);
        final boolean res8 = repository.hasKey(KEY_D);

        assertTrue(res1);
        assertTrue(res2);
        assertTrue(res3);
        assertTrue(res4);
        assertFalse(res5);
        assertTrue(res6);
        assertTrue(res7);
        assertTrue(res8);
    }

}
