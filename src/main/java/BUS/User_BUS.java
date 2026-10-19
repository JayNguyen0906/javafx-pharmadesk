package BUS;

import DAO.User_DAO;
import DTO.User;

import java.util.List;

public class User_BUS {
    private final User_DAO userDAO;

    public User_BUS() {
        this.userDAO = new User_DAO(); 
    }

    public boolean addUser(User user) {
        if (userDAO.findByUsername(user.getUsername()) != null) {
            System.out.println("Tên đăng nhập đã tồn tại!");
            return false;
        }
        return userDAO.insertUser(user);
    }

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public String getFullName (String username) {
    	return userDAO.getFullName(username);
    }
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int userId) {
        return userDAO.softDeleteUser(userId);
    }

    public List<User> getAllUsers(boolean onlyActive) {
        return userDAO.getAllUsers(onlyActive);
    }
}
