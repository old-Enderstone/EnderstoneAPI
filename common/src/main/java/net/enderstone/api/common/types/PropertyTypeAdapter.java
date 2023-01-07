package net.enderstone.api.common.types;

import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.enderstone.api.common.properties.AbstractProperty;

import java.io.IOException;
import java.util.UUID;

public class PropertyTypeAdapter extends TypeAdapter<AbstractProperty<?>> {

    @Override
    public void write(final JsonWriter out, final AbstractProperty<?> value) throws IOException {
        out.setSerializeNulls(true);
        out.beginObject();

        out.name("identifier");
        out.value(value.getKey().identifier());

        out.name("owner");
        if(value.getOwner() == null) {
            out.nullValue();
        } else out.value(value.getOwner().toString());

        out.name("value");
        if(value.get() == null) {
            out.nullValue();
        } else out.value(value.asString());

        out.endObject();
    }

    @Override
    public AbstractProperty<?> read(final JsonReader in) throws IOException {
        String identifier;
        UUID owner;
        String valueStr;

        String currentName;

        in.beginObject();
        while(in.hasNext()) {
            final JsonToken token = in.peek();

            if(token.equals(JsonToken.NAME)) {
                currentName =  in.nextName();
            }


        }

        return null;
    }
}
