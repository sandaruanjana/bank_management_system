package lk.ijse.BankManagementSystem.view.PaymentView;

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
import lk.ijse.BankManagementSystem.controller.LoanPaymentController;
import lk.ijse.BankManagementSystem.model.Loan;
import lk.ijse.BankManagementSystem.model.LoanPayment;

import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class PayLoanController implements Initializable {

    @FXML
    private AnchorPane anchorPanePayLoan;

    @FXML
    private JFXTextField txtLoanID;

    @FXML
    private JFXTextField txtPrincipalBalance;

    @FXML
    private JFXTextField txtPayAmount;

    @FXML
    private JFXComboBox cmbPaymentMethod;

    @FXML
    private JFXTextField txtFine;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnPay;

    @FXML
    private JFXTextField txtTotal;

    @FXML
    private JFXTextField txtInterest;

    @FXML
    private Label lblWrongLoanNumber;

    double interestRate;
    double emi;

    double monthlyInterest;
    double monthlyPrincipal;

    double afterpayingInterest;
    double afterpayingPrincipal;

    double afterpayfine;


    @Override
    public void initialize(URL event, ResourceBundle rb) {
        loadPaymentMethod();
        onlyNumberValidation();
        txtFine.setText(String.valueOf(0.0));
        txtPrincipalBalance.setText(String.valueOf(0.0));
        cmbPaymentMethod.getSelectionModel().select(0);
    }

    @FXML
    private void closeOnAction(ActionEvent event) {

        ((Stage)anchorPanePayLoan.getScene().getWindow()).close();

    }

    @FXML
    void loanNumberMouseExit(MouseEvent event) {



        lblWrongLoanNumber.setVisible(false);
        txtPrincipalBalance.setText("");

        int lid = txtLoanID.getText().equalsIgnoreCase("") ? 0 : Integer.valueOf(txtLoanID.getText());

        try {
            Loan dataForCalculate = LoanPaymentController.getDataForCalculate(lid);


            if (dataForCalculate==null){

                lblWrongLoanNumber.setVisible(true);
                btnPay.setDisable(true);

            }else {
                lblWrongLoanNumber.setVisible(false);
                btnPay.setDisable(false);
                Double amount = dataForCalculate.getAmount();
                double interest = dataForCalculate.getInterest();
                Double principal = dataForCalculate.getPrincipal();
                int period = dataForCalculate.getPeriod();
                double fine = dataForCalculate.getFine();


                interestRate = calculateInterest(amount, interest, period );
                txtInterest.setText(String.valueOf(new DecimalFormat("##.00").format(interestRate)));
                monthlyInterest=interestRate;

                double t=interest/(12*100);
                double p=period*12;

                emi = calculateEmi(amount, t, p);
                txtPrincipalBalance.setText(String.valueOf(new DecimalFormat("##.00").format(emi-interestRate)));
                monthlyPrincipal=emi-interestRate;

                LoanPayment calculateFine = LoanPaymentController.calculateFine(lid);
                Double latefine = calculateFine.getFine();

                txtFine.setText(String.valueOf(Math.round(latefine)));

                txtTotal.setText(String.valueOf(new DecimalFormat("##.00").format((emi-interestRate)+interestRate+latefine)));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



    @FXML
    private void payOnAction(ActionEvent event) {
        lblWrongLoanNumber.setVisible(false);

        if (txtPayAmount.getText().isEmpty() || txtLoanID.getText().isEmpty()){
            RequiredFieldValidator validator=new RequiredFieldValidator();
            validator.setMessage("Cannot be Empty!");
            txtPayAmount.getValidators().add(validator);
            txtPayAmount.validate();

            RequiredFieldValidator validator2=new RequiredFieldValidator();
            lblWrongLoanNumber.setVisible(true);
            txtLoanID.getValidators().add(validator2);
            txtLoanID.validate();
        }else {

            Integer lid = Integer.valueOf(txtLoanID.getText());
            Double fine = Double.valueOf(txtFine.getText());
            Double amount = Double.valueOf(txtPayAmount.getText());
            String paymentMethod = String.valueOf(cmbPaymentMethod.getValue());

            if (fine!=0.00){
               amount=amount-fine;
            }else {

                try {
                    if (LoanPaymentController.insertLoanPaymentData(lid,fine,amount,paymentMethod)){
                        ((Stage)anchorPanePayLoan.getScene().getWindow()).close();
                    }else {
                        System.out.println("Payment Fail");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    public void loadPaymentMethod(){
        ObservableList<String> list= FXCollections.observableArrayList("Cash");
        cmbPaymentMethod.setItems(list);
    }

    public double calculateEmi(double amount,double t,double p){
        return (amount*t*Math.pow(1+t,p))/(Math.pow(1+t,p)-1);
    }


    public double calculateInterest(double amount,double interest,int period){
        return amount/100*interest/12;
    }



    public void onlyNumberValidation(){
        txtLoanID.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtLoanID.setText(oldValue);
            }
        });
    }
}
