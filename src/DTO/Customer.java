package DTO;

public class Customer {
	private String Id_Cus;
	private String Name_Cus;
	private String Phone_Cus;

	public Customer() {

	}

	public Customer(String id_Cus, String name_Cus, String phone_Cus) {
		Id_Cus = id_Cus;
		Name_Cus = name_Cus;
		Phone_Cus = phone_Cus;
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

	@Override
	public String toString() {
		return "Customer{" + "Id_Cus='" + Id_Cus + '\'' + ", Name_Cus='" + Name_Cus + '\'' + ", Phone_Cus='" + Phone_Cus
				+ '\'' + '}';
	}
}