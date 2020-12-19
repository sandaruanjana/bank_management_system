package lk.ijse.BankManagementSystem.model;

import java.util.Date;

public class AccountModel {
    int ACID;
    String name;
    String type;
    double balance;
    Date date;
    String status;

    public AccountModel() {
    }

    public AccountModel(int ACID, String name, String type, double balance, Date date, String status) {
        this.ACID = ACID;
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.date = date;
        this.status = status;
    }

    public int getACID() {
        return ACID;
    }

    public void setACID(int ACID) {
        this.ACID = ACID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "ACID=" + ACID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", balance=" + balance +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
