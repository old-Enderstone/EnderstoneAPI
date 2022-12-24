package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import net.enderstone.api.common.types.Message;

public class StatusHandler {

    @URI(value = "/status", type = URI.URIType.STRING)
    public Object status() {
        return new Message(200, "GOOD");
    }

}
