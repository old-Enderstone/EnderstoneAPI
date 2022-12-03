package net.enderstone.api.service;

import net.enderstone.api.Main;
import net.enderstone.api.common.Player;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.impl.PlayerImpl;
import net.enderstone.api.repository.PlayerRepository;

import java.util.Collection;
import java.util.UUID;

public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public boolean playerExists(UUID uuid) {
        return repository.hasKey(uuid);
    }

    public void createPlayer(UUID id, String name) {
        repository.insert(id, new PlayerImpl(id, name, null));
    }

    public void saveLastKnownName(Player player) {
        repository.update(player.getId(), player);
    }

    public Collection<UUID> getIdByName(String name) {
        return repository.nameToUUID(name);
    }

    public Player getPlayerById(UUID id) {
        Player player = repository.get(id);
        if(player == null) return null;

        final Collection<IUserProperty<?>> properties = Main.userPropertyService.getAllUserProperties(id);
        player.setProperties(properties);

        return player;
    }

    public void deletePlayer(UUID id) {
        repository.delete(id);
    }

}
