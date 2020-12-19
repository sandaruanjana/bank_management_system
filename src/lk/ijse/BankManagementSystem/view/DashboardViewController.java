package lk.ijse.BankManagementSystem.view;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.fxml.Initializable;
import lk.ijse.BankManagementSystem.controller.DashboardController;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardViewController implements Initializable {

    @FXML
    private AnchorPane anchorPaneDashboardView;

    @FXML
    private AnchorPane anchorPaneTotalCustomer;

    @FXML
    private Label lblTotalCustomers;

    @FXML
    private AnchorPane anchorPaneTransaction;

    @FXML
    private Label lblTotalTransaction;

    @FXML
    private AnchorPane anchorPaneTotalLoans;

    @FXML
    private Label lblTotalLoan;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    String time;


    @Override
    public void initialize(URL event, ResourceBundle rb) {
        displayAllCustomers();
        displayTotalTransaction();
        displayTotalLoans();
        displayTIme();

        LocalDate date = LocalDate.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy");
        String formattedDate = date.format(myFormatObj);
        lblDate.setText(formattedDate);

    }

    public void displayAllCustomers()  {

        try {
            int customers = DashboardController.totalCustomerCount();
            lblTotalCustomers.setText(String.valueOf(customers));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayTotalTransaction(){
        try {
            double totalTransaction = DashboardController.totalTransaction();
            lblTotalTransaction.setText(String.valueOf(totalTransaction));

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void displayTotalLoans(){
        try {
            int loanCount = DashboardController.totalLoans();
            lblTotalLoan.setText(String.valueOf(loanCount));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void displayTIme(){

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Calendar calendar = new GregorianCalendar();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                int AM_PM=calendar.get(Calendar.AM_PM);

                String day_night="";
                if(AM_PM==1){
                    day_night="PM";
                }else{
                    day_night="AM";
                }

                time = hour + ":" + minute + ":" + second + ":" + day_night;

                Platform.runLater(() -> {
                    lblTime.setText(time);
                });
            };
        };
        t.schedule(tt, 0, 1000);

    }
}
