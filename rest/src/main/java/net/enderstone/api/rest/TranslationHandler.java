package net.enderstone.api.rest;

import com.bethibande.web.annotations.CacheRequest;
import com.bethibande.web.annotations.URI;
import net.enderstone.api.ApiContext;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.utils.Regex;
import net.enderstone.api.service.I18nService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TranslationHandler {

    @URI(value = "/i18n/get/" + Regex.PROPERTY_VALUE + "/" + Regex.LOCALE, type = URI.URIType.REGEX)
    @CacheRequest(global = true, cacheTime = 10, timeUnit = TimeUnit.MINUTES)
    public Object getTranslation(final @Parameter(2) String key,
                                 final @Parameter(3) String localeStr,
                                 final I18nService i18nService,
                                 final ApiContext context) {
        final Translation translation = i18nService.getTranslation(key, Locale.forLanguageTag(localeStr));
        if(translation == null) return context.entityNotFoundMessage();

        return translation;
    }

    @URI(value = "/i18n/bundle-keys/" + Regex.UUID, type = URI.URIType.REGEX)
    @CacheRequest(global = true, cacheTime = 10, timeUnit = TimeUnit.MINUTES)
    public Object getBundleKeys(final @Parameter(2) String bundleStr,
                                final I18nService i18nService,
                                final ApiContext context) {
        final UUID uuid = UUID.fromString(bundleStr);
        final List<String> keys = i18nService.getTranslationsOfBundle(uuid);

        if(keys == null || keys.isEmpty()) return context.entityNotFoundMessage();
        return keys;
    }

    @URI(value = "/i18n/bundle/" + Regex.UUID + "/" + Regex.LOCALE, type = URI.URIType.REGEX)
    @CacheRequest(global = true, cacheTime = 10, timeUnit = TimeUnit.MINUTES)
    public Object getBundle(final @Parameter(2) String bundleStr,
                            final @Parameter(3) String localeStr,
                            final I18nService i18nService,
                            final ApiContext context) {
        final UUID uuid = UUID.fromString(bundleStr);
        final List<Translation> translations = i18nService.getTranslations(Locale.forLanguageTag(localeStr), uuid);

        if(translations == null || translations.isEmpty()) return context.entityNotFoundMessage();
        return translations;
    }

}
