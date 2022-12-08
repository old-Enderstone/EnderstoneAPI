package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import com.bethibande.web.response.RequestResponse;
import net.enderstone.api.ApiContext;
import net.enderstone.api.Main;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.Player;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;

import java.util.UUID;

public class PlayerHandler {

    @URI(value = "/create/player/" + Regex.UUID + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object createPlayer(@Parameter(2) String uId,
                               @Parameter(3) String name,
                               ApiContext context) {
        final UUID uuid = UUID.fromString(uId);

        if(Main.playerService.playerExists(uuid)) {
            return context.entryAlreadyExistsMessage();
        }

        Main.playerService.createPlayer(uuid, name);
        return Main.playerService.getPlayerById(uuid);
    }

    @URI(value = "/get/player/" + Regex.UUID, type = URI.URIType.REGEX)
    public Object getPlayer(@Parameter(2) String uId) {
        Player player = Main.playerService.getPlayerById(UUID.fromString(uId));
        if(player != null) return player;

        return new RequestResponse()
                .withStatusCode(404)
                .withContentData(new Message(404, "Player not found"));
    }

}
