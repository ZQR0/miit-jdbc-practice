package repository.impl;

import model.User;
import repository.UserRepository;
import utils.ConnectionSingleton;
import utils.SQLErrorsValidator;
import utils.UserStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    private static final String STATUS = "status";
    private static final String SALARY = "salary";

    @Override
    public List<User> selectAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM tracker_schema.users ORDER BY salary DESC;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String id = resultSet.getString(ID);
                String email = resultSet.getString(EMAIL);
                String name = resultSet.getString(NAME);
                String status = resultSet.getString(STATUS);
                String salary = resultSet.getString(SALARY);

                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(users);
    }

    @Override
    public User findUserById(int id) {
        User user = null;
        String query = "SELECT * FROM tracker_schema.users WHERE id = ? ORDER BY salary DESC;";
        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = mapResultSetToUser(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        User user = null;
        String query = "SELECT * FROM tracker_schema.users WHERE email = ? LIMIT 1";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = mapResultSetToUser(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
        }

        return user;
    }

    @Override
    public List<User> findUsersByName(String name) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM tracker_schema.users WHERE name = ? ORDER BY salary DESC;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(users);
    }

    @Override
    public List<User> selectUsersByStatus(UserStatus status) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM tracker_schema.users WHERE STATUS = ? ORDER BY salary DESC";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status.toString());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(users);
    }

    @Override
    public List<User> selectUsersBySalary(double lowPos, double highPos) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM tracker_schema.users WHERE salary BETWEEN ?::money AND ?::money ORDER BY salary DESC";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(lowPos));
            statement.setString(2, String.valueOf(highPos));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = mapResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(users);
    }


    @Override
    public int createNewUser(String email, String name, double salary) {
        String query = "INSERT INTO tracker_schema.users (email, name, salary) " +
                "VALUES(?, ?, ?::money) RETURNING id, email, name, salary;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, email);
            statement.setString(2, name);
            statement.setString(3, String.valueOf(salary));

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                throw new SQLException("User creation failed");
            }

            int userId = 0;
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    userId = rs.getInt(1);
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            return userId;

        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
            return -1;
        }
    }


    @Override
    public String updateUserEmail(String oldEmail, String newEmail) {
        String query = "UPDATE tracker_schema.users SET email = ? WHERE email = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newEmail);
            statement.setString(2, oldEmail);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return newEmail;
            } else {
                connection.rollback();
                return "NULL";
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return "NULL";
    }


    @Override
    public String updateUserName(String email, String newName) {
        String query = "UPDATE tracker_schema.users SET name = ? WHERE email = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, email);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return newName;
            } else {
                connection.rollback();
                return "NULL";
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return "NULL";
    }


    @Override
    public String updateUserSalary(String email, double newSalary) {
        String query = "UPDATE tracker_schema.users SET salary = ?::money WHERE email = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, String.valueOf(newSalary));
            statement.setString(2, email);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return String.valueOf(newSalary);
            } else {
                connection.rollback();
                return "NULL";
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return "NULL";
    }

    //TODO: перевести SQL запрос на транзакции
    @Override
    public void deleteUserByEmail(String email) {
        String query = "DELETE FROM tracker_schema.users WHERE email = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        String id = rs.getString(ID);
        String email = rs.getString(EMAIL);
        String name = rs.getString(NAME);
        String status = rs.getString(STATUS);
        String salary = rs.getString(SALARY);

        return new User(id, email, name, status, salary);
    }
}
