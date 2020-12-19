package lk.ijse.BankManagementSystem.controller;

import lk.ijse.BankManagementSystem.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardController {

    public static int totalCustomerCount() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(" select count(customer.name) from customer");
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }

    public static double totalTransaction() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select sum(amount) from transaction");
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return rst.getDouble(1);
        }
        return 0.00;
    }

    public static int totalLoans() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select count(lid) from loan");
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return rst.getInt(1);
        }
        return 0;
    }
}
