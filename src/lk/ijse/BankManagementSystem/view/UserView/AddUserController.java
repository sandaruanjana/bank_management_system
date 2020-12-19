package lk.ijse.BankManagementSystem.view.UserView;

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
import javafx.scene.text.Text;
import lk.ijse.BankManagementSystem.controller.RoleController;
import lk.ijse.BankManagementSystem.controller.UserController;
import lk.ijse.BankManagementSystem.model.Role;
import lk.ijse.BankManagementSystem.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AddUserController implements Initializable {

    @FXML
    private AnchorPane borderPaneAddUser;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXComboBox<String> cmbRole;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnAddUser;

    public List<Role> role;

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        try {
            loadRole();
            cmbRole.getSelectionModel().select(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
     void addUserOnAction(ActionEvent event) {

        if (txtUserName.getText().isEmpty() || txtPassword.getText().isEmpty()){

            RequiredFieldValidator validator=new RequiredFieldValidator();
            validator.setMessage("Cannot be Empty!");
            txtUserName.getValidators().add(validator);
            txtUserName.validate();

            RequiredFieldValidator validator2=new RequiredFieldValidator();
            validator2.setMessage("Cannot be Empty!");
            txtPassword.getValidators().add(validator2);
            txtPassword.validate();

        }else {


            int id=0;
            String username=txtUserName.getText();
            String password=txtPassword.getText();
            int roleIndex=cmbRole.getSelectionModel().getSelectedIndex();
            int roleID = role.get(roleIndex).getRoleID();


            User user = new User(id,roleID,username,password);

            if (username.isEmpty() || txtUserName.getText().equals("") && password.isEmpty()){
                System.out.println("TextField Empty");
            }else {
                try {
                    boolean isAdded = UserController.AddUserDate(user);
                    if (isAdded){
                        txtUserName.setText("");
                        txtPassword.setText("");

                        JFXDialogLayout content = new JFXDialogLayout();
                        content.setHeading(new Text("Success!"));
                        content.setBody(new Text("User Data Added Successfully"));
                        content.setPrefSize(409, 246);
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
                        borderPaneAddUser.getChildren().add(stackPane);
                        AnchorPane.setTopAnchor(stackPane, (borderPaneAddUser.getHeight() - content.getPrefHeight()) / 2);
                        AnchorPane.setLeftAnchor(stackPane, (borderPaneAddUser.getWidth() - content.getPrefWidth()) / 2);
                        dialog.show();

                    }else{
                        txtUserName.setText("");
                        txtPassword.setText("");
                        System.out.println("Something Wrong Check Again!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }


    }

    @FXML
    public void loadRole() throws Exception{
        ObservableList<String> options = FXCollections.observableArrayList();
        role = RoleController.role();

        for (Role r : role) {
            options.add(r.getRoleName());
        }
        cmbRole.getItems().addAll(options);

    }
}
