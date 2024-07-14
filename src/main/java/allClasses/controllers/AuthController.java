package allClasses.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/logout")
    public String showLogoutPage() {
        return "logout";
    }

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup";
    }

//    @PostMapping("perform_signup")
//    public String signup(Model model) {
//        return "redirect:/";
//    }

}
