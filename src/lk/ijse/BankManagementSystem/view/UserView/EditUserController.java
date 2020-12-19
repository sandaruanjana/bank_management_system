package lk.ijse.BankManagementSystem.view.UserView;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.stage.Stage;
import lk.ijse.BankManagementSystem.controller.RoleController;
import lk.ijse.BankManagementSystem.controller.UserController;
import lk.ijse.BankManagementSystem.model.Role;
import lk.ijse.BankManagementSystem.model.User;
import lk.ijse.BankManagementSystem.view.MainLayoutController;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EditUserController implements Initializable {

    @FXML
    private AnchorPane anchorPaneEditUser;

    @FXML
    private JFXTextField txtSearchUsername;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private Label lblFieldEmpty;

    @FXML
    private FontAwesomeIconView iconSearch;

    @FXML
    private JFXComboBox cmbRole;

    @FXML
    private JFXButton btnUpdateUser;

    @FXML
    private JFXButton btnDelete;

    List<Role> role;

    int selectUserID;

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
    void SearchUsernameOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        search();

    }

    public void search() throws SQLException, ClassNotFoundException {
        String name = txtSearchUsername.getText();

        if (name.isEmpty()){
            lblFieldEmpty.setVisible(true);
        }else {
            lblFieldEmpty.setVisible(false);
            User user = UserController.searchUser(name);

            if (user==null){
                System.out.println("User Not Found");
            }else {
                String username = user.getUsername();
                int roleID = user.getRoleID();
                selectUserID=user.getId();


                List<Role> role = RoleController.findRole(roleID);

                for (Role r : role){
                    cmbRole.getSelectionModel().select(r.getRoleName());
                }

                txtUsername.setText(username);

            }


        }
    }

    @FXML
    private void userUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (txtSearchUsername.getText().isEmpty() || txtUsername.getText().isEmpty()){

            RequiredFieldValidator validator=new RequiredFieldValidator();
            validator.setMessage("Cannot be Empty!");
            txtSearchUsername.getValidators().add(validator);
            txtSearchUsername.validate();

            RequiredFieldValidator validator2=new RequiredFieldValidator();
            validator2.setMessage("Cannot be Empty!");
            txtUsername.getValidators().add(validator2);
            txtUsername.validate();

        }else {

            String name = txtUsername.getText().trim();
            Object rname = cmbRole.getSelectionModel().getSelectedItem();

            Role r = RoleController.findRoleID(rname);

            int roleID = r.getRoleID();


            try {
                boolean isAdded=UserController.editUser(roleID,name,selectUserID);
                if (isAdded){
                    showUpdateDialog("User Has Been Successfully Updated");
                    txtSearchUsername.setText("");
                    txtUsername.setText("");

                }else {
                    txtSearchUsername.setText("");
                    txtUsername.setText("");
                    showUpdateDialog("User Update Fail");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


    }

    @FXML
    void UserDeleteAction(ActionEvent event) {

        if (txtSearchUsername.getText().isEmpty() || txtUsername.getText().isEmpty()){

            RequiredFieldValidator validator=new RequiredFieldValidator();
            validator.setMessage("Cannot be Empty!");
            txtSearchUsername.getValidators().add(validator);
            txtSearchUsername.validate();

            RequiredFieldValidator validator2=new RequiredFieldValidator();
            validator2.setMessage("Cannot be Empty!");
            txtUsername.getValidators().add(validator2);
            txtUsername.validate();

        }else {

            try {
                boolean isAdded = UserController.removeUser(selectUserID);
                if (isAdded){
                    showUpdateDialog("User Has Been Successfully Removed");
                }else {
                    showUpdateDialog("User Remove Fail");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void iconSearchMouseClick(MouseEvent event) throws SQLException, ClassNotFoundException {
        search();
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


    public void showUpdateDialog(String name) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Manage User"));
        content.setBody(new Text(name));
        content.setPrefSize(200, 200);
        StackPane stackPane = new StackPane();
        stackPane.autosize();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.LEFT, true);
        JFXButton button = new JFXButton("Done");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog.close();

            }
        });
        button.setButtonType(com.jfoenix.controls.JFXButton.ButtonType.RAISED);
        button.setPrefHeight(32);
        button.setStyle("-fx-background-color: #59b65d");
        content.setActions(button);
        anchorPaneEditUser.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (anchorPaneEditUser.getHeight() - content.getPrefHeight()) / 2);
        AnchorPane.setLeftAnchor(stackPane, (anchorPaneEditUser.getWidth() - content.getPrefWidth()) / 2);
        dialog.show();
    }
}
