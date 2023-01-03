package net.enderstone.api.tasks;

import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.debug.CacheDumpGenerator;

import java.io.File;
import java.io.IOException;

public class CacheDumpTask implements Runnable {

    public static final File ROOT_DIR = new File("./dumps/");

    private final ICache<?, ?> cache;
    private final File out;

    public CacheDumpTask(final ICache<?, ?> cache, final File out) {
        this.cache = cache;
        this.out = out;
    }

    @Override
    public void run() {
        if(!ROOT_DIR.exists()) ROOT_DIR.mkdir();

        try {
            CacheDumpGenerator.dumpCache(cache, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
