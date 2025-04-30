package DTO;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Good {
    private StringProperty id;
    private StringProperty name;
    private DoubleProperty price;
    private StringProperty quantity;

    // Constructor có tham số
    public Good(String id, String name, double price, String quantity) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleStringProperty(quantity);
    }

    // Constructor không tham số (khởi tạo các thuộc tính)
    public Good() {
        this.id = new SimpleStringProperty("");  // Khởi tạo giá trị mặc định
        this.name = new SimpleStringProperty("");
        this.price = new SimpleDoubleProperty(0.0);
        this.quantity = new SimpleStringProperty("");
    }

    // Getter and Setter methods
    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setQuantity(String quantity) {
        this.quantity.set(quantity);
    }

    public StringProperty quantityProperty() {
        return quantity;
    }
}
