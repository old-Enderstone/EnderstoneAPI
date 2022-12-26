package net.enderstone.api.commands.command;

import net.enderstone.api.RestAPI;

public class CommandManager {

    private final CommandMap commandMap = new CommandMap();

    public void registerCommand(Command command) {
        commandMap.add(command.getCommandName(), command);
    }

    public void dispatch(String command) {
        Command cmd = commandMap.dispatch(command);
        if(cmd == null) {
            RestAPI.logger.warning("There is no such command '" + command + "'!");
            return;
        }

        cmd.dispatch(command);
    }

    public CommandMap getCommandMap() {
        return commandMap;
    }
}
