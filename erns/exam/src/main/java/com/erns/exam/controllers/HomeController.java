package com.erns.exam.controllers;

import com.erns.exam.model.Coffee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private List<Coffee> coffeeList = new ArrayList<>();

    public HomeController() {
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 196.00, "Dark", "Ethiopia", false, 10, Arrays.asList("Chocolate", "Nutty"), "Espresso"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 252.00, "Medium", "Brazil", false, 8, Arrays.asList("Creamy", "Sweet"), "Drip"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 280.00, "Medium", "Colombia", false, 12, Arrays.asList("Fruity", "Bold"), "French Press"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 266.00, "Dark", "Guatemala", false, 6, Arrays.asList("Chocolate", "Smooth"), "Espresso"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 182.00, "Light", "Kenya", false, 15, Arrays.asList("Citrus", "Balanced"), "Drip"));
    }

    // List & Search
    @GetMapping("/")
    public String getCoffees(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<Coffee> filteredCoffees;

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            filteredCoffees = coffeeList.stream()
                    .filter(coffee -> coffee.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            filteredCoffees = new ArrayList<>(coffeeList);
        }

        // Sort alphabetically by name
        filteredCoffees.sort(Comparator.comparing(Coffee::getName, String.CASE_INSENSITIVE_ORDER));

        model.addAttribute("coffees", filteredCoffees);
        return "index";
    }

    // Add
    @GetMapping("/add")
    public String addCoffeeForm() {
        return "new";
    }

    @PostMapping("/save")
    public String saveCoffee(@RequestParam String name,
                             @RequestParam String type,
                             @RequestParam String size,
                             @RequestParam double price,
                             @RequestParam String roastLevel,
                             @RequestParam String origin,
                             @RequestParam(defaultValue = "false") boolean isDecaf,
                             @RequestParam int stock,
                             @RequestParam List<String> flavorNotes,
                             @RequestParam String brewMethod,
                             @RequestParam("imageFile") MultipartFile imageFile) {

        String imageUrl = saveImage(imageFile); // store and get path

        int newId = coffeeList.isEmpty() ? 1 : coffeeList.stream().mapToInt(Coffee::getId).max().getAsInt() + 1;

        Coffee newCoffee = new Coffee(newId, name, type, size, price, roastLevel, origin,
                isDecaf, stock, flavorNotes, brewMethod);
        newCoffee.setImageUrl(imageUrl); // set image URL

        coffeeList.add(newCoffee);
        return "redirect:/";
    }

    // Edit
    @GetMapping("/edit")
    public String editCoffee(@RequestParam int id, Model model) {
        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == id) {
                model.addAttribute("coffee", coffee);
                return "edit";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateCoffee(@RequestParam int id,
                               @RequestParam String name,
                               @RequestParam String type,
                               @RequestParam String size,
                               @RequestParam double price,
                               @RequestParam String roastLevel,
                               @RequestParam String origin,
                               @RequestParam(defaultValue = "false") boolean isDecaf,
                               @RequestParam int stock,
                               @RequestParam List<String> flavorNotes,
                               @RequestParam String brewMethod) {

        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == id) {
                coffee.setName(name);
                coffee.setType(type);
                coffee.setSize(size);
                coffee.setPrice(price);
                coffee.setRoastLevel(roastLevel);
                coffee.setOrigin(origin);
                coffee.setDecaf(isDecaf);
                coffee.setStock(stock);
                coffee.setFlavorNotes(flavorNotes);
                coffee.setBrewMethod(brewMethod);
                break;
            }
        }
        return "redirect:/";
    }

    private String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        try {
            // Get original filename
            String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

            // Define the path to save the image
            String uploadDir = "src/main/resources/static/uploads/";
            java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + filename);
            java.nio.file.Files.createDirectories(path.getParent());

            // Save file
            java.nio.file.Files.write(path, imageFile.getBytes());

            // Return relative path used in HTML
            return "/uploads/" + filename;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // Delete
    @GetMapping("/delete")
    public String deleteCoffee(@RequestParam int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);
        return "redirect:/";
    }

    @GetMapping("/menu")
    public String showMenu(Model model) {
        List<Coffee> sortedCoffees = new ArrayList<>(coffeeList);
        sortedCoffees.sort(Comparator.comparing(Coffee::getName, String.CASE_INSENSITIVE_ORDER));
        model.addAttribute("coffees", sortedCoffees);
        return "menu";  // This should correspond to menu.html
    }


}
