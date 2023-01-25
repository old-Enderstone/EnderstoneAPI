package net.enderstone.api.config;

public record IPConfig(net.enderstone.api.config.IPConfig.IPEntry[] entries) {

    /**
     * @param _transient For future sharding support
     */
    public record IPEntry(String address, int port, String[] whitelist, Boolean _transient) {

    }

}
