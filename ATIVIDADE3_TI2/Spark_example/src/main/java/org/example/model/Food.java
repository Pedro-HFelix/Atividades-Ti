package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Food {
    private int id;
    private String name;
    private float price;
    private int quantity;
    private LocalDateTime dateOfFabrication;
    private LocalDate validDate;

    public Food(int id, String name, float price, int quantity, LocalDateTime dateOfFabrication, LocalDate validDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.dateOfFabrication = dateOfFabrication;
        this.validDate = validDate;
    }

    public Food( float price, int quantity, LocalDateTime dateOfFabrication, LocalDate validDate) {
        this.price = price;
        this.quantity = quantity;
        this.dateOfFabrication = dateOfFabrication;
        this.validDate = validDate;
    }

    public Food() {}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateOfFabrication() {
        return dateOfFabrication;
    }

    public void setDateOfFabrication(LocalDateTime dateOfFabrication) {
        this.dateOfFabrication = dateOfFabrication;
    }

    public LocalDate getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", dateOfFabrication=" + dateOfFabrication +
                ", validDate=" + validDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food product = (Food) o;
        return id == product.id && Float.compare(price, product.price) == 0 && quantity == product.quantity &&  Objects.equals(dateOfFabrication, product.dateOfFabrication) && Objects.equals(validDate, product.validDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, dateOfFabrication, validDate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}