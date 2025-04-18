package DTO;

public class Good {

	private String ID_Good;
	private String Name_Good;
	private String Classification_Good;
	private double Price_Good;

	public Good() {
	}

	public Good(String iD_Good, String name_Good, String classification_Good, double price_Good) {
		ID_Good = iD_Good;
		Name_Good = name_Good;
		Classification_Good = classification_Good;
		Price_Good = price_Good;
	}

	public String getID_Good() {
		return ID_Good;
	}

	public void setID_Good(String iD_Good) {
		ID_Good = iD_Good;
	}

	public String getName_Good() {
		return Name_Good;
	}

	public void setName_Good(String name_Good) {
		Name_Good = name_Good;
	}

	public String getClassification_Good() {
		return Classification_Good;
	}

	public void setClassification_Good(String classification_Good) {
		Classification_Good = classification_Good;
	}

	public double getPrice_Good() {
		return Price_Good;
	}

	public void setPrice_Good(double price_Good) {
		Price_Good = price_Good;
	}

	@Override
	public String toString() {
		return "Good [ID_Good=" + ID_Good + ", Name_Good=" + Name_Good + ", Classification_Good=" + Classification_Good
				+ ", Price_Good=" + Price_Good + "]";
	}

}
