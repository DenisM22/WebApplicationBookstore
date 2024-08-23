package allClasses.dao;

import allClasses.models.Order;
import allClasses.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUser(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
    }

    public void addNewUser(String username, String password) {
        jdbcTemplate.update("INSERT INTO users (username, password, role) VALUES (?, ?, ?)", username, password, "CLIENT");
    }

    public boolean checkUser(String username) {
        boolean result = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)", Boolean.class, username);
        return result;
    }

    public List<Order> getOrders(long userId) {
        return jdbcTemplate.query("SELECT orders.id, orders.user_id, orders.book_id, orders.amount, orders.created_at, books.name, books.price " +
                "FROM orders JOIN books ON orders.book_id = books.id WHERE user_id = ?", new Object[]{userId}, new BeanPropertyRowMapper<>(Order.class));
    }

    public void addToOrders(long userId) {
        jdbcTemplate.update("INSERT INTO orders (user_id, book_id, amount, created_at)" +
                "SELECT c.user_id, c.book_id, c.amount, CURRENT_TIMESTAMP FROM carts c WHERE user_id = ?", userId);
        jdbcTemplate.update("DELETE FROM carts WHERE user_id = ?", userId);
    }

    public double totalCheck(long userId) {
        double sum = 0;
        for (Order book : getOrders(userId)) {
            sum = sum + book.getPrice() * book.getAmount();
        }
        return sum;
    }

}
