import utils.ProjectStatus;
import utils.TaskStatus;
import utils.UserStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final DBManagementService dbService = new DBManagementService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("Система управления базой данных");
        System.out.println("Введите 'стоп' для выхода\n");

        while (true) {
            System.out.println("\nВыберите таблицу для работы:");
            System.out.println("1. Пользователи");
            System.out.println("2. Проекты");
            System.out.println("3. Интеграции");
            System.out.println("4. Задачи");
            System.out.print("Ваш выбор (1-4): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("стоп")) {
                System.out.println("Завершение работы системы...");
                break;
            }

            try {
                int choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        handleUsers();
                        break;
                    case 2:
                        handleProjects();
                        break;
                    case 3:
                        handleIntegrations();
                        break;
                    case 4:
                        handleTasks();
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 1 до 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число или 'стоп' для выхода.");
            }
        }

        scanner.close();
    }

    private static void handleUsers() {
        while (true) {
            System.out.println("\nОперации с таблицей Пользователи:");
            System.out.println("1. Показать всех пользователей");
            System.out.println("2. Найти пользователя по ID");
            System.out.println("3. Найти пользователя по email");
            System.out.println("4. Найти пользователей по имени");
            System.out.println("5. Найти пользователей по статусу");
            System.out.println("6. Найти пользователей по диапазону зарплат");
            System.out.println("7. Создать нового пользователя");
            System.out.println("8. Обновить email пользователя");
            System.out.println("9. Обновить имя пользователя");
            System.out.println("10. Обновить зарплату пользователя");
            System.out.println("11. Удалить пользователя по email");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор (0-11): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("стоп")) {
                return;
            }

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) break;

                switch (choice) {
                    case 1:
                        dbService.selectAllUsers();
                        break;
                    case 2:
                        System.out.print("Введите ID пользователя: ");
                        String idInput = scanner.nextLine();
                        if (!idInput.matches("\\d+")) {
                            System.out.println("Ошибка: ID должен быть целым числом");
                            break;
                        }
                        dbService.findUserById(Integer.parseInt(idInput));
                        break;
                    case 3:
                        System.out.print("Введите email пользователя: ");
                        String email = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        dbService.findUserByEmail(email);
                        break;
                    case 4:
                        System.out.print("Введите имя пользователя: ");
                        String name = validateNotEmpty(scanner.nextLine(), "Имя не может быть пустым");
                        dbService.findUsersByName(name);
                        break;
                    case 5:
                        System.out.println("Доступные статусы: WORKING или CHILLING");
                        System.out.print("Введите статус: ");
                        String statusInput = validateNotEmpty(scanner.nextLine(), "Статус не может быть пустым");
                        try {
                            UserStatus status = UserStatus.valueOf(statusInput.toUpperCase());
                            dbService.findUsersByStatus(status);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неверный статус");
                        }
                        break;
                    case 6:
                        System.out.print("Введите минимальную зарплату: ");
                        String minInput = scanner.nextLine();
                        System.out.print("Введите максимальную зарплату: ");
                        String maxInput = scanner.nextLine();
                        if (!minInput.matches("\\d+(\\.\\d+)?") || !maxInput.matches("\\d+(\\.\\d+)?")) {
                            System.out.println("Ошибка: зарплата должна быть числом");
                            break;
                        }
                        double min = Double.parseDouble(minInput);
                        double max = Double.parseDouble(maxInput);
                        if (min > max) {
                            System.out.println("Ошибка: минимальная зарплата не может быть больше максимальной");
                            break;
                        }
                        dbService.findUsersBySalary(min, max);
                        break;
                    case 7:
                        System.out.print("Введите email: ");
                        String newEmail = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        System.out.print("Введите имя: ");
                        String newName = validateNotEmpty(scanner.nextLine(), "Имя не может быть пустым");
                        System.out.print("Введите зарплату: ");
                        String salaryInput = scanner.nextLine();
                        if (!salaryInput.matches("\\d+(\\.\\d+)?")) {
                            System.out.println("Ошибка: зарплата должна быть числом");
                            break;
                        }
                        double salary = Double.parseDouble(salaryInput);
                        dbService.createUser(newEmail, newName, salary);
                        break;
                    case 8:
                        System.out.print("Введите текущий email: ");
                        String oldEmail = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        System.out.print("Введите новый email: ");
                        String updatedEmail = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        dbService.updateUserEmail(oldEmail, updatedEmail);
                        break;
                    case 9:
                        System.out.print("Введите email пользователя: ");
                        String userEmail = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        System.out.print("Введите новое имя: ");
                        String updatedName = validateNotEmpty(scanner.nextLine(), "Имя не может быть пустым");
                        dbService.updateUserName(userEmail, updatedName);
                        break;
                    case 10:
                        System.out.print("Введите email пользователя: ");
                        String emailForSalary = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        System.out.print("Введите новую зарплату: ");
                        String newSalaryInput = scanner.nextLine();
                        if (!newSalaryInput.matches("\\d+(\\.\\d+)?")) {
                            System.out.println("Ошибка: зарплата должна быть числом");
                            break;
                        }
                        double newSalary = Double.parseDouble(newSalaryInput);
                        dbService.updateUserSalary(emailForSalary, newSalary);
                        break;
                    case 11:
                        System.out.print("Введите email для удаления: ");
                        String emailToDelete = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        dbService.deleteUserByEmail(emailToDelete);
                        System.out.println("Пользователь удален");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 0 до 11.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }

    private static void handleProjects() {
        while (true) {
            System.out.println("\nОперации с таблицей Проекты:");
            System.out.println("1. Показать все проекты");
            System.out.println("2. Найти проект по ID");
            System.out.println("3. Найти проект по имени");
            System.out.println("4. Найти проекты по статусу");
            System.out.println("5. Создать новый проект");
            System.out.println("6. Обновить название проекта");
            System.out.println("7. Изменить статус проекта");
            System.out.println("8. Удалить проект по имени");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор (0-8): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("стоп")) {
                System.exit(0);
            }

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) break;

                switch (choice) {
                    case 1:
                        dbService.selectAllProjects();
                        break;
                    case 2:
                        System.out.print("Введите ID проекта: ");
                        String idInput = scanner.nextLine();
                        if (!idInput.matches("\\d+")) {
                            System.out.println("Ошибка: ID должен быть целым числом");
                            break;
                        }
                        dbService.findProjectById(Integer.parseInt(idInput));
                        break;
                    case 3:
                        System.out.print("Введите название проекта: ");
                        String name = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.findProjectByName(name);
                        break;
                    case 4:
                        System.out.println("Доступные статусы: WORKING или STOPPED");
                        System.out.print("Введите статус: ");
                        String statusInput = validateNotEmpty(scanner.nextLine(), "Статус не может быть пустым");
                        try {
                            ProjectStatus status = ProjectStatus.valueOf(statusInput.toUpperCase());
                            dbService.findProjectsByStatus(status);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неверный статус");
                        }
                        break;
                    case 5:
                        System.out.print("Введите название проекта: ");
                        String newName = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.createNewProject(newName);
                        break;
                    case 6:
                        System.out.print("Введите текущее название проекта: ");
                        String oldName = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        System.out.print("Введите новое название проекта: ");
                        String updatedName = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.updateProjectName(oldName, updatedName);
                        break;
                    case 7:
                        System.out.print("Введите название проекта: ");
                        String projectName = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        System.out.println("Доступные статусы: PROGRESS или STOPPED");
                        System.out.print("Введите новый статус: ");
                        String newStatusInput = validateNotEmpty(scanner.nextLine(), "Статус не может быть пустым");
                        try {
                            ProjectStatus newStatus = ProjectStatus.valueOf(newStatusInput.toUpperCase());
                            dbService.changeProjectStatus(projectName, newStatus);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неверный статус");
                        }
                        break;
                    case 8:
                        System.out.print("Введите название проекта для удаления: ");
                        String nameToDelete = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.deleteProjectByName(nameToDelete);
                        System.out.println("Проект удален");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 0 до 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }

    private static void handleIntegrations() {
        while (true) {
            System.out.println("\nОперации с таблицей Интеграции:");
            System.out.println("1. Показать все интеграции");
            System.out.println("2. Найти интеграцию по ID");
            System.out.println("3. Найти интеграцию по имени");
            System.out.println("4. Найти интеграцию по URL");
            System.out.println("5. Найти интеграции по названию проекта");
            System.out.println("6. Создать новую интеграцию");
            System.out.println("7. Обновить название интеграции");
            System.out.println("8. Обновить URL интеграции");
            System.out.println("9. Обновить проект интеграции");
            System.out.println("10. Удалить интеграцию по имени");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор (0-10): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("стоп")) {
                System.exit(0);
            }

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) break;

                switch (choice) {
                    case 1:
                        dbService.selectAllIntegrations();
                        break;
                    case 2:
                        System.out.print("Введите ID интеграции: ");
                        String idInput = scanner.nextLine();
                        if (!idInput.matches("\\d+")) {
                            System.out.println("Ошибка: ID должен быть целым числом");
                            break;
                        }
                        dbService.findIntegrationById(Integer.parseInt(idInput));
                        break;
                    case 3:
                        System.out.print("Введите название интеграции: ");
                        String name = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        dbService.findIntegrationByName(name);
                        break;
                    case 4:
                        System.out.print("Введите URL интеграции: ");
                        String url = validateNotEmpty(scanner.nextLine(), "URL не может быть пустым");
                        dbService.findIntegrationByURL(url);
                        break;
                    case 5:
                        System.out.print("Введите название проекта: ");
                        String projectName = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.findIntegrationsByProjectName(projectName);
                        break;
                    case 6:
                        System.out.print("Введите название интеграции: ");
                        String newName = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        System.out.print("Введите URL: ");
                        String newUrl = validateNotEmpty(scanner.nextLine(), "URL не может быть пустым");
                        System.out.print("Введите название проекта: ");
                        String newProject = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.createIntegration(newName, newUrl, newProject);
                        break;
                    case 7:
                        System.out.print("Введите текущее название интеграции: ");
                        String oldName = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        System.out.print("Введите новое название интеграции: ");
                        String updatedName = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        dbService.updateIntegrationName(oldName, updatedName);
                        break;
                    case 8:
                        System.out.print("Введите название интеграции: ");
                        String intName = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        System.out.print("Введите новый URL: ");
                        String updatedUrl = validateNotEmpty(scanner.nextLine(), "URL не может быть пустым");
                        dbService.updateIntegrationUrl(intName, updatedUrl);
                        break;
                    case 9:
                        System.out.print("Введите название интеграции: ");
                        String integrationName = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        System.out.print("Введите новое название проекта: ");
                        String updatedProject = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        dbService.updateIntegrationProject(integrationName, updatedProject);
                        break;
                    case 10:
                        System.out.print("Введите название интеграции для удаления: ");
                        String nameToDelete = validateNotEmpty(scanner.nextLine(), "Название интеграции не может быть пустым");
                        dbService.deleteIntegrationByName(nameToDelete);
                        System.out.println("Интеграция удалена");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 0 до 10.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }

    private static void handleTasks() {
        while (true) {
            System.out.println("\nОперации с таблицей Задачи:");
            System.out.println("1. Показать все задачи");
            System.out.println("2. Найти задачу по ID");
            System.out.println("3. Найти задачу по названию");
            System.out.println("4. Найти задачи по статусу");
            System.out.println("5. Создать новую задачу");
            System.out.println("6. Обновить название задачи");
            System.out.println("7. Обновить описание задачи");
            System.out.println("8. Обновить статус задачи");
            System.out.println("9. Удалить задачу по названию");
            System.out.println("0. Вернуться в главное меню");
            System.out.print("Ваш выбор (0-9): ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("стоп")) {
                System.exit(0);
            }

            try {
                int choice = Integer.parseInt(input);

                if (choice == 0) break;

                switch (choice) {
                    case 1:
                        dbService.selectAllTasks();
                        break;
                    case 2:
                        System.out.print("Введите ID задачи: ");
                        String idInput = scanner.nextLine();
                        if (!idInput.matches("\\d+")) {
                            System.out.println("Ошибка: ID должен быть целым числом");
                            break;
                        }
                        dbService.selectTaskById(Integer.parseInt(idInput));
                        break;
                    case 3:
                        System.out.print("Введите название задачи: ");
                        String name = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        dbService.selectTaskByName(name);
                        break;
                    case 4:
                        System.out.println("Доступные статусы: DONE или UNDONE");
                        System.out.print("Введите статус: ");
                        String statusInput = validateNotEmpty(scanner.nextLine(), "Статус не может быть пустым");
                        try {
                            TaskStatus status = TaskStatus.valueOf(statusInput.toUpperCase());
                            dbService.selectAllByStatus(status);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неверный статус");
                        }
                        break;
                    case 5:
                        System.out.print("Введите название задачи: ");
                        String newName = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        System.out.print("Введите описание: ");
                        String description = validateNotEmpty(scanner.nextLine(), "Описание не может быть пустым");
                        System.out.print("Введите срок выполнения (yyyy-MM-dd): ");
                        String dateInput = validateNotEmpty(scanner.nextLine(), "Дата не может быть пустой");
                        Date deadline;
                        try {
                            deadline = dateFormat.parse(dateInput);
                        } catch (ParseException e) {
                            System.out.println("Ошибка: неверный формат даты. Используйте yyyy-MM-dd");
                            break;
                        }
                        System.out.print("Введите название проекта: ");
                        String projectName = validateNotEmpty(scanner.nextLine(), "Название проекта не может быть пустым");
                        System.out.print("Введите email исполнителя: ");
                        String email = validateNotEmpty(scanner.nextLine(), "Email не может быть пустым");
                        dbService.createNewTask(newName, description, deadline, projectName, email);
                        break;
                    case 6:
                        System.out.print("Введите текущее название задачи: ");
                        String oldName = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        System.out.print("Введите новое название задачи: ");
                        String updatedName = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        dbService.updateTaskName(oldName, updatedName);
                        break;
                    case 7:
                        System.out.print("Введите название задачи: ");
                        String taskName = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        System.out.print("Введите новое описание: ");
                        String newDesc = validateNotEmpty(scanner.nextLine(), "Описание не может быть пустым");
                        dbService.updateTaskDescription(taskName, newDesc);
                        break;
                    case 8:
                        System.out.print("Введите название задачи: ");
                        String nameForStatus = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        System.out.println("Доступные статусы: DONE или UNDONE");
                        System.out.print("Введите новый статус: ");
                        String newStatusInput = validateNotEmpty(scanner.nextLine(), "Статус не может быть пустым");
                        try {
                            TaskStatus newStatus = TaskStatus.valueOf(newStatusInput.toUpperCase());
                            dbService.updateTaskStatus(nameForStatus, newStatus);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка: неверный статус");
                        }
                        break;
                    case 9:
                        System.out.print("Введите название задачи для удаления: ");
                        String nameToDelete = validateNotEmpty(scanner.nextLine(), "Название задачи не может быть пустым");
                        dbService.deleteTaskByName(nameToDelete);
                        System.out.println("Задача удалена");
                        break;
                    default:
                        System.out.println("Неверный выбор. Введите число от 0 до 9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }

    private static String validateNotEmpty(String input, String errorMessage) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return input.trim();
    }
}
