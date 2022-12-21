package net.enderstone.api.i18n;

import net.enderstone.api.cache.CacheBuilder;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.impl.FileCacheWriter;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.types.SimpleEntry;

import java.io.File;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class I18NService {

    private final File l2StorageRoot;

    /**
     * Heap Cache
     */
    private final ICache<SimpleEntry<String, Locale>, Translation> l1Cache;

    /**
     * Disk Cache
     */
    private final ICache<SimpleEntry<String, Locale>, Translation> l2Cache;

    public I18NService(File l2StorageRoot) {
        this.l2StorageRoot = l2StorageRoot;

        l2Cache = CacheBuilder.<SimpleEntry<String, Locale>, Translation>build("translations")
                .setStorageType(StorageType.SERIALIZED_FILE)
                .setWriter(new FileCacheWriter<>(l2StorageRoot))
                .create();

        l1Cache = CacheBuilder.<SimpleEntry<String, Locale>, Translation>build("translations")
                .setStorageType(StorageType.HEAP)
                .setWriter((k, v) -> new HeapReference<>(v))
                .setSupplier(l2Cache::get)
                .setMaxSize(100)
                .setLifetime(1L, TimeUnit.MINUTES)
                .create();
    }

    /**
     * Retrieve translation from l1 or l2 storage, translation will be created in db if it doesn't already exit.
     * @param bundle bundle the translation belongs to
     * @param key translation key
     * @param locale locale of the translation you want to load
     */
    public Translation getTranslation(final UUID bundle, final String key, final Locale locale) {
        final Translation translation = l1Cache.get(new SimpleEntry<>(key, locale));
        if(translation == null) {
            //repository.createTranslation(bundle, translation); // TODO: repository
            return new Translation(key, locale, key);
        }

        return translation;
    }

    /**
     * Load all translations of a given bundle using the given locale into l2 storage
     * @param bundle bundle to load
     * @param locale locale to load
     */
    public void loadBundle(final UUID bundle, final Locale locale) { // TODO: repository
        //final Collection<Translation> translations = repository.getBundleTranslations(bundle);
        //translations.forEach(t -> l2Storage.set(t.toEntry(), t));
    }

    public File getL2StorageRoot() {
        return l2StorageRoot;
    }

    public void eraseL2Cache() {
        l2Cache.clear();
    }

}
