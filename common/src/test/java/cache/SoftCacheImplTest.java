package cache;

import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SoftCacheImplTest {

    @Test
    public void cacheTest() {
        final ICache<String, String> cache = CacheBuilder.<String, String>build("cache")
                .setStorageType(StorageType.SOFT_HEAP)
                .create();

        cache.set("a", "a");
        cache.set("b", "b");
        cache.set("c", "c");

        assertEquals(3, cache.getSize());

        cache.remove("a");

        assertEquals(2, cache.getSize());
        assertEquals("b", cache.get("b"));

        cache.clear();

        assertEquals(0, cache.getSize());
    }

}