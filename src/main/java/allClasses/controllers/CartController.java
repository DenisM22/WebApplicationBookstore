package allClasses.controllers;

import allClasses.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final BookDAO bookDAO;

    @Autowired
    public CartController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    // Корзина
    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cart", bookDAO.getCart());
        model.addAttribute("totalCheck", bookDAO.totalCheck());
        return "cart";
    }

    // Добавление книги в корзину
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public String addToCart(@RequestParam("id") long id) {
        bookDAO.addToCart(id);
        return "redirect:/cart";
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/clear")
    public String clearCart() {
        bookDAO.clearCart();
        return "redirect:/cart";
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    public String deleteFromCart(@RequestParam("book_id") long bookId) {
        bookDAO.deleteFromCart(bookId);
        return "redirect:/cart";
    }

}
