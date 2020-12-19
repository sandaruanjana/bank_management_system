package lk.ijse.BankManagementSystem.view;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInDown;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.BankManagementSystem.db.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
        DBConnection.getInstance().getConnection();

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);


        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();


        //primaryStage.getIcons().add(new Image("./resources/chase-bank-logo.png"));
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        //new FadeInDown(root).play();

        primaryStage.setTitle("Asia Bank PLC");
        primaryStage.show();


    }
}
