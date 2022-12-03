package net.enderstone.api.annotations;

import com.bethibande.web.context.ServerContext;
import com.bethibande.web.processors.AnnotationProcessor;

import java.lang.reflect.Executable;

public class ParameterAnnotationProcessor extends AnnotationProcessor<Parameter> {

    public ParameterAnnotationProcessor() {
        super(Parameter.class, true);
    }

    @Override
    public Object accept(ServerContext serverContext, Parameter parameter, Executable executable, java.lang.reflect.Parameter parameter2) {
        return serverContext.request().getUri().getPath().split("/")[parameter.value() + 1];
    }
}
