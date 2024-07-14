package allClasses.controllers;

import allClasses.dao.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeesController {

    private final BookDAO bookDAO;

    @Autowired
    public EmployeesController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping("/employee")
    public String employee() {
        return "employee";
    }

}
