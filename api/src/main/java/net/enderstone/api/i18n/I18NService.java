package net.enderstone.api.i18n;

import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.CacheLifetimeType;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.cache.StorageType;
import net.enderstone.api.common.cache.impl.FileCacheWriter;
import net.enderstone.api.common.cache.ref.HeapReference;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.repository.TranslationRepository;

import java.io.File;
import java.util.Collection;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public abstract class I18NService {

    private final File l2StorageRoot;
    private final TranslationRepository repository;

    /**
     * Heap Cache
     */
    private final ICache<SimpleEntry<String, Locale>, Translation> l1Cache;

    /**
     * Disk Cache
     */
    private final ICache<SimpleEntry<String, Locale>, Translation> l2Cache;

    public I18NService(final File l2StorageRoot, final TranslationRepository repository) {
        this.l2StorageRoot = l2StorageRoot;
        this.repository = repository;

        l2Cache = CacheBuilder.<SimpleEntry<String, Locale>, Translation>build("translations")
                .setStorageType(StorageType.SERIALIZED_FILE)
                .setWriter(new FileCacheWriter<>(l2StorageRoot))
                .setSupplier(k -> repository.getTranslation(k.getKey(), k.getValue()))
                .create();

        l1Cache = CacheBuilder.<SimpleEntry<String, Locale>, Translation>build("translations")
                .setStorageType(StorageType.HEAP)
                .setWriter((k, v) -> new HeapReference<>(v))
                .setSupplier(l2Cache::get)
                .setMaxSize(100)
                .setLifetime(1L, TimeUnit.MINUTES)
                .setLifetimeType(CacheLifetimeType.ON_ACCESS)
                .create();
    }

    protected void updateTranslation(final String key, final Locale locale, final String translation) {
        repository.updateTranslation(key, locale, translation);

        final SimpleEntry<String, Locale> entry = new SimpleEntry<>(key, locale);
        l1Cache.remove(entry);
        l2Cache.remove(entry);
    }

    /**
     * Retrieve translation from l1 or l2 storage, translation will be created in db if it doesn't already exit.
     * @param bundle bundle the translation belongs to
     * @param key translation key
     * @param locale locale of the translation you want to load
     */
    protected Translation getTranslation(final UUID bundle, final String key, final Locale locale) {
        Translation translation = l1Cache.get(new SimpleEntry<>(key, locale));
        if(translation == null) {
            repository.createEmptyTranslation(bundle, key, locale);
            translation = new Translation(key, locale, key);
            l2Cache.set(translation.toEntry(), translation);
            l1Cache.set(translation.toEntry(), translation);
        }

        return translation;
    }

    /**
     * Load all translations of a given bundle using the given locale into l2 storage
     * @param bundle bundle to load
     * @param locale locale to load
     */
    protected void loadBundle(final UUID bundle, final Locale locale) {
        final Collection<Translation> translations = repository.getBundleTranslations(bundle, locale);
        if(translations == null || translations.isEmpty()) return;
        System.out.println("loaded " + translations.size() + " translations.");
        translations.forEach(t -> l2Cache.set(t.toEntry(), t));
    }

    protected File getL2StorageRoot() {
        return l2StorageRoot;
    }

    protected void eraseL2Cache() {
        l2Cache.clear();
    }

}
