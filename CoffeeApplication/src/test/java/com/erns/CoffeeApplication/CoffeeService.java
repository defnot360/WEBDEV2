package com.example.coffee;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {

    private List<com.example.coffee.Coffee> coffeeList = new ArrayList<>();
    private int nextId = 1;

    public List<com.example.coffee.Coffee> getAllCoffees() {
        return coffeeList;
    }

    public Optional<com.example.coffee.Coffee> getCoffeeById(int id) {
        return coffeeList.stream()
                .filter(coffee -> coffee.getId() == id)
                .findFirst();
    }

    public com.example.coffee.Coffee createCoffee(com.example.coffee.Coffee coffee) {
        coffee.setId(nextId++);
        coffeeList.add(coffee);
        return coffee;
    }

    public Optional<com.example.coffee.Coffee> updateCoffee(int id, com.example.coffee.Coffee updatedCoffee) {
        Optional<com.example.coffee.Coffee> existingCoffee = getCoffeeById(id);
        if (existingCoffee.isPresent()) {
            updatedCoffee.setId(id);
            coffeeList.removeIf(c -> c.getId() == id);
            coffeeList.add(updatedCoffee);
            return Optional.of(updatedCoffee);
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteCoffee(int id) {
        return coffeeList.removeIf(coffee -> coffee.getId() == id);
    }
}
