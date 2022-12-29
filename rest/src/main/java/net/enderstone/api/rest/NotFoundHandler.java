package net.enderstone.api.rest;

import com.bethibande.web.annotations.URI;
import com.bethibande.web.response.RequestResponse;
import net.enderstone.api.common.types.Message;

public class NotFoundHandler {

    @URI(value = "/", type = URI.URIType.STRING, priority = Integer.MIN_VALUE)
    public Object notFound() {
        return new RequestResponse()
                .withStatusCode(404)
                .withContentData(new Message(404, "Path not found"));
    }

}
