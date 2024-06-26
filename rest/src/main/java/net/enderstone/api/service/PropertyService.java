package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.NumberProperty;
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

    protected final ICache<PropertyKey<?>, AbstractProperty<?>> propertyCache;

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

        this.propertyCache = CacheBuilder.<PropertyKey<?>, AbstractProperty<?>>build("PropertyCache")
                .setStorageType(StorageType.HEAP)
                .setWriter((k, v) -> new HeapReference<>(v))
                .setSupplier(k -> this.getProperty(k, null))
                .create();

        Properties.registry.setOnUpdate(this::onUpdate);
        Properties.registry.setOnAdd(this::add);
        Properties.registry.setOnSubtract(this::subtract);
        Properties.registry.setOnMultiply(this::multiply);
        Properties.registry.setOnDivide(this::divide);
    }

    private <T extends Number> T add(final NumberProperty<T> property, T n) {
        final T result = property.add(property.get(), n);
        property.set(result);
        return result;
    }

    private <T extends Number> T subtract(final NumberProperty<T> property, T n) {
        final T result = property.sub(property.get(), n);
        property.set(result);
        return result;
    }

    private <T extends Number> T multiply(final NumberProperty<T> property, T n) {
        final T result = property.mul(property.get(), n);
        property.set(result);
        return result;
    }

    private <T extends Number> T divide(final NumberProperty<T> property, T n) {
        final T result = property.div(property.get(), n);
        property.set(result);
        return result;
    }

    private void onUpdate(final AbstractProperty<?> property) {
        if(property instanceof ArrayProperty<?>) return;

        final int key = keyCache.get(property.getKey().identifier());
        propertyRepository.update(new SimpleEntry<>(key, property.getOwner()), property.asString());
    }

    public int registerIdentifier(final String identifier) {
        return keyRepository.create(identifier);
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
            if(entry.getValue() != null) property.fromString(entry.getValue());

            properties.add(property);
        }

        return properties;
    }

    @SuppressWarnings("unchecked")
    public <T> AbstractProperty<T> getProperty(final PropertyKey<T> propertyKey, final @Nullable UUID owner) {
        Integer key = keyCache.get(propertyKey.identifier());
        if(key == null) {
            key = registerIdentifier(propertyKey.identifier());
            keyCache.set(propertyKey.identifier(), key);
        }

        if(owner == null) {
            final AbstractProperty<T> property = (AbstractProperty<T>) propertyCache.get(propertyKey);
            if(property != null) return property;
        }

        final String value = propertyRepository.get(new SimpleEntry<>(key, owner));
        final AbstractProperty<T> property = propertyKey.supplier().apply(propertyKey);
        if(value != null) property.fromString(value);
        property.setOwner(owner);

        if(owner == null) {
            propertyCache.set(propertyKey, property);
        }

        return property;
    }

}
