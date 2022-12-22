package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.impl.EPlayerImpl;
import net.enderstone.api.repository.PlayerRepository;

import java.util.Collection;
import java.util.UUID;

public class PlayerService extends GlobalBean {

    private final PlayerRepository repository;
    private final UserPropertyService userPropertyService;

    public PlayerService(final PlayerRepository repository, final UserPropertyService userPropertyService) {
        this.repository = repository;
        this.userPropertyService = userPropertyService;
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
        EPlayer EPlayer = repository.get(id);
        if(EPlayer == null) return null;

        final Collection<IUserProperty<?>> properties = userPropertyService.getAllUserProperties(id);
        EPlayer.setProperties(properties);

        return EPlayer;
    }

    public void deletePlayer(UUID id) {
        repository.delete(id);
    }

}
