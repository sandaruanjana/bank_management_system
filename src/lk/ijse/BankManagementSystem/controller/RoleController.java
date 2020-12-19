package lk.ijse.BankManagementSystem.controller;

import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Role;
import lk.ijse.BankManagementSystem.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleController {
    public static List<Role> role() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("select * from role");

        List<Role> roleTemp = new ArrayList<>();

        while (rst.next()){
            roleTemp.add(
                    new Role(rst.getInt(1),
                            rst.getString(2))
            );

        }
        return roleTemp;
    }

    public static List<Role> findRole(int ID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select * from role where roleID=?");
        pst.setInt(1,ID);
        ResultSet rst = pst.executeQuery();

        List<Role> roletemp=new ArrayList<>();

        while (rst.next()){
           roletemp.add(
                   new Role(
                           rst.getInt(1),
                           rst.getString(2))
                   );
        }
        return roletemp;

    }

    public static Role findRoleID(Object name) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select * from role where roleName=?");
        pst.setObject(1,name);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new Role(
                    rst.getInt(1),
                    rst.getString(2));
        }
        return null;
    }
}
