package net.enderstone.api;

import net.enderstone.api.common.Player;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.properties.SystemProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.IntegerUserProperty;
import net.enderstone.api.common.properties.abstraction.StringProperty;
import net.enderstone.api.common.utils.Strings;
import net.enderstone.api.repository.TranslationRepository;

import java.io.File;
import java.util.Locale;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        EnderStoneAPI api = EnderStoneAPI.getInstance();

        final File root = new File("./l2/");
        root.mkdir();

        final TranslationRepository translationRepository = new TranslationRepository(api);
        final TestImpl i18n = new TestImpl(root, translationRepository);

        final UUID bundle = UUID.fromString("ac7e200c-0054-44e8-9176-d8f62007d7c8");
        final Locale locale = new Locale("DE", "DE");

        final Translation t = i18n.getTranslation(bundle, "default.test", locale);
        System.out.println(t == null ? null: t.getTranslation());

        i18n.updateTranslation("default.test", locale, "test §6translation");

        final Translation t2 = i18n.getTranslation(bundle, "default.test", locale);
        System.out.println(t2.getTranslation());

        i18n.loadBundle(bundle, locale);
    }

}
