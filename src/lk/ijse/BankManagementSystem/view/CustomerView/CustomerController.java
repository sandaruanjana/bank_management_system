package lk.ijse.BankManagementSystem.view.CustomerView;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.BankManagementSystem.model.Customer;
import lk.ijse.BankManagementSystem.model.TransactionModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private AnchorPane anchorPaneCustomer;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer,Integer> colCID;

    @FXML
    private TableColumn<Customer,Integer> colNic;

    @FXML
    private TableColumn<Customer,String> colName;

    @FXML
    private TableColumn<Customer,String> colAddress;

    @FXML
    private TableColumn<Customer, Date> colDOB;

    @FXML
    private TableColumn<Customer,Integer> colTel;

    @FXML
    private TableColumn<Customer, Date> colDate;

    @FXML
    private TableColumn<Customer,String> colOccupation;

    @FXML
    private TableColumn<Customer,String> colGender;

    @FXML
    private JFXTextField txtFilter;


    public static ObservableList<Customer> oblist= FXCollections.observableArrayList();


    @Override
    public void initialize(URL event, ResourceBundle rb) {
        try {
            oblist.clear();
            loadCustomer();
            addEditButtonToTable();
            addDeleteButtonToTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void filterKeyPressed(KeyEvent event) {
        FilteredList<Customer> filteredData = new FilteredList<>(oblist, b -> true);

        txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (customer.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                } else if (customer.getAddress().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (customer.getOccupation().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (customer.getGender().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else if (String.valueOf(customer.getNic()).indexOf(lowerCaseFilter)!=-1){
                    return true;}
                else if (String.valueOf(customer.getTel()).indexOf(lowerCaseFilter)!=-1){
                    return true;}
                else if (String.valueOf(customer.getCID()).indexOf(lowerCaseFilter)!=-1){
                    return true;}
                else if (String.valueOf(customer.getDOB()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false;
            });
        });

        SortedList<Customer> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());

        customerTable.setItems(sortedData);
    }


    @FXML
    private void addCustomerOnAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddCustomer.fxml"));
            Parent root = loader.load();



            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            stage.setTitle("Add Customer");
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void loadCustomer() throws SQLException, ClassNotFoundException {

        lk.ijse.BankManagementSystem.controller.CustomerController.customerData(oblist);


        colCID.setCellValueFactory(new PropertyValueFactory<>("CID"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colOccupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        customerTable.setItems(oblist);

    }

    public void clearTable(){
        customerTable.getItems().clear();
    }


    private void addEditButtonToTable() {
        TableColumn<Customer, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                    private final JFXButton btn = new JFXButton("Edit");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int CID = getTableView().getItems().get(getIndex()).getCID();
                            String nic = getTableView().getItems().get(getIndex()).getNic();
                            String name = getTableView().getItems().get(getIndex()).getName();
                            String address = getTableView().getItems().get(getIndex()).getAddress();
                            Date dob = getTableView().getItems().get(getIndex()).getDOB();
                            int contact = getTableView().getItems().get(getIndex()).getTel();
                            String occupation = getTableView().getItems().get(getIndex()).getOccupation();
                            String gender = getTableView().getItems().get(getIndex()).getGender();

                            //System.out.println(CID+" "+nic+" "+name+" "+address+" "+s+" "+contact+" "+occupation+" "+gender);

                           try {

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditCustomer.fxml"));
                                Parent root = loader.load();

                                EditCustomerController scene2 = loader.getController();
                                scene2.getData(CID,nic,name,address,dob,contact,occupation,gender);

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

        customerTable.getColumns().add(colBtn);

    }




    private void addDeleteButtonToTable() {
        TableColumn<Customer, Void> colBtn = new TableColumn("Action");

        Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
            @Override
            public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
                final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

                    private final JFXButton btn = new JFXButton("Delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            int cid = getTableView().getItems().get(getIndex()).getCID();
                            confirmDeleteDialog(cid);

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

        customerTable.getColumns().add(colBtn);

    }

    public void confirmDeleteDialog(int cid) {

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
                    boolean isDeleted = lk.ijse.BankManagementSystem.controller.CustomerController.deleteCustomer(cid);
                    if (isDeleted){
                        clearTable();
                        loadCustomer();
                    }else {
                        System.out.println("Customer Delete Fail");
                    }
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
        anchorPaneCustomer.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (anchorPaneCustomer.getHeight()-content.getPrefHeight())/2);
        AnchorPane.setLeftAnchor(stackPane, (anchorPaneCustomer.getWidth()-content.getPrefWidth())/2);
        dialog.show();
    }


}
