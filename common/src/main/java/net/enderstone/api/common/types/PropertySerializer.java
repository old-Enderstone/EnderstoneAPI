package net.enderstone.api.common.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.enderstone.api.common.properties.AbstractProperty;

import java.lang.reflect.Type;

public class PropertySerializer implements JsonSerializer<AbstractProperty<?>> {

    @Override
    public JsonElement serialize(final AbstractProperty<?> abstractProperty, final Type type, final JsonSerializationContext jsonSerializationContext) {
        final JsonObject object = (JsonObject) jsonSerializationContext.serialize(abstractProperty);
        object.addProperty("identifier", abstractProperty.getKey().identifier());
        return object;
    }
}
