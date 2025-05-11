package DTO;

public class Supplier {
	private String ID_Sup;
	private String Name_Sup;
	private String Phone_Sup;
	private String Email_Sup;

	public Supplier() {
	}

	public Supplier(String iD_Sup, String name_Sup, String phone_Sup, String email_Sup) {
		ID_Sup = iD_Sup;
		Name_Sup = name_Sup;
		Phone_Sup = phone_Sup;
		Email_Sup = email_Sup;
	}

	public String getID_Sup() {
		return ID_Sup;
	}

	public void setID_Sup(String iD_Sup) {
		ID_Sup = iD_Sup;
	}

	public String getName_Sup() {
		return Name_Sup;
	}

	public void setName_Sup(String name_Sup) {
		Name_Sup = name_Sup;
	}

	public String getPhone_Sup() {
		return Phone_Sup;
	}

	public void setPhone_Sup(String phone_Sup) {
		Phone_Sup = phone_Sup;
	}

	public String getEmail_Sup() {
		return Email_Sup;
	}

	public void setEmail_Sup(String email_Sup) {
		Email_Sup = email_Sup;
	}

	@Override
	public String toString() {
		return "Suplier [ID_Sup=" + ID_Sup + ", Name_Sup=" + Name_Sup + ", Phone_Sup=" + Phone_Sup
				+ ", Email_Sup=" + Email_Sup + "]";
	}

}
