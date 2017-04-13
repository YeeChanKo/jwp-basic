package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

import next.model.User;

public class UserDao extends Dao implements Function<ResultSet, User> {

	// 생성자 function
	@Override
	public User apply(ResultSet rs) {
		try {
			return new User(rs.getString("userId"), rs.getString("password"),
					rs.getString("name"), rs.getString("email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insert(User user) {
		super.insert("INSERT INTO USERS VALUES (?, ?, ?, ?)", user.getUserId(),
				user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) {
		super.update(
				"UPDATE USERS set password = ?, name = ?, email = ? WHERE userId = ?",
				user.getPassword(), user.getName(), user.getEmail(),
				user.getUserId());
	}

	public List<User> findAll() {
		return super.<User>selectAll(
				"SELECT userId, password, name, email FROM USERS", this);
	}

	public User findByUserId(String userId) {
		return super.<User>selectByValue(
				"SELECT userId, password, name, email FROM USERS WHERE userid=?",
				this, userId);
	}
}
