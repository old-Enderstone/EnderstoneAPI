package net.enderstone.api.common;

import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.IntegerUserProperty;
import net.enderstone.api.common.properties.abstraction.LongUserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class EPlayer {

    protected final UUID id;
    protected String lastKnownName;
    protected Collection<IUserProperty<?>> properties;

    public EPlayer(UUID id, String lastKnownName, Collection<IUserProperty<?>> properties) {
        this.id = id;
        this.lastKnownName = lastKnownName;
        this.properties = properties;
    }

    /**
     * Gets a user property or creates a new one using the given default value, if the property does not yet exist.
     */
    public abstract IUserProperty<?> getProperty(UserProperty property);

    public void setLastKnownName(String lastKnownName) {
        this.lastKnownName = lastKnownName;
    }

    public void setProperties(Collection<IUserProperty<?>> properties) {
        this.properties = properties;
    }

    public UUID getId() {
        return id;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }

    public Collection<IUserProperty<?>> getProperties() {
        return List.copyOf(properties);
    }

    private IntegerUserProperty getAsIntProperty(final UserProperty property) {
        return (IntegerUserProperty) getProperty(property);
    }

    private LongUserProperty getAsLongProperty(final UserProperty property){
        return (LongUserProperty) getProperty(property);
    }

    private StringUserProperty getAsStringProperty(final UserProperty property) {
        return (StringUserProperty) getProperty(property);
    }

    public IntegerUserProperty getCoinsProperty() {
        return getAsIntProperty(UserProperty.COINS);
    }

    public int getCoins() {
        return getCoinsProperty().get();
    }

    /**
     * Get last seen date
     * @return unix timestamp in ms
     */
    public long getLastSeen() {
        return getAsLongProperty(UserProperty.LAST_SEEN).get();
    }

    /**
     * Set last seen date
     * @param value unix timestamp in ms
     */
    public void setLastSeen(final long value) {
        getAsLongProperty(UserProperty.LAST_SEEN).set(value);
    }

    /**
     * Get player locale
     */
    public Locale getLocale() {
        return Locale.forLanguageTag(getAsStringProperty(UserProperty.LOCALE).get());
    }

    /**
     * Set player locale
     */
    public void setLocale(final Locale locale) {
        getAsStringProperty(UserProperty.LOCALE).set(locale.toLanguageTag());
    }

    /**
     * Returns country iso code (de, gb, fr, us, [...]) or null
     */
    public String getLastCountry() {
        return getAsStringProperty(UserProperty.LAST_COUNTRY).get();
    }

    /**
     * Set last seen country iso code
     */
    public void setLastCountry(final String iso) {
        getAsStringProperty(UserProperty.LAST_COUNTRY).set(iso);
    }

    /**
     * Get player play time in minutes
     */
    public int getPlayTime() {
        return getAsIntProperty(UserProperty.PLAYTIME).get();
    }

    public IntegerUserProperty getPlayTimeProperty() {
        return getAsIntProperty(UserProperty.PLAYTIME);
    }

}
