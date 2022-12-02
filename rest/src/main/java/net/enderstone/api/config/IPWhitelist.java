package net.enderstone.api.config;

import java.util.ArrayList;
import java.util.List;

public class IPWhitelist {

    public boolean use = false;
    public List<String> whitelist;

    public IPWhitelist() {
        this.whitelist = new ArrayList<>();
    }

}
