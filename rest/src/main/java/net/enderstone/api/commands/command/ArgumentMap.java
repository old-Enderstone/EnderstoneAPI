package net.enderstone.api.commands.command;

import net.enderstone.api.RestAPI;
import net.enderstone.api.utils.Arrays;

import java.util.Collection;
import java.util.HashMap;

public class ArgumentMap {

    private final HashMap<String, Argument> arguments = new HashMap<>();

    public Collection<Argument> getArguments() {
        return arguments.values();
    }

    public boolean isEmpty() {
        return arguments.isEmpty();
    }

    public boolean isNotEmpty() {
        return !arguments.isEmpty();
    }

    public boolean hasArgument(String arg) {
        return arguments.containsKey(arg);
    }

    public boolean containsArgument(String arg) {
        return hasArgument(arg);
    }

    public void addArgument(Argument arg) {
        arguments.put(arg.getName(), arg);
    }

    public boolean hasAllRequiredArguments(CommandArguments args) {
        return arguments.values().stream().filter(Argument::isRequired).filter(arg -> !args.hasArgument(arg)).findAny().isEmpty();
    }

    public boolean matchArgumentTypes(CommandArguments args) {

        for(Argument arg : arguments.values()) {
            if(!args.hasArgument(arg)) continue;

            String val = args.getArgument(arg.getName());
            ArgumentType type = arg.getType();

            if(!arg.isValidValue(val)) {
                RestAPI.logger.warning("Command argument type mismatch argument '" + arg.getName() + "', required type '" + type + "'!");
                return false;
            }

            if(arg.getAllowedValues() != null && !Arrays.contains(Arrays.toLowerCase(arg.getAllowedValues()), val.toLowerCase())) {
                RestAPI.logger.warning("Value '" + val + "' not allowed for argument '" + arg.getName() + "'!");
                return false;
            }

        }

        return true;
    }

}
