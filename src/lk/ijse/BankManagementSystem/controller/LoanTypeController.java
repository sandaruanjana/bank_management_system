package lk.ijse.BankManagementSystem.controller;

import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.AccountType;
import lk.ijse.BankManagementSystem.model.LoanType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanTypeController {

    public static List<LoanType> selectLoanType() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("select * from loan_type");

        List<LoanType> roleTemp = new ArrayList<>();

        while (rst.next()) {
            roleTemp.add(
                    new LoanType(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getDouble(3)
                    )
            );
        }
        return roleTemp;
    }

    public static LoanType findLoanTypeId(String name) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select * from loan_type where name=?");
        pst.setString(1,name);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new LoanType(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getDouble(3)
            );
        }
    return null;

    }

    public static LoanType findLoanInterest(String name) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select interest_rate from loan_type where name=?");
        pst.setString(1,name);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new LoanType(rst.getDouble(1));
        }
        return null;
    }
}
