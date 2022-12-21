package net.enderstone.api;

import net.enderstone.api.i18n.I18NService;
import net.enderstone.api.repository.TranslationRepository;

import java.io.File;

public class TestImpl extends I18NService {

    public TestImpl(File l2StorageRoot, TranslationRepository repository) {
        super(l2StorageRoot, repository);
    }
}
