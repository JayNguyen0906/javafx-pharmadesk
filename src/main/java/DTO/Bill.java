package DTO;

import java.time.LocalDate;

public class Bill {
	private int ID_Bill;
	private int ID_Cus;
	private int Quantity;
	private double TotalAmount;
	private LocalDate OrderDate;
	private String Status;

	public Bill() {
	}
	
	public Bill(int iD_Bill, int iD_Cus, int quantity, double totalAmount, LocalDate orderDate,
			String status) {
		ID_Bill = iD_Bill;
		ID_Cus = iD_Cus;
		Quantity = quantity;
		TotalAmount = totalAmount;
		OrderDate = orderDate;
		Status = status;
	}
	
	public Bill( int iD_Cus, int quantity, double totalAmount, LocalDate orderDate,
			String status) {
		ID_Cus = iD_Cus;
		Quantity = quantity;
		TotalAmount = totalAmount;
		OrderDate = orderDate;
		Status = status;
	}

	public Bill(int iD_Bill, int iD_Item, int iD_Cus, int quantity, double totalAmount, LocalDate orderDate,
			String status) {
		ID_Bill = iD_Bill;
		ID_Cus = iD_Cus;
		Quantity = quantity;
		TotalAmount = totalAmount;
		OrderDate = orderDate;
		Status = status;
	}

	public int getID_Bill() {
		return ID_Bill;
	}

	public void setID_Bill(int iD_Bill) {
		ID_Bill = iD_Bill;
	}

	public int getID_Cus() {
		return ID_Cus;
	}

	public void setID_Cus(int iD_Cus) {
		ID_Cus = iD_Cus;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public double getTotalAmount() {
		return TotalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		TotalAmount = totalAmount;
	}

	public LocalDate getOrderDate() {
		return OrderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		OrderDate = orderDate;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "Bill [ID_Bill=" + ID_Bill + ", ID_Item=" + ", ID_Cus=" + ID_Cus + ", Quantity=" + Quantity
				+ ", TotalAmount=" + TotalAmount + ", OrderDate=" + OrderDate + ", Status=" + Status + "]";
	}

}
