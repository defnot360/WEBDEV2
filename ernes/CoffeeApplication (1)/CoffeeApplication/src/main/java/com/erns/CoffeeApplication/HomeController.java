package com.erns.CoffeeApplication;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final List<Coffee> coffeeList = new ArrayList<>();

    public HomeController() {
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 3.50, "Dark", "Ethiopia", false, 10, Arrays.asList("Chocolate", "Nutty"), "Espresso"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 4.50, "Medium", "Brazil", false, 8, Arrays.asList("Creamy", "Sweet"), "Drip"));

    }

    // Display all coffees or search by name
    @GetMapping("/")
    public String getCoffees(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Coffee> filteredCoffees = coffeeList;

        if (search != null && !search.isEmpty()) {
            filteredCoffees = coffeeList.stream()
                    .filter(coffee -> coffee.getName().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("coffees", filteredCoffees);
        model.addAttribute("search", search);
        return "index"; // Thymeleaf template for displaying the list
    }

    // Display form for adding a new coffee
    @GetMapping("/add")
    public String addCoffee(Model model) {
        model.addAttribute("coffee", new Coffee(0, "", "", "", 0.0, "", "", false, 0, Arrays.asList(), "")); // Empty object for the form
        return "new"; // Thymeleaf template for adding a new coffee
    }

    // Save a new coffee
    @PostMapping("/save")
    public String saveCoffee(@ModelAttribute Coffee coffee) {
        int newId = coffeeList.size() > 0 ? coffeeList.get(coffeeList.size() - 1).getId() + 1 : 1;
        coffee.setId(newId);
        coffeeList.add(coffee);
        return "redirect:/"; // Redirect to the list after saving
    }

    // Display form for editing an existing coffee
    @GetMapping("/edit/{id}")
    public String editCoffee(@PathVariable("id") int id, Model model) {
        Coffee coffee = coffeeList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
            return "edit"; // Thymeleaf template for editing coffee
        }
        return "redirect:/"; // Redirect if coffee not found
    }

    @PostMapping("/update/{id}")
    public String updateCoffee(@PathVariable("id") int id, @ModelAttribute Coffee updatedCoffee) {
        for (int i = 0; i < coffeeList.size(); i++) {
            if (coffeeList.get(i).getId() == id) {
                updatedCoffee.setId(id);
                coffeeList.set(i, updatedCoffee);
                break;
            }
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteCoffee(@PathVariable("id") int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        return "redirect:/";
    }
}

