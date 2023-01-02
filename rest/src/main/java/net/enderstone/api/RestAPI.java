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
import net.enderstone.api.commands.commands.HelpCommand;
import net.enderstone.api.commands.commands.StopCommand;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.common.properties.IProperty;
import net.enderstone.api.common.properties.IUserProperty;
import net.enderstone.api.common.types.Message;
import net.enderstone.api.config.Config;
import net.enderstone.api.config.IPWhitelist;
import net.enderstone.api.dbm.DatabaseMigration;
import net.enderstone.api.repository.PlayerRepository;
import net.enderstone.api.repository.SystemPropertyRepository;
import net.enderstone.api.repository.TranslationBundleRepository;
import net.enderstone.api.repository.TranslationRepository;
import net.enderstone.api.repository.UserPropertyRepository;
import net.enderstone.api.rest.*;
import net.enderstone.api.service.I18nService;
import net.enderstone.api.service.PlayerService;
import net.enderstone.api.sql.SQLConnector;
import net.enderstone.api.tasks.ErrorWriteJob;
import net.enderstone.api.types.SystemPropertySerializer;
import net.enderstone.api.utils.FileUtil;
import net.enderstone.api.types.UserPropertySerializer;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static TranslationBundleRepository translationBundleRepository;
    private static TranslationRepository translationRepository;

    public static CommandManager commandManager;

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

        logger = new net.enderstone.api.logging.Logger(executor);
        logger.setLevel(Level.FINE);
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

        translationBundleRepository = new TranslationBundleRepository();
        translationRepository = new TranslationRepository();
        final I18nService i18nService = new I18nService(translationRepository, translationBundleRepository);

        final int currentVersion = DatabaseMigration.getCurrentVersion();
        final int newVersion = DatabaseMigration.updateDatabase(currentVersion);
        if(currentVersion != newVersion) DatabaseMigration.setCurrentVersion(newVersion);

        restServer = new JWebServer()
                .withLogger(logger)
                .withExecutor(executor)
                .withBindAddress(config.bindAddress, config.port)
                .withMethodInvocationHandler(new WhitelistedInvocationHandler())
                .withMethodInvocationHandler(new AuthenticationInvocationHandler())
                .withProcessor(new ParameterAnnotationProcessor())
                .withContextFactory(ApiContext::new)
                .withHandler(PlayerHandler.class)
                .withHandler(UserPropertyHandler.class)
                .withHandler(SystemPropertyHandler.class)
                .withHandler(TranslationHandler.class)
                .withHandler(StatusHandler.class)
                .withHandler(NotFoundHandler.class)
                .withErrorHandler(RestAPI::handleError);
        restServer.start();

        restServer.setGson(new GsonBuilder().serializeNulls()
                                            .create());

        restServer.storeGlobalBean(systemPropertyService);
        restServer.storeGlobalBean(playerService);
        restServer.storeGlobalBean(userPropertyService);
        restServer.storeGlobalBean(i18nService);

        commandManager = new CommandManager();
        commandManager.registerCommand(new HelpCommand(commandManager, logger));
        commandManager.registerCommand(new StopCommand());

        final CommandDispatcher commandDispatcher = new CommandDispatcher();
        commandDispatcher.setCommandManager(commandManager);
        commandDispatcher.start();

        logger.info(annotate("Started!", GREEN));

        exit.join();

        logger.info(annotate("Good Bye!", BLUE));

        connector.disconnect();
        restServer.stop();

        CacheBuilder.caches.forEach(ICache::clear);

        System.exit(0);
    }

    public static void handleError(final Throwable th, final ServerContext ctx) {
        final UUID id = UUID.randomUUID();
        ctx.server().getLogger().severe("Encountered an error: " + th.getLocalizedMessage() + " for path: " + ctx.request().getUri().getPath());
        ctx.server().getLogger().severe("Stack trace was save under '" + new File(ErrorWriteJob.ERROR_DIR + "/" + id).getPath() + "'");

        executor.execute(new ErrorWriteJob(id.toString(), th, "Error for route: " + ctx.request().getUri().toString()));

        ctx.request().setResponse(new RequestResponse()
                .withStatusCode(500)
                .withContentData(new Message(500, "Internal server error")));
    }

}
