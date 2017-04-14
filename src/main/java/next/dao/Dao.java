package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;

import core.jdbc.ConnectionManager;

public class Dao {

	private void setPreparedStatement(PreparedStatement pstmt,
			Object... pstmtParams) throws SQLException {
		for (int i = 1; i <= pstmtParams.length; i++) {
			pstmt.setObject(i, pstmtParams[i - 1]);
		}
	}

	// insert or update
	protected void executeSql(String sql, Object... pstmtParams) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			setPreparedStatement(pstmt, pstmtParams);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected <T> List<T> selectAll(String selectAllSql,
			Function<ResultSet, T> constructor, Object... pstmtParams) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(selectAllSql);) {
			setPreparedStatement(pstmt, pstmtParams);
			ResultSet rs = pstmt.executeQuery();
			List<T> objects = Lists.newArrayList();
			while (rs.next()) {
				objects.add(constructor.apply(rs));
			}
			rs.close();
			return objects;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected <T> T selectByValue(String selectByValueSql,
			Function<ResultSet, T> constructor, Object... value) {
		List<T> values = selectAll(selectByValueSql, constructor, value);
		if (values == null || values.isEmpty()) {
			return null;
		}
		return values.get(0);
	}
}
