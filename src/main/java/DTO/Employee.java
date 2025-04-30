package DTO;

public class Employee {

    private String ID_Emp;
    private String Name_Emp;
    private String Phone_Emp;
    private String CCCD_Emp;

    public Employee() {
    }

    public Employee(String iD_Emp, String name_Emp, String phone_Emp, String cccd_Emp) {
        ID_Emp = iD_Emp;
        Name_Emp = name_Emp;
        Phone_Emp = phone_Emp;
        CCCD_Emp = cccd_Emp;
    }

    public String getID_Emp() {
        return ID_Emp;
    }

    public void setID_Emp(String iD_Emp) {
        ID_Emp = iD_Emp;
    }

    public String getName_Emp() {
        return Name_Emp;
    }

    public void setName_Emp(String name_Emp) {
        Name_Emp = name_Emp;
    }

    public String getPhone_Emp() {
        return Phone_Emp;
    }

    public void setPhone_Emp(String phone_Emp) {
        Phone_Emp = phone_Emp;
    }

    public String getCCCD_Emp() {
        return CCCD_Emp;
    }

    public void setCCCD_Emp(String cCCD_Emp) {
        CCCD_Emp = cCCD_Emp;
    }

    @Override
    public String toString() {
        return "Employee [ID_Emp=" + ID_Emp + ", Name_Emp=" + Name_Emp + ", Phone_Emp=" + Phone_Emp + ", CCCD_Emp="
                + CCCD_Emp + "]";
    }

}