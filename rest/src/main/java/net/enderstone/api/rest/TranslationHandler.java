package net.enderstone.api.rest;

import com.bethibande.web.annotations.CacheRequest;
import com.bethibande.web.annotations.URI;
import net.enderstone.api.ApiContext;
import net.enderstone.api.annotations.Parameter;
import net.enderstone.api.annotations.Whitelisted;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.utils.Regex;
import net.enderstone.api.service.I18nService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TranslationHandler {

    @URI(value = "/i18n/create/" + Regex.UUID + "/" + Regex.PROPERTY_VALUE + "/" + Regex.LOCALE, type = URI.URIType.REGEX)
    @CacheRequest(global = true, cacheTime = 10, timeUnit = TimeUnit.MINUTES)
    @Whitelisted
    public Object createEmptyTranslation(final @Parameter(2) String bundleStr,
                                         final @Parameter(3) String keyStr,
                                         final @Parameter(4) String localeStr,
                                         final I18nService i18nService,
                                         final ApiContext context) {
        final UUID bundleId = UUID.fromString(bundleStr);
        final Locale locale = Locale.forLanguageTag(localeStr);

        if(i18nService.translationExists(keyStr, locale)) return context.entityAlreadyExistsMessage();
        i18nService.createEmptyTranslation(bundleId, keyStr, locale);

        return new Message(200, "Ok");
    }

    @URI(value = "/i18n/update/" + Regex.PROPERTY_VALUE + "/" + Regex.LOCALE + "/" + Regex.PROPERTY_VALUE, type = URI.URIType.REGEX)
    @Whitelisted
    public Object updateTranslation(final @Parameter(2) String keyStr,
                                    final @Parameter(3) String localeStr,
                                    final @Parameter(4) String valueStr,
                                    final I18nService i18nService,
                                    final ApiContext context) {
        final Locale locale = Locale.forLanguageTag(localeStr);

        if(!i18nService.translationExists(keyStr, locale)) return context.entityNotFoundMessage();
        i18nService.updateTranslation(keyStr, locale, URLDecoder.decode(valueStr, StandardCharsets.UTF_8));

        return new Message(200, "OK");
    }


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

        if(keys == null) return new ArrayList<String>();
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

        if(translations == null) return new ArrayList<Translation>();
        return translations;
    }

}
