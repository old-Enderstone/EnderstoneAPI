package net.enderstone.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.types.TypedMessage;
import net.enderstone.api.types.SystemPropertyDeserializer;
import net.enderstone.api.types.UserPropertyDeserializer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class IOUtils {

    public static Gson gson = new GsonBuilder()
                                  .registerTypeAdapter(IUserProperty.class, new UserPropertyDeserializer())
                                  .registerTypeAdapter(IProperty.class, new SystemPropertyDeserializer())
                                  .create();

    public static <T> T getJson(String url, Class<T> type) {
        return getJson(url, str -> gson.fromJson(str, type));
    }

    public static <T> TypedMessage<T> getTypedMessage(String url, Class<T> type) {
        return getJson(url, str -> gson.fromJson(str, TypedMessage.getType(type)));
    }

    public static <T> T getJson(String url, Type type) {
        return getJson(url, str -> gson.fromJson(str, type));
    }

    public static <T> T getJson(String url, Function<String, T> deserializer) {
        try {
            HttpURLConnection con = (HttpURLConnection) new URI(url).toURL().openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            int code = con.getResponseCode();
            int contentLength = con.getContentLength();
            if(contentLength <= 0 || code < 200 || code > 299) return null;

            InputStream in = con.getInputStream();
            byte[] bytes = in.readNBytes(contentLength);
            return deserializer.apply(new String(bytes, StandardCharsets.UTF_8));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
