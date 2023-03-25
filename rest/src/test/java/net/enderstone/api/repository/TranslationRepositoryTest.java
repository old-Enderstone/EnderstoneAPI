package net.enderstone.api.repository;

import net.enderstone.api.RestAPI;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.tests.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TranslationRepositoryTest {

    public static final Locale LOCALE_A = Locale.GERMAN;
    public static final Locale LOCALE_B = Locale.ENGLISH;

    public static final Map.Entry<String, Locale> TRANSLATION_A = new SimpleEntry<>("key", LOCALE_A);
    public static final Map.Entry<String, Locale> TRANSLATION_B = new SimpleEntry<>("key", LOCALE_B);
    public static final Map.Entry<String, Locale> TRANSLATION_C = new SimpleEntry<>("key2", LOCALE_A);

    public TranslationRepository repository;

    @BeforeAll
    void setup() {
        TestUtil.before();
        repository = new TranslationRepository();
    }
    
    @AfterAll
    void cleanUp() {
        TestUtil.after();
    }
    
    @Test
    @Order(1)
    void test1() {
        final boolean res1 = repository.hasKey(TRANSLATION_A);
        final boolean res2 = repository.hasKey(TRANSLATION_B);

        repository.insert(TRANSLATION_A, new Translation(null, null, "translation_a"));

        final boolean res3 = repository.hasKey(TRANSLATION_A);
        final boolean res4 = repository.hasKey(TRANSLATION_B);

        repository.insert(TRANSLATION_B, new Translation(null, null, "translation_b"));

        final boolean res5 = repository.hasKey(TRANSLATION_A);
        final boolean res6 = repository.hasKey(TRANSLATION_B);

        assertFalse(res1);
        assertFalse(res2);
        assertTrue(res3);
        assertFalse(res4);
        assertTrue(res5);
        assertTrue(res6);
    }

    @Test
    @Order(2)
    void get() {
        final Translation res1 = repository.get(TRANSLATION_A);
        final Translation res2 = repository.get(TRANSLATION_B);

        assertNotNull(res1);
        assertNotNull(res2);
        assertEquals("key", res1.getKey());
        assertEquals("key", res2.getKey());
        assertEquals(LOCALE_A, res1.getLocale());
        assertEquals(LOCALE_B, res2.getLocale());
        assertEquals("translation_a", res1.getTranslation());
        assertEquals("translation_b", res2.getTranslation());
    }

    @Test
    @Order(3)
    void update() {
        repository.update(TRANSLATION_A, new Translation(null, null, "translation_a_a"));
        repository.update(TRANSLATION_B, new Translation(null, null, "translation_b_b"));

        final Translation res1 = repository.get(TRANSLATION_A);
        final Translation res2 = repository.get(TRANSLATION_B);

        assertEquals("translation_a_a", res1.getTranslation());
        assertEquals("translation_b_b", res2.getTranslation());
    }

    @Test
    @Order(4)
    void getAllTranslationOfBundle() {
        final UUID bundleId = UUID.randomUUID();
        final int insert = RestAPI.connector.update("insert into bundles values (?, ?);", bundleId.toString(), TRANSLATION_A.getKey());

        repository.insert(TRANSLATION_C, new Translation(TRANSLATION_C.getKey(), TRANSLATION_C.getValue(), "translation_c"));

        final List<Translation> translations = repository.getAllTranslationOfBundle(LOCALE_A, bundleId);

        assertEquals(1, insert);
        assertEquals(1, translations.size());
        assertEquals("key", translations.get(0).getKey());
        assertEquals(LOCALE_A, translations.get(0).getLocale());
        assertEquals("translation_a_a", translations.get(0).getTranslation());
    }

    @Test
    @Order(5)
    void delete() {
        repository.delete(TRANSLATION_A);
        repository.delete(TRANSLATION_B);

        assertNull(repository.get(TRANSLATION_A));
        assertNull(repository.get(TRANSLATION_B));
        assertNotNull(repository.get(TRANSLATION_C));
    }
}