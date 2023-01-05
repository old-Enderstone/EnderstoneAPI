package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.Properties;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.properties.impl.ArrayProperty;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.repository.PropertyKeyRepository;
import net.enderstone.api.repository.PropertyRepository;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PropertyService extends GlobalBean {

    private final PropertyKeyRepository keyRepository;
    private final PropertyRepository propertyRepository;
    private final ICache<String, Integer> keyCache;
    private final ICache<Integer, String> mirrorCache;

    public PropertyService(final PropertyRepository propertyRepository, final PropertyKeyRepository keyRepository) {
        this.keyRepository = keyRepository;
        this.propertyRepository = propertyRepository;

        this.keyCache = CacheBuilder.<String, Integer>build("propertyKeys")
                .setStorageType(StorageType.HEAP)
                .setLifetime(20L, TimeUnit.MINUTES)
                .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                .setSupplier(keyRepository::get)
                .setWriter((k, v) -> new HeapReference<>(v))
                .setMaxSize(20)
                .create();

        mirrorCache = CacheBuilder.<Integer, String>build("propertyKeysMirror")
                .setStorageType(StorageType.HEAP)
                .setLifetime(20L, TimeUnit.MINUTES)
                .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                .setSupplier(keyRepository::getIdentifier)
                .setWriter((k, v) -> new HeapReference<>(v))
                .setMaxSize(20)
                .create();

        Properties.registry.setOnUpdate(this::onUpdate);
    }

    private void onUpdate(final AbstractProperty<?> property) {
        if(property instanceof ArrayProperty<?>) return;

        final int key = keyCache.get(property.getKey().identifier());
        propertyRepository.update(new SimpleEntry<>(key, property.getOwner()), property.asString());
    }

    public int registerIdentifier(final String identifier) {
        keyRepository.insert(identifier, null);
        return keyRepository.get(identifier);
    }

    public <T> AbstractProperty<T> getSystemProperty(final PropertyKey<T> propertyKey) {
        return getProperty(propertyKey, null);
    }

    public PropertyKey<?> getKeyByIdentifier(final String identifier) {
        return Properties.registry.getKeyByIdentifier(identifier);
    }

    @SuppressWarnings("unchecked")
    public List<AbstractProperty<?>> getPropertiesByOwner(final @Nullable UUID owner) {
        final HashMap<Integer, String> values = propertyRepository.getAllByOwner(owner);
        final List<AbstractProperty<?>> properties = new ArrayList<>(values.size());

        for(Map.Entry<Integer, String> entry : values.entrySet()) {
            final String identifier = mirrorCache.get(entry.getKey());
            final PropertyKey<Object> propertyKey = (PropertyKey<Object>) getKeyByIdentifier(identifier);

            final AbstractProperty<?> property = propertyKey.supplier().apply(propertyKey);
            property.setOwner(owner);
            property.fromString(entry.getValue());

            properties.add(property);
        }

        return properties;
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
        property.setOwner(owner);

        return property;
    }

}
