package net.enderstone.api.repository;

import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.Player;
import net.enderstone.api.utils.IOUtils;

import java.util.UUID;

public class PlayerRepository {

    private final EnderStoneAPI api;

    public PlayerRepository(EnderStoneAPI api) {
        this.api = api;
    }

    public Player get(UUID id) {
        return IOUtils.getJson(String.format("%s%s%s", api.getBaseUrl(), "/get/user/", id.toString()), Player.class);
    }

}