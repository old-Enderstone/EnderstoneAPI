package net.enderstone.api.repository;

import net.enderstone.api.tests.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PropertyKeyRepositoryTest {

    public static final String KEY_A = "prop_a";
    public static final String KEY_B = "prop_b";

    private static final int ID_A = 1;
    private static final int ID_B = 2;


    private PropertyKeyRepository repository;

    @BeforeAll
    void setup() {
        TestUtil.before();
        repository = new PropertyKeyRepository();
    }

    @AfterAll
    void cleanUp() {
        TestUtil.after();
    }

    @Test
    @Order(1)
    void create() {
        final boolean res1 = repository.hasKey(KEY_A);
        final boolean res2 = repository.hasKey(KEY_B);

        final int res3 = repository.create(KEY_A);

        final boolean res4 = repository.hasKey(KEY_A);
        final boolean res5 = repository.hasKey(KEY_B);

        final int res6 = repository.create(KEY_B);

        final boolean res7 = repository.hasKey(KEY_A);
        final boolean res8 = repository.hasKey(KEY_B);

        assertEquals(ID_A, res3);
        assertEquals(ID_B, res6);
        assertFalse(res1);
        assertFalse(res2);
        assertTrue(res4);
        assertFalse(res5);
        assertTrue(res7);
        assertTrue(res8);
    }

    @Test
    @Order(2)
    void insert() {
        assertThrows(UnsupportedOperationException.class, () -> {
            repository.insert(KEY_A, 1);
        });

    }

    @Test
    @Order(3)
    void update() {
        assertThrows(UnsupportedOperationException.class, () -> {
            repository.update(KEY_A, 2);
        });
    }

    @Test
    @Order(4)
    void getIdentifier() {
        final String res1 = repository.getIdentifier(ID_A);
        final String res2 = repository.getIdentifier(ID_B);

        assertNotNull(res1);
        assertNotNull(res2);
        assertEquals(KEY_A, res1);
        assertEquals(KEY_B, res2);
    }

    @Test
    @Order(5)
    void get() {
        final int res1 = repository.get(KEY_A);
        final int res2 = repository.get(KEY_B);

        assertEquals(ID_A, res1);
        assertEquals(ID_B, res2);
    }

    @Test
    @Order(6)
    void delete() {
        final boolean res1 = repository.hasKey(KEY_A);
        final boolean res2 = repository.hasKey(KEY_B);

        repository.delete(KEY_A);

        final boolean res3 = repository.hasKey(KEY_A);
        final boolean res4 = repository.hasKey(KEY_B);

        assertTrue(res1);
        assertTrue(res2);
        assertFalse(res3);
        assertTrue(res4);
    }

}
