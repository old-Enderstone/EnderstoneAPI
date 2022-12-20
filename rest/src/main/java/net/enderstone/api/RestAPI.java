package net.enderstone.api;

import com.bethibande.web.JWebServer;
import com.bethibande.web.logging.LoggerFactory;
import net.enderstone.api.annotations.ParameterAnnotationProcessor;
import net.enderstone.api.annotations.WhitelistedInvocationHandler;
import net.enderstone.api.config.Config;
import net.enderstone.api.config.IPWhitelist;
import net.enderstone.api.repository.IRepository;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.SystemPropertyRepository;
import net.enderstone.api.repository.UserPropertyRepository;
import net.enderstone.api.rest.PlayerHandler;
import net.enderstone.api.rest.SystemPropertyHandler;
import net.enderstone.api.rest.UserPropertyHandler;
import net.enderstone.api.service.PlayerService;
import net.enderstone.api.service.SystemPropertyService;
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

public class RestAPI {

    public static final File configFile = new File("./config.json");
    public static final File whitelistFile = new File("./ip-whitelist.json");

    public static ScheduledThreadPoolExecutor executor;
    public static Logger logger;

    public static Config config;
    public static IPWhitelist whitelist;

    public static SQLConnector connector;

    private static SystemPropertyRepository systemPropertyRepository;

    private static PlayerRepository playerRepository;

    private static UserPropertyRepository userPropertyRepository;

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

        systemPropertyRepository = new SystemPropertyRepository();
        final SystemPropertyService systemPropertyService = new SystemPropertyService(systemPropertyRepository);

        userPropertyRepository = new UserPropertyRepository();
        final UserPropertyService userPropertyService = new UserPropertyService(userPropertyRepository);

        playerRepository = new PlayerRepository(userPropertyService);
        final PlayerService playerService = new PlayerService(playerRepository, userPropertyService);


        if(Arrays.contains(args, "--createDatabase")) {
            logger.info("Creating database..");
            Stream.of(playerRepository, userPropertyRepository).forEach(IRepository::setupDatabase);
            logger.info("Database created!");
        }

        restServer = new JWebServer()
                .withLogLevel(Level.FINE)
                .withBindAddress(config.bindAddress, config.port)
                .withMethodInvocationHandler(new WhitelistedInvocationHandler())
                .withProcessor(new ParameterAnnotationProcessor())
                .withContextFactory(ApiContext::new)
                .withHandler(PlayerHandler.class)
                .withHandler(UserPropertyHandler.class)
                .withHandler(SystemPropertyHandler.class);
        restServer.start();

        restServer.storeGlobalBean(systemPropertyService);
        restServer.storeGlobalBean(playerService);
        restServer.storeGlobalBean(userPropertyService);

        logger.info(annotate("Started!", GREEN));

        exit.join();

        logger.info(annotate("Good Bye!", BLUE));
    }

}
