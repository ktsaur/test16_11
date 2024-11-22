package ru.kpfu.dao;

import ru.kpfu.entity.User;
import ru.kpfu.util.ConnectionProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private ConnectionProvider connectionProvider;

        public UserDao(ConnectionProvider connectionProvider) {
            this.connectionProvider = connectionProvider;
        }

        public User getUser(String login, String password) throws Exception {
            try {
                PreparedStatement st = this.connectionProvider.getCon().prepareStatement("SELECT * FROM users WHERE login = ? AND password = MD5(?)");
                st.setString(1, login);
                st.setString(2, password);
                ResultSet result = st.executeQuery();
                boolean hasOne = result.next();
                if (hasOne) {
                    return new User(
                            result.getInt("id"),
                            result.getString("login"),
                            null,
                            result.getString("email")
                    );
                } else { return null; }
            } catch (SQLException e) {
                throw new Exception("Can't get user from db.", e);
            }
        }

    public boolean addUser(User user) throws Exception {
        try {
            PreparedStatement st = this.connectionProvider.getCon().prepareStatement("INSERT INTO users (login, password, email) VALUES (?, ?, ?) ");
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            int affectedRows = st.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new Exception("Can't add user from db.", e);
        }
    }

    public void logLoginAttempt(String username, boolean success) throws Exception {
        String sql = "INSERT INTO login_attempts (username, success) VALUES (?, ?)";
        try (PreparedStatement statement = this.connectionProvider.getCon().prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setBoolean(2, success);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error logging login attempt.", e);
        }
    }

    public void addCities(String login, String city) {
            String sql = "INSERT INTO cities (login, city) VALUES (?, ?)";
            try (PreparedStatement statement = this.connectionProvider.getCon().prepareStatement(sql)) {
                statement.setString(1, login);
                statement.setString(2, city);
                statement.executeUpdate();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
    }

}
