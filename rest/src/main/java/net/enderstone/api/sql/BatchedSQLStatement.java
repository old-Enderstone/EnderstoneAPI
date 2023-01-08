package net.enderstone.api.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchedSQLStatement {

    private final String statement;
    private final List<Object[]> parameters = new ArrayList<>();

    public BatchedSQLStatement(final String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    public List<Object[]> getParameters() {
        return parameters;
    }

    public void addParameterSet(final Object[] parameters) {
        this.parameters.add(parameters);
    }

    public void execute(final SQLConnector connector) {
        try (PreparedStatement ps = connector.createPreparedStatement(statement)) {

            System.out.println("executing batch, parameters: " + parameters.size());

            for (Object[] parameterSet : parameters) {
                for (int i = 0; i < parameterSet.length; i++) {
                    ps.setObject(i, parameterSet[i]);
                }
                ps.addBatch();
            }

            ps.execute();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

}
