package allClasses.controllers;

import allClasses.dao.BookDAO;
import allClasses.dao.CartDAO;
import allClasses.models.Book;
import allClasses.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class BooksController {

    private final BookDAO bookDAO;
    private final CartDAO cartDAO;
    private long userId;

    @Autowired
    public BooksController(BookDAO bookDAO, CartDAO cartDAO) {
        this.bookDAO = bookDAO;
        this.cartDAO = cartDAO;
    }

    // Все книги
    @GetMapping
    public String showAllBooks(Model model) {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books/books";
    }

    // Детальная информация о книге
    @GetMapping("/book/{id}")
    public String showBook(@PathVariable("id") long bookId, Model model) {
        model.addAttribute("book", bookDAO.getBook(bookId));
        model.addAttribute("containsCart", cartDAO.cartContainsBook(userId, bookId));
        model.addAttribute("countCart", cartDAO.countCart(userId, bookId));
        return "books/detailed-book";
    }

    // Форма для добавления книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/add-book")
    public String addForm(@ModelAttribute("book") Book book) {
        return "books/add-book";
    }

    // Добавление книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public String addNewBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/add-book";

        bookDAO.addNewBook(book);
        return "redirect:/";
    }

    // Форма для редактирования книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/book/{id}/edit")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit";
    }

    // Редактирование книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/book/{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult, @PathVariable long id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDAO.editBook(id, book);
        return "redirect:/book/{id}";
    }

    // Удаление книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookDAO.deleteBook(id);
        return "redirect:/";
    }

    // Получить текущего пользователя
    @ModelAttribute("user")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        this.userId = user.getUserId();
        return user;
    }

}
