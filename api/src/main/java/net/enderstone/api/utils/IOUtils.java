package net.enderstone.api.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class IOUtils {

    public static Gson gson = new Gson();

    public static <T> T getJson(String url, Class<T> type) {
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
            return gson.fromJson(new String(bytes, StandardCharsets.UTF_8), type);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
