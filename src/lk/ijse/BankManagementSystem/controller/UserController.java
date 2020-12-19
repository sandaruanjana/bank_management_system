package lk.ijse.BankManagementSystem.controller;

import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Role;
import lk.ijse.BankManagementSystem.model.User;

import java.sql.*;

public class UserController {

    public static boolean userLogin(String name, String password) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select * from user where username=? and md5(?)");
        pst.setString(1,name);
        pst.setString(2,password);
        ResultSet rst = pst.executeQuery();
        if (rst.next()){
            return true;
        }
        return false;
    }


    public static Role findRoleId(String username) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select r.roleid from role r JOIN user u ON\n" +
                "r.roleid=u.roleid where username=?");
        pst.setString(1,username);
        ResultSet rst = pst.executeQuery();

        if (rst.next()){
            return new Role(rst.getInt(1));
        }
        return null;

    }


    public static boolean AddUserDate(User user) throws SQLException, ClassNotFoundException {
        Connection con= DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("insert into user values(NULL,?,?,md5(?))");
        pst.setObject(1,user.getRoleID());
        pst.setObject(2,user.getUsername());
        pst.setObject(3,user.getPassword());

        return pst.executeUpdate()>0;
    }

    public static User searchUser(String name) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        Statement stm = con.createStatement();
        String SQL="Select * From user where username='"+name+"'";
        ResultSet rst = stm.executeQuery(SQL);
        if(rst.next()){
            return new User(
                    rst.getInt("id"),
                    rst.getInt("roleID"),
                    rst.getString("username"),
                    rst.getString("password"));
        }
        return null;
    }

    public static boolean editUser(int roleID,String username,int userID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("Update user set roleID=?, username=? where id=? ");
        pst.setInt(1,roleID);
        pst.setString(2,username);
        pst.setInt(3,userID);
        return pst.executeUpdate()>0;
    }

    public static boolean removeUser(int userID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("delete from user where id=?");
        pst.setInt(1,userID);
        return pst.executeUpdate()>0;
    }
}
