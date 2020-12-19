package lk.ijse.BankManagementSystem.view.CustomerView;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.CustomerController;
import lk.ijse.BankManagementSystem.model.Customer;
import lk.ijse.BankManagementSystem.view.MainLayoutController;

import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class EditCustomerController implements Initializable {

    @FXML
    private AnchorPane anchorPaneEditCustomer;

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
    private JFXButton btnColse;

    @FXML
    private JFXButton btnUpdate;

    int CID;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        loadGender();
        validation();

    }




    public static String convertSimpleDateFormat(Date date){
        Format format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    public void getData(int CID, String nic,String name, String address, Date dob,int contact,String occupation,String gender){
        this.CID=CID;
        txtNic.setText(nic);
        txtName.setText(name);
        txtAddress.setText(address);

        String dobString = convertSimpleDateFormat(dob);
        LocalDate birth = LocalDate.parse(dobString);
        dateDOB.setValue(birth);

        txtContact.setText(String.valueOf(contact));
        txtOccupation.setText(occupation);
        cmbGender.getSelectionModel().select(gender);

    }

    @FXML
    private void btnCloseOnAction(ActionEvent event) {
        ((Stage)anchorPaneEditCustomer.getScene().getWindow()).close();
    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {
        String nic = txtNic.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        LocalDate birth = dateDOB.getValue();
        int contact = Integer.parseInt(txtContact.getText());
        String occupation = txtOccupation.getText();
        Object genderValue = cmbGender.getValue();

        String gender=String.valueOf(genderValue);
        Date dob = java.sql.Date.valueOf(birth);

        Customer customer = new Customer(CID, nic, name, address, dob, contact, null, occupation, gender);

        try {
            boolean isUpdated= CustomerController.updateCustomer(customer);
            if (isUpdated){
                showUpdateSuccessfull();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadGender(){
        ObservableList role= FXCollections.observableArrayList("Male",
                "Female");

        cmbGender.getItems().addAll(role);
    }

    public void showUpdateSuccessfull() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Customer Update"));
        content.setBody(new Text("Success !! Customer has been updated"));
        content.setPrefSize(480, 78);
        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.LEFT, true);
        JFXButton button = new JFXButton("Okay");
        button.setStyle("-fx-background-color: #59b65d");
        button.setTextFill(Paint.valueOf("#ffffff"));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage)anchorPaneEditCustomer.getScene().getWindow()).close();

                try {
                    new MainLayoutController().customerOnAction(event);
                }catch (Exception e){}
            }
        });
        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setPrefHeight(32);
        //button.setStyle(dialogBtnStyle);
        content.setActions(button);
        anchorPaneEditCustomer.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (anchorPaneEditCustomer.getHeight() - content.getPrefHeight()) / 2);
        AnchorPane.setLeftAnchor(stackPane, (anchorPaneEditCustomer.getWidth() - content.getPrefWidth()) / 2);
        dialog.show();
    }

    public void validation(){

        txtContact.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.matches("[0-9]*")){
                txtContact.setText(oldValue);
            }
        });
    }
}
