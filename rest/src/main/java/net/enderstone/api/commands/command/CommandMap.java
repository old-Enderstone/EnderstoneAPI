package net.enderstone.api.commands.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandMap {

    private final HashMap<String, Command> commandMap = new HashMap<>();
    private final List<Command> uniqueCommands = new ArrayList<>();

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }

    public List<Command> getUniqueCommands() {
        return uniqueCommands;
    }

    public void add(String cmd, Command command) {
        commandMap.remove(cmd.toLowerCase());
        commandMap.put(cmd.toLowerCase(), command);

        if(!uniqueCommands.contains(command)) uniqueCommands.add(command);

        if(cmd.equalsIgnoreCase(command.getCommandName()) && command.getAliases() != null) {
            for (String alias : command.getAliases()) {
                add(alias.toLowerCase(), command);
            }
        }
    }

    public void remove(String cmd) {
        commandMap.remove(cmd.toLowerCase());
    }

    public Command dispatch(String command) {
        return commandMap.get(command.split("\\s+")[0].toLowerCase());
    }

}
