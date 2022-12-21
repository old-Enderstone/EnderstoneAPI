package net.enderstone.api.repository;

import com.google.gson.reflect.TypeToken;
import net.enderstone.api.EnderStoneAPI;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.utils.IOUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TranslationRepository {

    private final EnderStoneAPI api;

    public TranslationRepository(final EnderStoneAPI api) {
        this.api = api;
    }

    public Translation getTranslation(final String key, final Locale locale) {
        return IOUtils.getJson(String.format("%s/i18n/get/%s/%s", api.getBaseUrl(), key, locale.toString()),
                               Translation.class);
    }

    public List<String> getBundleKeys(final UUID bundle) {
        return IOUtils.getJson(String.format("%s/i18n/bundle-keys/%s", api.getBaseUrl(), bundle.toString()),
                               TypeToken.getParameterized(ArrayList.class, String.class).getType());
    }

    public List<Translation> getBundleTranslations(final UUID bundle, final Locale locale) {
        return IOUtils.getJson(String.format("%s/i18n/bundle/%s/%s", api.getBaseUrl(), bundle.toString(), locale.toString()),
                               TypeToken.getParameterized(ArrayList.class, Translation.class).getType());
    }

}
