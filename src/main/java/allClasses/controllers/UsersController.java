package allClasses.controllers;

import allClasses.dao.UserDAO;
import allClasses.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UsersController {

    private final UserDAO userDAO;
    private Long userId;

    @Autowired
    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Получить список заказов
    @GetMapping("/profile")
    public String showProfile(Model model) {
        model.addAttribute("orders", userDAO.getOrders(userId));
        model.addAttribute("totalCheck", userDAO.totalCheck(userId));
        return "profile";
    }

    // Получить текущего пользователя
    @ModelAttribute("user")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        this.userId = user.getUserId();
        return user;
    }

}
