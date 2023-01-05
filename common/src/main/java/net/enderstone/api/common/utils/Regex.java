package net.enderstone.api.common.utils;

public class Regex {

    public static final String UUID = "[a-f0-9]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}";
    public static final String NUMBER = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
    public static final String PROPERTY = "[a-zA-Z0-9_]+";

    public static final String PROPERTY_VALUE = "[^/?#]+";

    public static final String LOCALE ="[A-Za-z]{2}-[A-Za-z]{2}"; // TODO: Not Valid in some cases, needs to be updated

}
