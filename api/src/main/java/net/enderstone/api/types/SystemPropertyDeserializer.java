package net.enderstone.api.types;

import com.google.gson.*;
import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.SystemProperty;

import java.lang.reflect.Type;

public class SystemPropertyDeserializer implements JsonDeserializer<IProperty<?>> {

    @Override
    public IProperty<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final SystemProperty key = SystemProperty.valueOf(jsonObject.get("key").getAsString());

        return EnderStoneAPI.getInstance().getSystemPropertyFactory().createOfValue(key, jsonObject.get("value").getAsString());
    }
}
