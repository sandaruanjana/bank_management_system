package lk.ijse.BankManagementSystem.view.LoanView;

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
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.BankManagementSystem.controller.GuarantorController;
import lk.ijse.BankManagementSystem.controller.LoanController;
import lk.ijse.BankManagementSystem.model.Guarantor;
import lk.ijse.BankManagementSystem.model.Loan;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class LoanViewController implements Initializable {

    @FXML
    private JFXButton btnCreateLoan;

    @FXML
    private TableView<Loan> tableLoan;

    @FXML
    private TableColumn<Loan,Integer> colLID;

    @FXML
    private TableColumn<Loan,String> colName;

    @FXML
    private TableColumn<Loan,Integer> colACID;

    @FXML
    private TableColumn<Loan,String> colLTID;

    @FXML
    private TableColumn<Loan,Double> colAmount;

    @FXML
    private TableColumn<Loan,Double> colPrincipal;

    @FXML
    private TableColumn<Loan, Date> colIssueDate;

    @FXML
    private TableColumn<Loan,Date> colEndDate;

    @FXML
    private TableColumn<Loan,Integer> colPeriod;

    @FXML
    private Label lblWrongLoanNumber;

    @FXML
    private JFXTextField txtAccountID;

    public static ObservableList<Loan> oblist= FXCollections.observableArrayList();

    @Override
    public void initialize(URL event, ResourceBundle rb) {

        addViewButtonToTable();
        numberValidation();
    }

    public void numberValidation(){
        txtAccountID.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtAccountID.setText(oldValue);
            }
        });
    }


    @FXML
    void accountIDOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtAccountID.getText().length()>10){
            lblWrongLoanNumber.setText("Wrong Account Number!");
            lblWrongLoanNumber.setVisible(true);
        }else if(txtAccountID.getText().isEmpty()) {
            lblWrongLoanNumber.setText("Account Number Field Empty!");
            lblWrongLoanNumber.setVisible(true);
        }else {
            lblWrongLoanNumber.setVisible(false);
            int accountID = Integer.parseInt(txtAccountID.getText().trim());
            oblist.clear();
            loadLoan(accountID);


        }

    }

    public void loadLoan(int accountID) throws SQLException, ClassNotFoundException {
        LoanController.loadLoanTable(oblist,accountID);

        colLID.setCellValueFactory(new PropertyValueFactory<>("LID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colACID.setCellValueFactory(new PropertyValueFactory<>("ACID"));
        colLTID.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPrincipal.setCellValueFactory(new PropertyValueFactory<>("principal"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPeriod.setCellValueFactory(new PropertyValueFactory<>("period"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("end"));


        if (oblist.isEmpty()){lblWrongLoanNumber.setText("Wrong Account Number or No Active Loan This Account Number");
            lblWrongLoanNumber.setVisible(true);}else {tableLoan.setItems(oblist);lblWrongLoanNumber.setVisible(false);}


    }

    @FXML
    void issueLoanOnAction(ActionEvent event) {

        txtAccountID.setText("");
        oblist.clear();
        lblWrongLoanNumber.setVisible(false);


        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("IssueLoan.fxml"));
            Parent root = loader.load();

            IssueLoanController scene2 = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Issue Loan");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    private void addViewButtonToTable() {
        TableColumn<Loan, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>> cellFactory = new Callback<TableColumn<Loan, Void>, TableCell<Loan, Void>>() {
            @Override
            public TableCell<Loan, Void> call(final TableColumn<Loan, Void> param) {
                final TableCell<Loan, Void> cell = new TableCell<Loan, Void>() {

                    private final JFXButton btn = new JFXButton("View");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            int lid = getTableView().getItems().get(getIndex()).getLID();
                            String customerName = getTableView().getItems().get(getIndex()).getName();
                            int acid = getTableView().getItems().get(getIndex()).getACID();
                            String loanType = getTableView().getItems().get(getIndex()).getLoanType();
                            Double amount = getTableView().getItems().get(getIndex()).getAmount();
                            Double principal = getTableView().getItems().get(getIndex()).getPrincipal();
                            Date date = getTableView().getItems().get(getIndex()).getDate();
                            int period = getTableView().getItems().get(getIndex()).getPeriod();
                            Date end = getTableView().getItems().get(getIndex()).getEnd();

                            String gname=null;
                            String gnic=null;
                            int gtel=0;
                            String gaddress=null;

                            String g2name=null;
                            String g2nic=null;
                            int g2tel=0;
                            String g2address=null;

                            try {
                                Guarantor guarantor = GuarantorController.loansGuarantors(lid);
                                gname = guarantor.getGname();
                                gnic = guarantor.getGnic();
                                gtel = guarantor.getGtel();
                                gaddress = guarantor.getGaddress();

                                g2name = guarantor.getG2name();
                                g2nic = guarantor.getG2nic();
                                g2tel = guarantor.getG2tel();
                                g2address = guarantor.getG2address();



                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                            try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewLoanDetails.fxml"));
                                Parent root = loader.load();

                                ViewLoanDetailsController scene2 = loader.getController();
                                scene2.getData(lid,customerName,acid,loanType,amount,principal,date,period,end);
                                scene2.getGuarantor(gname,gnic,gtel,gaddress,g2name,g2nic,g2tel,g2address);
                                scene2.lid=lid;

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Loan Details");
                                stage.show();
                            } catch (IOException ex) {
                                System.err.println(ex);
                            }

                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableLoan.getColumns().add(colBtn);

    }
}
