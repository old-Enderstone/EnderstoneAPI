package net.enderstone.api;

import com.bethibande.web.JWebServer;
import com.bethibande.web.context.ServerContext;
import com.bethibande.web.response.RequestResponse;
import com.bethibande.web.sessions.Session;
import com.bethibande.web.types.ServerInterface;
import com.bethibande.web.types.WebRequest;
import net.enderstone.api.common.types.Message;

public class ApiContext extends ServerContext {

    public ApiContext(final JWebServer server,
                      final ServerInterface _interface,
                      final Session session,
                      final WebRequest request) {
        super(server, _interface, session, request);
    }

    /**
     * @return Response with status code 400, content data = message {id: 400, message: "An entry with the specified key already exists."}
     */
    public RequestResponse entityAlreadyExistsMessage() {
        return new RequestResponse()
                .withContentData(new Message(400, "An entity with the specified key already exists."))
                .withStatusCode(400);
    }

    /**
     * @return Response with status code 400, content data = message {id: 400, message: "Invalid Parameter: %s"}
     */
    public RequestResponse invalidParameterMessage(final String parameter) {
        return new RequestResponse()
                .withContentData(new Message(400, String.format("Invalid Parameter: %s", parameter)))
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
