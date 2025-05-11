package DTO;

import java.sql.Date;

public class Warehouse {
	private String itemName;
	private String supplierName;
	private int currentQuantity;
	private Date expiry;
	private double primeCost;

	public Warehouse(String itemName, String supplierName, int currentQuantity, Date expiry, double primeCost) {
		this.itemName = itemName;
		this.supplierName = supplierName;
		this.currentQuantity = currentQuantity;
		this.expiry = expiry;
		this.primeCost = primeCost;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public int getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(int currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public double getPrimeCost() {
		return primeCost;
	}

	public void setPrimeCost(double primeCost) {
		this.primeCost = primeCost;
	}
	
}