package net.enderstone.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {

    public static <T> T readJson(File file, Class<T> type) {
        try {
            return new Gson().fromJson(Files.readString(file.toPath()), type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeJson(Object obj, File file) {
        try {
            Files.writeString(file.toPath(), new GsonBuilder().setPrettyPrinting().create().toJson(obj));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
