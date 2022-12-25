package net.enderstone.api;

import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.i18n.I18NService;
import net.enderstone.api.repository.TranslationRepository;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

public class TestImpl extends I18NService {

    public TestImpl(File l2StorageRoot, TranslationRepository repository) {
        super(l2StorageRoot, repository);
    }

    public Translation test() {
        return super.getTranslation(UUID.randomUUID(), "key", new Locale("DE", "de"));
    }

}
