package net.enderstone.api.repository;

import net.enderstone.api.common.EPlayer;
import net.enderstone.api.impl.EPlayerImpl;
import net.enderstone.api.tests.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerRepositoryTest {

    public static final UUID PLAYER_A = UUID.randomUUID();
    public static final UUID PLAYER_B = UUID.randomUUID();

    private PlayerRepository repository;

    @BeforeAll
    void setup() {
        TestUtil.before();
        repository = new PlayerRepository(null);
    }

    @AfterAll
    void cleanUp() {
        TestUtil.after();
    }

    private EPlayer createPlayer(final UUID id, final String name) {
        return new EPlayerImpl(id, name, null, null);
    }

    @Test
    @Order(1)
    void insert() {
        final boolean res1 = repository.hasKey(PLAYER_A);
        final boolean res2 = repository.hasKey(PLAYER_B);

        repository.insert(PLAYER_A, createPlayer(PLAYER_A, "p_a"));

        final boolean res3 = repository.hasKey(PLAYER_A);
        final boolean res4 = repository.hasKey(PLAYER_B);

        repository.insert(PLAYER_B, createPlayer(PLAYER_B, "p_b"));

        final boolean res5 = repository.hasKey(PLAYER_A);
        final boolean res6 = repository.hasKey(PLAYER_B);

        assertFalse(res1);
        assertFalse(res2);
        assertTrue(res3);
        assertFalse(res4);
        assertTrue(res5);
        assertTrue(res6);
    }

    @Test
    @Order(2)
    void nameToId() {
        final Collection<UUID> res1 = repository.nameToUUID("p_a");
        final Collection<UUID> res2 = repository.nameToUUID("p_b");
        final Collection<UUID> res3 = repository.nameToUUID("p_c");

        assertNotNull(res1);
        assertNotNull(res2);
        assertNotNull(res3);
        assertEquals(1, res1.size());
        assertEquals(1, res2.size());
        assertEquals(0, res3.size());
        assertEquals(PLAYER_A, res1.iterator().next());
        assertEquals(PLAYER_B, res2.iterator().next());
    }

    @Test
    @Order(3)
    void update() {
        repository.update(PLAYER_B, createPlayer(PLAYER_B, "p_c"));
        final Collection<UUID> res1 = repository.nameToUUID("p_c");

        assertNotNull(res1);
        assertEquals(1, res1.size());
        assertEquals(PLAYER_B, res1.iterator().next());
    }

    @Test
    @Order(4)
    void get() {
        final EPlayer res1 = repository.get(PLAYER_A);
        final EPlayer res2 = repository.get(PLAYER_B);
        final EPlayer res3 = repository.get(UUID.randomUUID());

        assertNotNull(res1);
        assertNotNull(res2);
        assertNull(res3);
        assertEquals(PLAYER_A, res1.getId());
        assertEquals(PLAYER_B, res2.getId());
        assertEquals("p_a", res1.getLastKnownName());
        assertEquals("p_c", res2.getLastKnownName());
    }

    @Test
    @Order(5)
    void delete() {
        final boolean res1 = repository.hasKey(PLAYER_A);
        final boolean res2 = repository.hasKey(PLAYER_B);

        repository.delete(PLAYER_A);

        final boolean res3 = repository.hasKey(PLAYER_A);
        final boolean res4 = repository.hasKey(PLAYER_B);

        assertTrue(res1);
        assertTrue(res2);
        assertFalse(res3);
        assertTrue(res4);
    }

}
