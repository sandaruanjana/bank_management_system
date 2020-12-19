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
import lk.ijse.BankManagementSystem.view.AccountView.AccountViewController;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    @FXML
    private AnchorPane anhorPaneDeposit;

    @FXML
    private JFXTextField txtAccountNumber;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnDeposit;

    @FXML
    private Label lblWrongAccountNumber;

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
        ((Stage)anhorPaneDeposit.getScene().getWindow()).close();
    }

    @FXML
    private void depositOnAction(ActionEvent event)  {


        if (txtAccountNumber.getText().isEmpty() || txtAmount.getText().isEmpty()){
            validation();
        }else {
            try {
                int accountID = Integer.parseInt(txtAccountNumber.getText().trim());
                double amount = Double.parseDouble(txtAmount.getText().trim());

                if (TransactionController.insertDepositData(accountID, amount)){
                    lblWrongAccountNumber.setVisible(false);
                    ((Stage)anhorPaneDeposit.getScene().getWindow()).close();
                }else {
                    lblWrongAccountNumber.setVisible(true);
                }

            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }



    }
}
