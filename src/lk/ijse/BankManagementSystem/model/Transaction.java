package lk.ijse.BankManagementSystem.model;

import java.util.Date;

public class Transaction {

    int TRID;
    int ACID;
    double amount;
    Date date;
    String type;

    public Transaction() {
    }

    public Transaction(int TRID, int ACID, double amount, Date date, String type) {
        this.TRID = TRID;
        this.ACID = ACID;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getTRID() {
        return TRID;
    }

    public void setTRID(int TRID) {
        this.TRID = TRID;
    }

    public int getACID() {
        return ACID;
    }

    public void setACID(int ACID) {
        this.ACID = ACID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "TRID=" + TRID +
                ", ACID=" + ACID +
                ", amount=" + amount +
                ", date=" + date +
                ", type='" + type + '\'' +
                '}';
    }
}
