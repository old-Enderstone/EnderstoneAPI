package net.enderstone.api;

import com.bethibande.web.JWebServer;
import com.bethibande.web.context.ServerContext;
import com.bethibande.web.response.RequestResponse;
import com.bethibande.web.sessions.Session;
import com.bethibande.web.types.WebRequest;
import net.enderstone.api.common.types.Message;

public class ApiContext extends ServerContext {

    public ApiContext(final JWebServer server, final Session session, final WebRequest request) {
        super(server, session, request);
    }

    /**
     * @return Response with status code 400, content data = message {id: 400, message: "An entry with the specified key already exists."}
     */
    public RequestResponse entryAlreadyExistsMessage() {
        return new RequestResponse()
                .withContentData(new Message(400, "An entry with the specified key already exists."))
                .withStatusCode(400);
    }

    /**
     * @return Response with status code 400, content data = message {id: 400, message: "Invalid Property type."}
     */
    public RequestResponse invalidPropertyMessage() {
        return new RequestResponse()
                .withContentData(new Message(400, "Invalid Property type."))
                .withStatusCode(400);
    }

    public RequestResponse entityNotFoundMessage() {
        return new RequestResponse()
                .withContentData(new Message(404, "Entity Not Found."))
                .withStatusCode(404);
    }

}
