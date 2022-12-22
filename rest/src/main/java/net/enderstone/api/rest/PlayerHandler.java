package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import com.bethibande.web.response.RequestResponse;
import net.enderstone.api.ApiContext;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;
import net.enderstone.api.service.PlayerService;

import java.util.UUID;

public class PlayerHandler {

    @URI(value = "/create/player/" + Regex.UUID + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object createPlayer(final @Parameter(2) String uId,
                               final @Parameter(3) String name,
                               final ApiContext context,
                               final PlayerService playerService) {
        final UUID uuid = UUID.fromString(uId);

        if(playerService.playerExists(uuid)) {
            return context.entityAlreadyExistsMessage();
        }

        playerService.createPlayer(uuid, name);
        return playerService.getPlayerById(uuid);
    }

    @URI(value = "/get/player/" + Regex.UUID, type = URI.URIType.REGEX)
    public Object getPlayer(final @Parameter(2) String uId,
                            final PlayerService playerService) {
        EPlayer EPlayer = playerService.getPlayerById(UUID.fromString(uId));
        if(EPlayer != null) return EPlayer;

        return new RequestResponse()
                .withStatusCode(404)
                .withContentData(new Message(404, "Player not found"));
    }

}
