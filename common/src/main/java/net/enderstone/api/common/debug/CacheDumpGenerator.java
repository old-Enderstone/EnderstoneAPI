package net.enderstone.api.common.debug;

import net.enderstone.api.common.cache.*;

import java.io.*;
import java.util.Date;

public class CacheDumpGenerator {

    /**
     * Create a cache dump, will write all info necessary to debug a cache instance to the given file.
     * This includes all the caches settings and all objects and keys stored in the given cache.
     * Beware, this methode will override whatever contents the given file may have.
     * @param cache the cache to dump
     * @param destination the destination file to write the dump to
     */
    public static <K, V> void dumpCache(final ICache<K, V> cache, final File destination) throws IOException {
        PrintWriter writer = new PrintWriter(new FileOutputStream(destination));

        writer.println("Cache dump - " + new Date());
        writer.println();
        writer.println("General Info");
        writer.println("  CacheImpl: " + cache.getClass().getName());
        writer.println("  WriterImpl: " + cache.getWriter().getClass());
        writer.println("  CacheId: " + cache.getId());
        writer.println("  Items in Cache: " + cache.getSize());
        writer.println();
        writer.println("Specifications");
        writer.println("  Has Lifetime: " + (cache instanceof HasLifetime ? "yes": "no"));
        writer.println("  Has Listeners: " + (cache instanceof HasListeners<?,?> ? "yes": "no"));
        writer.println("  HasMaxSize: " + (cache instanceof HasMaxSize ? "yes": "no"));
        writer.println("  Has Supplier: " + (cache instanceof HasSupplier<?,?> ? "yes": "no"));
        writer.println();
        writer.println("Settings");
        if(cache instanceof HasLifetime lifetime) {
            writer.println("  Lifetime: " + lifetime.getItemLifetime());
            writer.println("  Lifetime Type: " + lifetime.getLifetimeType());
        }
        if(cache instanceof HasListeners<?,?> listeners) {
            writer.println("  Set Listener: " + (listeners.getSetListener() == null ? null: listeners.getSetListener().getClass().getName()));
            writer.println("  Remove Listener: " + (listeners.getRemoveListener() == null ? null: listeners.getRemoveListener().getClass().getName()));
        }
        if(cache instanceof HasMaxSize size) {
            writer.println("  Max Size: " + size.getMaxSize());
        }
        if(cache instanceof HasSupplier<?,?> supplier) {
            writer.println("  Supplier: " + (supplier.getSupplier() == null ? null: supplier.getSupplier().getClass().getName()));
        }
        writer.println();
        writer.println("Items [" + cache.getSize() + "]");
        writer.flush();
        for(K key : cache.getAllKeys()) {
            final V value = cache.get(key);
            writer.println("  " + (key == null ? null: key.toString()) + ": " + (value == null ? null: value.toString()));
            writer.flush();
        }
        writer.println();
        writer.println("End Of Dump");
        writer.close();
    }

}
