package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.i18n.TranslationBundleItem;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.repository.TranslationBundleRepository;
import net.enderstone.api.repository.TranslationRepository;

import java.util.AbstractMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class I18nService extends GlobalBean {

    private final TranslationRepository translationRepository;
    private final TranslationBundleRepository translationBundleRepository;

    public I18nService(TranslationRepository translationRepository, TranslationBundleRepository translationBundleRepository) {
        this.translationRepository = translationRepository;
        this.translationBundleRepository = translationBundleRepository;
    }

    public boolean translationExists(final String key, final Locale locale) {
        return translationRepository.hasKey(new SimpleEntry<>(key, locale));
    }

    public void createEmptyTranslation(final UUID bundle, final String key, final Locale locale) {
        if(getTranslation(key, locale) !=  null) return;
        translationRepository.insert(new SimpleEntry<>(key, locale), new Translation(key, locale, null));
        translationBundleRepository.insert(bundle, new TranslationBundleItem(bundle, key));
    }

    public void updateTranslation(final String key, final Locale locale, final String translation) {
        translationRepository.update(new SimpleEntry<>(key, locale), new Translation(key, locale, translation));
    }

    /**
     * Get a single translation
     */
    public Translation getTranslation(final String key, final Locale locale) {
        return translationRepository.get(new AbstractMap.SimpleImmutableEntry<>(key, locale));
    }

    /**
     * Gets all translations of a given locale that also belong to a given bundle
     */
    public List<Translation> getTranslations(final Locale locale, final UUID bundle) {
        return translationRepository.getAllTranslationOfBundle(locale, bundle);
    }

    /**
     * Returns a list of all translation keys belonging to the given bundle
     */
    public List<String> getTranslationsOfBundle(final UUID bundle) {
        return translationBundleRepository.getTranslationsOfBundle(bundle);
    }

}
