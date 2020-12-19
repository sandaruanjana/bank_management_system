package lk.ijse.BankManagementSystem.controller;

import javafx.collections.ObservableList;
import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.TransactionModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionController {

    public static TransactionModel searchAccountBy(int ACID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select c.name, a.ACID, at.name, a.balance, a.status from customer c JOIN account a ON\n" +
                "c.CID=a.CID JOIN account_type at ON at.ATID=a.ATID where a.ACID=?");
        pst.setInt(1,ACID);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new TransactionModel(
                    rst.getString(1),
                    rst.getInt(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5)
            );
        }
        return null;
    }

    public static boolean insertDepositData(int accountID,Double amount) throws SQLException {
        Connection connection=null;

        try {
            connection =DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pst = connection.prepareStatement("insert into transaction values(null,?,?,CURRENT_TIMESTAMP(),'Deposit')");
            pst.setInt(1,accountID);
            pst.setDouble(2,amount);
            boolean isAdded=pst.executeUpdate()>0;

            if (isAdded){
                PreparedStatement pst2 = connection.prepareStatement("update account set balance=balance+? where ACID=?");
                pst2.setDouble(1,amount);
                pst2.setInt(2,accountID);
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
            //e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    public static boolean insertWithdrawData(int accountID,Double amount) throws SQLException {
        Connection connection=null;

        try {
            connection=DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement pst = connection.prepareStatement("insert into transaction values(null,?,?,CURRENT_TIMESTAMP(),'Withdrawal')");
            pst.setInt(1,accountID);
            pst.setDouble(2,amount);
            boolean isAdded=pst.executeUpdate()>0;

            if (isAdded){
                PreparedStatement pst2 = connection.prepareStatement("update account set balance=balance-? where ACID=?");
                pst2.setDouble(1,amount);
                pst2.setInt(2,accountID);
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
            //e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            connection.setAutoCommit(true);
        }
        return false;

    }

    public static void transactionDataUsing(ObservableList list,int accountNumber) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select tr.TRID, c.name, a.ACID, tr.amount, tr.date, tr.type from customer c JOIN account a ON\n" +
                "c.CID=a.CID JOIN transaction tr ON tr.ACID=a.ACID where a.ACID=?");
        pst.setInt(1,accountNumber);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            list.add(
                    new TransactionModel(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getInt(3),
                            rst.getString(6),
                            rst.getDouble(4),
                            rst.getTimestamp(5)
                    )
            );
        }

    }

}
