package allClasses.controllers;

import allClasses.dao.CartDAO;
import allClasses.dao.UserDAO;
import allClasses.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartsController {

    private final CartDAO cartDAO;
    private final UserDAO userDAO;
    private Long userId;

    @Autowired
    public CartsController(CartDAO cartDAO, UserDAO userDAO) {
        this.cartDAO = cartDAO;
        this.userDAO = userDAO;
    }

    // Показать корзину
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cartDAO.getCart(userId));
        model.addAttribute("totalCheck", cartDAO.totalCheck(userId));
        return "cart";
    }

    // Добавить книгу в корзину
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public String addToCart(@RequestParam("id") long bookId) {
        cartDAO.addToCart(userId, bookId);
        return "redirect:/cart";
    }

    // Очистить корзину
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/clear")
    public String clearCart() {
        cartDAO.clearCart(userId);
        return "redirect:/cart";
    }

    // Удалить книгу из корзины
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping
    public String deleteFromCart(@RequestParam("book_id") long bookId) {
        cartDAO.deleteFromCart(userId, bookId);
        return "redirect:/cart";
    }

    // Оформить заказ
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/add-order")
    public String addToOrders() {
        userDAO.addToOrders(userId);
        return "redirect:/cart";
    }

    // Получить текущего пользователя
    @ModelAttribute("user")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        this.userId = user.getId();
        return user;
    }

}
