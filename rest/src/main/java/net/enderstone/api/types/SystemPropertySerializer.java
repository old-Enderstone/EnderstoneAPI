package net.enderstone.api.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.enderstone.api.common.properties.IProperty;

import java.lang.reflect.Type;

public class SystemPropertySerializer implements JsonSerializer<IProperty<?>> {

    @Override
    public JsonElement serialize(IProperty<?> src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("key", src.getKey().toString());
        obj.addProperty("value", src.get().toString());
        return obj;
    }
}
