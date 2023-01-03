package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.repository.PropertyKeyRepository;
import net.enderstone.api.repository.PropertyRepository;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PropertyService extends GlobalBean {

    private final PropertyKeyRepository keyRepository;
    private final PropertyRepository propertyRepository;
    private final ICache<String, Integer> keyCache;

    public PropertyService(final PropertyRepository propertyRepository, final PropertyKeyRepository keyRepository) {
        this.keyRepository = keyRepository;
        this.propertyRepository = propertyRepository;

        this.keyCache = CacheBuilder.<String, Integer>build("propertyKeys")
                .setLifetime(20L, TimeUnit.MINUTES)
                .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                .setSupplier(keyRepository::get)
                .setWriter((k, v) -> new HeapReference<>(v))
                .setMaxSize(20)
                .create();
    }

    public int registerIdentifier(final String identifier) {
        keyRepository.insert(identifier, null);
        return keyRepository.get(identifier);
    }

    public <T> AbstractProperty<T> getSystemProperty(final PropertyKey<T> propertyKey) {
        return getProperty(propertyKey, null);
    }

    public <T> AbstractProperty<T> getProperty(final PropertyKey<T> propertyKey, final @Nullable UUID owner) {
        Integer key = keyCache.get(propertyKey.identifier());
        if(key == null) {
            key = registerIdentifier(propertyKey.identifier());
            keyCache.set(propertyKey.identifier(), key);
        }

        final String value = propertyRepository.get(new SimpleEntry<>(key, owner));
        final AbstractProperty<T> property = propertyKey.supplier().apply(propertyKey);
        property.fromString(value);

        return property;
    }

}
