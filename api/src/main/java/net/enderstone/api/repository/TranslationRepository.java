package net.enderstone.api.repository;

import com.google.gson.reflect.TypeToken;
import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.utils.IOUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TranslationRepository {

    private final EnderStoneAPI api;

    public TranslationRepository(final EnderStoneAPI api) {
        this.api = api;
    }

    public void createEmptyTranslation(final UUID bundle, final String key, final Locale locale) {
        final Message message = IOUtils.getJson(String.format("%s/i18n/create/%s/%s/%s", api.getBaseUrl(), bundle.toString(), key, locale.toLanguageTag()),
                                                Message.class);
        if(message == null) throw new RuntimeException("Couldn't create translation key: " + bundle + " " + key + ":" + locale);
    }

    public void updateTranslation(final String key, final Locale locale, final String translation) {
        final Message message = IOUtils.getJson(String.format("%s/i18n/update/%s/%s/%s",
                                                              api.getBaseUrl(),
                                                              key,
                                                              locale.toLanguageTag(),
                                                              URLEncoder.encode(translation, StandardCharsets.UTF_8)),
                                                Message.class);
        if(message == null) throw new RuntimeException("Couldn't update translation: " + key + ":" + locale + " -> " + translation);
    }

    public Translation getTranslation(final String key, final Locale locale) {
        return IOUtils.getJson(String.format("%s/i18n/get/%s/%s", api.getBaseUrl(), key, locale.toLanguageTag()),
                               Translation.class);
    }

    public List<String> getBundleKeys(final UUID bundle) {
        return IOUtils.getJson(String.format("%s/i18n/bundle-keys/%s", api.getBaseUrl(), bundle.toString()),
                               TypeToken.getParameterized(ArrayList.class, String.class).getType());
    }

    public List<Translation> getBundleTranslations(final UUID bundle, final Locale locale) {
        return IOUtils.getJson(String.format("%s/i18n/bundle/%s/%s", api.getBaseUrl(), bundle.toString(), locale.toLanguageTag()),
                               TypeToken.getParameterized(ArrayList.class, Translation.class).getType());
    }

}
