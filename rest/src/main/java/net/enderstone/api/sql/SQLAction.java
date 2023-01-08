package net.enderstone.api.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SQLAction<T> {

    T perform(final ResultSet rs) throws SQLException;

}
