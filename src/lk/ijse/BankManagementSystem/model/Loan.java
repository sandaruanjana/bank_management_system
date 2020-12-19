package lk.ijse.BankManagementSystem.model;

import java.util.Date;

public class Loan {
    int LID;
    String name;
    int ACID;
    int LTID;
    String loanType;
    Double amount;
    int period;
    Date date;
    String firstGuarantor;
    String secondGuarantor;
    Double principal;
    Date end;

    double interest;
    double fine;

    public Loan() {
    }

    public Loan(Double amount, int period, Double principal, double interest, double fine) {
        this.amount = amount;
        this.period = period;
        this.principal = principal;
        this.interest = interest;
        this.fine = fine;
    }

    public Loan(Double principal) {
        this.principal = principal;
    }

    public Loan(String name, int ACID, String loanType, Double amount, Date date, Double principal, Date end) {
        this.name = name;
        this.ACID = ACID;
        this.loanType = loanType;
        this.amount = amount;
        this.date = date;
        this.principal = principal;
        this.end = end;
    }

    public Loan(String firstGuarantor, String secondGuarantor) {
        this.firstGuarantor = firstGuarantor;
        this.secondGuarantor = secondGuarantor;
    }

    public Loan(int LID, String name, int ACID, String loanType, Double amount, int period, Date date, Double principal, Date end) {
        this.LID = LID;
        this.name = name;
        this.ACID = ACID;
        this.loanType = loanType;
        this.amount = amount;
        this.period = period;
        this.date = date;
        this.principal = principal;
        this.end = end;
    }

    public Loan(int LID, int ACID, int LTID, Double amount, int period, Date date, String firstGuarantor, String secondGuarantor, Double principal, Date end) {
        this.LID = LID;
        this.ACID = ACID;
        this.LTID = LTID;
        this.amount = amount;
        this.period = period;
        this.date = date;
        this.firstGuarantor = firstGuarantor;
        this.secondGuarantor = secondGuarantor;
        this.principal = principal;
        this.end = end;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLID() {
        return LID;
    }

    public void setLID(int LID) {
        this.LID = LID;
    }

    public int getACID() {
        return ACID;
    }

    public void setACID(int ACID) {
        this.ACID = ACID;
    }

    public int getLTID() {
        return LTID;
    }

    public void setLTID(int LTID) {
        this.LTID = LTID;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFirstGuarantor() {
        return firstGuarantor;
    }

    public void setFirstGuarantor(String firstGuarantor) {
        this.firstGuarantor = firstGuarantor;
    }

    public String getSecondGuarantor() {
        return secondGuarantor;
    }

    public void setSecondGuarantor(String secondGuarantor) {
        this.secondGuarantor = secondGuarantor;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "LID=" + LID +
                ", name='" + name + '\'' +
                ", ACID=" + ACID +
                ", LTID=" + LTID +
                ", loanType='" + loanType + '\'' +
                ", amount=" + amount +
                ", period=" + period +
                ", date=" + date +
                ", firstGuarantor='" + firstGuarantor + '\'' +
                ", secondGuarantor='" + secondGuarantor + '\'' +
                ", principal=" + principal +
                ", end=" + end +
                ", interest=" + interest +
                ", fine=" + fine +
                '}';
    }
}
