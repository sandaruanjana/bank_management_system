package lk.ijse.BankManagementSystem.controller;

import javafx.collections.ObservableList;
import lk.ijse.BankManagementSystem.db.DBConnection;
import lk.ijse.BankManagementSystem.model.Customer;

import java.sql.*;

public class CustomerController {

    public static void customerData(ObservableList list) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet rst = con.createStatement().executeQuery("select * from customer");

        while (rst.next()){
            list.add(
                    new Customer(
                            rst.getInt(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getDate(5),
                            rst.getInt(6),
                            rst.getDate(7),
                            rst.getString(8),
                            rst.getString(9)
                    )
            );
        }
    }


    public static boolean addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("insert into customer values(NULL,?,?,?,?,?,CURRENT_TIMESTAMP(),?,?)");
        pst.setString(1,customer.getNic());
        pst.setString(2,customer.getName());
        pst.setString(3,customer.getAddress());
        pst.setDate(4,(Date) customer.getDOB());
        pst.setInt(5,customer.getTel());
        pst.setString(6,customer.getOccupation());
        pst.setString(7,customer.getGender());
        return pst.executeUpdate()>0;

    }

    public static boolean updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("Update customer set nic=?, name =?, address=?, DOB=?, tel=?, date=CURRENT_TIMESTAMP(), occupation=?, gender=? where CID=?");
        pst.setString(1,customer.getNic());
        pst.setString(2,customer.getName());
        pst.setString(3,customer.getAddress());
        pst.setDate(4,(Date) customer.getDOB());
        pst.setInt(5,customer.getTel());
        pst.setString(6,customer.getOccupation());
        pst.setString(7,customer.getGender());
        pst.setInt(8,customer.getCID());
        return pst.executeUpdate()>0;
    }

    public static boolean deleteCustomer(int cID) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("Delete from customer where CID=?");
        pst.setInt(1,cID);
        return pst.executeUpdate()>0;

    }


    public static Customer searchCustomerBy(String search) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM customer WHERE (nic=?) OR (name=?)");
        pst.setString(1,search);
        pst.setString(2,search);
        ResultSet rst = pst.executeQuery();

        if (rst.next()){
            return new Customer(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getInt(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getString(9)

            );

        }
        return null;
    }

    public static Customer findCustomerID(String name) throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM customer WHERE nic=?");
        pst.setString(1,name);
        ResultSet rst = pst.executeQuery();

        while (rst.next()){
            return new Customer(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getDate(5),
                    rst.getInt(6),
                    rst.getDate(7),
                    rst.getString(8),
                    rst.getString(9)
            );
        }
        return null;
    }
}
