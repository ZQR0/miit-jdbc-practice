import model.Integration;
import model.Project;
import model.Task;
import model.User;
import repository.IntegrationsRepository;
import repository.ProjectsRepository;
import repository.TasksRepository;
import repository.UserRepository;
import repository.impl.IntegrationsRepositoryImpl;
import repository.impl.ProjectsRepositoryImpl;
import repository.impl.TaskRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import utils.ProjectStatus;
import utils.TaskStatus;
import utils.UserStatus;

import java.util.Date;
import java.util.List;

//TODO: сделать проверки на отсутствие чтобы выводить в случае чего нужные данные
public class DBManagementService {

    private final UserRepository userRepository;
    private final ProjectsRepository projectsRepository;
    private final IntegrationsRepository integrationsRepository;
    private final TasksRepository tasksRepository;

    public DBManagementService() {
        this.userRepository = new UserRepositoryImpl();
        this.projectsRepository = new ProjectsRepositoryImpl();
        this.integrationsRepository = new IntegrationsRepositoryImpl();
        this.tasksRepository = new TaskRepositoryImpl();
    }

    // Users logic
    public void selectAllUsers() {
        List<User> users = this.userRepository.selectAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }

    public void findUserById(int id) {
        User user = this.userRepository.findUserById(id);
        if (user == null) {
            System.out.println("Пользователь не найден");
        } else {
            System.out.println(user.toString());
        }
    }

    public void findUserByEmail(String email) {
        User user = this.userRepository.findUserByEmail(email);
        if (user == null) {
            System.out.println("Пользователь не найден");
        } else {
            System.out.println(user.toString());
        }
    }

    public void findUsersByName(String name) {
        List<User> users = this.userRepository.findUsersByName(name);
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }

    public void findUsersByStatus(UserStatus status) {
        List<User> users = this.userRepository.selectUsersByStatus(status);
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }

    public void findUsersBySalary(double lowPos, double highPos) {
        List<User> users = this.userRepository.selectUsersBySalary(lowPos, highPos);
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println();
    }

    public void createUser(String email, String name, double salary) {
        int created = this.userRepository.createNewUser(email, name, salary);
        if (created > 0) {
            System.out.println("Создано");
        } else {
            System.out.println("Ошибка");
        }
    }

    public void updateUserEmail(String oldEmail, String newEmail) {
        String updated = this.userRepository.updateUserEmail(oldEmail, newEmail);
        if (updated.equals(newEmail)) {
            System.out.println("Успешно обновлено");
        } else {
            System.out.println("Не удалось обновить");
        }
    }

    public void updateUserName(String email, String newName) {
        String updated = this.userRepository.updateUserName(email, newName);
        if (updated.equals(newName)) {
            System.out.println("Обновлено");
        } else {
            System.out.println("Не удалось обновить");
        }
    }

    public void updateUserSalary(String email, double newSalary) {
        String updated = this.userRepository.updateUserSalary(email, newSalary);
        if (updated.equals(String.valueOf(newSalary))) {
            System.out.println("Обновлено");
        } else {
            System.out.println("Не удалось обновить");
        }
    }

    public void deleteUserByEmail(String email) {
        this.userRepository.deleteUserByEmail(email);
    }


    //Projects operations
    public void selectAllProjects() {
        List<Project> projects = this.projectsRepository.selectAllProjects();
        for (Project project : projects) {
            System.out.println(project.toString());
        }
        System.out.println();
    }

    public void findProjectById(int id) {
        Project project = this.projectsRepository.findProjectById(id);
        if (project == null) {
            System.out.println("Проект не найден");
        } else {
            System.out.println(project.toString());
        }
    }

    public void findProjectByName(String name) {
        Project project = this.projectsRepository.findProjectByName(name);
        if (project == null) {
            System.out.println("Проект на найден");
        } else {
            System.out.println(project.toString());
        }
    }

    public void findProjectsByStatus(ProjectStatus status) {
        List<Project> projects = this.projectsRepository.findProjectsByStatus(status.getValue());
        for (Project project : projects) {
            System.out.println(project.toString());
        }
        System.out.println();
    }

    public void createNewProject(String name) {
        int created = this.projectsRepository.createNewProject(name);
        if (created > 0) System.out.println("Успешно создано");
        else System.out.println("Не удалось создать");
    }

