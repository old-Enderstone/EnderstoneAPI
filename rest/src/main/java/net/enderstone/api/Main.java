package net.enderstone.api;

import com.bethibande.web.logging.ConsoleColors;
import com.bethibande.web.logging.LoggerFactory;
import net.enderstone.api.common.properties.UserProperty;
import net.enderstone.api.common.properties.abstraction.IntegerUserProperty;
import net.enderstone.api.common.properties.abstraction.StringUserProperty;
import net.enderstone.api.config.Config;
import net.enderstone.api.config.IPWhitelist;
import net.enderstone.api.repo.UserPropertyRepository;
import net.enderstone.api.sql.SQLConnector;
import net.enderstone.api.utils.FileUtil;

import java.io.File;
import java.util.AbstractMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Logger;

import static com.bethibande.web.logging.ConsoleColors.*;

public class Main {

    public static final File configFile = new File("./config.json");
    public static final File whitelistFile = new File("./ip-whitelist.json");

    public static ScheduledThreadPoolExecutor executor;
    public static Logger logger;

    public static Config config;
    public static IPWhitelist whitelist;

    public static SQLConnector connector;

    public static final CompletableFuture<Integer> exit = new CompletableFuture<>();

    public static void createDefaults() {
        if(!configFile.exists()) {
            final Config def = new Config();
            def.bindAddress = "0.0.0.0";
            def.port = 80;
            def.sqlHost = "localhost";
            def.sqlPort = 3306;
            def.sqlDatabase = "api";
            def.sqlUsername = "username";
            def.sqlPassword = "password";

            FileUtil.writeJson(def, configFile);
        }

        if(!whitelistFile.exists()) {
            FileUtil.writeJson(new IPWhitelist(), whitelistFile);
        }
    }

    public static void main(String[] args) {
        createDefaults();
        executor = new ScheduledThreadPoolExecutor(10);
        executor.setCorePoolSize(10);

        logger = LoggerFactory.createLogger(executor);
        logger.info("Starting..");

        config = FileUtil.readJson(configFile, Config.class);
        whitelist = FileUtil.readJson(whitelistFile, IPWhitelist.class);

        connector = new SQLConnector();
        connector.setHostAddress(config.sqlHost, config.sqlPort)
                 .setUser(config.sqlUsername, config.sqlPassword)
                 .setDatabase(config.sqlDatabase);

        connector.connect();

        logger.info(annotate("Started!", GREEN));

        UserPropertyRepository repo = new UserPropertyRepository();
        IntegerUserProperty prop = (IntegerUserProperty) repo.get(new AbstractMap.SimpleImmutableEntry<>(UUID.randomUUID(), UserProperty.COINS));
        prop.set(6);
        prop.add(1); // 7
        prop.subtract(3); // 4
        prop.divide(2); // 2
        prop.multiply(4); // 8

        exit.join();

        logger.info(annotate("Good Bye!", BLUE));
    }

}
