package com.erns.CoffeeApplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coffees")
public class CoffeeController {

    private final List<com.example.coffee.Coffee> coffeeList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<com.example.coffee.Coffee>> getAllCoffees() {
        return new ResponseEntity<>(coffeeList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.example.coffee.Coffee> getCoffeeById(@PathVariable int id) {
        Optional<com.example.coffee.Coffee> coffee = findCoffeeById(id);
        return coffee.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<com.example.coffee.Coffee> createCoffee(@RequestBody com.example.coffee.Coffee coffee) {
        coffee.setId(coffeeList.size() + 1);
        coffeeList.add(coffee);
        return new ResponseEntity<>(coffee, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<com.example.coffee.Coffee> updateCoffee(@PathVariable int id, @RequestBody com.example.coffee.Coffee updatedCoffee) {
        Optional<com.example.coffee.Coffee> existingCoffee = findCoffeeById(id);
        if (existingCoffee.isPresent()) {
            updatedCoffee.setId(id);
            coffeeList.removeIf(c -> c.getId() == id);
            coffeeList.add(updatedCoffee);
            return new ResponseEntity<>(updatedCoffee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoffee(@PathVariable int id) {
        boolean removed = coffeeList.removeIf(coffee -> coffee.getId() == id);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private Optional<com.example.coffee.Coffee> findCoffeeById(int id) {
        return coffeeList.stream()
                .filter(coffee -> coffee.getId() == id)
                .findFirst();
    }
}