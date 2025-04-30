package DTO;

import java.util.Date;

public class Warehouse {
	private String ID_Good;
	private String ID_Supp;
	private int CurrentQuantity;
	private Date Expiry;
	private double PrimeCost;

	public Warehouse() {
	}

	public Warehouse(String iD_Good, String iD_Supp, int currentQuantity, Date expiry, double primeCost) {
		ID_Good = iD_Good;
		ID_Supp = iD_Supp;
		CurrentQuantity = currentQuantity;
		Expiry = expiry;
		PrimeCost = primeCost;
	}

	public String getID_Good() {
		return ID_Good;
	}

	public void setID_Good(String iD_Good) {
		ID_Good = iD_Good;
	}

	public String getID_Supp() {
		return ID_Supp;
	}

	public void setID_Supp(String iD_Supp) {
		ID_Supp = iD_Supp;
	}

	public int getCurrentQuantity() {
		return CurrentQuantity;
	}

	public void setCurrentQuantity(int currentQuantity) {
		CurrentQuantity = currentQuantity;
	}

	public Date getExpiry() {
		return Expiry;
	}

	public void setExpiry(Date expiry) {
		Expiry = expiry;
	}

	public double getPrimeCost() {
		return PrimeCost;
	}

	public void setPrimeCost(double primeCost) {
		PrimeCost = primeCost;
	}

	@Override
	public String toString() {
		return "Warehouse [ID_Good=" + ID_Good + ", ID_Supp=" + ID_Supp + ", CurrentQuantity=" + CurrentQuantity
				+ ", Expiry=" + Expiry + ", PrimeCost=" + PrimeCost + "]";
	}

}
