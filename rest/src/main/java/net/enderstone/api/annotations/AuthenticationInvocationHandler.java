package net.enderstone.api.annotations;

import com.bethibande.web.JWebServer;
import com.bethibande.web.processors.MethodInvocationHandlerAdapter;
import com.bethibande.web.response.RequestResponse;
import com.bethibande.web.types.WebRequest;
import net.enderstone.api.common.types.Message;

import java.lang.reflect.Method;

public class AuthenticationInvocationHandler extends MethodInvocationHandlerAdapter {

    @Override
    public void beforeInvocation(Method method, WebRequest request, JWebServer server) {
        if(!method.isAnnotationPresent(Authentication.class)) return;

        final Authentication auth = method.getAnnotation(Authentication.class);

        final String requestAuth = request.getRequestHeaders().getFirst("Authorization");
        if(requestAuth == null || !requestAuth.startsWith("Basic")) {
            request.setResponse(new RequestResponse()
                    .withStatusCode(401)
                    .withContentData(new Message(401, "Unauthorized"))
                    .withHeader("WWW-Authenticate", "Basic realm=\"" + auth.realm() + "\", charset=\"UTF-8\""));
            request.setFinished(true);
            return;
        }

        final String encoded = requestAuth.substring(7); // Base64 encoded username:password
        System.out.println("encoded: " + encoded);
    }
}
