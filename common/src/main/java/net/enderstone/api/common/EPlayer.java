package net.enderstone.api.common;

import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.NumberProperty;
import net.enderstone.api.common.properties.Properties;
import net.enderstone.api.common.properties.PropertyKey;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class EPlayer {

    protected final UUID id;
    protected String lastKnownName;
    protected Collection<AbstractProperty<?>> properties;

    public EPlayer(UUID id, String lastKnownName, Collection<AbstractProperty<?>> properties) {
        this.id = id;
        this.lastKnownName = lastKnownName;
        this.properties = properties;
    }

    /**
     * Gets a user property or creates a new one using the given default value, if the property does not yet exist.
     */
    public abstract <T> AbstractProperty<T> getProperty(PropertyKey<T> property);

    public void setLastKnownName(String lastKnownName) {
        this.lastKnownName = lastKnownName;
    }

    public void setProperties(Collection<AbstractProperty<?>> properties) {
        this.properties = properties;
    }

    public UUID getId() {
        return id;
    }

    public String getLastKnownName() {
        return lastKnownName;
    }

    public Collection<AbstractProperty<?>> getProperties() {
        return List.copyOf(properties);
    }

    public boolean hasAcceptedTos() {
        return getProperty(Properties.PLAYER_ACCEPT_TOS).get();
    }

    public void acceptTos() {
        getProperty(Properties.PLAYER_ACCEPT_TOS).set(true);
    }

    public AbstractProperty<Integer> getCoinsProperty() {
        return getProperty(Properties.PLAYER_COINS);
    }

    public int getCoins() {
        return getCoinsProperty().get();
    }

    /**
     * Get last seen date
     * @return unix timestamp in ms
     */
    public long getLastSeen() {
        return getProperty(Properties.PLAYER_LAST_SEEN).get();
    }

    /**
     * Set last seen date
     * @param value unix timestamp in ms
     */
    public void setLastSeen(final long value) {
        getProperty(Properties.PLAYER_LAST_SEEN).set(value);
    }

    /**
     * Get player locale
     */
    public Locale getLocale() {
        return getProperty(Properties.PLAYER_LOCALE).get();
    }

    /**
     * Set player locale
     */
    public void setLocale(final Locale locale) {
        getProperty(Properties.PLAYER_LOCALE).set(locale);
    }

    /**
     * Returns country iso code (de, gb, fr, us, [...]) or null
     */
    public String getLastCountry() {
        return getProperty(Properties.PLAYER_LAST_COUNTRY).get();
    }

    /**
     * Set last seen country iso code
     */
    public void setLastCountry(final String iso) {
        getProperty(Properties.PLAYER_LAST_COUNTRY).set(iso);
    }

    /**
     * Get player play time in minutes
     */
    public int getPlayTime() {
        return getProperty(Properties.PLAYER_PLAYTIME).get();
    }

    public NumberProperty<Integer> getPlayTimeProperty() {
        return getProperty(Properties.PLAYER_PLAYTIME).asIntProperty();
    }

}
