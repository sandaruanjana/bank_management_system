package lk.ijse.BankManagementSystem.model;

import java.util.Date;

public class TransactionModel {

    int TRID;
    String name;
    int ACID;
    String type;
    double balance;
    Date date;
    String status;



    public TransactionModel() {
    }

    public TransactionModel(int TRID, String name, int ACID, String type, double balance, Date date) {
        this.TRID = TRID;
        this.name = name;
        this.ACID = ACID;
        this.type = type;
        this.balance = balance;
        this.date = date;
    }

    public TransactionModel(String name, int ACID, String type, double balance, String status) {
        this.name = name;
        this.ACID = ACID;
        this.type = type;
        this.balance = balance;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getACID() {
        return ACID;
    }

    public void setACID(int ACID) {
        this.ACID = ACID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTRID() {
        return TRID;
    }

    public void setTRID(int TRID) {
        this.TRID = TRID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "name='" + name + '\'' +
                ", ACID=" + ACID +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                '}';
    }
}
