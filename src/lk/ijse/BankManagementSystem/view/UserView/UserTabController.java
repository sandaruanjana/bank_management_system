package lk.ijse.BankManagementSystem.view.UserView;

import com.jfoenix.controls.JFXTabPane;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserTabController implements Initializable {

    @FXML
    private BorderPane borderPaneUserTab;

    @FXML
    private JFXTabPane tabPane;

    @FXML
    private Tab tabAddUser;

    @FXML
    private Tab tabManageUser;


    public void addUserTab(){
        FXMLLoader loader = new FXMLLoader();

        try {
            AnchorPane a1= loader.load(getClass().getResource("AddUser.fxml"));
            tabAddUser.setContent(a1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editUserTab(){
        FXMLLoader loader = new FXMLLoader();

        try {
            AnchorPane a1= loader.load(getClass().getResource("EditUser.fxml"));
            tabManageUser.setContent(a1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL event, ResourceBundle rb) {
        addUserTab();
        editUserTab();
    }
}
