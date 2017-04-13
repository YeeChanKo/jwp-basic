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
			String... pstmtParams) throws SQLException {
		for (int i = 1; i <= pstmtParams.length; i++) {
			pstmt.setString(i, pstmtParams[i - 1]);
		}
	}

	// insert or update
	protected void executeSql(String sql, String... pstmtParams) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			setPreparedStatement(pstmt, pstmtParams);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected <T> List<T> selectAll(String selectAllSql,
			Function<ResultSet, T> constructor, String... resultSetParams) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(selectAllSql);) {
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

	public <T> T selectByValue(String selectByValueSql,
			Function<ResultSet, T> constructor, String value) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con
						.prepareStatement(selectByValueSql);) {
			setPreparedStatement(pstmt, value);
			ResultSet rs = pstmt.executeQuery();
			T object = null;
			if (rs.next()) {
				object = constructor.apply(rs);
			}
			rs.close();
			return object;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
