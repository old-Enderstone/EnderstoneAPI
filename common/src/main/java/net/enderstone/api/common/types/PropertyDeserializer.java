package net.enderstone.api.common.types;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.Properties;
import net.enderstone.api.common.properties.PropertyKey;

import java.lang.reflect.Type;
import java.util.UUID;

public class PropertyDeserializer implements JsonDeserializer<AbstractProperty<?>> {

    @Override
    @SuppressWarnings("unchecked")
    public AbstractProperty<?> deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = (JsonObject) jsonElement;

        final String identifier = jsonObject.get("identifier").getAsString();
        final PropertyKey<Object> key = (PropertyKey<Object>) Properties.registry.getKeyByIdentifier(identifier);
        final AbstractProperty<?> property = key.supplier().apply(key);

        if(jsonObject.has("owner")) {
            final UUID owner = jsonObject.get("owner").isJsonNull() ? null: UUID.fromString(jsonObject.get("owner").getAsString());
            property.setOwner(owner);
        }

        if(jsonObject.has("value")) {
            final String valueStr = jsonObject.isJsonNull() ? null: jsonObject.get("value").getAsString();
            property.fromString(valueStr);
        }

        return property;
    }
}
