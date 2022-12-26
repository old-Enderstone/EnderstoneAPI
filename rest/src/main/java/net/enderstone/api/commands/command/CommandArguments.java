package net.enderstone.api.commands.command;

import net.enderstone.api.utils.Arrays;

public class CommandArguments {

    private final String[] args;

    public CommandArguments(String[] args) {
        this.args = args;
    }

    public String[] getArguments() {
        return args;
    }

    public boolean hasArgument(Argument arg) {
        return hasArgument(arg.getName());
    }

    public boolean hasArgument(String arg) {
        return Arrays.contains(args, "-" + arg);
    }

    public String getArgument(String arg) {
        for(int i = 0; i < args.length; i++) {
            String _a = args[i];

            if(_a.equals("-" + arg)) {
                if(i + 1 >= args.length) return null;

                String val = args[i+1];
                if(!val.startsWith("-")) {
                    if(val.startsWith("\"")) {
                        i++;
                        if(!val.endsWith("\"")) {
                            while (i < args.length) {
                                i++;

                                String __a = args[i];
                                val = val + " " + __a;

                                if (__a.endsWith("\"") && !__a.equals("\\\"")) {
                                    break;
                                }
                            }
                        }

                        val = val.replaceAll("\\\\", "");
                        val = val.substring(1, val.length()-1);
                    }
                    return val;
                } else return null;
            }
        }
        return null;
    }

    public String getString(String arg) {
        return getArgument(arg);
    }

    public Character getCharacter(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return str.charAt(0);
    }

    public Byte getByte(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return Byte.parseByte(str);
    }

    public Short getShort(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return Short.parseShort(str);
    }

    public Integer getInt(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return Integer.parseInt(str);
    }

    public Long getLong(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return Long.parseLong(str);
    }

    public Float getFloat(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return Float.parseFloat(str);
    }

    public Double getDouble(String arg) {
        String str = getArgument(arg);
        if(str == null) return null;

        return Double.parseDouble(str);
    }

    public Boolean getBoolean(String arg) {
        String str = getArgument(arg);
        if(str == null && hasArgument(arg)) return true;
        if(str == null) return false;

        return Boolean.parseBoolean(str);
    }

}
