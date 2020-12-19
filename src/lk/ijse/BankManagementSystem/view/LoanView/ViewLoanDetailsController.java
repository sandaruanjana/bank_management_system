package lk.ijse.BankManagementSystem.view.LoanView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.LoanController;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewLoanDetailsController implements Initializable {

    @FXML
    private AnchorPane anchorPaneLoanDetail;

    @FXML
    private JFXTextField txtLID;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtAccountID;

    @FXML
    private JFXTextField txtLoanType;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtPrincipal;

    @FXML
    private JFXTextField txtIssueDate;

    @FXML
    private JFXTextField txtPeriod;

    @FXML
    private JFXTextField txtEndDate;

    @FXML
    private JFXTextField txtFirstGName;

    @FXML
    private JFXTextField txtSecondGName;

    @FXML
    private JFXTextField txtFirstGNic;

    @FXML
    private JFXTextField txtFirstGTel;

    @FXML
    private JFXTextField txtFirstGAddress;

    @FXML
    private JFXTextField txtSecondGNic;

    @FXML
    private JFXTextField txtSecondGTel;

    @FXML
    private JFXTextField txtSecondGAddress;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnDelete;

    int lid=0;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
    }

    @FXML
    private void closeOnAction(ActionEvent event) {
        ((Stage)anchorPaneLoanDetail.getScene().getWindow()).close();
    }

    public void getData(int lid, String customerName, int acid, String loanType, Double amount, Double principal, Date date,int period,Date end){
        txtLID.setText(String.valueOf(lid));
        txtCustomerName.setText(customerName);
        txtAccountID.setText(String.valueOf(acid));
        txtLoanType.setText(loanType);
        txtAmount.setText(String.valueOf(amount));
        txtPrincipal.setText(String.valueOf(principal));
        txtIssueDate.setText(String.valueOf(date));
        txtPeriod.setText(String.valueOf(period));
        txtEndDate.setText(String.valueOf(end));

    }

    public void getGuarantor(String gname,String gnic,int gtel,String gaddress,String g2name,String g2nic,int g2tel,String g2address){
        txtFirstGName.setText(gname);
        txtFirstGNic.setText(gnic);
        txtFirstGTel.setText(String.valueOf(gtel));
        txtFirstGAddress.setText(gaddress);

        txtSecondGName.setText(g2name);
        txtSecondGNic.setText(g2nic);
        txtSecondGTel.setText(String.valueOf(g2tel));
        txtSecondGAddress.setText(g2address);

    }

    @FXML
    void deleteOnAction(ActionEvent event) {
        try {
            if (LoanController.deleteLoan(lid)){
                ((Stage)anchorPaneLoanDetail.getScene().getWindow()).close();
                LoanViewController.oblist.clear();
            }else {
                System.out.println("Loan Delete Fail");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
