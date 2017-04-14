package next.dao;

import java.util.List;

import core.jdbc.JdbcHelper;
import core.jdbc.RowMapper;
import next.model.User;

public class UserDao {
	private JdbcHelper daoHelper = new JdbcHelper();
	private RowMapper<User> constructor = (rs) -> {
		return new User(rs.getString("userId"), rs.getString("password"),
				rs.getString("name"), rs.getString("email"));
	};

	public void insert(User user) {
		daoHelper.executeSql("INSERT INTO USERS VALUES (?, ?, ?, ?)",
				user.getUserId(), user.getPassword(), user.getName(),
				user.getEmail());
	}

	public void update(User user) {
		daoHelper.executeSql(
				"UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?",
				user.getPassword(), user.getName(), user.getEmail(),
				user.getUserId());
	}

	public List<User> findAll() {
		return daoHelper.<User>selectAll(
				"SELECT userId, password, name, email FROM USERS", constructor);

	}

	public User findByUserId(String userId) {
		return daoHelper.<User>selectOne(
				"SELECT userId, password, name, email FROM USERS WHERE userid=?",
				constructor, userId);
	}
}
