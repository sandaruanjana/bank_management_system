package lk.ijse.BankManagementSystem.view.TransactionView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.TransactionController;
import lk.ijse.BankManagementSystem.model.TransactionModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WithdrawalController implements Initializable {

    @FXML
    private AnchorPane anchorPaneWithdraw;

    @FXML
    private JFXTextField txtAccountNumber;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXButton btnClose;

    @FXML
    private Label lblWrongAccountNumber;

    @FXML
    private Label lblAlertBalance;

    @FXML
    private JFXButton btnWithdraw;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        numberValidation();
    }

    public void numberValidation(){
        txtAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtAmount.setText(oldValue);
            }
        });

        txtAccountNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtAccountNumber.setText(oldValue);
            }
        });
    }


    public void validation(){

        lblWrongAccountNumber.setVisible(false);

        RequiredFieldValidator validator1=new RequiredFieldValidator();
        lblWrongAccountNumber.setVisible(true);
        txtAccountNumber.getValidators().add(validator1);
        txtAccountNumber.validate();

        RequiredFieldValidator validator2=new RequiredFieldValidator();
        lblWrongAccountNumber.setVisible(true);
        txtAmount.getValidators().add(validator2);
        txtAmount.validate();
    }

    @FXML
    private void closeOnAction(ActionEvent event) {
        ((Stage)anchorPaneWithdraw.getScene().getWindow()).close();
    }

    @FXML
    private void withdrawOnAction(ActionEvent event) {


        if (txtAccountNumber.getText().isEmpty() || txtAmount.getText().isEmpty()){
            validation();
        }else {
            try {
                int accountID = Integer.parseInt(txtAccountNumber.getText().trim());
                double amount = Double.parseDouble(txtAmount.getText().trim());

                TransactionModel transactionModel = TransactionController.searchAccountBy(accountID);

                if (transactionModel==null){
                    lblWrongAccountNumber.setVisible(true);
                }else if (amount>transactionModel.getBalance()){
                    lblWrongAccountNumber.setVisible(false);
                    lblAlertBalance.setVisible(true);
                }else {
                    lblAlertBalance.setVisible(false);
                    if (TransactionController.insertWithdrawData(accountID,amount)){
                        lblWrongAccountNumber.setVisible(false);
                        ((Stage)anchorPaneWithdraw.getScene().getWindow()).close();
                    }else {
                        lblWrongAccountNumber.setVisible(true);
                    }
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



    }
}
