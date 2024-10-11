package allClasses.controllers;

import allClasses.models.User;
import allClasses.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UsersService usersService;

    @Autowired
    public AuthController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {

        if (error != null) {
            model.addAttribute("error", "Неправильное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("logout", "Вы успешно вышли из системы");
        }

        return "security/login";
    }

    @GetMapping("/logout")
    public String showLogoutPage() {
        return "security/logout";
    }

    @GetMapping("/signup")
    public String showSignupPage(@ModelAttribute("user") User user) {
        return "security/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "security/signup";

        if (usersService.checkUser(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Пользователь с именем " + user.getUsername() + " уже существует");
            return "security/signup";
        }

        usersService.saveUser(user);
        return "redirect:/login";
    }

}
