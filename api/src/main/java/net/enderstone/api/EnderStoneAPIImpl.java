package net.enderstone.api;

import net.enderstone.api.repository.PlayerRepository;

public class EnderStoneAPIImpl extends EnderStoneAPI {

    public EnderStoneAPIImpl() {
        super();
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

}
