package net.enderstone.api.repository;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.impl.EPlayerImpl;
import net.enderstone.api.utils.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerRepository {

    private final EnderStoneAPI api;

    public PlayerRepository(EnderStoneAPI api) {
        this.api = api;
    }

    public void update(final @NotNull EPlayer player) {
        final Message message = IOUtils.getJson(String.format("%s/update/player/%s/%s",
                                                              api.getBaseUrl(),
                                                              player.getId().toString(),
                                                              player.getLastKnownName()),
                                                Message.class);
        if(message == null) throw new RuntimeException("Couldn't update player " + player.getId() + ".");
    }

    public EPlayer get(UUID id) {
        return IOUtils.getJson(String.format("%s%s%s", api.getBaseUrl(), "/get/player/", id.toString()), EPlayerImpl.class);
    }

    public EPlayer create(UUID id, String name) {
        return IOUtils.getJson(String.format("%s/%s/%s/%s", api.getBaseUrl(), "create/player", id.toString(), name), EPlayerImpl.class);
    }

}
