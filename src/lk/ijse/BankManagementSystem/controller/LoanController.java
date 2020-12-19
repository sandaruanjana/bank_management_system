package lk.ijse.BankManagementSystem.controller;

import javafx.collections.ObservableList;
import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Loan;

import java.sql.*;

public class LoanController {

    public static boolean insertLoanData(int accountID,int LTID,double amount,int period,
                                         String firstGuarantorName,String firstGuarantorNic,Integer firstGuarantorTel,
                                         String firstGuarantorAddress,String secondGuarantorName,String secondGuarantorNic,
                                         Integer secondGuarantorTel,String secondGuarantorAddress,double total) throws SQLException {

        int lid;
        int gid;

        Connection connection=null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pst = connection.prepareStatement("insert into loan values(null,?,?,?,?,CURRENT_TIMESTAMP(),?,DATE_ADD(date, INTERVAL 1 MONTH))",Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1,accountID);
            pst.setInt(2,LTID);
            pst.setDouble(3,amount);
            pst.setInt(4,period);
            pst.setDouble(5,total);

            boolean isAdded= pst.executeUpdate()>0;


            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {

                    lid=(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating loan failed, no ID obtained.");
                }
            }


            if (isAdded){
                PreparedStatement pst2 = connection.prepareStatement("INSERT INTO guarantor VALUES(null,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
                pst2.setString(1,firstGuarantorName);
                pst2.setString(2,firstGuarantorNic);
                pst2.setInt(3,firstGuarantorTel);
                pst2.setString(4,firstGuarantorAddress);
                pst2.setString(5,secondGuarantorName);
                pst2.setString(6,secondGuarantorNic);
                pst2.setInt(7,secondGuarantorTel);
                pst2.setString(8,secondGuarantorAddress);

                boolean isAdded2=pst2.executeUpdate()>0;

                try (ResultSet generatedKeys = pst2.getGeneratedKeys()) {
                    if (generatedKeys.next()) {

                        gid=(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating guarantor failed, no ID obtained.");
                    }
                }

                
                if (isAdded2){

                    PreparedStatement pst3 = connection.prepareStatement("insert into transaction values(null,?,?,CURRENT_TIMESTAMP(),'Deposit')");
                    pst3.setInt(1,accountID);
                    pst3.setDouble(2,amount);

                    boolean isAdded3=pst3.executeUpdate()>0;

                    if (isAdded3){

                        PreparedStatement pst4 = connection.prepareStatement("update account set balance=balance+? where ACID=?");
                        pst4.setDouble(1,amount);
                        pst4.setInt(2,accountID);
                        boolean isAdded4=pst4.executeUpdate()>0;

                        if (isAdded4){

                            PreparedStatement pst5 = connection.prepareStatement("insert loan_guarantor values(null,?,?)");
                            pst5.setInt(1,lid);
                            pst5.setInt(2,gid);

                            boolean isAdded5=pst5.executeUpdate()>0;

                            if (isAdded5){
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

                    }else {
                        connection.rollback();
                        return false;
                    }

                }else {
                    connection.rollback();
                    return false;
                }

            }else {
                connection.rollback();
                return false;
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        connection.rollback();
        return false;
    }

    public static void loadLoanTable(ObservableList list,int accountID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select l.LID, c.name, a.ACID, lt.name, l.amount, l.principal, l.date, l.period, l.end from customer c JOIN account a ON\n" +
                "c.CID=a.CID JOIN loan l ON l.ACID=a.ACID JOIN loan_type lt ON lt.LTID=l.LTID where a.ACID=?");
        pst.setInt(1,accountID);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){

            list.add(
                   new Loan(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getString(4),
                    rst.getDouble(5),
                    rst.getInt(8),
                    rst.getDate(7),
                    rst.getDouble(6),
                    rst.getDate(9))
            );

        }
    }

    public static Loan loanDataForPaymentView(int LID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select c.name, a.ACID, lt.name, l.amount, l.principal, l.date, l.end from customer c JOIN account a ON\n" +
                "c.CID=a.CID JOIN loan l ON l.ACID=a.ACID JOIN loan_type lt ON lt.LTID=l.LTID where l.lid=?");
        pst.setInt(1,LID);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new Loan(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getDate(6),
                    rst.getDouble(5),
                    rst.getDate(7)
            );
        }
        return null;
    }

    public static boolean deleteLoan(int lid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement(" delete from loan where lid=?");
        pst.setInt(1,lid);
        return pst.executeUpdate()>0;
    }

}
