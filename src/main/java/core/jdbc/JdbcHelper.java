package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.common.collect.Lists;

public class JdbcHelper {

	private void setPreparedStatement(PreparedStatement pstmt,
			Object... pstmtParams) throws SQLException {
		for (int i = 1; i <= pstmtParams.length; i++) {
			pstmt.setObject(i, pstmtParams[i - 1]);
		}
	}

	// insert or update
	public void executeSql(String sql, Object... pstmtParams) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			setPreparedStatement(pstmt, pstmtParams);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> selectAll(String selectAllSql,
			RowMapper<T> constructor, Object... pstmtParams) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(selectAllSql);) {
			setPreparedStatement(pstmt, pstmtParams);
			ResultSet rs = pstmt.executeQuery();
			List<T> objects = Lists.newArrayList();
			while (rs.next()) {
				objects.add(constructor.createFromRow(rs));
			}
			rs.close();
			return objects;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T selectOne(String selectOneSql,
			RowMapper<T> constructor, Object... pstmtParams) {
		List<T> objects = selectAll(selectOneSql, constructor, pstmtParams);
		if (objects == null || objects.isEmpty()) {
			return null;
		}
		return objects.get(0);
	}
}
