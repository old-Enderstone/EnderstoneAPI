package net.enderstone.api;

import com.bethibande.web.JWebServer;
import com.bethibande.web.context.ServerContext;
import com.bethibande.web.response.RequestResponse;
import com.google.gson.GsonBuilder;
import net.enderstone.api.annotations.AuthenticationInvocationHandler;
import net.enderstone.api.annotations.ParameterAnnotationProcessor;
import net.enderstone.api.annotations.WhitelistedInvocationHandler;
import net.enderstone.api.commands.command.CommandDispatcher;
import net.enderstone.api.commands.command.CommandManager;
import net.enderstone.api.commands.commands.CacheCommand;
import net.enderstone.api.commands.commands.HelpCommand;
import net.enderstone.api.commands.commands.StopCommand;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.properties.AbstractProperty;
import net.enderstone.api.common.properties.impl.BooleanProperty;
import net.enderstone.api.common.properties.impl.LocaleProperty;
import net.enderstone.api.common.properties.impl.StringProperty;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.common.types.PropertySerializer;
import net.enderstone.api.config.Config;
import net.enderstone.api.config.IPConfig;
import net.enderstone.api.config.IPWhitelist;
import net.enderstone.api.jdbc.dbm.DatabaseMigration;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.PropertyKeyRepository;
import net.enderstone.api.repository.PropertyRepository;
import net.enderstone.api.repository.TranslationBundleRepository;
import net.enderstone.api.repository.TranslationRepository;
import net.enderstone.api.rest.NotFoundHandler;
import net.enderstone.api.rest.PlayerHandler;
import net.enderstone.api.rest.PropertyHandler;
import net.enderstone.api.rest.StatusHandler;
import net.enderstone.api.rest.TranslationHandler;
import net.enderstone.api.service.I18nService;
import net.enderstone.api.service.PlayerService;
import net.enderstone.api.service.PropertyService;
import net.enderstone.api.jdbc.SQLConnector;
import net.enderstone.api.tasks.ErrorWriteJob;
import net.enderstone.api.utils.Arrays;
import net.enderstone.api.utils.FileUtil;

import java.io.File;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.bethibande.web.logging.ConsoleColors.*;

public class RestAPI {

    /**
     * Argument used to indicate, that the rest api is starting as part of a unit test
     */
    public static final String ARG_UNIT_TEST = "--unit-test";

    public static final File ipConfigFile = new File("./ip-config.json");
    public static final File configFile = new File("./config.json");
    /**
     * Config used for unit tests, unit tests will cause a complete rebase of the database
     */
    public static final File unitConfigFile = new File("./u-config.json");
    public static final File whitelistFile = new File("./ip-whitelist.json");

    public static ScheduledThreadPoolExecutor executor;
    public static Logger logger;

    public static Config config;
    public static IPConfig ipConfig;
    public static IPWhitelist whitelist;

    public static SQLConnector connector;

    private static PropertyKeyRepository propertyKeyRepository;
    private static PropertyRepository propertyRepository;
    private static PlayerRepository playerRepository;
    private static TranslationBundleRepository translationBundleRepository;
    private static TranslationRepository translationRepository;

    public static CommandManager commandManager;

    public static JWebServer restServer;

    public static final CompletableFuture<Integer> exit = new CompletableFuture<>();

    public static void createDefaults(final String[] args) {
        if(!ipConfigFile.exists()) {
            final IPConfig config = new IPConfig(Arrays.of(
                    new IPConfig.IPEntry("127.0.0.1", 80, null, true),
                    new IPConfig.IPEntry("::1", 80, null, true)
            ));

            FileUtil.writeJson(config, ipConfigFile);
        }

        if(!configFile.exists()) {
            final Config def = new Config();
            def.sqlHost = "localhost";
            def.sqlPort = 3306;
            def.sqlDatabase = "api";
            def.sqlUsername = "username";
            def.sqlPassword = "password";

            FileUtil.writeJson(def, configFile);
        }

        if(Arrays.contains(args, ARG_UNIT_TEST)) {
            if(!unitConfigFile.exists()) {
                final Config def = new Config();
                def.sqlHost = "localhost";
                def.sqlPort = 3306;
                def.sqlDatabase = "api-tests";
                def.sqlUsername = "username";
                def.sqlPassword = "password";

                FileUtil.writeJson(def, unitConfigFile);
            }
        }

        if(!whitelistFile.exists()) {
            FileUtil.writeJson(new IPWhitelist(), whitelistFile);
        }
    }

    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
        final boolean isUnitTest = Arrays.contains(args, ARG_UNIT_TEST);

        createDefaults(args);
        executor = new ScheduledThreadPoolExecutor(10);
        executor.setCorePoolSize(10);

        logger = new net.enderstone.api.logging.Logger(executor);
        logger.setLevel(isUnitTest ? Level.OFF: Level.ALL);
        logger.info("Starting..");

