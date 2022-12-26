package net.enderstone.api.commands.commands;

import net.enderstone.api.commands.command.*;
import net.enderstone.api.common.utils.Strings;
import net.enderstone.api.utils.Arrays;

import java.util.logging.Logger;

public class HelpCommand extends Command {

    private final CommandManager manager;
    private final Logger l;

    public HelpCommand(CommandManager manager, Logger logger) {
        super("help");
        this.manager = manager;
        this.l = logger;

        setDescription("command help, use 'help [command]' for more specific help");
    }

    @Override
    public void execute(CommandArguments args, String command, String name) {
        if(args.getArguments().length == 0) {
            l.info("Help - Commands");

            for(Command cmd : manager.getCommandMap().getUniqueCommands()) {
                String names = cmd.getAliases() != null ? Arrays.join(Arrays.add(cmd.getAliases(), cmd.getCommandName()), "/"): cmd.getCommandName();
                l.info(" " + names + " | " + cmd.getDescription());
            }
        }

        int length = args.getArguments().length;
        Command cmd = null;
        SubCommand sub = null;
        for(int i = 0; i < length; i++) {
            String arg = args.getArguments()[i];
            if(i == 0) {
                cmd = manager.getCommandMap().dispatch(arg);

                if(cmd == null) {
                    l.warning("There is no such command '" + arg + "'");
                    return;
                }

                if(length == 1) {
                    l.info("Help - " + cmd.getCommandName());
                    l.info(" Description: " + cmd.getDescription());
                    l.info(" Aliases: " + (cmd.getAliases() != null ? "<" + Arrays.join(cmd.getAliases(), "/") + ">": "none"));
                    l.info(" Sub commands: " + (cmd.getChildren().isEmpty() ? "none": Arrays.join(cmd.getChildren().stream().map(SubCommand::getCommandName).toArray(String[]::new), ", ")));

                    if(cmd.getArgumentMap().isNotEmpty()) {
                        l.info(" Arguments: ");
                        for(Argument a : cmd.getArgumentMap().getArguments()) {

                            l.info("  -" + a.getName() + " | " + a.getDescription());
                            if(a.isRequired()) l.info("   Required: yes");
                            if(a.getDefaultValue() != null) l.info("   Default Value: " + a.getDefaultValue());
                            l.info("   Type: " + a.getType());
                            if(a.getAllowedValues() != null) {
                                l.info("   Values: <" + Arrays.join(a.getAllowedValues(), ", ") + ">");
                            }
                        }
                    } else l.info(" Arguments: none");
                }
                continue;
            }

            if(sub == null) {
                sub = cmd.getSubCommand(arg);
            } else sub = sub.getSubCommand(arg);

            if(sub == null) {
                l.warning("No such command '" +  arg + "' -> '" + Arrays.join(args.getArguments(), " ") + "'!");
                return;
            }

            l.info("Help - " + sub.getCommandName());
            l.info(" Description: " + sub.getDescription());
            l.info(" Aliases: " + (sub.getAliases() != null ? "<" + Arrays.join(sub.getAliases(), "/") + ">": "none"));
            l.info(" Sub commands: " + (sub.getChildren().isEmpty() ? "none": Arrays.join(sub.getChildren().stream().map(SubCommand::getCommandName).toArray(String[]::new), ", ")));

            if(sub.getArgumentMap().isNotEmpty()) {
                l.info(" Arguments: ");
                for(Argument a : sub.getArgumentMap().getArguments()) {
                    l.info("  -" + a.getName() + " | " + a.getDescription());
                    if(a.isRequired()) l.info("   Required: yes");
                    if(a.getDefaultValue() != null) l.info("   Default Value: " + a.getDefaultValue());
                    l.info("   Type: " + a.getType());
                    if(a.getAllowedValues() != null) {
                        l.info("   Values: <" + Arrays.join(a.getAllowedValues(), ", ") + ">");
                    }
                }
            } else l.info(" Arguments: none");
        }
    }
}
