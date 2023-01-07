package net.enderstone.api;

import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.Properties;
import net.enderstone.api.common.properties.PropertyKey;
import net.enderstone.api.common.properties.PropertyKeyRegistry;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.PropertyRepository;

import java.util.UUID;

public abstract class EnderStoneAPI {

    private static EnderStoneAPI instance;

    public static EnderStoneAPI getInstance() {
        if(instance == null) instance = new EnderStoneAPIImpl();
        return instance;
    }

    protected final PropertyRepository propertyRepository = new PropertyRepository(this);
    protected final PlayerRepository playerRepository = new PlayerRepository(this);

    protected final ICache<UUID, EPlayer> playerCache = CacheBuilder.<UUID, EPlayer>build("PlayerCache")
                                                                    .setStorageType(StorageType.SOFT_HEAP)
                                                                    .setWriter((k, v) -> new HeapReference<>(v))
                                                                    .setSupplier(playerRepository::get)
                                                                    .create();

    protected final ICache<PropertyKey<?>, AbstractProperty<?>> propertyCache = CacheBuilder.<PropertyKey<?>, AbstractProperty<?>>build("PropertyCache")
                                                                                            .setStorageType(StorageType.HEAP)
                                                                                            .setWriter((k, v) -> new HeapReference<>(v))
                                                                                            .setSupplier(this::loadSystemProperty)
                                                                                            .create();

    private String baseUrl = "http://api.enderstone.net:4455";

    protected EnderStoneAPI() {
        init();
    }

    private void init() {
        final PropertyKeyRegistry propertyKeyRegistry = getPropertyKeyRegistry();
        propertyKeyRegistry.setOnUpdate(propertyRepository::setProperty);
        propertyKeyRegistry.setOnAdd(propertyRepository::add);
        propertyKeyRegistry.setOnSubtract(propertyRepository::subtract);
        propertyKeyRegistry.setOnDivide(propertyRepository::divide);
        propertyKeyRegistry.setOnMultiply(propertyRepository::multiply);


        String host = System.getProperty("net.enderstone.api.host");
        if("debug".equalsIgnoreCase(host)) baseUrl = "http://127.0.0.1:4455";
    }

    public PropertyKeyRegistry getPropertyKeyRegistry() {
        return Properties.registry;
    }

    /**
     * Removes given player from cache
     */
    public void unloadPlayer(final UUID uuid) {
        playerCache.remove(uuid);
    }

    @SuppressWarnings("unchecked")
    public <T> AbstractProperty<T> getSystemProperty(final PropertyKey<T> propertyKey) {
        return (AbstractProperty<T>) propertyCache.get(propertyKey);
    }

    private <T> AbstractProperty<T> loadSystemProperty(final PropertyKey<T> propertyKey) {
        final AbstractProperty<T> property = propertyRepository.getProperty(propertyKey, null);
        if(property != null) return property;
        return propertyKey.supplier().apply(propertyKey);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public EPlayer getPlayerById(final UUID id) {
        return playerCache.get(id);
    }

    public EPlayer createPlayer(final UUID id, final String name) {
        final EPlayer EPlayer = playerRepository.create(id, name);
        if(EPlayer == null) return null;

        playerCache.set(id, EPlayer);
        return EPlayer;
    }

}
