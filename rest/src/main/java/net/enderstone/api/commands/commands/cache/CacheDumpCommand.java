package net.enderstone.api.commands.commands.cache;

import net.enderstone.api.RestAPI;
import net.enderstone.api.commands.command.CommandArguments;
import net.enderstone.api.commands.command.SubCommand;
import net.enderstone.api.commands.command.arguments.StringArgument;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.ICache;
import net.enderstone.api.tasks.CacheDumpTask;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

public class CacheDumpCommand extends SubCommand {

    private final StringArgument idArgument = new StringArgument("c", true);

    public CacheDumpCommand() {
        super("dump");
        setAliases("d");
        setDescription("Create a cache dump");

        addArgument(idArgument);
        idArgument.setAllowSpace(false)
                  .setDescription("cache id")
                  .setAllowedValues(CacheBuilder.caches.stream().map(c -> c.getId().toString()).toArray(String[]::new));
    }

    @Override
    public void execute(final CommandArguments args, final String command, final String name) {
        final UUID id = UUID.fromString(idArgument.getValue(args));
        final Optional<ICache<?, ?>> optCache = CacheBuilder.caches.stream().filter(c -> c.getId().equals(id)).findFirst();

        if(optCache.isEmpty()) {
            RestAPI.logger.warning(String.format("Cache with id '%s' not found", id));
            return;
        }

        final File file = new File(CacheDumpTask.ROOT_DIR + "/" + id);
        final ICache<?, ?> cache = optCache.get();

        RestAPI.logger.info(String.format("Creating cache dump, dump will be written to the following file '%s'", file.getPath()));

        RestAPI.executor.execute(new CacheDumpTask(cache, file));
    }
}
