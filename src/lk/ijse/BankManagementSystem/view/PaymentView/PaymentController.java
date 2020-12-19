package lk.ijse.BankManagementSystem.view.PaymentView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.LoanController;
import lk.ijse.BankManagementSystem.controller.LoanPaymentController;
import lk.ijse.BankManagementSystem.model.Loan;
import lk.ijse.BankManagementSystem.model.LoanPayment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML
    private AnchorPane anchorPanePayment;

    @FXML
    private JFXTextField txtSearchLoanID;

    @FXML
    private JFXButton btnPayLoan;

    @FXML
    private TableView<LoanPayment> tableLoanPayment;

    @FXML
    private TableColumn<LoanPayment,Integer> colPaymentID;

    @FXML
    private TableColumn<LoanPayment,String> colCustomerName;

    @FXML
    private TableColumn<LoanPayment,Integer> colLoanID;

    @FXML
    private TableColumn<LoanPayment,Date> colPaymentDate;

    @FXML
    private TableColumn<LoanPayment,Double> colFine;

    @FXML
    private TableColumn<LoanPayment,Double> colAmount;

    @FXML
    private TableColumn<LoanPayment,String> colPaymentMethod;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtAccountID;

    @FXML
    private JFXTextField txtLoanType;

    @FXML
    private JFXTextField txtLoanAmount;

    @FXML
    private JFXTextField txtPrincipalBalance;

    @FXML
    private JFXTextField txtIssueDate;

    @FXML
    private JFXTextField txtEndDate;

    @FXML
    private Label lblWrongLoanNumber;

    ObservableList<LoanPayment> list= FXCollections.observableArrayList();

    Integer searchLID;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        numberValidation();
    }

    public void numberValidation(){
        txtSearchLoanID.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtSearchLoanID.setText(oldValue);
            }
        });
    }

    @FXML
    private void PayLoanOnAction(ActionEvent event) {
        clearTextField();
        list.clear();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("PayLoan.fxml"));
            Parent root = loader.load();

            PayLoanController scene2 = loader.getController();
            //scene2.getData();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Pay Loan");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    @FXML
    void searchOnAction(ActionEvent event) {

        if (txtSearchLoanID.getText().length()>10 || txtSearchLoanID.getText().isEmpty()){
            lblWrongLoanNumber.setVisible(true);
        }else {
            lblWrongLoanNumber.setVisible(false);

            searchLID = Integer.valueOf(txtSearchLoanID.getText().trim());

            try {
                Loan loan = LoanController.loanDataForPaymentView(searchLID);

                if (loan==null){
                    clearTextField();
                    list.clear();
                    lblWrongLoanNumber.setVisible(true);

                }else {
                    lblWrongLoanNumber.setVisible(false);
                    loadPaymentDetails();
                    txtCustomerName.setText(loan.getName());
                    txtAccountID.setText(String.valueOf(loan.getACID()));
                    txtLoanType.setText(loan.getLoanType());
                    txtLoanAmount.setText(String.valueOf(loan.getAmount()));
                    txtPrincipalBalance.setText(String.valueOf(loan.getPrincipal()));
                    txtIssueDate.setText(String.valueOf(loan.getDate()));
                    txtEndDate.setText(String.valueOf(loan.getEnd()));
                }



            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }


    public void loadPaymentDetails() throws SQLException, ClassNotFoundException {

        LoanPaymentController.loadPaymentDetails(list,searchLID);

        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("LPID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colLoanID.setCellValueFactory(new PropertyValueFactory<>("LID"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));

        tableLoanPayment.setItems(list);


    }



    public void clearTextField(){
        ArrayList<JFXTextField> list=new ArrayList();
        list.add(txtSearchLoanID);
        list.add(txtCustomerName);
        list.add(txtAccountID);
        list.add(txtLoanType);
        list.add(txtLoanAmount);
        list.add(txtPrincipalBalance);
        list.add(txtIssueDate);
        list.add(txtEndDate);

        for (JFXTextField j:list){
            j.setText("");
        }
    }
}
