package allClasses.controllers;

import allClasses.dao.BookDAO;
import allClasses.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class BooksController {

    private final BookDAO bookDAO;

    @Autowired
    public BooksController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    // Все книги
    @GetMapping
    public String showAllBooks(Model model) throws SQLException {
        model.addAttribute("books", bookDAO.getAllBooks());
        return "books";
    }

    // Детальная информация о книге
    @GetMapping("/book/{id}")
    public String showBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        model.addAttribute("containsCart", bookDAO.containsCart(id));
        model.addAttribute("countCart", bookDAO.countCart(id));
        return "detailed-book";
    }

    // Форма для добавления книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/add-book")
    public String addForm(@ModelAttribute("book") Book book) {
        return "add-book";
    }

    // Добавление книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public String addNewBook(@ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "add-book";

        bookDAO.addNewBook(book);
        return "redirect:/";
    }

    // Форма для редактирования книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/book/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "edit";
    }

    // Редактирование книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/book/{id}")
    public String editBook(@ModelAttribute("book") @Valid Book book,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "edit";

        bookDAO.editBook(book);
        return "redirect:/book/{id}";
    }

    // Удаление книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookDAO.deleteBook(id);
        return "redirect:/";
    }

    @ModelAttribute("user")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
