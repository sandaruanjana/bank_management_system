package lk.ijse.BankManagementSystem.model;

public class Guarantor {
    int gid;
    String gname;
    String gnic;
    int gtel;
    String gaddress;

    String g2name;
    String g2nic;
    int g2tel;
    String g2address;

    public Guarantor() {
    }

    public Guarantor(int gid, String gname, String gnic, int gtel, String gaddress, String g2name, String g2nic, int g2tel, String g2address) {
        this.gid = gid;
        this.gname = gname;
        this.gnic = gnic;
        this.gtel = gtel;
        this.gaddress = gaddress;
        this.g2name = g2name;
        this.g2nic = g2nic;
        this.g2tel = g2tel;
        this.g2address = g2address;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGnic() {
        return gnic;
    }

    public void setGnic(String gnic) {
        this.gnic = gnic;
    }

    public int getGtel() {
        return gtel;
    }

    public void setGtel(int gtel) {
        this.gtel = gtel;
    }

    public String getGaddress() {
        return gaddress;
    }

    public void setGaddress(String gaddress) {
        this.gaddress = gaddress;
    }

    public String getG2name() {
        return g2name;
    }

    public void setG2name(String g2name) {
        this.g2name = g2name;
    }

    public String getG2nic() {
        return g2nic;
    }

    public void setG2nic(String g2nic) {
        this.g2nic = g2nic;
    }

    public int getG2tel() {
        return g2tel;
    }

    public void setG2tel(int g2tel) {
        this.g2tel = g2tel;
    }

    public String getG2address() {
        return g2address;
    }

    public void setG2address(String g2address) {
        this.g2address = g2address;
    }

    @Override
    public String toString() {
        return "Guarantor{" +
                "gid=" + gid +
                ", gname='" + gname + '\'' +
                ", gnic='" + gnic + '\'' +
                ", gtel=" + gtel +
                ", gaddress='" + gaddress + '\'' +
                ", g2name='" + g2name + '\'' +
                ", g2nic='" + g2nic + '\'' +
                ", g2tel=" + g2tel +
                ", g2address='" + g2address + '\'' +
                '}';
    }
}
