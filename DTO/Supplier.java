package DTO;

public class Supplier {
	private String ID_Supp;
	private String Name_Supp;
	private String Phone_Supp;
	private String Email_Supp;

	public Supplier() {
	}

	public Supplier(String iD_Supp, String name_Supp, String phone_Supp, String email_Supp) {
		ID_Supp = iD_Supp;
		Name_Supp = name_Supp;
		Phone_Supp = phone_Supp;
		Email_Supp = email_Supp;
	}

	public String getID_Supp() {
		return ID_Supp;
	}

	public void setID_Supp(String iD_Supp) {
		ID_Supp = iD_Supp;
	}

	public String getName_Supp() {
		return Name_Supp;
	}

	public void setName_Supp(String name_Supp) {
		Name_Supp = name_Supp;
	}

	public String getPhone_Supp() {
		return Phone_Supp;
	}

	public void setPhone_Supp(String phone_Supp) {
		Phone_Supp = phone_Supp;
	}

	public String getEmail_Supp() {
		return Email_Supp;
	}

	public void setEmail_Supp(String email_Supp) {
		Email_Supp = email_Supp;
	}

	@Override
	public String toString() {
		return "Supplier [ID_Supp=" + ID_Supp + ", Name_Supp=" + Name_Supp + ", Phone_Supp=" + Phone_Supp
				+ ", Email_Supp=" + Email_Supp + "]";
	}

}
