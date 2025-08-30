package repository.impl;

import model.Integration;
import repository.IntegrationsRepository;
import utils.ConnectionSingleton;
import utils.SQLErrorsValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegrationsRepositoryImpl implements IntegrationsRepository {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String URL = "url";
    private static final String PROJECT_ID = "project_id";
    private static final String PROJECT_NAME = "project_name";


    @Override
    public List<Integration> selectAllIntegrations() {

        String query = "SELECT i.id, i.name, i.url, p.name AS project_name " +
                "FROM tracker_schema.integrations AS i " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON i.project_id = p.id;";

        List<Integration> integrations = new ArrayList<>();

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integration integration = mapResultSetToIntegration(resultSet);
                integrations.add(integration);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(integrations);
    }

    @Override
    public Integration findById(int id) {

        String query = "SELECT i.id, i.name, i.url, p.name AS project_name " +
                "FROM tracker_schema.integrations AS i " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON i.id = p.id " +
                "WHERE i.id = ?;";

        Integration integration = null;

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                integration = mapResultSetToIntegration(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return integration;
    }

    @Override
    public Integration findByName(String name) {
        String query = "SELECT i.id, i.name, i.url, p.name AS project_name " +
                "FROM tracker_schema.integrations AS i " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON i.id = p.id " +
                "WHERE i.name = ?;";

        Integration integration = null;

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                integration = mapResultSetToIntegration(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return integration;
    }

    @Override
    public Integration findByURL(String url) {
        String query = "SELECT i.id, i.name, i.url, p.name AS project_name " +
                "FROM tracker_schema.integrations AS i " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON i.id = p.id " +
                "WHERE i.url = ?;";
        Integration integration = null;

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, url);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                integration = mapResultSetToIntegration(resultSet);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return integration;
    }

    @Override
    public List<Integration> findByProjectName(String projectName) {
        String query = "SELECT i.id, i.name, i.url, p.name AS project_name " +
                "FROM tracker_schema.integrations AS i " +
                "INNER JOIN tracker_schema.projects AS p " +
                "ON i.id = p.id " +
                "WHERE p.name = ?;";

        List<Integration> integrations = new ArrayList<>();

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integration integration = mapResultSetToIntegration(resultSet);
                integrations.add(integration);
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }

        return Collections.unmodifiableList(integrations);
    }

    //TODO: перевести SQL запрос на транзакции
    @Override
    public int createNewIntegration(String name, String url, String projectName) {
        String query = "INSERT INTO tracker_schema.integrations (name, url, project_id) " +
                "VALUES(?, ?, (SELECT id FROM tracker_schema.projects WHERE name = ?));";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, url);
            statement.setString(3, projectName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating integration failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    connection.commit();
                    return generatedKeys.getInt(1);
                } else {
                    connection.rollback();
                    throw new SQLException("Creating integration failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    //TODO: перевести SQL запрос на транзакции
    @Override
    public String updateIntegrationName(String oldName, String newName) {
        String query = "UPDATE tracker_schema.integrations SET name = ? WHERE name = ?";

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
            return "NULL";
        }
    }

    //TODO: перевести SQL запрос на транзакции
    @Override
    public String updateIntegrationUrl(String integrationName, String newUrl) {
        String query = "UPDATE tracker_schema.integrations SET url = ? WHERE name = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newUrl);
            statement.setString(2, integrationName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return newUrl;
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

    //FIXME: починить SQL запрос
    @Override
    public String updateIntegrationProject(String integrationName, String projectName) {
        String query = "update tracker_schema.integrations as i set project_id = (" +
                "select id from tracker_schema.projects as p " +
                "where p.name = ? " +
                "limit 1 " +
                ") " +
                "where i.name = ?;";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, projectName);
            statement.setString(2, integrationName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                return projectName;
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
    public void deleteIntegrationByName(String name) {
        String query = "DELETE FROM tracker_schema.integrations WHERE name = ?";

        try (Connection connection = ConnectionSingleton.INSTANCE.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException ex) {
            SQLErrorsValidator.validate(ex.getSQLState());
            System.out.println(ex.getMessage());
        }
    }

    private Integration mapResultSetToIntegration(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString(ID);
        String name = resultSet.getString(NAME);
        String url = resultSet.getString(URL);
        String projectName = resultSet.getString(PROJECT_NAME);

        return new Integration(id, name, url, projectName);
    }
}
