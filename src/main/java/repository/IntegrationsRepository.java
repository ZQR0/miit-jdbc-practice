package repository;

import model.Integration;
import java.sql.SQLException;
import java.util.List;

public interface IntegrationsRepository {

    // Read operations
    List<Integration> selectAllIntegrations();
    Integration findById(int id);
    Integration findByName(String name);
    Integration findByURL(String url);
    List<Integration> findByProjectName(String projectName);

    // Create operations
    int createNewIntegration(String name, String url, String projectName);

    // Update operations
    String updateIntegrationName(String oldName, String newName);
    String updateIntegrationUrl(String integrationName, String newUrl);
    String updateIntegrationProject(String integrationName, String projectName);

    // Delete operations
    void deleteIntegrationByName(String name);
}
