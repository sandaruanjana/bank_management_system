package lk.ijse.BankManagementSystem.model;

public class AccountType {
    int ATID;
    String name;
    int interest;

    public AccountType() {
    }

    public AccountType(int ATID, String name, int interest) {
        this.ATID = ATID;
        this.name = name;
        this.interest = interest;
    }

    public int getATID() {
        return ATID;
    }

    public void setATID(int ATID) {
        this.ATID = ATID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "AccountType{" +
                "ATID=" + ATID +
                ", name='" + name + '\'' +
                ", interest=" + interest +
                '}';
    }
}
