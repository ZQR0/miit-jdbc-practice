package repository;

import model.User;
import utils.UserStatus;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    // Read operations
    List<User> selectAllUsers();
    User findUserById(int id);
    User findUserByEmail(String email);
    List<User> findUsersByName(String name);
    List<User> selectUsersByStatus(UserStatus status);
    List<User> selectUsersBySalary(double lowPos, double highPos);

    // Create operations
    int createNewUser(String email, String name, double salary);

    // Update operations
    String updateUserEmail(String oldEmail, String newEmail);
    String updateUserName(String email, String newName);
    String updateUserSalary(String email, double newSalary);

    // Delete operations
    void deleteUserByEmail(String email);
}
