package lk.ijse.BankManagementSystem.view.AccountView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.BankManagementSystem.controller.AccountController;
import lk.ijse.BankManagementSystem.controller.CustomerController;
import lk.ijse.BankManagementSystem.model.AccountModel;
import lk.ijse.BankManagementSystem.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class AccountViewController implements Initializable {

    @FXML
    private AnchorPane anchorPaneAccount;

    @FXML
    private TableView<AccountModel> tableAccount;

    @FXML
    private TableColumn<AccountModel,Integer> colACID;

    @FXML
    private TableColumn<AccountModel,String> colCID;

    @FXML
    private TableColumn<AccountModel,String> colATID;

    @FXML
    private TableColumn<AccountModel,Double> colBalance;

    @FXML
    private TableColumn<AccountModel, Date> colDate;

    @FXML
    private TableColumn<AccountModel,String> colStatus;

    @FXML
    private JFXButton btnCreateAccount;

    @FXML
    private JFXTextField txtSearchCustomer;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtDOB;

    @FXML
    private JFXTextField txtOccupation;

    @FXML
    private JFXTextField txtGender;

    @FXML
    private Label lblSearchWrong;


    public static ObservableList<AccountModel> oblist= FXCollections.observableArrayList();

    int CID=0;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        try {
            clearTextField();
            loadAccount();
            addEditButtonToTable();
            addDeleteButtonToTable();
            clearTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void clearTextField(){
        ArrayList<JFXTextField> fields=new ArrayList<>();
        fields.add(txtSearchCustomer);
        fields.add(txtName);
        fields.add(txtNic);
        fields.add(txtAddress);
        fields.add(txtDOB);
        fields.add(txtGender);
        fields.add(txtOccupation);

        for (JFXTextField r : fields){
            r.setText("");
        }
    }

    public void clearTable(){
        tableAccount.getItems().clear();
    }


    @FXML
    private void CreateAccountOnAction(ActionEvent event) {

        oblist.clear();
        txtName.setText("");
        txtAddress.setText("");
        txtDOB.setText("");
        txtGender.setText("");
        txtNic.setText("");
        txtOccupation.setText("");
        txtSearchCustomer.setText("");

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAccount.fxml"));
            Parent root = loader.load();

            AddAccountController scene2 = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Account");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }


    @FXML
    void SearchCustomerOnAction(ActionEvent event) {
            String nameOrNic=txtSearchCustomer.getText();
        try {
            Customer customer = CustomerController.searchCustomerBy(nameOrNic);

            if (customer==null){
                lblSearchWrong.setVisible(true);
                clearTextField();
                loadAccount();
                oblist.clear();
                clearTable();

            }else {
                lblSearchWrong.setVisible(false);
                clearTable();
                CID=customer.getCID();
                txtName.setText(customer.getName());
                txtNic.setText(customer.getNic());
                txtAddress.setText(customer.getAddress());
                txtDOB.setText(String.valueOf(customer.getDOB()));
                txtOccupation.setText(customer.getOccupation());
                txtGender.setText(customer.getGender());
                loadAccount();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadAccount() throws SQLException, ClassNotFoundException {

        AccountController.accountDataUsing(oblist,CID);

        colACID.setCellValueFactory(new PropertyValueFactory<>("ACID"));
        colCID.setCellValueFactory(new PropertyValueFactory<>("name"));
        colATID.setCellValueFactory(new PropertyValueFactory<>("type"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        tableAccount.setItems(oblist);
    }



    private void addDeleteButtonToTable() {
        TableColumn<AccountModel, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<AccountModel, Void>, TableCell<AccountModel, Void>> cellFactory = new Callback<TableColumn<AccountModel, Void>, TableCell<AccountModel, Void>>() {
            @Override
            public TableCell<AccountModel, Void> call(final TableColumn<AccountModel, Void> param) {
                final TableCell<AccountModel, Void> cell = new TableCell<AccountModel, Void>() {

                    private final JFXButton btn = new JFXButton("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int acid = getTableView().getItems().get(getIndex()).getACID();
                            confirmDeleteDialog(acid);
                            

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

        tableAccount.getColumns().add(colBtn);

    }

    private void addEditButtonToTable() {
        TableColumn<AccountModel, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<AccountModel, Void>, TableCell<AccountModel, Void>> cellFactory = new Callback<TableColumn<AccountModel, Void>, TableCell<AccountModel, Void>>() {
            @Override
            public TableCell<AccountModel, Void> call(final TableColumn<AccountModel, Void> param) {
                final TableCell<AccountModel, Void> cell = new TableCell<AccountModel, Void>() {

                    private final JFXButton btn = new JFXButton("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int acid = getTableView().getItems().get(getIndex()).getACID();
                            String name = getTableView().getItems().get(getIndex()).getName();

                            try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditAccount.fxml"));
                                Parent root = loader.load();

                                EditAccountController scene2 = loader.getController();
                                scene2.getData(acid,name);

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Edit Customer");
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

        tableAccount.getColumns().add(colBtn);

    }

    public void confirmDeleteDialog(int acid) {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Are You Sure?"));
        content.setBody(new Text("You won't be able to revert this!"));
        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.LEFT, true);
        JFXButton okayBtn = new JFXButton("Yes");
        okayBtn.addEventHandler(ActionEvent.ACTION, (e)-> {
            dialog.close();
        });
        okayBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    boolean isDeleted = AccountController.deleteAccountData(acid);
                    clearTable();
                    loadAccount();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        okayBtn.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        okayBtn.setPrefHeight(32);
        okayBtn.setStyle("-fx-background-color: #dd3333");
        okayBtn.setTextFill(Paint.valueOf("#ffffff"));
        JFXButton cancelBtn = new JFXButton("Cancel");
        cancelBtn.addEventHandler(ActionEvent.ACTION, (e)-> {
            dialog.close();
        });

        cancelBtn.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        cancelBtn.setPrefHeight(32);
        cancelBtn.setStyle("-fx-background-color: #3085d6");
        cancelBtn.setTextFill(Paint.valueOf("#ffffff"));
        content.setActions(cancelBtn, okayBtn);
        content.setPrefSize(300, 250);
        anchorPaneAccount.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (anchorPaneAccount.getHeight()-content.getPrefHeight())/2);
        AnchorPane.setLeftAnchor(stackPane, (anchorPaneAccount.getWidth()-content.getPrefWidth())/2);
        dialog.show();
    }



}
