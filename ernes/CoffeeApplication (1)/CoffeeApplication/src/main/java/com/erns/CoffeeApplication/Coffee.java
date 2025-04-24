package com.erns.CoffeeApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Coffee {

    private int id;
    private String name;
    private String string;
    // You might have other properties like origin, roast level, etc.

    public Coffee() {
        // Default constructor
    }

    public Coffee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public <T> Coffee(int i, String espresso, String arabica, String small, double v, String dark, String ethiopia, boolean b, int i1, List<T> list, String espresso1) {
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

    public String getType() {
        return "";
    }

    public String getSize() {
        return "";
    }

    public String getBrewMethod() {
        return "";
    }

    public Collection<Object> getFlavorNotes() {
        return java.util.List.of();
    }

    public String getRoastLevel() {
        return "";
    }

    public String getOrigin() {
        return "";
    }

    public int getPrice() {
        return 0;
    }

    public int isDecaf() {
        return 0;
    }

    public int getStock() {
        return 0;
    }

    public void setType(String datum) {
    }

    public void setFlavorNotes(ArrayList<?> objects) {
    }

    public void setSize(String datum) {
        this.string = datum;
    }

    public void setPrice(double v) {
    }

    public void setRoastLevel(String datum) {
    }

    public void setDecaf(boolean b) {
    }

    public void setOrigin(String datum) {
    }

    public void setStock(int i) {
    }

    public void setBrewMethod(String datum) {
    }

    // You'll likely have getters and setters for other properties as well
}