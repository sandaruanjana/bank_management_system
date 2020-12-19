package lk.ijse.BankManagementSystem.model;

import java.time.LocalDate;
import java.util.Date;

public class Customer {
    int CID;
    String nic;
    String name;
    String address;
    Date DOB;
    int tel;
    Date date;
    String occupation;
    String gender;

    public Customer() {
    }

    public Customer(int CID, String nic, String name, String address, Date DOB, int tel, Date date, String occupation, String gender) {
        this.CID = CID;
        this.nic = nic;
        this.name = name;
        this.address = address;
        this.DOB = DOB;
        this.tel = tel;
        this.date = date;
        this.occupation = occupation;
        this.gender = gender;
    }

    public int getCID() {
        return CID;
    }

    public void setCID(int CID) {
        this.CID = CID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CID=" + CID +
                ", nic='" + nic + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", DOB=" + DOB +
                ", tel=" + tel +
                ", date=" + date +
                ", occupation='" + occupation + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
