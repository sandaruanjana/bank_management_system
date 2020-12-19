package lk.ijse.BankManagementSystem.controller;

import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.AccountType;
import lk.ijse.BankManagementSystem.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountTypeController {
    public static List<AccountType> selectAccount() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("select * from account_type");

        List<AccountType> roleTemp = new ArrayList<>();

        while (rst.next()) {
            roleTemp.add(
                    new AccountType(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getInt(3))
            );
        }
        return roleTemp;
    }

    public static AccountType findAccountTypeID(String type) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select * from account_type where name=?");
        pst.setString(1,type);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new AccountType(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
        }
        return null;

    }
}
