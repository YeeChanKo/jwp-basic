package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import next.model.User;

public class UserDao extends Dao implements Function<ResultSet, User> {
	// interface를 따로 빼서 익명 함수 만들기

	// 생성자 function
	@Override
	public User apply(ResultSet rs) {
		// 익셉션 발생하는 부분 다 끌어들이기 -> 라이브러리에서 처리해줘라
		try {
			return new User(rs.getString("userId"), rs.getString("password"),
					rs.getString("name"), rs.getString("email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insert(User user) {
		executeSql("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(),
				user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) {
		executeSql(
				"UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?",
				user.getPassword(), user.getName(), user.getEmail(),
				user.getUserId());
	}

	public List<User> findAll() {
		return <User>selectAll(
				"SELECT userId, password, name, email FROM USERS",
				new Function<ResultSet, User>() {
					@Override
					public User apply(ResultSet rs) {
						try {
							return new User(rs.getString("userId"),
									rs.getString("password"),
									rs.getString("name"),
									rs.getString("email"));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return null;
					}
				});
	}

	public User findByUserId(String userId) {
		return <User>selectByValue(
				"SELECT userId, password, name, email FROM USERS WHERE userid=?",
				(ResultSet rs) -> {
					try {
						return new User(rs.getString("userId"),
								rs.getString("password"), rs.getString("name"),
								rs.getString("email"));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return null;
				}, userId);
	}
}
