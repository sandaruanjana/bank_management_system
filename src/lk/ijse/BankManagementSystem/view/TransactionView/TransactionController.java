package lk.ijse.BankManagementSystem.view.TransactionView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.model.TransactionModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTransaction;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnDeposit;

    @FXML
    private TableView<TransactionModel> tableTransaction;

    @FXML
    private TableColumn<TransactionModel,Integer> colTRID;

    @FXML
    private TableColumn<TransactionModel,String> colCustomer;

    @FXML
    private TableColumn<TransactionModel,Integer> colAccount;

    @FXML
    private TableColumn<TransactionModel,Double> colAmount;

    @FXML
    private TableColumn<TransactionModel, Date> colDate;

    @FXML
    private TableColumn<TransactionModel,String> colType;

    @FXML
    private JFXButton btnWithdraw;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtAccountNumber;

    @FXML
    private JFXTextField txtAccountType;

    @FXML
    private JFXTextField txtFilter;

    @FXML
    private JFXTextField txtAccountBalance;

    @FXML
    private Label lblWrongAccountNumber;

    @FXML
    private JFXTextField txtAccountStatus;

    public static ObservableList<TransactionModel> oblist= FXCollections.observableArrayList();

    int searchAccount=0;


    @Override
    public void initialize(URL event, ResourceBundle rb) {
        numberValidation();
    }

    public void numberValidation(){
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtSearch.setText(oldValue);
            }
        });
    }

    @FXML
    void depositOnAction(ActionEvent event) {

        txtSearch.setText("");
        txtAccountBalance.setText("");
        txtAccountNumber.setText("");
        txtAccountStatus.setText("");
        txtAccountType.setText("");
        txtCustomerName.setText("");
        txtFilter.setText("");
        oblist.clear();


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Deposit.fxml"));
            Parent root = loader.load();

            DepositController scene2 = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Deposit");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void withdrawOnAction(ActionEvent event) {

        txtSearch.setText("");
        txtAccountBalance.setText("");
        txtAccountNumber.setText("");
        txtAccountStatus.setText("");
        txtAccountType.setText("");
        txtCustomerName.setText("");
        txtFilter.setText("");
        oblist.clear();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Withdrawal.fxml"));
            Parent root = loader.load();

            WithdrawalController scene2 = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Withdrawal");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @FXML
    void searchOnAction(ActionEvent event) {

        if (txtSearch.getText().length()>10){
            lblWrongAccountNumber.setText("Wrong Account Number!");
            lblWrongAccountNumber.setVisible(true);
        }else if (txtSearch.getText().isEmpty()){
            lblWrongAccountNumber.setText("Account Number Field Empty!");
            lblWrongAccountNumber.setVisible(true);
        }else {
            lblWrongAccountNumber.setVisible(false);
            searchAccount=Integer.parseInt(txtSearch.getText());

            try {
                TransactionModel transactionModel =
                        lk.ijse.BankManagementSystem.controller.TransactionController.searchAccountBy(searchAccount);

                if (transactionModel == null){
                    lblWrongAccountNumber.setText("Wrong Account Number!");
                    lblWrongAccountNumber.setVisible(true);
                    oblist.clear();
                    txtCustomerName.setText("");
                    txtAccountNumber.setText("");
                    txtAccountType.setText("");
                    txtAccountBalance.setText("");
                    txtAccountStatus.setText("");
                }else {
                    lblWrongAccountNumber.setVisible(false);
                    oblist.clear();
                    loadTransaction();
                    txtCustomerName.setText(transactionModel.getName());
                    txtAccountNumber.setText(String.valueOf(transactionModel.getACID()));
                    txtAccountType.setText(transactionModel.getType());
                    txtAccountBalance.setText(String.valueOf(transactionModel.getBalance()));
                    txtAccountStatus.setText(transactionModel.getStatus());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



    }


    @FXML
    void filterKeyPressed(KeyEvent event) {
        filter();
    }


    public void loadTransaction() throws SQLException, ClassNotFoundException {

        lk.ijse.BankManagementSystem.controller.TransactionController.transactionDataUsing(oblist,searchAccount);

        colTRID.setCellValueFactory(new PropertyValueFactory<>("TRID"));
        colCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAccount.setCellValueFactory(new PropertyValueFactory<>("ACID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableTransaction.setItems(oblist);

    }

    public void filter(){
        FilteredList<TransactionModel> filteredData = new FilteredList<>(oblist, b -> true);

        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(transaction -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (transaction.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (transaction.getType().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (String.valueOf(transaction.getTRID()).indexOf(lowerCaseFilter)!=-1){
                    return true;}
                else if (String.valueOf(transaction.getACID()).indexOf(lowerCaseFilter)!=-1){
                    return true;}
                else if (String.valueOf(transaction.getBalance()).indexOf(lowerCaseFilter)!=-1){
                    return true;}
                else if (String.valueOf(transaction.getDate()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false;
            });
        });

        SortedList<TransactionModel> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableTransaction.comparatorProperty());

        tableTransaction.setItems(sortedData);
    }
}
