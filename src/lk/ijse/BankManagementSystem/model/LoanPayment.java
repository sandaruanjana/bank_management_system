package lk.ijse.BankManagementSystem.model;

import java.util.Date;

public class LoanPayment {

    int LPID;
    String name;
    int LID;
    Date date;
    Double fine;
    Double amount;
    String paymentMethod;

    public LoanPayment(int LPID, String name, int LID, Date date, Double fine, Double amount, String paymentMethod) {
        this.LPID = LPID;
        this.name = name;
        this.LID = LID;
        this.date = date;
        this.fine = fine;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public LoanPayment(Double fine) {
        this.fine = fine;
    }

    public LoanPayment(int LPID, int LID, Date date, Double fine, Double amount) {
        this.LPID = LPID;
        this.LID = LID;
        this.date = date;
        this.fine = fine;
        this.amount = amount;
    }

    public int getLPID() {
        return LPID;
    }

    public void setLPID(int LPID) {
        this.LPID = LPID;
    }

    public int getLID() {
        return LID;
    }

    public void setLID(int LID) {
        this.LID = LID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "LoanPayment{" +
                "LPID=" + LPID +
                ", name='" + name + '\'' +
                ", LID=" + LID +
                ", date=" + date +
                ", fine=" + fine +
                ", amount=" + amount +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
