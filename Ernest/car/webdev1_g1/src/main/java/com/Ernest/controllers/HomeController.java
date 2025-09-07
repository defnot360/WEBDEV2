package com.Ernest.controllers;

import com.Ernest.exception.ResourceNotFoundException;
import com.Ernest.model.Car;
import com.Ernest.repository.CarRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

    private final CarRepository carRepository;

    public HomeController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Car> cars = carRepository.findAll();
        model.addAttribute("cars", cars);
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("car", new Car());
        return "create";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("car") Car car, BindingResult result) {
        if (result.hasErrors()) {
            return "create";
        }
        carRepository.save(car);
        return "redirect:/";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        model.addAttribute("car", car);
        return "show";
    }
}
