package net.enderstone.api.common.i18n;

import java.util.UUID;

public class TranslationBundleItem {

    private final UUID bundleId;
    private final String translationKey;

    public TranslationBundleItem(UUID bundleId, String translationKey) {
        this.bundleId = bundleId;
        this.translationKey = translationKey;
    }

    public UUID getBundleId() {
        return bundleId;
    }

    public String getTranslationKey() {
        return translationKey;
    }
}
