package DTO;

public class Medicine {
	private int itemId;
    private String name;
    private String dosage;
    private String note;
    private double price;

    public Medicine(int itemId, String name, String dosage, String note) {
    	this.itemId = itemId;
        this.name = name;
        this.dosage = dosage;
        this.note = note;
    }
    
    public Medicine(String name, String dosage, String note) {
        this.name = name;
        this.dosage = dosage;
        this.note = note;
    }
    
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Medicine() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public double getPrice() {
    	return price;
    }
    
	public void setPrice(double price) {
		this.price = price;
	}
}
