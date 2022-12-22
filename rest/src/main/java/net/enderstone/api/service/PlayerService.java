package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.impl.EPlayerImpl;
import net.enderstone.api.repository.PlayerRepository;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerService extends GlobalBean {

    private final PlayerRepository repository;
    private final UserPropertyService userPropertyService;

    private final ICache<UUID, EPlayer> playerCache;

    public PlayerService(final PlayerRepository repository, final UserPropertyService userPropertyService) {
        this.repository = repository;
        this.userPropertyService = userPropertyService;

        this.playerCache = CacheBuilder.<UUID, EPlayer>build("players")
                                       .setLifetime(5L, TimeUnit.MINUTES)
                                       .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                                       .setStorageType(StorageType.HEAP)
                                       .setWriter((k, v) -> new HeapReference<>(v))
                                       .setSupplier(repository::get)
                                       .create();
    }

    public boolean playerExists(UUID uuid) {
        return repository.hasKey(uuid);
    }

    public void createPlayer(UUID id, String name) {
        repository.insert(id, new EPlayerImpl(id, name, null, userPropertyService));
    }

    public void saveLastKnownName(EPlayer EPlayer) {
        repository.update(EPlayer.getId(), EPlayer);
    }

    public Collection<UUID> getIdByName(String name) {
        return repository.nameToUUID(name);
    }

    public EPlayer getPlayerById(UUID id) {
        final EPlayer player = playerCache.get(id);
        if(player == null) return null;

        if(player.getProperties().isEmpty()) {
            final Collection<IUserProperty<?>> properties = userPropertyService.getAllUserProperties(id);
            player.setProperties(properties);
        }

        return player;
    }

    public void deletePlayer(UUID id) {
        repository.delete(id);
    }

}
