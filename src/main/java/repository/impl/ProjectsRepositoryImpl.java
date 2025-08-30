package repository.impl;

import model.Project;
import repository.ProjectsRepository;
import utils.ConnectionSingleton;
import utils.ProjectStatus;
import utils.SQLErrorsValidator;


import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectsRepositoryImpl implements ProjectsRepository {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String STATUS = "status";


    @Override
    public List<Project> selectAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM tracker_schema.projects;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Project project = mapResultSetToProject(resultSet);
                projects.add(project);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return projects;
    }

    @Override
    public Project findProjectById(int id) {
        String query = "SELECT * FROM tracker_schema.projects WHERE id = ?;";

        Project project = null;
        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                project = mapResultSetToProject(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return project;
    }

    @Override
    public Project findProjectByName(String name) {
        String query = "SELECT * FROM tracker_schema.projects WHERE name = ? LIMIT 1;";
        Project project = null;

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                project = mapResultSetToProject(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return project;
    }

    @Override
    public List<Project> findProjectsByStatus(String status) {
        String query = "SELECT * FROM tracker_schema.projects WHERE status = ?;";
        List<Project> projects = new ArrayList<>();

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Project project = mapResultSetToProject(resultSet);
                projects.add(project);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(projects);
    }

    //TODO: перевести SQL запрос на транзакции
    @Override
    public int createNewProject(String name) {
        String query = "INSERT INTO tracker_schema.projects (name) VALUES (?);";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Project creation failed");
            }

            int projectId;
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    projectId = rs.getInt(1);
                    connection.commit();
                } else {
                    connection.rollback();
                    throw new SQLException("No ID obtained");
                }
            }

            return projectId;
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //TODO: перевести SQL запрос на транзакции
    @Override
    public String updateProjectName(String oldName, String newName) {
        String query = "UPDATE tracker_schema.projects SET name = ? WHERE name = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

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

    //TODO: перевести SQL запрос на транзакции
    @Override
    public String changeProjectStatus(String name, ProjectStatus status) {
        String query = "UPDATE tracker_schema.projects SET status = ? WHERE name = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status.getValue());
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
    public void deleteProject(String name) {
        String query = "DELETE FROM tracker_schema.projects WHERE name = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);

            statement.executeUpdate();
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }
    }

    private Project mapResultSetToProject(ResultSet rs) throws SQLException {
        String id = rs.getString(ID);
        String name = rs.getString(NAME);
        String status = rs.getString(STATUS);

        return new Project(id, name, status);
    }
}
