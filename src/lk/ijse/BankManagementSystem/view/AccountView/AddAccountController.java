package lk.ijse.BankManagementSystem.view.AccountView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.AccountController;
import lk.ijse.BankManagementSystem.controller.AccountTypeController;
import lk.ijse.BankManagementSystem.controller.CustomerController;
import lk.ijse.BankManagementSystem.model.AccountType;
import lk.ijse.BankManagementSystem.model.Customer;
import lk.ijse.BankManagementSystem.model.Role;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddAccountController implements Initializable {

    @FXML
    private AnchorPane anchorPaneAddAccount;

    @FXML
    private JFXComboBox<String> cmbSelectAccountType;

    @FXML
    private JFXComboBox<String> cmbSelectCustomer;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnCreate;

    @FXML
    private JFXTextField txtCustomerNic;

    @FXML
    private Label lblWrongNicNumber;

    public List<AccountType> accountType;

    public List<Customer> customers;

    public static ObservableList<Customer> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        loadAccountType();
        cmbSelectAccountType.getSelectionModel().select(0);

    }



    public void validation(){
        lblWrongNicNumber.setVisible(false);

        RequiredFieldValidator validator1=new RequiredFieldValidator();
        lblWrongNicNumber.setVisible(true);
        txtCustomerNic.getValidators().add(validator1);
        txtCustomerNic.validate();

        RequiredFieldValidator validator2=new RequiredFieldValidator();
        lblWrongNicNumber.setVisible(true);
        cmbSelectAccountType.getValidators().add(validator2);
        cmbSelectAccountType.validate();
    }

    @FXML
    private void closeOnAction(ActionEvent event) {
        ((Stage)anchorPaneAddAccount.getScene().getWindow()).close();
    }

    @FXML
    private void createOnAction(ActionEvent event) {



        if (txtCustomerNic.getText().isEmpty()){
            validation();
        }else {
            try {

                String accountValue = cmbSelectAccountType.getValue();
                String nic = txtCustomerNic.getText().trim();


                AccountType accountTypeID = AccountTypeController.findAccountTypeID(accountValue);
                Customer customerID = CustomerController.findCustomerID(nic);

                if (customerID==null || accountTypeID==null){
                    validation();
                    lblWrongNicNumber.setVisible(true);
                }else {
                    lblWrongNicNumber.setVisible(false);
                    int cid = customerID.getCID();
                    int atid = accountTypeID.getATID();


                    boolean isAdded = AccountController.insertAccountData(cid, atid);

                    if (isAdded){
                        ((Stage)anchorPaneAddAccount.getScene().getWindow()).close();
                    }else {
                        System.out.println("Account Create fail");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void loadAccountType(){
        ObservableList<String> options = FXCollections.observableArrayList();
        int ACTID=0;


        try {
            accountType=AccountTypeController.selectAccount();

            for (AccountType a: accountType){
                options.add(a.getName());
                ACTID=a.getATID();
            }


            cmbSelectAccountType.getItems().addAll(options);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadCustomer(){
        ObservableList<String> options = FXCollections.observableArrayList();

        try {
            CustomerController.customerData(oblist);

            for (Customer c: oblist){
                options.add(c.getName());
            }

            cmbSelectCustomer.getItems().addAll(options);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
