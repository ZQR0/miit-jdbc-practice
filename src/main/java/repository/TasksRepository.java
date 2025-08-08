package repository;

import model.Task;
import utils.TaskStatus;

import java.util.Date;
import java.util.List;

public interface TasksRepository {
    // Read operations
    List<Task> selectAllTasks();
    Task selectTaskById(int id);
    Task selectTaskByName(String name);
    List<Task> selectAllByStatus(TaskStatus status);

    // Create operations
    int createNewTask(String name, String description, Date deadline, String projectName, String implementerEmail);

    // Update operations
    String updateTaskName(String oldName, String newName);
    String updateTaskDescription(String name, String description);
    String updateTaskStatus(String name, TaskStatus status);

    // Delete operations
    void deleteTaskByName(String name);

}
