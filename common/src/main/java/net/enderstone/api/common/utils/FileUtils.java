package net.enderstone.api.common.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void createFileIfNotExists(final File file) {
        if(file.exists()) return;
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
