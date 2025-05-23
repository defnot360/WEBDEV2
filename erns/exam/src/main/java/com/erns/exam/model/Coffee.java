package com.erns.exam.model;


import java.util.List;

public class Coffee {
    private int id;
    private String name;
    private String type;
    private String size;
    private double price;
    private String roastLevel;
    private String origin;
    private boolean isDecaf;
    private int stock;
    private List<String> flavorNotes;
    private String brewMethod;
    private String imageUrl; // ✅ NEW FIELD for coffee image

    // Default constructor
    public Coffee() {
    }

    // Constructor without imageUrl
    public Coffee(int id, String name, String type, String size, double price, String roastLevel,
                  String origin, boolean isDecaf, int stock, List<String> flavorNotes, String brewMethod) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
    }

    // ✅ Constructor with imageUrl
    public Coffee(int id, String name, String type, String size, double price, String roastLevel,
                  String origin, boolean isDecaf, int stock, List<String> flavorNotes,
                  String brewMethod, String imageUrl) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
        this.imageUrl = imageUrl;
    }

    // ✅ Constructor without id (for new entries)
    public Coffee(String name, String type, String size, double price, String roastLevel,
                  String origin, boolean isDecaf, int stock, List<String> flavorNotes,
                  String brewMethod, String imageUrl) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRoastLevel() {
        return roastLevel;
    }

    public void setRoastLevel(String roastLevel) {
        this.roastLevel = roastLevel;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isDecaf() {
        return isDecaf;
    }

    public void setDecaf(boolean decaf) {
        isDecaf = decaf;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getFlavorNotes() {
        return flavorNotes;
    }

    public void setFlavorNotes(List<String> flavorNotes) {
        this.flavorNotes = flavorNotes;
    }

    public String getBrewMethod() {
        return brewMethod;
    }

    public void setBrewMethod(String brewMethod) {
        this.brewMethod = brewMethod;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ✅ toString() method for logging/debugging
    @Override
    public String toString() {
        return "Coffee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", roastLevel='" + roastLevel + '\'' +
                ", origin='" + origin + '\'' +
                ", isDecaf=" + isDecaf +
                ", stock=" + stock +
                ", flavorNotes=" + flavorNotes +
                ", brewMethod='" + brewMethod + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}