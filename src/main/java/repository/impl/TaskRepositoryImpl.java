package repository.impl;

import model.Task;
import repository.TasksRepository;
import utils.ConnectionSingleton;
import utils.SQLErrorsValidator;
import utils.TaskStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TaskRepositoryImpl implements TasksRepository {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String STATUS = "status";
    private static final String DEADLINE = "deadline";
    private static final String CREATED_AT = "created_at";
    private static final String PROJECT_ID = "project_id";
    private static final String IMPLEMENTER_ID = "implementer";

    private static final String PROJECT_NAME = "project_name";
    private static final String IMPLEMENTER_EMAIL = "implementer_email";


    @Override
    public List<Task> selectAllTasks() {
        String query = "SELECT t.id, t.name, t.description, t.status, t.deadline, t.created_at, p.name AS project_name, u.email AS implementer_email" +
                "FROM tracker_schema.tasks AS t " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON t.project_id = p.id " +
                "INNER JOIN tracker_schema.users AS u " +
                "ON t.implementer = u.id;";

        List<Task> tasks = new ArrayList<>();

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(tasks);
    }

    @Override
    public Task selectTaskById(int id) {
        String query = "SELECT t.id, t.name, t.description, t.status, t.deadline, t.created_at, p.name AS project_name, u.email AS implementer_email" +
                "FROM tracker_schema.tasks AS t " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON t.project_id = p.id " +
                "INNER JOIN tracker_schema.users AS u " +
                "ON t.implementer = u.id " +
                "WHERE t.id = ?;";

        Task task = null;
        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                task = mapResultSetToTask(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return task;
    }

    @Override
    public Task selectTaskByName(String name) {
        String query = "SELECT t.id, t.name, t.description, t.status, t.deadline, t.created_at, p.name AS project_name, u.email AS implementer_email" +
                "FROM tracker_schema.tasks AS t " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON t.project_id = p.id " +
                "INNER JOIN tracker_schema.users AS u " +
                "ON t.implementer = u.id " +
                "WHERE t.name = ?;";
        Task task = null;

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                task = mapResultSetToTask(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return task;
    }

    @Override
    public List<Task> selectAllByStatus(TaskStatus status) {
        String query = "SELECT t.id, t.name, t.description, t.status, t.deadline, t.created_at, p.name AS project_name, u.email AS implementer_email" +
                "FROM tracker_schema.tasks AS t " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON t.project_id = p.id " +
                "INNER JOIN tracker_schema.users AS u " +
                "ON t.implementer = u.id " +
                "WHERE t.status = ?;";
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status.getValue());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Task task = mapResultSetToTask(resultSet);
                tasks.add(task);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(tasks);
    }


    @Override
    public int createNewTask(String name, String description, Date deadline, String projectName, String implementerEmail) {
        String query = "INSERT INTO tracker_schema.tasks (name, description, deadline, project_id, implementer) " +
                "VALUES (?, ?, ?, " +
                "(SELECT id FROM tracker_schema.projects WHERE name = ?), " +
                "(SELECT id FROM tracker_schema.users WHERE email = ?));";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setString(3, deadline.toString());
            statement.setString(4, projectName);
            statement.setString(5, implementerEmail);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating task failed, no rows affected.");
            }

            int taskId;
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    taskId = rs.getInt(1);
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("Creating task failed, no ID obtained.");
                }
            }

            return taskId;
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
            return -1;
        }
    }


    @Override
    public String updateTaskName(String oldName, String newName) {
        String query = "UPDATE tracker_schema.tasks SET name = ? WHERE name = ?";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setString(2, oldName);

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
    public String updateTaskDescription(String name, String description) {
        String query = "UPDATE tracker_schema.tasks SET description = ? WHERE name = ?";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, description);
            statement.setString(2, name);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return description;
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
    public String updateTaskStatus(String name, TaskStatus status) {
        String query = "UPDATE tracker_schema.tasks SET status = ? WHERE name = ?";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status.toString());
            statement.setString(2, name);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return status.getValue();
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
    public void deleteTaskByName(String name) {
        String query = "DELETE FROM tracker_schema.tasks WHERE name = ?";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }
    }


    private Task mapResultSetToTask(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(ID);
        String name = resultSet.getString(NAME);
        String description = resultSet.getString(DESCRIPTION);
        String status = resultSet.getString(STATUS);
        Date deadline = resultSet.getDate(DEADLINE);
        Date createdAt = resultSet.getDate(CREATED_AT);
        String projectName = resultSet.getString(PROJECT_NAME);
        String implementerEmail = resultSet.getString(IMPLEMENTER_EMAIL);

        return new Task(id, name, description, status, deadline, createdAt, projectName, implementerEmail);
    }
}
