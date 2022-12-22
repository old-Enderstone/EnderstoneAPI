package net.enderstone.api;

import net.enderstone.api.cache.CacheBuilder;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.impl.types.SystemPropertyFactoryImpl;
import net.enderstone.api.impl.types.UserPropertyFactoryImpl;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.SystemPropertyRepository;
import net.enderstone.api.repository.UserPropertyRepository;
import net.enderstone.api.types.ISystemPropertyFactory;
import net.enderstone.api.types.IUserPropertyFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class EnderStoneAPI {

    private static EnderStoneAPI instance;

    public static EnderStoneAPI getInstance() {
        if(instance == null) instance = new EnderStoneAPIImpl();
        return instance;
    }

    protected final PlayerRepository playerRepository = new PlayerRepository(this);
    protected final UserPropertyRepository userPropertyRepository = new UserPropertyRepository(this);
    protected final SystemPropertyRepository systemPropertyRepository = new SystemPropertyRepository(this);

    protected final ICache<UUID, EPlayer> playerCache = CacheBuilder.<UUID, EPlayer>build("PlayerCache")
                                                                 .setStorageType(StorageType.HEAP)
                                                                 .setWriter((k, v) -> new HeapReference<>(v))
                                                                 .setSupplier(playerRepository::get)
                                                                 .setLifetime(15L, TimeUnit.MINUTES)
                                                                 .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                                                                 .setMaxSize(1000)
                                                                 .create();

    protected final ICache<SystemProperty, IProperty<?>> propertyCache = CacheBuilder.<SystemProperty, IProperty<?>>build("PropertyCache")
                                                                                   .setStorageType(StorageType.HEAP)
                                                                                   .setWriter((k, v) -> new HeapReference<>(v))
                                                                                   .setSupplier(this::loadSystemProperty)
                                                                                   .create();

    private String baseUrl;

    protected IUserPropertyFactory userPropertyFactory = new UserPropertyFactoryImpl(userPropertyRepository);
    protected ISystemPropertyFactory systemPropertyFactory = new SystemPropertyFactoryImpl(systemPropertyRepository);

    protected EnderStoneAPI() {
        init();
    }

    private void init() {
        String host = System.getProperty("net.enderstone.api.host");
        if(host == null || host.equals("prod")) baseUrl = "http://api.enderstone.net";

        if("debug".equalsIgnoreCase(host)) {
            baseUrl = "http://127.0.0.1:4455";
        }
    }

    /**
     * Removes given player from cache
     */
    public void unloadPlayer(final UUID uuid) {
        playerCache.remove(uuid);
    }

    public IProperty<?> getSystemProperty(final SystemProperty property) {
        return propertyCache.get(property);
    }

    private IProperty<?> loadSystemProperty(final SystemProperty property) {
        final IProperty<?> systemProperty = systemPropertyRepository.getProperty(property);
        if(systemProperty != null) return systemProperty;
        return systemPropertyFactory.createEmpty(property, systemPropertyRepository);
    }

    public void setSystemPropertyFactory(ISystemPropertyFactory systemPropertyFactory) {
        this.systemPropertyFactory = systemPropertyFactory;
    }

    public ISystemPropertyFactory getSystemPropertyFactory() {
        return systemPropertyFactory;
    }

    public void setUserPropertyFactory(IUserPropertyFactory userPropertyFactory) {
        this.userPropertyFactory = userPropertyFactory;
    }

    public IUserPropertyFactory getUserPropertyFactory() {
        return userPropertyFactory;
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
