package lk.ijse.BankManagementSystem.controller;

import javafx.collections.ObservableList;
import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Loan;
import lk.ijse.BankManagementSystem.model.LoanPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanPaymentController {


    public static void loadPaymentDetails(ObservableList list, int lid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select lp.lpid, c.name, lp.lid, lp.date, lp.fine, lp.amount, lp.payment_method from customer c JOIN account a ON\n" +
                "c.CID=a.CID JOIN loan l ON l.ACID=a.ACID JOIN loan_payment lp ON lp.lid=l.lid where l.lid=?");
        pst.setInt(1,lid);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            list.add(
                    new LoanPayment(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getDate(4),
                            rst.getDouble(5),
                            rst.getDouble(6),
                            rst.getString(7)
                    )
            );
        }
    }






    public static boolean insertLoanPaymentData(int lid,double fine,double amount,String paymentMethod) throws SQLException {
        Connection connection=null;

        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pst = connection.prepareStatement("insert into loan_payment values(null,?,CURRENT_TIMESTAMP(),?,?,?)");
            pst.setInt(1,lid);
            pst.setDouble(2,fine);
            pst.setDouble(3,amount);
            pst.setString(4,paymentMethod);

            boolean isAdded=pst.executeUpdate()>0;

            if (isAdded){
                PreparedStatement pst2 = connection.prepareStatement("update loan set principal=principal-?, end=DATE_ADD(end, INTERVAL 1 MONTH) where lid=?");
                pst2.setDouble(1,amount);
                pst2.setInt(2,lid);

                boolean isAdded2=pst2.executeUpdate()>0;

                if (isAdded2){
                    connection.commit();
                    return true;
                }else {
                    connection.rollback();
                    return false;
                }

            }else {
                connection.rollback();
                return false;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        connection.rollback();
        return false;
    }




    public static Loan getDataForCalculate(int lid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select l.amount, lt.interest_rate, l.period, l.principal,bank_settings.loan_fine from bank_settings,loan l JOIN loan_type lt ON\n" +
                "lt.LTID=l.LTID where lid=?");
        pst.setInt(1,lid);

        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new Loan(
                    rst.getDouble(1),
                    rst.getInt(3),
                    rst.getDouble(4),
                    rst.getDouble(2),
                    rst.getDouble(5)
            );
        }
        return null;
    }



    public static LoanPayment calculateFine(int lid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT IF(DATEDIFF(end,CURDATE())  < 0 , amount/100*bank_settings.loan_fine/365*ABS(DATEDIFF(end,CURDATE())) , 0)\n" +
                "from loan CROSS JOIN bank_settings  where lid=?");
        pst.setInt(1,lid);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new LoanPayment(rst.getDouble(1));
        }
        return null;
    }

}
