package com.ern.jumon.coffee;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static java.util.regex.Pattern.matches;

@Controller
public class CoffeeAuthController {

    @Autowired
    private CoffeeAuthController coffeeUserService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("coffeeUser", new CoffeeAuthController());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("coffeeUser") CoffeeAuthController user,
                        BindingResult bindingResult,
                        HttpSession session,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        CoffeeAuthController foundUser = null; // TODO: Implement proper user lookup
        CoffeeAuthController authController = new CoffeeAuthController();
        if (foundUser != null && coffeeUserService.getPassword() != null) {
            boolean isPasswordMatch = matches((String) coffeeUserService.getPassword(), (CharSequence) foundUser.getPassword());
            session.setAttribute("coffeeUser", foundUser);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid credentials");
        }

        return "login";
    }

    private CoffeeAuthController coffeeUserService() {
        return null; // TODO: Implement proper user lookup
    }

    private Object getPassword() {
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}