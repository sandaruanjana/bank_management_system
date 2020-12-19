package lk.ijse.BankManagementSystem.view.CustomerView;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.javaws.util.JfxHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.CustomerController;
import lk.ijse.BankManagementSystem.model.Customer;
import lk.ijse.BankManagementSystem.view.MainLayoutController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    @FXML
    private AnchorPane anchorPaneAddCustomer;

    @FXML
    private JFXTextField txtNic;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXDatePicker dateDOB;

    @FXML
    private JFXTextField txtContact;

    @FXML
    private JFXTextField txtOccupation;

    @FXML
    private JFXComboBox cmbGender;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnAddCustomer;

    @FXML
    private Label lblCustomerRegisterFail;

    @FXML
    private Label lblWrongContactNumber;


    @Override
    public void initialize(URL event, ResourceBundle rb) {
        loadGender();
        numberValidation();
        wordValidation();
        cmbGender.getSelectionModel().select(0);
        //validation();
    }

    public void wordValidation(){
        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("^[a-zA-Z\\s]*$")){
                txtName.setText(oldValue);
            }
        });

        txtOccupation.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("^[a-zA-Z\\s]*$")){
                txtOccupation.setText(oldValue);
            }
        });
    }

    public void numberValidation(){
        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtContact.setText(oldValue);
            }
        });
    }

    public void validation(){

        dateDOB.validate();
        cmbGender.getActiveValidator();


        RequiredFieldValidator validator1=new RequiredFieldValidator();
        validator1.setMessage("Cannot be Empty!");
        cmbGender.getValidators().add(validator1);
        cmbGender.validate();

        RequiredFieldValidator validator2=new RequiredFieldValidator();
        validator2.setMessage("Cannot be Empty!");
        dateDOB.getValidators().add(validator2);
        dateDOB.validate();

        ArrayList<JFXTextField> textFields=new ArrayList<>();
        textFields.add(txtNic);
        textFields.add(txtName);
        textFields.add(txtAddress);
        textFields.add(txtContact);
        textFields.add(txtOccupation);

        for (JFXTextField field : textFields){

            RequiredFieldValidator validator=new RequiredFieldValidator();
            validator.setMessage("Cannot be Empty!");
            field.getValidators().add(validator);
            field.validate();

        }
    }


    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        ((Stage)anchorPaneAddCustomer.getScene().getWindow()).close();
    }

    @FXML
    private void btnAddCustomerOnAction(ActionEvent event) throws ParseException {

        int cID=0;
        String nic = txtNic.getText().trim();
        String name = txtName.getText().trim();
        String address = txtAddress.getText().trim();
        LocalDate birth = dateDOB.getValue();

        //int contact = Integer.parseInt(txtContact.getText().trim());

        if (txtContact.getText().length()>10){
            lblWrongContactNumber.setVisible(true);

        }else {
            lblCustomerRegisterFail.setVisible(false);

            int contact = txtContact.getText().equalsIgnoreCase("") ? 0 : Integer.valueOf(txtContact.getText());

            String occupation = txtOccupation.getText().trim();
            Object genderValue = cmbGender.getValue();
            String gender=String.valueOf(genderValue);


            if (birth==null || gender==null || txtName.getText()==null || contact==0 || txtName.getText()==null || txtAddress.getText()==null ){
                validation();

            } else {
                Date dob = Date.valueOf(birth);

                Customer customer = new Customer(cID, nic, name, address, dob, contact, null, occupation, gender);


                try {
                    boolean isAdded= CustomerController.addCustomer(customer);
                    if (isAdded){
                        showSaveDialog();
                    }else {
                        lblCustomerRegisterFail.setVisible(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }





    }

    public void loadGender(){
        ObservableList role=FXCollections.observableArrayList("Male",
                "Female");

        cmbGender.getItems().addAll(role);
    }

    public void showSaveDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Customer Register"));
        content.setBody(new Text("Customer Register has been Successfully"));
        content.setPrefSize(200, 200);
        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.LEFT, true);
        JFXButton button = new JFXButton("Done");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ((Stage)anchorPaneAddCustomer.getScene().getWindow()).close();

                try {
                    new MainLayoutController().customerOnAction(event);
                }catch (Exception e){}

            }
        });
        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setPrefHeight(32);
        button.setStyle("-fx-background-color: #59b65d");
        content.setActions(button);
        anchorPaneAddCustomer.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (anchorPaneAddCustomer.getHeight() - content.getPrefHeight()) / 2);
        AnchorPane.setLeftAnchor(stackPane, (anchorPaneAddCustomer.getWidth() - content.getPrefWidth()) / 2);
        dialog.show();
    }
}
