package net.enderstone.api.commands.commands;

import net.enderstone.api.commands.command.Command;
import net.enderstone.api.commands.commands.cache.CacheDumpCommand;
import net.enderstone.api.commands.commands.cache.CacheListCommand;

public class CacheCommand extends Command {

    public CacheCommand() {
        super("cc");

        setDescription("Cache control command");

        addSubCommand(new CacheListCommand());
        addSubCommand(new CacheDumpCommand());
    }

}
