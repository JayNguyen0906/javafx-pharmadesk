package DTO;

import java.util.Date;

public class Bill {
    private String ID_Bill;
    private String ID_Good;
    private String ID_Cus;
    private int Quantity;
    private double TotalAmount;
    private Date OrderDate;
    private String Status;

    public Bill() {
    }

    public Bill(String iD_Bill, String iD_Good, String iD_Cus, int quantity, double totalAmount, Date orderDate,
                String status) {
        ID_Bill = iD_Bill;
        ID_Good = iD_Good;
        ID_Cus = iD_Cus;
        Quantity = quantity;
        TotalAmount = totalAmount;
        OrderDate = orderDate;
        Status = status;
    }

    public String getID_Bill() {
        return ID_Bill;
    }

    public void setID_Bill(String iD_Bill) {
        ID_Bill = iD_Bill;
    }

    public String getID_Good() {
        return ID_Good;
    }

    public void setID_Good(String iD_Good) {
        ID_Good = iD_Good;
    }

    public String getID_Cus() {
        return ID_Cus;
    }

    public void setID_Cus(String iD_Cus) {
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

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
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
        return "Bill [ID_Bill=" + ID_Bill + ", ID_Good=" + ID_Good + ", ID_Cus=" + ID_Cus + ", Quantity=" + Quantity
                + ", TotalAmount=" + TotalAmount + ", OrderDate=" + OrderDate + ", Status=" + Status + "]";
    }

}