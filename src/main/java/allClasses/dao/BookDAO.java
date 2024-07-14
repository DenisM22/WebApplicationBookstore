package allClasses.dao;

import allClasses.models.Book;
import allClasses.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() throws SQLException {
        return jdbcTemplate.query("SELECT * FROM BOOKS", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(long id) {
        return jdbcTemplate.query("SELECT * FROM BOOKS WHERE ID = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void addNewBook(Book book) {
        jdbcTemplate.update("INSERT INTO BOOKS (name, author, language, year, genres, description, ISBN, amountPages, rate, isNew, imagePath, price, amount) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getLanguage(), book.getYear(),
                book.getGenres(), book.getDescription(), book.getISBN(), book.getAmountPages(),
                book.getRate(), book.getIsNew(), book.getImagePath(), book.getPrice(), book.getAmount());
    }

    public void editBook(Book book) {
        jdbcTemplate.update("UPDATE BOOKS SET name = ?, author = ?, language = ?, year = ?, genres = ?," +
                        "description = ?, ISBN = ?, amountPages = ?, rate = ?, isNew = ?, imagePath = ?, price = ?, " +
                        "amount = ? WHERE ID = ?", book.getName(), book.getAuthor(), book.getLanguage(),
                book.getYear(), book.getGenres(), book.getDescription(), book.getISBN(), book.getAmountPages(),
                book.getRate(), book.getIsNew(), book.getImagePath(), book.getPrice(), book.getAmount(), book.getId());
    }

    public void deleteBook(long id) {
        jdbcTemplate.update("DELETE FROM BOOKS WHERE ID = ?", id);
    }

    public boolean containsBook(long id) {
        int result = jdbcTemplate.queryForObject("SELECT AMOUNT FROM BOOKS WHERE ID = ?", new Object[]{id}, Integer.class);
        return result > 0;
    }

    private void returnAmount(long id, int amount) {
        jdbcTemplate.update("UPDATE BOOKS SET AMOUNT = AMOUNT + ? WHERE ID = ?", amount, id);
    }

    public List<Cart> getCart() {
        return jdbcTemplate.query("SELECT cart.book_id, books.name, cart.amount, books.price " +
                "FROM cart JOIN books ON cart.book_id = books.id", new BeanPropertyRowMapper<>(Cart.class));
    }

    public void addToCart(Long id) {
        if (containsBook(id)) {
            if (!containsCart(id))
                jdbcTemplate.update("INSERT INTO CART VALUES (?, ?)", id, 1);
            else
                jdbcTemplate.update("UPDATE CART SET AMOUNT = AMOUNT + 1 WHERE BOOK_ID = ?", id);

            jdbcTemplate.update("UPDATE BOOKS SET AMOUNT = AMOUNT - 1 WHERE ID = ?", id);
        }

    }

    public void clearCart() {
        for (Cart cart : getCart()) {
            int amount = jdbcTemplate.queryForObject("SELECT AMOUNT FROM CART WHERE BOOK_ID = ?", new Object[]{cart.getBook_id()}, Integer.class);
            returnAmount(cart.getBook_id(), amount);
        }
        jdbcTemplate.update("DELETE FROM CART");
    }

    public void deleteFromCart(long book_id) {
        int amount = jdbcTemplate.queryForObject("SELECT AMOUNT FROM CART WHERE BOOK_ID = ?", new Object[]{book_id}, Integer.class);
        returnAmount(book_id, amount);
        jdbcTemplate.update("DELETE FROM CART WHERE BOOK_ID = ?", book_id);
    }

    public boolean containsCart(long book_id) {
        Cart result = jdbcTemplate.query("SELECT * FROM CART WHERE BOOK_ID = ?", new Object[]{book_id}, new BeanPropertyRowMapper<>(Cart.class))
                .stream().findAny().orElse(null);
        return result != null;
    }

    public int countCart(long book_id) {
        if (containsCart(book_id))
            return jdbcTemplate.queryForObject("SELECT AMOUNT FROM CART WHERE BOOK_ID = ?", new Object[]{book_id}, Integer.class);
        else
            return 0;
    }

    public double totalCheck() {
        double sum = 0;
        for (Cart cart : getCart()) {
            sum = sum + cart.getPrice() * cart.getAmount();
        }
        return sum;
    }

}
