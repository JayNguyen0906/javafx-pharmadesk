package DTO;

public class Item {
    private int id;
    private String name;
    private double price;
    private String classification;

    public Item(int id, String name, double price, String classification) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.classification = classification;
    }

    public Item() {
    }

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
    
    @Override
    public String toString() {
        return name;  // Trả về tên để AutoCompletion gợi ý theo tên
    }
}
