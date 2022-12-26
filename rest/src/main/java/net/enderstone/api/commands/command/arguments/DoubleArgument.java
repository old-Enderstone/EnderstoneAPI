package net.enderstone.api.commands.command.arguments;

import net.enderstone.api.commands.command.Argument;
import net.enderstone.api.commands.command.ArgumentType;
import net.enderstone.api.commands.command.CommandArguments;

public class DoubleArgument extends Argument<Double> {

    public DoubleArgument(String name, String description, boolean required) {
        super(name, description, ArgumentType.DOUBLE, required);
    }

    public DoubleArgument(String name, boolean required) {
        super(name, ArgumentType.DOUBLE, required);
    }

    @Override
    public Double getValue(CommandArguments arguments) {
        if(!arguments.hasArgument(super.getName())) return getDefaultValue();
        return arguments.getDouble(super.getName());
    }

    @Override
    public boolean isValidValue(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
