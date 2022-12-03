package net.enderstone.api;

import com.bethibande.web.JWebServer;
import com.bethibande.web.logging.LoggerFactory;
import net.enderstone.api.annotations.ParameterAnnotationProcessor;
import net.enderstone.api.annotations.WhitelistedInvocationHandler;
import net.enderstone.api.config.Config;
import net.enderstone.api.config.IPWhitelist;
import net.enderstone.api.repository.IRepository;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.UserPropertyRepository;
import net.enderstone.api.rest.PlayerHandler;
import net.enderstone.api.service.PlayerService;
import net.enderstone.api.service.UserPropertyService;
import net.enderstone.api.sql.SQLConnector;
import net.enderstone.api.utils.Arrays;
import net.enderstone.api.utils.FileUtil;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static com.bethibande.web.logging.ConsoleColors.*;

public class Main {

    public static final File configFile = new File("./config.json");
    public static final File whitelistFile = new File("./ip-whitelist.json");

    public static ScheduledThreadPoolExecutor executor;
    public static Logger logger;

    public static Config config;
    public static IPWhitelist whitelist;

    public static SQLConnector connector;

    private static PlayerRepository playerRepository;
    public static PlayerService playerService;

    private static UserPropertyRepository userPropertyRepository;
    public static UserPropertyService userPropertyService;

    public static JWebServer restServer;

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

        playerRepository = new PlayerRepository();
        playerService = new PlayerService(playerRepository);

        userPropertyRepository = new UserPropertyRepository();
        userPropertyService = new UserPropertyService(userPropertyRepository);

        if(Arrays.contains(args, "--genDatabase")) {
            Stream.of(playerRepository, userPropertyRepository).forEach(IRepository::setupDatabase);
        }

        restServer = new JWebServer()
                .withLogLevel(Level.FINE)
                .withBindAddress(config.bindAddress, config.port)
                .withMethodInvocationHandler(new WhitelistedInvocationHandler())
                .withProcessor(new ParameterAnnotationProcessor())
                .withHandler(PlayerHandler.class);
        restServer.start();

        logger.info(annotate("Started!", GREEN));

        exit.join();

        logger.info(annotate("Good Bye!", BLUE));
    }

}
