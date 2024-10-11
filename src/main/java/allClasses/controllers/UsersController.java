package allClasses.controllers;

import allClasses.models.User;
import allClasses.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {

    private final UsersService usersService;
    private User user;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // Получить список заказов
    @GetMapping("/profile")
    public String showProfile(Model model) {
        model.addAttribute("orders", usersService.findAllOrders(user));
        model.addAttribute("totalCheck", usersService.totalCheck(user));
        return "profile";
    }

    // Получить текущего пользователя
    @ModelAttribute("user")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        this.user = user;
        return user;
    }

    //Rest controller для добавления пользователя
    @PostMapping("/add-user")
    @ResponseBody
    public ResponseEntity<HttpStatus> rest(@RequestBody @Valid User user,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        usersService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
