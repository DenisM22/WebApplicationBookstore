package allClasses.controllers;

import allClasses.models.User;
import allClasses.services.CartsService;
import allClasses.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartsController {

    private final CartsService cartsService;
    private final UsersService usersService;
    private User user;

    @Autowired
    public CartsController(CartsService cartsService, UsersService usersService) {
        this.cartsService = cartsService;
        this.usersService = usersService;
    }

    // Показать корзину
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", cartsService.findCart(user));
        model.addAttribute("totalCheck", cartsService.totalCheck(user));
        return "cart";
    }

    // Добавить книгу в корзину
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping
    public String addToCart(@RequestParam("bookId") long bookId) {
        cartsService.addToCart(user, bookId);
        return "redirect:/cart";
    }

    // Очистить корзину
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping("/clear")
    public String clearCart() {
        cartsService.clearCart(user);
        return "redirect:/cart";
    }

    // Удалить книгу из корзины
    @PreAuthorize("hasRole('CLIENT')")
    @DeleteMapping
    public String deleteFromCart(@RequestParam("book_id") long bookId) {
        cartsService.deleteFromCart(user, bookId);
        return "redirect:/cart";
    }

    // Оформить заказ
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/add-order")
    public String addToOrders() {
        usersService.saveOrder(user);
        return "redirect:/cart";
    }

    // Получить текущего пользователя
    @ModelAttribute("user")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        this.user = user;
        return user;
    }

}
