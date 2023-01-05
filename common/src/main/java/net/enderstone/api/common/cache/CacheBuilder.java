package net.enderstone.api.common.cache;

import net.enderstone.api.common.cache.impl.SimpleCacheImpl;
import net.enderstone.api.common.cache.impl.SoftCacheImpl;
import net.enderstone.api.common.utils.Strings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CacheBuilder<K, V> {

    /**
     * A List of all cache instances created by this builder class. Probably going to remove this later though,
     * as this seems like an easy way to create a memory leak.
     */
    public static final List<ICache<?, ?>> caches = new ArrayList<>();

    public static <K, V> CacheBuilder<K, V> build(final @NotNull UUID id) {
        return new CacheBuilder<K, V>(id);
    }

    public static <K, V> CacheBuilder<K, V> build(final @NotNull String name) {
        return new CacheBuilder<K, V>(name);
    }

    private final UUID id;
    private StorageType storageType;

    private Integer maxSize;

    private Long lifetime;
    private CacheLifetimeType lifetimeType;

    private ICacheWriter<K, V> writer;

    private Function<K, V> supplier;

    private BiConsumer<K, V> setListener;
    private BiConsumer<K, V> removeListener;

    private Consumer<K> onCollect;

    protected CacheBuilder(@NotNull UUID id) {
        this.id = id;
    }

    protected CacheBuilder(@NotNull String id) {
        this.id = Strings.nameToUUID(id);
    }

    @SuppressWarnings("unchecked")
    @Contract(value = "->new", pure = true)
    public ICache<K, V> create() {
        if(id == null || storageType == null || writer == null) throw new UnsupportedOperationException("Id, StorageType and Writer must not be null.");

        final ICache<K, V> cache = switch (storageType) {
            case HEAP, SERIALIZED_FILE -> new SimpleCacheImpl<>(id);
            case SOFT_HEAP -> new SoftCacheImpl<>(id);
            default -> throw new UnsupportedOperationException("The specified storage type has not yet been implemented.");
        };

        cache.setWriter(writer);

        if(cache instanceof HasMaxSize sized) {
            sized.setMaxSize(maxSize);
        }

        if(cache instanceof HasLifetime timed && lifetime != null) {
            timed.setItemLifetime(lifetime);
            timed.setLifetimeType(lifetimeType);
        }

        if(cache instanceof HasListeners listen) {
            listen.setRemoveListener(removeListener);
            listen.setSetListener(setListener);
        }

        if(cache instanceof HasSupplier supplied) {
            supplied.setSupplier(supplier);
        }

        if(cache instanceof HasGcListener listener) {
            listener.setOnCollect(onCollect);
        }

        caches.add(cache);
        return cache;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setOnCollect(final Consumer<K> onCollect) {
        this.onCollect = onCollect;
        return this;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setStorageType(StorageType storageType) {
        this.storageType = storageType;
        return this;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setLifetime(Long lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    @Contract("_,_->this")
    public CacheBuilder<K, V> setLifetime(Long lifetime, TimeUnit timeUnit) {
        this.lifetime = timeUnit.toMillis(lifetime);
        return this;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setLifetimeType(CacheLifetimeType lifetimeType) {
        this.lifetimeType = lifetimeType;
        return this;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setWriter(ICacheWriter<K, V> writer) {
        this.writer = writer;
        return this;
    }

    @Contract("_->this")
    public CacheBuilder<K, V> setSupplier(Function<K, V> supplier) {
        this.supplier = supplier;
        return this;
    }

    public CacheBuilder<K, V> setSetListener(BiConsumer<K, V> setListener) {
        this.setListener = setListener;
        return this;
    }

    public CacheBuilder<K, V> setRemoveListener(BiConsumer<K, V> removeListener) {
        this.removeListener = removeListener;
        return this;
    }
}
