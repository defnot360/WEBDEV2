package com.ern.jumon.coffee;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Controller
public class CoffeeController {

    @Autowired
    CoffeeController coffeeService;

    private final String[] types = {"Affogato", "Espresso", "Americano", "Latte", "Cappuccino", "Mocha", "Flat White", "Iced Coffee"};
    private final String[] sizes = {"Small", "Medium", "Large"};
    private final String[] roastLevels = {"Light", "Medium", "Dark"};
    private final String[] brewMethods = {"Drip", "French Press", "Espresso", "Filter"};

    /**
     * Displays the home page with a list of coffees.
     *
     * @param search Search query to filter coffee entries.
     * @param model  Model object for passing data to the view.
     * @return Name of the Thymeleaf template to render.
     */
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "") String search, Model model, HttpSession session) {
        CoffeeController currentUser = (CoffeeController) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }

        model.addAttribute("coffees", coffeeService.searchCoffee(search));
        return "index";
    }

    private Object searchCoffee(String search) {
        return null;
    }


    /**
     * Deletes a coffee entry by ID.
     *
     * @param id The ID of the coffee to delete.
     * @return Redirects to the home page after deletion.
     */
    @GetMapping("/delete")
    public String delete(@RequestParam int id, HttpSession session) {
        CoffeeController currentUser = (CoffeeController) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }

        coffeeService.deleteCoffee(id);
        return "redirect:/";
    }

    private void deleteCoffee(int id) {
    }

    /**
     * Displays the add coffee form.
     *
     * @param model Model object for passing data to the view.
     * @return Name of the Thymeleaf template to render.
     */
    @GetMapping("/add")
    public String add(Model model, HttpSession session) {
        CoffeeController currentUser = (CoffeeController) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return "redirect:/login";
        }
        model.addAttribute("coffee", new CoffeeController());
        model.addAttribute("types", types);
        model.addAttribute("sizes", sizes);
        model.addAttribute("roastLevels", roastLevels);
        model.addAttribute("brewMethods", brewMethods);
        return "add";
    }

    /**
     * Handles submission of the add coffee form.
     *
     * @param coffee         The Coffee object populated from the form.
     * @param bindingResult  Validation result.
     * @param model          Model object for passing data back to the view if there are errors.
     * @return Redirects to the home page or reloads the add form on validation failure.
     */
    @PostMapping("/save")
    public String store(@ModelAttribute("coffee") CoffeeController coffee,
                        BindingResult bindingResult,
                        @RequestParam(value = "imageFile") MultipartFile coffeePicture, Model model, HttpSession session) {

        CoffeeController currentUser = (CoffeeController) session.getAttribute("coffeeUser");
        if (currentUser == null) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "add";  // Make sure the 'add' template is loaded
        }

        // Assign ID BEFORE handling the image
        int newId = coffeeService.getLastId() + 1;
        coffee.setId(newId);
        coffeeService.edit(coffee, newId);

        // Handle image upload
        if (!coffeePicture.isEmpty()) {
            String path = "data/coffee_pictures/";
            File uploadFolder = new File(path);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = UUID.randomUUID() + coffeePicture.getOriginalFilename().substring(coffeePicture.getOriginalFilename().lastIndexOf("."));
            try {
                coffeePicture.transferTo(new File(uploadFolder.getAbsolutePath() + File.separator + fileName));
                coffee.setCoffeePicture(fileName);
            } catch (IOException e) {
                System.out.println("File upload error: " + e.getMessage());
            }
        }

        coffeeService.addCoffee(coffee);  // Save the coffee with the new picture
        return "redirect:/";  // Redirect to the list page
    }

    private void edit(CoffeeController coffee, int newId) {
    }

    private void setId(int newId) {
    }

    private void addCoffee(CoffeeController coffee) {
    }

    private void setCoffeePicture(String fileName) {
    }

    private int getLastId() {
        return 0;
    }


    /**
     * Displays the edit form for a specific coffee entry.
     *
     * @param id    The ID of the coffee to edit.
     * @param model Model object for passing data to the view.
     */
    @GetMapping("/edit")
    public void edit(@RequestParam int id, Model model, HttpSession session) {
        CoffeeController currentUser = (CoffeeController) session.getAttribute("coffeeUser");
        if(currentUser == null){
            return;
        }
        CoffeeController coffee = coffeeService.getCoffee(id);
        if (coffee != null) {
            model.addAttribute("coffee", coffee);
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
        }
    }

    private CoffeeController getCoffee(int id) {
        return null;
    }

    /**
     * Handles submission of the edit coffee form.
     *
     * @param session         The updated Coffee object.
     * @param bindingResult  Validation result.
     * @param model          Model object for passing data back to the view if there are errors.
     * @return Redirects to the home page or reloads the edit form on validation failure.
     */
    @PostMapping("/update")
    public <coffeeController> String update(@ModelAttribute("coffee") coffeeController coffee, BindingResult bindingResult, Model model, HttpSession session) {
        coffeeController currentUser = (coffeeController) session.getAttribute("coffeeCoffeeController");
        if(currentUser == null){
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("types", types);
            model.addAttribute("sizes", sizes);
            model.addAttribute("roastLevels", roastLevels);
            model.addAttribute("brewMethods", brewMethods);
            return "edit";
        }
        coffeeService.updateCoffee(coffee);
        return "redirect:/";
    }

    private <CoffeeController> void updateCoffee(CoffeeController coffee) {
    }

    private static Object getId() {
        return null;
    }

    @GetMapping("/coffee/{id}")
    public String view(@PathVariable int id, Model model) {
        CoffeeController coffee = coffeeService.getCoffee(id);
        model.addAttribute("coffee", coffee);
        return "coffee"; // This should point to a Thymeleaf template named coffee.html
    }

}
