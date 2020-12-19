package lk.ijse.BankManagementSystem.view.LoanView;

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
import lk.ijse.BankManagementSystem.controller.LoanController;
import lk.ijse.BankManagementSystem.controller.LoanTypeController;
import lk.ijse.BankManagementSystem.model.LoanType;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class IssueLoanController implements Initializable {

    @FXML
    private AnchorPane anchorPaneIssueLoan;

    @FXML
    private JFXTextField txtACID;

    @FXML
    private JFXTextField txtAmount;

    @FXML
    private JFXTextField txtPeriod;

    @FXML
    private JFXTextField txtFirstGuarantorName;

    @FXML
    private JFXTextField txtSecondGuarantorName;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnIssue;

    @FXML
    private JFXComboBox cmbLoanType;

    @FXML
    private JFXTextField txtFirstGuarantorNic;

    @FXML
    private JFXTextField txtFirstGuarantorTel;

    @FXML
    private JFXTextField txtSecondGuarantorNic;

    @FXML
    private JFXTextField txtSecondGuarantorTel;

    @FXML
    private JFXTextField txtFirstGuarantorAddress;

    @FXML
    private JFXTextField txtSecondGuarantorAddress;

    @FXML
    private Label lblWrongAccountNumber;


    @Override
    public void initialize(URL event, ResourceBundle rb) {

        loadLoanType();
        numberValidation();
        wordValidation();
        cmbLoanType.getSelectionModel().select(0);
    }

    public void wordValidation(){
        txtFirstGuarantorName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("^[a-zA-Z\\s]*$")){
                txtFirstGuarantorName.setText(oldValue);
            }
        });

        txtSecondGuarantorName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("^[a-zA-Z\\s]*$")){
                txtSecondGuarantorName.setText(oldValue);
            }
        });
    }

    public void numberValidation(){

        ArrayList<JFXTextField> list=new ArrayList<>();
        list.add(txtACID);
        list.add(txtAmount);
        list.add(txtPeriod);
        list.add(txtFirstGuarantorTel);
        list.add(txtSecondGuarantorTel);

        for (JFXTextField field : list){
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue.matches("[0-9]*")){
                    field.setText(oldValue);
                }
            });
        }

    }

    public void validation(){
        ArrayList<JFXTextField> list=new ArrayList<>();
        list.add(txtACID);
        list.add(txtAmount);
        list.add(txtPeriod);
        list.add(txtFirstGuarantorTel);
        list.add(txtSecondGuarantorTel);
        list.add(txtFirstGuarantorName);
        list.add(txtFirstGuarantorNic);
        list.add(txtFirstGuarantorAddress);
        list.add(txtSecondGuarantorName);
        list.add(txtSecondGuarantorNic);
        list.add(txtSecondGuarantorAddress);

        for (JFXTextField field:list){
            RequiredFieldValidator validator=new RequiredFieldValidator();
            validator.setMessage("Cannot be Empty!");
            field.getValidators().add(validator);
            field.validate();
        }

    }

    public boolean checkEmptyField(){
        ArrayList<JFXTextField> list=new ArrayList<>();
        list.add(txtACID);
        list.add(txtAmount);
        list.add(txtPeriod);
        list.add(txtFirstGuarantorTel);
        list.add(txtSecondGuarantorTel);
        list.add(txtFirstGuarantorName);
        list.add(txtFirstGuarantorNic);
        list.add(txtFirstGuarantorAddress);
        list.add(txtSecondGuarantorName);
        list.add(txtSecondGuarantorNic);
        list.add(txtSecondGuarantorAddress);

        for (JFXTextField field:list){

            if (field.getText().isEmpty()){
                validation();
                return true;
            }
        }
        return false;
    }

    @FXML
    private void closeOnAction(ActionEvent event) {
        ((Stage)anchorPaneIssueLoan.getScene().getWindow()).close();

    }

    @FXML
    private void issueOnAction(ActionEvent event)  {

        if (checkEmptyField()){
            validation();
        }else {
            validation();
            int accountID = Integer.parseInt(txtACID.getText().trim());
            String loadnType = String.valueOf(cmbLoanType.getValue());

            //double amount = Double.parseDouble(txtAmount.getText().trim());
            double amount = txtAmount.getText().equalsIgnoreCase("") ? 0 : Integer.valueOf(txtAmount.getText());

            //int period = Integer.parseInt(txtPeriod.getText().trim());
            int period = txtPeriod.getText().equalsIgnoreCase("") ? 0 : Integer.valueOf(txtPeriod.getText());



            String firstGuarantorName = txtFirstGuarantorName.getText();
            String firstGuarantorNic = txtFirstGuarantorNic.getText().trim();

            //Integer firstGuarantorTel = Integer.valueOf(txtFirstGuarantorTel.getText().trim());
            int firstGuarantorTel = txtFirstGuarantorTel.getText().equalsIgnoreCase("") ? 0 : Integer.valueOf(txtFirstGuarantorTel.getText());

            String firstGuarantorAddress = txtFirstGuarantorAddress.getText();
            String secondGuarantorName = txtSecondGuarantorName.getText();
            String secondGuarantorNic = txtSecondGuarantorNic.getText().trim();

            //Integer secondGuarantorTel = Integer.valueOf(txtSecondGuarantorTel.getText());
            int secondGuarantorTel = txtSecondGuarantorTel.getText().equalsIgnoreCase("") ? 0 : Integer.valueOf(txtSecondGuarantorTel.getText());


            String secondGuarantorAddress = txtSecondGuarantorAddress.getText();


            try {

                LoanType loan = LoanTypeController.findLoanTypeId(loadnType);
                int LTID = loan.getLTID();
                double interest = loan.getInterest();

                double t=interest/(12*100);
                double p=period*12;

                double emi = calculateEmi(amount, t, p);

                double total=emi*period*12;


                if (LoanController.insertLoanData(accountID, LTID, amount, period,firstGuarantorName,firstGuarantorNic,firstGuarantorTel,
                        firstGuarantorAddress,secondGuarantorName,secondGuarantorNic,
                        secondGuarantorTel,secondGuarantorAddress,total)){

                    ((Stage)anchorPaneIssueLoan.getScene().getWindow()).close();
                }else {
                    ((Stage)anchorPaneIssueLoan.getScene().getWindow()).close();
                }
            } catch (SQLException | ClassNotFoundException e) {
                lblWrongAccountNumber.setVisible(true);
                //e.printStackTrace();
            }

        }


    }



    public void loadLoanType(){
        List<LoanType> list=new ArrayList();
        ObservableList<String> oblist= FXCollections.observableArrayList();

        try {
            list=LoanTypeController.selectLoanType();

            for (LoanType r: list){
                oblist.add(
                        r.getName()
                );
            }

            cmbLoanType.getItems().addAll(oblist);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double calculateEmi(double amount,double t,double p){
        return (amount*t*Math.pow(1+t,p))/(Math.pow(1+t,p)-1);
    }


}
