package lk.ijse.BankManagementSystem.model;

public class User {
    int id;
    int roleID;
    String username;
    String password;

    public User() {
    }

    public User(int id, int roleID, String username, String password) {
        this.id = id;
        this.roleID = roleID;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", roleID=" + roleID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
