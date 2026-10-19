package DTO;

public class Employee {

    private int ID_Emp;
    private String Name_Emp;
    private String Position_Emp;
    private String Email_Emp;
    private String Phone_Emp;
    private String CCCD_Emp;

    public Employee() {
    }

    public Employee(String name_Emp,String position_Emp ,String email_Emp, String phone_Emp, String cccd_Emp) {
        Name_Emp = name_Emp;
        Position_Emp = position_Emp;
        Email_Emp = email_Emp;
        Phone_Emp = phone_Emp;
        CCCD_Emp = cccd_Emp;
    }
    
    public Employee(int id_Emp, String name_Emp,String position_Emp ,String email_Emp, String phone_Emp, String cccd_Emp) {
        ID_Emp = id_Emp;
        Name_Emp = name_Emp;
        Position_Emp = position_Emp;
        Email_Emp = email_Emp;
        Phone_Emp = phone_Emp;
        CCCD_Emp = cccd_Emp;
    }

    public int getID_Emp() {
        return ID_Emp;
    }

    public void setID_Emp(int id_Emp) {
        ID_Emp = id_Emp;
    }

    public String getName_Emp() {
        return Name_Emp;
    }

    public void setName_Emp(String name_Emp) {
        Name_Emp = name_Emp;
    }

    public String getPosition_Emp() {
        return Position_Emp;
    }

    public void setPosition_Emp(String position_Emp) {
        Position_Emp = position_Emp;
    }

    public String getEmail_Emp() {
        return Email_Emp;
    }

    public void setEmail_Emp(String email_Emp) {
        Email_Emp = email_Emp;
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



}