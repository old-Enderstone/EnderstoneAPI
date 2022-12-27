package net.enderstone.api.sql;

import java.util.ArrayList;
import java.util.List;

public class SQLTransaction {

    private SQLConnector connector;

    private final List<SQLStatement> statements = new ArrayList<>();

    /**
     * Set the sql connector used to transact your transaction
     */
    public SQLTransaction withConnector(final SQLConnector connector) {
        this.connector = connector;
        return this;
    }

    /**
     * Add a statement to the transaction.
     * @see #transact(SQLConnector)
     */
    public SQLTransaction withStatement(final SQLStatement statement) {
        this.statements.add(statement);
        return this;
    }

    private void rollback() {
        connector.update("ROLLBACK;");
    }

    private void commit() {
        connector.update("COMMIT;");
    }

    /**
     * Does the same as {@link #transact()} but sets the sql connector instance before invoking the method
     */
    public void transact(final SQLConnector connector) {
        this.connector = connector;
        transact();
    }

    /**
     * Transact the transaction, this will execute all the transactions statements.
     * If an error occurs whilst executing any of the statements, a rollback will be caused.
     * If all statements where executed successfully, all changes will be committed
     */
    public void transact() {
        commit(); // Commit whatever changes have been made before the transaction
        try {
            statements.forEach(sta -> sta.execute(connector));
        } catch(Throwable th) {
            rollback();
            throw new RuntimeException(th);
        }
        commit();
    }

}
