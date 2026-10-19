package DTO;

public class Customer {
    private int id_Cus;
    private String name_Cus;
    private int birthYear;
    private String gender;
    private String phone_Cus;
    private String address_Cus;
    private String healthInsuranceNumber;

    public Customer() {
    }

    public Customer(int id_Cus, String name_Cus, int birthYear, String gender,
                    String phone_Cus, String address_Cus, String healthInsuranceNumber) {
        this.id_Cus = id_Cus;
        this.name_Cus = name_Cus;
        this.birthYear = birthYear;
        this.gender = gender;
        this.phone_Cus = phone_Cus;
        this.address_Cus = address_Cus;
        this.healthInsuranceNumber = healthInsuranceNumber;
    }
    
    public Customer(String name_Cus, int birthYear, String gender,
            String phone_Cus, String address_Cus, String healthInsuranceNumber) {
		this.name_Cus = name_Cus;
		this.birthYear = birthYear;
		this.gender = gender;
		this.phone_Cus = phone_Cus;
		this.address_Cus = address_Cus;
		this.healthInsuranceNumber = healthInsuranceNumber;
	}

    public int getId_Cus() {
        return id_Cus;
    }

    public void setId_Cus(int id_Cus) {
        this.id_Cus = id_Cus;
    }

    public String getName_Cus() {
        return name_Cus;
    }

    public void setName_Cus(String name_Cus) {
        this.name_Cus = name_Cus;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_Cus() {
        return phone_Cus;
    }

    public void setPhone_Cus(String phone_Cus) {
        this.phone_Cus = phone_Cus;
    }

    public String getAddress_Cus() {
        return address_Cus;
    }

    public void setAddress_Cus(String address_Cus) {
        this.address_Cus = address_Cus;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

}
