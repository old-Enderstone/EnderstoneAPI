package net.enderstone.api.common.types;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public record TypedMessage<T>(int id, T message) {

    public static <T> Type getType(Class<T> type) {
        return TypeToken.getParameterized(TypedMessage.class, type).getType();
    }

}
