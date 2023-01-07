package net.enderstone.api;

import net.enderstone.api.common.EPlayer;
import net.enderstone.api.common.i18n.Translation;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.Properties;
import net.enderstone.api.common.properties.impl.IntProperty;
import net.enderstone.api.repository.TranslationRepository;

import java.io.File;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {
        EnderStoneAPI api = EnderStoneAPI.getInstance();
        final UUID uId = UUID.fromString("d97d7d0a-4253-499c-b5b8-d401c1d88fc1");
        EPlayer player = api.getPlayerById(uId);
        if(player == null) {
            player = api.createPlayer(uId, "Test");
            System.out.println("Created player");
        }
        final IntProperty coinsProperty = (IntProperty) player.getCoinsProperty();
        System.out.println("current: " + coinsProperty.get());
        coinsProperty.set(200);
        System.out.println("value: " + coinsProperty.get());
        coinsProperty.multiply(2);
        System.out.println("value: " + coinsProperty.get());

        final AbstractProperty<String> motd = api.getSystemProperty(Properties.SYSTEM_MOTD);

        System.out.println("MOTD: " + motd.get());
        motd.set("§cTest");
        System.out.println("MOTD: " + motd.get());

        TranslationRepository rep = new TranslationRepository(EnderStoneAPI.getInstance());
        File dir = new File("./l2/");
        dir.mkdirs();
        TestImpl t = new TestImpl(dir, rep);
        Translation tr = t.test();
        System.out.println(tr);
        if(tr != null) System.out.println(tr.getTranslation());
    }

}
