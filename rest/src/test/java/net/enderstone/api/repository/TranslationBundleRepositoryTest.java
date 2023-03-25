package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.i18n.TranslationBundleItem;
import net.enderstone.api.tests.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TranslationBundleRepositoryTest {

    public static UUID KEY_A = UUID.randomUUID();
    public static UUID KEY_B = UUID.randomUUID();
    public static UUID KEY_C = UUID.randomUUID();

    public static final String TRANSLATION_A = "key_a";
    public static final String TRANSLATION_B = "key_b";

    private TranslationBundleRepository repository;

    @BeforeAll
    void setup() {
        TestUtil.before();
        repository = new TranslationBundleRepository();
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

        RestAPI.connector.update(
                "insert into `translations` values (?, ?, ?), (?, ?, ?);",
                TRANSLATION_A,
                "de",
                "v",
                TRANSLATION_B,
                "de",
                "v"
        );

        repository.insert(KEY_A, new TranslationBundleItem(null, TRANSLATION_A));
        repository.insert(KEY_A, new TranslationBundleItem(null, TRANSLATION_B));

        final boolean res3 = repository.hasKey(KEY_A);
        final boolean res4 = repository.hasKey(KEY_B);

        repository.insert(KEY_B, new TranslationBundleItem(null, TRANSLATION_A));

        final boolean res5 = repository.hasKey(KEY_A);
        final boolean res6 = repository.hasKey(KEY_B);

        assertFalse(res1);
        assertFalse(res2);
        assertTrue(res3);
        assertFalse(res4);
        assertTrue(res5);
        assertTrue(res6);
    }

    @Test
    @Order(2)
    void update() {
        assertThrows(UnsupportedOperationException.class, () -> {
            repository.update(KEY_A, new TranslationBundleItem(KEY_A, TRANSLATION_A));
        });
    }

    @Test
    @Order(3)
    void get() {
        final TranslationBundleItem res1 = repository.get(KEY_A);
        final TranslationBundleItem res2 = repository.get(KEY_B);

        assertNotNull(res1);
        assertNotNull(res2);
        assertEquals(KEY_A, res1.getBundleId());
        assertEquals(KEY_B, res2.getBundleId());
        assertEquals(TRANSLATION_A, res1.getTranslationKey());
        assertEquals(TRANSLATION_A, res2.getTranslationKey());
    }

    @Test
    @Order(4)
    void getAll() {
        final List<String> res1 = repository.getTranslationsOfBundle(KEY_A);
        final List<String> res2 = repository.getTranslationsOfBundle(KEY_B);
        final List<String> res3 = repository.getTranslationsOfBundle(KEY_C);

        assertEquals(2, res1.size());
        assertEquals(1, res2.size());
        assertEquals(0, res3.size());

        assertEquals(TRANSLATION_A, res1.get(0));
        assertEquals(TRANSLATION_B, res1.get(1));
        assertEquals(TRANSLATION_A, res2.get(0));
    }

    @Test
    @Order(5)
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
