package net.enderstone.api.sql;

public class SQLStatement {

    private String statement;
    private Object[] arguments;

    public SQLStatement() { }

    public SQLStatement(final String statement) {
        this.statement = statement;
    }

    public SQLStatement(final String statement, final Object... arguments) {
        this.statement = statement;
        this.arguments = arguments;
    }

    /**
     * Set the sql statement, like "select `uId` from `Player` where `lastKnownName`=?;"
     */
    public SQLStatement withStatement(final String command) {
        this.statement = command;
        return this;
    }

    /**
     * Set the arguments of the specified sql statement
     */
    public SQLStatement withArguments(final Object... arguments) {
        this.arguments = arguments;
        return this;
    }


    public String getStatement() {
        return statement;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void execute(final SQLConnector connector) {
        if(arguments == null || arguments.length == 0) {
            connector.update(statement);
            return;
        }
        connector.update(statement, arguments);
    }

}
