package net.enderstone.api;

import net.enderstone.api.cache.CacheBuilder;
import net.enderstone.api.common.Player;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.impl.types.UserPropertyFactoryImpl;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.UserPropertyRepository;
import net.enderstone.api.types.IUserPropertyFactory;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class EnderStoneAPI {

    private static EnderStoneAPI instance;

    public static EnderStoneAPI getInstance() {
        if(instance == null) instance = new EnderStoneAPI();
        return instance;
    }

    private final PlayerRepository playerRepository = new PlayerRepository(this);
    private final UserPropertyRepository userPropertyRepository = new UserPropertyRepository(this);

    private final ICache<UUID, Player> playerCache = CacheBuilder.<UUID, Player>build("PlayerCache")
                                                                 .setStorageType(StorageType.HEAP)
                                                                 .setWriter((k, v) -> new HeapReference<>(v))
                                                                 .setSupplier(playerRepository::get)
                                                                 .setLifetime(15L, TimeUnit.MINUTES)
                                                                 .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                                                                 .setMaxSize(1000)
                                                                 .create();

    private String baseUrl;

    private IUserPropertyFactory userPropertyFactory = new UserPropertyFactoryImpl(userPropertyRepository);

    private EnderStoneAPI() {
        init();
    }

    private void init() {
        String host = System.getProperty("net.enderstone.api.host");
        if(host == null || host.equals("prod")) baseUrl = "http://api.enderstone.net";

        if(host.equals("debug")) {
            baseUrl = "http://127.0.0.1:4455";
        }
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

    public Player getUserById(UUID id) {
        return playerCache.get(id);
    }

}
