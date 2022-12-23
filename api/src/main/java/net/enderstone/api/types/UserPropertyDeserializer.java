package net.enderstone.api.types;

import com.google.gson.*;
import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;

import java.lang.reflect.Type;
import java.util.UUID;

public class UserPropertyDeserializer implements JsonDeserializer<IUserProperty<?>> {

    @Override
    public IUserProperty<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final String keyStr = jsonObject.get("key").getAsString();
        final UserProperty key = UserProperty.valueOf(keyStr);
        final IUserPropertyFactory factory = EnderStoneAPI.getInstance().getUserPropertyFactory();

        final UUID owner = UUID.fromString(jsonObject.get("owner").getAsString());

        factory.createOfValue(owner, key, jsonObject.get("value").getAsString());

        return null;
    }
}
