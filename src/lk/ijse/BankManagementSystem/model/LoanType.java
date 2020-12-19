package lk.ijse.BankManagementSystem.model;

public class LoanType {
    int LTID;
    String name;
    Double interest;

    public LoanType() {
    }

    public LoanType(Double interest) {
        this.interest = interest;
    }

    public LoanType(int LTID, String name, Double interest) {
        this.LTID = LTID;
        this.name = name;
        this.interest = interest;
    }

    public int getLTID() {
        return LTID;
    }

    public void setLTID(int LTID) {
        this.LTID = LTID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "LoanType{" +
                "LTID=" + LTID +
                ", name='" + name + '\'' +
                ", interest=" + interest +
                '}';
    }
}
