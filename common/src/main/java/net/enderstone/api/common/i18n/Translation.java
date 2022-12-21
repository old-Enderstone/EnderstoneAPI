package net.enderstone.api.common.i18n;

import net.enderstone.api.common.types.IStreamSerializable;
import net.enderstone.api.common.types.SimpleEntry;
import net.enderstone.api.common.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class Translation implements IStreamSerializable {

    private String key;
    private Locale locale;
    private String translation;

    /**
     * Needed because I was too lazy to property implement {@link net.enderstone.api.common.utils.ReflectUtils#createInstance(Class)}
     */
    public Translation() {
        
    }
    
    public Translation(String key, Locale locale, String translation) {
        this.key = key;
        this.locale = locale;
        this.translation = translation;
    }

    public Translation setTranslation(String translation) {
        this.translation = translation;
        return this;
    }

    public String getKey() {
        return key;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getTranslation() {
        return translation;
    }

    /**
     * @return an entry of the translations key and locale
     */
    public SimpleEntry<String, Locale> toEntry() {
        return new SimpleEntry<>(key, locale);
    }

    @Override
    public void serialize(OutputStream output) throws IOException {
        final byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        final byte[] translationBytes = translation.getBytes(StandardCharsets.UTF_8);

        output.write(IOUtils.writeUnsignedShort(keyBytes.length));
        output.write(keyBytes);
        output.write(locale.toString().getBytes(StandardCharsets.UTF_8));
        output.write(IOUtils.writeUnsignedShort(translationBytes.length));
        output.write(translationBytes);
        output.flush();
    }

    @Override
    public void deserialize(InputStream data) throws IOException {
        final int keyLength = IOUtils.readUnsignedShort(data.readNBytes(2));
        this.key = new String(data.readNBytes(keyLength), StandardCharsets.UTF_8);

        final String localeString = new String(data.readNBytes(5), StandardCharsets.UTF_8);
        this.locale = new Locale(localeString);

        final int translationLength = IOUtils.readUnsignedShort(data.readNBytes(2));
        this.translation = new String(data.readNBytes(translationLength), StandardCharsets.UTF_8);
    }
}
