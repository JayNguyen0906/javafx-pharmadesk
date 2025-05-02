package main.java.DTO;

public class Customer {
	private String Id_Cus;
	private String Name_Cus;
	private String Phone_Cus;
	private String Address_Cus;

	public Customer() {

	}

	public Customer(String id_Cus, String name_Cus, String phone_Cus, String address_Cus) {
		Id_Cus = id_Cus;
		Name_Cus = name_Cus;
		Phone_Cus = phone_Cus;
		Address_Cus = address_Cus;
	}

	public String getId_Cus() {
		return Id_Cus;
	}

	public void setId_Cus(String id_Cus) {
		Id_Cus = id_Cus;
	}

	public String getName_Cus() {
		return Name_Cus;
	}

	public void setName_Cus(String name_Cus) {
		Name_Cus = name_Cus;
	}

	public String getPhone_Cus() {
		return Phone_Cus;
	}

	public void setPhone_Cus(String phone_Cus) {
		Phone_Cus = phone_Cus;
	}
	
	public String getAddress_Cus() {
		return Address_Cus;
	}

	public void setAddress_Cus(String address_Cus) {
		Address_Cus = address_Cus;
	}
}