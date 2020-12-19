package lk.ijse.BankManagementSystem.controller;

import javafx.collections.ObservableList;
import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Account;
import lk.ijse.BankManagementSystem.model.AccountModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountController {
    public static void accountData(ObservableList list) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("select * from account");

        while (rst.next()){
            list.add(
                    new Account(
                            rst.getInt(1),
                            rst.getInt(2),
                            rst.getInt(3),
                            rst.getDouble(4),
                            rst.getDate(5),
                            rst.getString(6)
                    )
            );
        }

    }

    public static void accountDataUsing(ObservableList list,int cid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("select a.ACID,c.name,at.name as AccountType,a.balance,a.date,a.status from customer c JOIN account a ON\n" +
                "c.CID=a.CID JOIN account_type at ON at.ATID=a.ATID where c.CID=?");
        pst.setInt(1,cid);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){

            list.add(
                    new AccountModel(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4),
                            rst.getDate(5),
                            rst.getString(6)
                    )
            );


        }

    }

    public static boolean insertAccountData(int cid,int atid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("insert into account values(NULL,?,?,0,CURRENT_TIMESTAMP(),'Active')");
        pst.setInt(1,cid);
        pst.setInt(2,atid);
        return pst.executeUpdate()>0;
    }

    public static boolean deleteAccountData(int acid) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("Delete from account where ACID=?");
        pst.setInt(1,acid);
        return pst.executeUpdate()>0;
    }

    public static boolean updateAccountData(int ACID,String status) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("Update account set status=? where ACID=?");
        pst.setString(1,status);
        pst.setInt(2,ACID);
        return pst.executeUpdate()>0;
    }
}