    public void updateProjectName(String oldName, String newName) {
        String updated = this.projectsRepository.updateProjectName(oldName, newName);
        if (updated.equals(newName)) {
            System.out.println("Обновлено");
        } else {
            System.out.println("Не удалось обновить");
        }
    }

    public void changeProjectStatus(String name, ProjectStatus status) {
        String updated = this.projectsRepository.changeProjectStatus(name, status);
        if (updated.equals(status.getValue())) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    public void deleteProjectByName(String name) {
        this.projectsRepository.deleteProject(name);
    }


    // Integrations operations
    public void selectAllIntegrations() {
        List<Integration> integrations = this.integrationsRepository.selectAllIntegrations();
        for (Integration integration : integrations) {
            System.out.println(integration.toString());
        }
        System.out.println();
    }

    public void findIntegrationById(int id) {
        Integration integration = this.integrationsRepository.findById(id);
        if (integration == null) {
            System.out.println("Не удалось найти");
        } else {
            System.out.println(integration.toString());
        }
    }

    public void findIntegrationByName(String name) {
        Integration integration = this.integrationsRepository.findByName(name);
        if (integration == null) {
            System.out.println("Не удалось найти");
        } else {
            System.out.println(integration.toString());
        }
    }

    public void findIntegrationByURL(String url) {
        Integration integration = this.integrationsRepository.findByURL(url);
        if (integration == null) {
            System.out.println("Не удалось найти");
        } else {
            System.out.println(integration.toString());
        }
    }

    public void findIntegrationsByProjectName(String projectName) {
        List<Integration> integrations = this.integrationsRepository.findByProjectName(projectName);
        for (Integration integration : integrations) {
            System.out.println(integration.toString());
        }
        System.out.println();
    }

    public void createIntegration(String name, String url, String projectName) {
        int created = this.integrationsRepository.createNewIntegration(name, url, projectName);
        if (created > 0) System.out.println("Создано");
        else System.out.println("Не удалось создать, ошибка");
    }

    public void updateIntegrationName(String oldName, String newName) {
        String updated = this.integrationsRepository.updateIntegrationName(oldName, newName);
        if (updated.equals(newName)) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    public void updateIntegrationUrl(String integrationName, String newUrl) {
        String updated = this.integrationsRepository.updateIntegrationUrl(integrationName, newUrl);
        if (updated.equals(newUrl)) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    public void updateIntegrationProject(String integrationName, String projectName) {
        String updated = this.integrationsRepository.updateIntegrationProject(integrationName, projectName);
        if (updated.equals(projectName)) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    void deleteIntegrationByName(String name) {
        this.integrationsRepository.deleteIntegrationByName(name);
    }


    // Tasks operations
    public void selectAllTasks() {
        List<Task> tasks = this.tasksRepository.selectAllTasks();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        System.out.println();
    }

    public void selectTaskById(int id) {
        Task task = this.tasksRepository.selectTaskById(id);
        if (task == null) {
            System.out.println("Не удалось найти");
        } else {
            System.out.println(task.toString());
        }
    }

    public void selectTaskByName(String name) {
        Task task = this.tasksRepository.selectTaskByName(name);
        if (task == null) {
            System.out.println("Не удалось найти");
        } else {
            System.out.println(task.toString());
        }
    }

    public void selectAllByStatus(TaskStatus taskStatus) {
        List<Task> tasks = this.tasksRepository.selectAllByStatus(taskStatus);
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        System.out.println();
    }

    public void createNewTask(String name, String description, Date deadline, String projectName, String implementerEmail) {
        int created = this.tasksRepository.createNewTask(name, description, deadline, projectName, implementerEmail);
        if (created > 0) System.out.println("Успешно создано");
        else System.out.println("Не удалось создать, ошибка");
    }

    public void updateTaskName(String oldName, String newName) {
        String updated = this.tasksRepository.updateTaskName(oldName, newName);
        if (updated.equals(newName)) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    public void updateTaskDescription(String name, String description) {
        String updated = this.tasksRepository.updateTaskDescription(name, description);
        if (updated.equals(description)) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    public void updateTaskStatus(String name, TaskStatus status) {
        String updated = this.tasksRepository.updateTaskStatus(name, status);
        if (updated.equals(status.getValue())) System.out.println("Обновлено");
        else System.out.println("Не удалось обновить");
    }

    public void deleteTaskByName(String name) {
        this.tasksRepository.deleteTaskByName(name);
    }
}
