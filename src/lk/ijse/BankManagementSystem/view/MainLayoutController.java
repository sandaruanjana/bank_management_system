package lk.ijse.BankManagementSystem.view;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainLayoutController implements Initializable {

    @FXML
    private BorderPane borderPaneMainLayout;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnAccount;

    @FXML
    private JFXButton btnTransaction;

    @FXML
    private JFXButton btnLoan;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnWithdraw;

    @FXML
    private JFXButton btnUser;

    private int roleId;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        loadPage("DashboardView");
    }

    public void setRoleId(int roleId){
        this.roleId=roleId;
    }

    @FXML
    void accountOnAction(ActionEvent event) {
        loadPage("AccountView/AccountView");
    }

    @FXML
    public void customerOnAction(ActionEvent event) {
        loadPage("CustomerView/Customer");
    }

    @FXML
    void transactionOnAction(ActionEvent event) {
        loadPage("TransactionView/Transaction");
    }

    @FXML
    void loanOnAction(ActionEvent event) {
        loadPage("LoanView/LoanView");
    }

    @FXML
    void paymentOnAction(ActionEvent event) {
        loadPage("PaymentView/Payment");
    }

    @FXML
    void dashboardOnAction(ActionEvent event) {
        loadPage("DashboardView");
    }


    @FXML
    void userOnAction(ActionEvent event) {

        if(roleId==1){
            loadPage("UserView/UserTab");
        }else {
            loadPage("UserView/Access");
        }


    }

    private void loadPage(String page) {

        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));


        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }
        borderPaneMainLayout.setCenter(root);
    }

}
