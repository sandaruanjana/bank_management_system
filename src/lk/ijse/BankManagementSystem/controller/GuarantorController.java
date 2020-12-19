package lk.ijse.BankManagementSystem.controller;

import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Guarantor;
import lk.ijse.BankManagementSystem.model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuarantorController {

    public static Guarantor loansGuarantors(int lid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select g.gid, g.gname, g.gnic, g.gtel, g.gaddress, g.g2name, g.g2nic, g.g2tel, g.g2address from guarantor g JOIN loan_guarantor lg ON\n" +
                "g.gid=lg.gid JOIN loan l ON l.lid=lg.lid where l.lid=?");
        pst.setInt(1,lid);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new Guarantor(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getInt(8),
                    rst.getString(9)
            );
        }
        return null;
    }
}
