package allClasses.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseInit {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseInit(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void createTables() {

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(50) NOT NULL," +
                "role VARCHAR(50) NOT NULL)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS books (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, " +
                "author VARCHAR(50) NOT NULL, " +
                "language VARCHAR(255) NOT NULL, " +
                "release INT NOT NULL, " +
                "genres VARCHAR(100), " +
                "description VARCHAR(1000), " +
                "ISBN VARCHAR(20), " +
                "amountPages INT, " +
                "rate DOUBLE, " +
                "isNew BOOLEAN, " +
                "imagePath VARCHAR(255), " +
                "price DOUBLE NOT NULL, " +
                "amount INT NOT NULL)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS carts (" +
                "user_id BIGINT, " +
                "book_id BIGINT, " +
                "amount INT NOT NULL, " +
                "PRIMARY KEY (user_id, book_id), " +
                "FOREIGN KEY (user_id) REFERENCES users(id), " +
                "FOREIGN KEY (book_id) REFERENCES books(id)" +
                "ON DELETE CASCADE)");

        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS orders (" +
                "id BIGSERIAL PRIMARY KEY, " +
                "user_id BIGINT, " +
                "book_id BIGINT, " +
                "amount INT NOT NULL, " +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(id), " +
                "FOREIGN KEY (book_id) REFERENCES books(id)" +
                "ON DELETE CASCADE)");

    }

    @PostConstruct
    private void createUsers() {
//        jdbcTemplate.update("INSERT INTO users (username, password, role) VALUES (?, ?, ?)", "Emp", "123", "EMPLOYEE");
    }

}
