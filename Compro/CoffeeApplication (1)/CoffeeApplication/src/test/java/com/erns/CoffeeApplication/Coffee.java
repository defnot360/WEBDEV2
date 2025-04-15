package com.example.coffee;

public class Coffee {

    private int id;
    private String name;
    // You might have other properties like origin, roast level, etc.

    public Coffee() {
        // Default constructor
    }

    public Coffee(int id, String name) {
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

    // You'll likely have getters and setters for other properties as well
}