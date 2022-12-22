package net.enderstone.api.annotations;

import com.bethibande.web.JWebServer;
import com.bethibande.web.processors.MethodInvocationHandlerAdapter;
import com.bethibande.web.response.RequestResponse;
import com.bethibande.web.types.WebRequest;
import net.enderstone.api.RestAPI;
import net.enderstone.api.common.types.Message;

import java.lang.reflect.Method;

public class WhitelistedInvocationHandler extends MethodInvocationHandlerAdapter {

    @Override
    public void beforeInvocation(Method method, WebRequest request, JWebServer server) {
        if(!method.isAnnotationPresent(Whitelisted.class)) return;
        if(!RestAPI.whitelist.use) return;

        final String str = request.getExchange().getRemoteAddress().getAddress().getHostAddress();
        if(!RestAPI.whitelist.whitelist.contains(str)) {
            request.setFinished(true);
            request.setResponse(new RequestResponse()
                    .withContentData(new Message(403, "Not Permitted"))
                    .withStatusCode(404));
        }
    }
}
