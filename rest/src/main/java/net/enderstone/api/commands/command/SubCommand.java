package net.enderstone.api.commands.command;

import net.enderstone.api.RestAPI;
import net.enderstone.api.utils.Arrays;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class SubCommand {

    private final String commandName;
    private String description = "Default sub-command description";
    private String[] aliases = null;
    private String[] simpleCommandMap;
    private final Collection<SubCommand> children = new ArrayList<>();
    private final ArgumentMap argumentMap = new ArgumentMap();

    public SubCommand(String commandName) {
        this.commandName = commandName;
        this.simpleCommandMap = Arrays.of(commandName);
    }

    public void addArgument(Argument arg) {
        argumentMap.addArgument(arg);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAliases(String... aliases) {
        this.aliases = aliases;
        this.simpleCommandMap = Arrays.add(Arrays.toLowerCase(aliases), commandName.toLowerCase());
    }

    public ArgumentMap getArgumentMap() {
        return argumentMap;
    }

    public String[] simpleCommandMap() {
        return simpleCommandMap;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getDescription() {
        return description;
    }

    public Collection<SubCommand> getChildren() {
        return children;
    }

    public SubCommand getSubCommand(String cmd) {
        return children.stream().filter(sub -> sub.getCommandName().equalsIgnoreCase(cmd.split("\\s+")[0])).findAny().orElse(null);
    }

    public final void dispatch(String command) {
        String[] s = command.split("\\s+");
        String cmd = s[0].toLowerCase();
        String subCmd = s.length >= 2 ? s[1].toLowerCase(): null;

        SubCommand subCommand = this.children.stream().filter(sub -> Arrays.contains(sub.simpleCommandMap(), subCmd)).findFirst().orElse(null);

        if(subCommand == null) {
            String[] args = new String[0];

            if(command.contains(" ")) args = command.substring(cmd.length()+1).split("\\s+");

            CommandArguments cmdArguments = new CommandArguments(args);

            if(!argumentMap.hasAllRequiredArguments(cmdArguments)) {
                RestAPI.logger.warning("Not all required arguments found, for more details enter 'help <command>'");
                return;
            }

            if(!argumentMap.matchArgumentTypes(cmdArguments)) {
                return;
            }

            execute(cmdArguments, command, cmd);

            return;
        }

        subCommand.dispatch(command.substring(cmd.length()+1));
    }

    /**
     * Override this method to implement command
     * @param args the command arguments
     * @param command the whole command the user entered
     * @param name commandName or the alias used
     */
    public void execute(CommandArguments args, String command, String name) {

    }

}
