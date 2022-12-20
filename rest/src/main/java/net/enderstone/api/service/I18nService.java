package net.enderstone.api.service;

import com.bethibande.web.beans.GlobalBean;
import net.enderstone.api.common.i18n.Translation;
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
