package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
	public abstract T createFromRow(ResultSet rs) throws SQLException;
}