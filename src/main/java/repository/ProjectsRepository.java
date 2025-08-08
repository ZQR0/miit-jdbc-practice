package repository;

import model.Project;
import utils.ProjectStatus;

import java.sql.SQLException;
import java.util.List;

public interface ProjectsRepository {
    // Read operations
    List<Project> selectAllProjects();
    Project findProjectById(int id);
    Project findProjectByName(String name);
    List<Project> findProjectsByStatus(String status);

    // Create operations
    int createNewProject(String name);

    // Update operations
    String updateProjectName(String oldName, String newName);
    String changeProjectStatus(String name, ProjectStatus status);

    // Delete operations
    void deleteProject(String name);
}
