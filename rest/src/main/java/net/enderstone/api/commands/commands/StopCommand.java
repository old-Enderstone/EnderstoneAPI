package net.enderstone.api.commands.commands;

import net.enderstone.api.RestAPI;
import net.enderstone.api.commands.command.Command;
import net.enderstone.api.commands.command.CommandArguments;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");
        setAliases("shutdown");

        setDescription("Stop the rest api.");
    }

    @Override
    public void execute(CommandArguments args, String command, String name) {
        RestAPI.exit.complete(0);
    }
}