        config = FileUtil.readJson(configFile, Config.class);
        whitelist = FileUtil.readJson(whitelistFile, IPWhitelist.class);
        ipConfig = FileUtil.readJson(ipConfigFile, IPConfig.class);


        if(isUnitTest) {
            config = FileUtil.readJson(unitConfigFile, Config.class);
        }

        logger.config(String.format(
                "Using SQL Server %s with database %s and user %s",
                annotate(config.sqlHost + ":" + config.sqlPort, MAGENTA),
                annotate(config.sqlDatabase, BLUE),
                annotate(config.sqlUsername, BLUE)
        ));

        connector = new SQLConnector();
        connector.setDriverClass("org.mariadb.jdbc.Driver")
                 .setHostAddress(config.sqlHost, config.sqlPort)
                 .setUser(config.sqlUsername, config.sqlPassword);

        connector.connect();

        if(isUnitTest) {
            connector.update("drop database if exists `" + config.sqlDatabase + "`;");
            connector.update("create database `" + config.sqlDatabase + "`;");
        }

        connector.setDatabase(config.sqlDatabase);
        connector.update("use `" + config.sqlDatabase + "`;");

        propertyKeyRepository = new PropertyKeyRepository();
        propertyRepository = new PropertyRepository();

        final PropertyService propertyService = new PropertyService(propertyRepository, propertyKeyRepository);

        playerRepository = new PlayerRepository(propertyService);
        final PlayerService playerService = new PlayerService(playerRepository, propertyService);

        translationBundleRepository = new TranslationBundleRepository();
        translationRepository = new TranslationRepository();
        final I18nService i18nService = new I18nService(translationRepository, translationBundleRepository);

        final int currentVersion = DatabaseMigration.getCurrentVersion();
        final int newVersion = DatabaseMigration.updateDatabase(currentVersion);
        if(currentVersion != newVersion) DatabaseMigration.setCurrentVersion(newVersion);

        restServer = new JWebServer()
                .withLogger(logger)
                .withExecutor(executor)
                .withMethodInvocationHandler(new WhitelistedInvocationHandler())
                .withMethodInvocationHandler(new AuthenticationInvocationHandler())
                .withProcessor(new ParameterAnnotationProcessor())
                .withContextFactory(ApiContext::new)
                .withHandler(PlayerHandler.class)
                .withHandler(TranslationHandler.class)
                .withHandler(StatusHandler.class)
                .withHandler(NotFoundHandler.class)
                .withHandler(PropertyHandler.class)
                .withErrorHandler(RestAPI::handleError);

        for(IPConfig.IPEntry entry : ipConfig.entries()) {
            restServer.start(new InetSocketAddress(entry.address(), entry.port()));
        }

        // Gson isn't properly detecting types, all types, that do not extend NumberProperty need to be registered separately
        restServer.setGson(new GsonBuilder().serializeNulls()
                                            .registerTypeAdapter(AbstractProperty.class, new PropertySerializer())
                                            .registerTypeAdapter(StringProperty.class, new PropertySerializer())
                                            .registerTypeAdapter(BooleanProperty.class, new PropertySerializer())
                                            .registerTypeAdapter(LocaleProperty.class, new PropertySerializer())
                                            .create());

        restServer.storeGlobalBean(propertyService);
        restServer.storeGlobalBean(playerService);
        restServer.storeGlobalBean(i18nService);

        commandManager = new CommandManager();
        commandManager.registerCommand(new HelpCommand(commandManager, logger));
        commandManager.registerCommand(new StopCommand());
        commandManager.registerCommand(new CacheCommand());

        final CommandDispatcher commandDispatcher = new CommandDispatcher();
        commandDispatcher.setCommandManager(commandManager);
        commandDispatcher.start();

        long time = System.currentTimeMillis() - start;

        logger.info(annotate(String.format("Started in %d ms!", time), GREEN));

        if(!isUnitTest) {
            final int status = exit.join();

            exit(status);
        }
    }

    public static void exit(final int code) {
        logger.info(annotate("Good Bye!", BLUE));

        connector.disconnect();
        restServer.stop();

        CacheBuilder.caches.forEach(ICache::clear);

        System.exit(0);
    }

    public static void handleError(final Throwable th, final ServerContext ctx) {
        final UUID id = UUID.randomUUID();
        ctx.api().getLogger().severe("Encountered an error: " + th.getLocalizedMessage() + " for path: " + ctx.request().getUri().getPath());
        ctx.api().getLogger().severe("Stack trace was save under '" + new File(ErrorWriteJob.ERROR_DIR + "/" + id).getPath() + "'");

        executor.execute(new ErrorWriteJob(id.toString(), th, "Error for route: " + ctx.request().getUri().toString()));

        ctx.request().setResponse(new RequestResponse()
                .withStatusCode(500)
                .withContentData(new Message(500, "Internal server error")));
    }

}
