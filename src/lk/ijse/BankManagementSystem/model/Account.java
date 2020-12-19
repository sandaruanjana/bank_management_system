package lk.ijse.BankManagementSystem.model;

import java.util.Date;

public class Account {
    int ACID;
    int CID;
    int ATID;
    double balance;
    Date date;
    String status;

    public Account() {
    }

    public Account(int ACID, int CID, int ATID, double balance, Date date, String status) {
        this.ACID = ACID;
        this.CID = CID;
        this.ATID = ATID;
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

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public int getATID() {
        return ATID;
    }

    public void setATID(int ATID) {
        this.ATID = ATID;
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
        return "Account{" +
                "ACID=" + ACID +
                ", CID=" + CID +
                ", ATID=" + ATID +
                ", balance=" + balance +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
