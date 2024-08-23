package allClasses.dao;

import allClasses.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cart> getCart(long userId) {
        return jdbcTemplate.query("SELECT carts.book_id, books.name, carts.amount, books.price " +
                "FROM carts JOIN books ON carts.book_id = books.id WHERE user_id = ?", new Object[]{userId}, new BeanPropertyRowMapper<>(Cart.class));
    }

    public void addToCart(long userId, Long bookId) {
        if (containsBook(bookId)) {
            if (containsCart(userId, bookId))
                jdbcTemplate.update("UPDATE carts SET AMOUNT = AMOUNT + 1 WHERE user_id = ? AND book_id = ?", userId, bookId);
            else
                jdbcTemplate.update("INSERT INTO carts VALUES (?, ?, ?)", userId, bookId, 1);

            jdbcTemplate.update("UPDATE books SET AMOUNT = AMOUNT - 1 WHERE ID = ?", bookId);
        }
    }

    public boolean containsBook(long bookId) {
        int result = jdbcTemplate.queryForObject("SELECT AMOUNT FROM books WHERE ID = ?", new Object[]{bookId}, Integer.class);
        return result > 0;
    }

    public boolean containsCart(long userId, long bookId) {
        return countCart(userId, bookId) > 0;
    }

    public int countCart(long userId, long bookId) {
        try {
            Integer amount = jdbcTemplate.queryForObject(
                    "SELECT AMOUNT FROM carts WHERE user_id = ? AND book_id = ?",
                    new Object[]{userId, bookId},
                    Integer.class
            );
            return amount != null ? amount : 0;
        }
        catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    public void clearCart(long userId) {
        for (Cart book : getCart(userId)) {
            int amount = countCart(userId, book.getBookId());
            returnAmount(book.getBookId(), amount);
        }
        jdbcTemplate.update("DELETE FROM carts WHERE user_id = ?", userId);
    }

    public void deleteFromCart(long userId, long bookId) {
        int amount = countCart(userId, bookId);
        returnAmount(bookId, amount);
        jdbcTemplate.update("DELETE FROM carts WHERE user_id = ? AND book_id = ?", userId, bookId);
    }

    public double totalCheck(long userId) {
        double sum = 0;
        for (Cart book : getCart(userId)) {
            sum = sum + book.getPrice() * book.getAmount();
        }
        return sum;
    }

    public void returnAmount(long bookId, int amount) {
        jdbcTemplate.update("UPDATE books SET AMOUNT = AMOUNT + ? WHERE ID = ?", amount, bookId);
    }

}
