package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;

public class StringArgument extends Argument<String> {

    private boolean allowSpace = true;

    public StringArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.STRING, required);
    }

    /**
     * Returns true if the string is allowed to contain space symbols, <br>
     * strings with space symbols must be defined as 'command -argument "text text"' instead of 'command -argument text'.<br>
     * @return are space symbols allowed or not
     */
    public boolean isAllowSpace() {
        return allowSpace;
    }

    /**
     * @see #isAllowSpace()
     */
    public StringArgument setAllowSpace(boolean allowSpace) {
        this.allowSpace = allowSpace;
        return this;
    }

    public StringArgument(String name, boolean required) {
        super(name, ArgumentType.STRING, required);
    }

    @Override
    public String getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue();
        return arguments.getArgument(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        return allowSpace || !value.contains(" ");
    }
}
