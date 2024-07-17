package allClasses.dao;

import allClasses.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }
    
    public Book getBook(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM books WHERE ID = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void addNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO books (name, author, language, release, genres, description, ISBN, amountPages, rate, isNew, imagePath, price, amount) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getLanguage(), book.getRelease(),
                book.getGenres(), book.getDescription(), book.getISBN(), book.getAmountPages(),
                book.getRate(), book.getIsNew(), book.getImagePath(), book.getPrice(), book.getAmount());
    }

    public void editBook(Book book) {
        jdbcTemplate.update("UPDATE books SET name = ?, author = ?, language = ?, release = ?, genres = ?," +
                        "description = ?, ISBN = ?, amountPages = ?, rate = ?, isNew = ?, imagePath = ?, price = ?, " +
                        "amount = ? WHERE ID = ?", book.getName(), book.getAuthor(), book.getLanguage(),
                book.getRelease(), book.getGenres(), book.getDescription(), book.getISBN(), book.getAmountPages(),
                book.getRate(), book.getIsNew(), book.getImagePath(), book.getPrice(), book.getAmount(), book.getId());
    }

    public void deleteBook(long id) {
        jdbcTemplate.update("DELETE FROM books WHERE ID = ?", id);
    }

}
