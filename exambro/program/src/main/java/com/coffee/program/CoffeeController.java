package com.coffee.program;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class CoffeeController {

    private final CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model) {
        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeService.deleteCoffeeExam(id);
        return "redirect:/";
    }

    @GetMapping("/add")
    public String add() {
        return "new";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam String type,
                       @RequestParam String size,
                       @RequestParam double price,
                       @RequestParam String roastLevel,
                       @RequestParam String origin,
                       @RequestParam Boolean isDecaf,
                       @RequestParam int stock,
                       @RequestParam String flavorNotes,
                       @RequestParam String brewMethod) {
        CoffeeExam coffee = new CoffeeExam();
        coffee.setId(coffeeService.generateNewId());
        coffee.setName(name);
        coffee.setType(type);
        coffee.setSize(size);
        coffee.setPrice(price);
        coffee.setRoastLevel(roastLevel);
        coffee.setOrigin(origin);
        coffee.setDecaf(isDecaf);
        coffee.setStock(stock);
        coffee.setFlavorNotes(flavorNotes != null && !flavorNotes.isEmpty() ? Arrays.asList(flavorNotes.split(";")) : new ArrayList<>());
        coffee.setBrewMethod(brewMethod);

        coffeeService.addCoffee(coffee);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @RequestParam String name,
                         @RequestParam String type,
                         @RequestParam String size,
                         @RequestParam double price,
                         @RequestParam String roastLevel,
                         @RequestParam String origin,
                         @RequestParam(required = false) Boolean isDecaf,
                         @RequestParam int stock,
                         @RequestParam String flavorNotes,
                         @RequestParam String brewMethod) {
        CoffeeExam coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            coffee.setName(name);
            coffee.setType(type);
            coffee.setSize(size);
            coffee.setPrice(price);
            coffee.setRoastLevel(roastLevel);
            coffee.setOrigin(origin);
            if (isDecaf != null) {
                coffee.setDecaf(isDecaf);
            }
            coffee.setStock(stock);
            coffee.setFlavorNotes(flavorNotes != null && !flavorNotes.isEmpty() ? Arrays.asList(flavorNotes.split(";")) : new ArrayList<>());
            coffee.setBrewMethod(brewMethod);
            coffeeService.updateCoffee(id, coffee);
        }
        return "redirect:/";
    }
