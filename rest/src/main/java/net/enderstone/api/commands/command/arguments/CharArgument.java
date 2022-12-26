package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;

public class CharArgument extends Argument<Character> {

    public CharArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.CHAR, required);
    }

    public CharArgument(String name, boolean required) {
        super(name, ArgumentType.CHAR, required);
    }

    @Override
    public Character getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue();
        return arguments.getCharacter(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        return value.length() == 1;
    }
}
