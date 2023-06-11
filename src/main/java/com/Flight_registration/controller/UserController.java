package com.Flight_registration.controller;



import com.Flight_registration.entities.User;
import com.Flight_registration.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/login")
	public String showLoginPage() {
		return "login";
	}

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
       userRepository.save(user);
        return "login";
    }

    @GetMapping("/registration-success")
    public String showRegistrationSuccessPage() {
        return "registration_success";
    }
    @PostMapping("/saveLogin")
    public String processLogin(User user, Model model) {
        // Check if the user exists in the repository
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            // User exists and password matches, perform login logic
            // For example, set a session attribute to mark the user as logged in
            // Or redirect to a secure page
            return "findFlight";
        } else {
            // User does not exist or password is incorrect
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
}

