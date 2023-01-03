package net.enderstone.api.commands.commands.cache;

import net.enderstone.api.RestAPI;
import net.enderstone.api.commands.command.CommandArguments;
import net.enderstone.api.commands.command.SubCommand;
import net.enderstone.api.common.cache.CacheBuilder;
import net.enderstone.api.common.cache.ICache;

import java.util.logging.Logger;

import static com.bethibande.web.logging.ConsoleColors.*;

public class CacheListCommand extends SubCommand {

    public CacheListCommand() {
        super("list");
        setAliases("ls");
        setDescription("List available caches");
    }

    @Override
    public void execute(final CommandArguments args, final String command, final String name) {
        final Logger logger = RestAPI.logger;
        logger.info("List - Caches");

        for(ICache<?, ?> cache : CacheBuilder.caches) {
            logger.info(String.format("  %s [%d]", annotate(cache.getId().toString(), RED), cache.getSize()));
        }
    }
}
