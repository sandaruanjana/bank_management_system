package lk.ijse.BankManagementSystem.view;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
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
import lk.ijse.BankManagementSystem.controller.UserController;
import lk.ijse.BankManagementSystem.model.Role;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnLogin;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
    }

    public void validation(){

        RequiredFieldValidator validator1=new RequiredFieldValidator();
        validator1.setMessage("Cannot be Empty!");
        txtUserName.getValidators().add(validator1);
        txtUserName.validate();

        RequiredFieldValidator validator2=new RequiredFieldValidator();
        validator2.setMessage("Cannot be Empty!");
        txtPassword.getValidators().add(validator2);
        txtPassword.validate();
    }

    @FXML
    private void loginAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        String email = txtUserName.getText();
        String password = txtPassword.getText();

        if (txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()) {
           validation();
        }else {

            boolean isAdded=UserController.userLogin(email,password);

            if (isAdded){

                String user=txtUserName.getText();

                Role roleId = UserController.findRoleId(user);
                int roleID = roleId.getRoleID();

                new MainLayoutController().setRoleId(roleID);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Mainlayout.fxml"));

                Parent root = (Parent)fxmlLoader.load();
                MainLayoutController controller = fxmlLoader.getController();
                controller.setRoleId(roleID);
                Scene scene = new Scene(root);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            }else {

                txtUserName.setText("");
                txtPassword.setText("");

                JFXDialogLayout content = new JFXDialogLayout();
                content.setHeading(new Text("Login Error!"));
                content.setBody(new Text("UserName Or Password Wrong Check Again!"));
                content.setPrefSize(400, 200);
                StackPane stackPane = new StackPane();
                stackPane.autosize();
                JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.LEFT, true);
                JFXButton button = new JFXButton("Okay");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                    }
                });
                button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
                button.setPrefHeight(32);
                //button.setStyle(dialogBtnStyle);
                content.setActions(button);
                loginAnchorPane.getChildren().add(stackPane);
                AnchorPane.setTopAnchor(stackPane, (loginAnchorPane.getHeight() - content.getPrefHeight()) / 2);
                AnchorPane.setLeftAnchor(stackPane, (loginAnchorPane.getWidth() - content.getPrefWidth()) / 2);
                dialog.show();
            }

        }
    }
}
