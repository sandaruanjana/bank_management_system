package lk.ijse.BankManagementSystem.view.AccountView;

import com.jfoenix.controls.*;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.AccountController;
import lk.ijse.BankManagementSystem.controller.CustomerController;
import lk.ijse.BankManagementSystem.model.Customer;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditAccountController implements Initializable {

    @FXML
    private AnchorPane anchorPaneEditAccount;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXComboBox cmbStatus;

    @FXML
    private JFXButton btnClose;

    @FXML
    private JFXButton btnUpdate;

    int ACID;

    @Override
    public void initialize(URL event, ResourceBundle rb) {

        statusData();
        cmbStatus.getSelectionModel().select(0);
    }

    @FXML
    private void closeOnAction(ActionEvent event) {
        ((Stage)anchorPaneEditAccount.getScene().getWindow()).close();
    }

    @FXML
    private void updateOnAction(ActionEvent event) {
        Object value = cmbStatus.getValue();
        String status = String.valueOf(value);


        try {
            if(AccountController.updateAccountData(ACID,status)){
                showUpdateSuccessfull();
            }else {
                //System.out.println("Account Update Fail");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getData(int ACID,String name){
        this.ACID=ACID;
        txtCustomerName.setText(name);
    }

    public void statusData(){
        ObservableList list=FXCollections.observableArrayList("Active","Close");
        cmbStatus.getItems().addAll(list);
    }

    public void showUpdateSuccessfull() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Account Update"));
        content.setBody(new Text("Account has been updated"));
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
                ((Stage)anchorPaneEditAccount.getScene().getWindow()).close();

            }
        });
        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setPrefHeight(32);
        //button.setStyle(dialogBtnStyle);
        content.setActions(button);
        anchorPaneEditAccount.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (anchorPaneEditAccount.getHeight() - content.getPrefHeight()) / 2);
        AnchorPane.setLeftAnchor(stackPane, (anchorPaneEditAccount.getWidth() - content.getPrefWidth()) / 2);
        dialog.show();
    }


}
