package allClasses.controllers;

import allClasses.models.Book;
import allClasses.models.User;
import allClasses.services.BooksService;
import allClasses.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BooksController {

    private final BooksService booksService;
    private final UsersService usersService;
    private User user;

    @Autowired
    public BooksController(BooksService booksService, UsersService usersService) {
        this.booksService = booksService;
        this.usersService = usersService;
    }

    // Все книги
    @GetMapping
    public String showAllBooks(Model model) {
        model.addAttribute("books", booksService.findAllBooks());
        return "books/books";
    }

    // Детальная информация о книге
    @GetMapping("/book/{id}")
    public String showBook(@PathVariable("id") long bookId, Model model) {
        model.addAttribute("book", booksService.findBookById(bookId));
        model.addAttribute("countCart", booksService.countCart(user, bookId));
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

        booksService.saveBook(book);
        return "redirect:/";
    }

    // Форма для редактирования книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/book/{id}/edit")
    public String showEditForm(@PathVariable("id") long bookId, Model model) {
        Book book = booksService.findBookById(bookId);
        model.addAttribute("book", book);
        return "books/edit";
    }

    // Редактирование книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PatchMapping("/book/{id}/edit")
    public String editBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.editBook(book);
        return "redirect:/book/{id}";
    }

    // Удаление книги
    @PreAuthorize("hasRole('EMPLOYEE')")
    @DeleteMapping("/book/{id}")
    public String deleteBook(@PathVariable("id") long bookId) {
        booksService.deleteBookById(bookId);
        return "redirect:/";
    }

    // Получить текущего пользователя
    @ModelAttribute("user")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        this.user = user;
        return user;
    }

}
