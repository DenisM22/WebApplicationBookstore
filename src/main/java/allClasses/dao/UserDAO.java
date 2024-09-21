package allClasses.dao;

import allClasses.models.Order;
import allClasses.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public User getUser(String username) {
        Session session = entityManager.unwrap(Session.class);
        return session.createQuery("from User where username = :username", User.class)
                .setParameter("username", username).uniqueResult();
    }

    public void addNewUser(String username, String password) {
        Session session = entityManager.unwrap(Session.class);
        User user = new User(username, password);
        session.persist(user);
    }

    public boolean checkUser(String username) {
        Session session = entityManager.unwrap(Session.class);
        Long count = (Long) session.createQuery("select count(u) from User u where u.username = :username")
                .setParameter("username", username)
                .uniqueResult();
        return count > 0;
    }

    public List<Order> getOrders(long userId) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(User.class, userId).getOrders();
    }

    public void addToOrders(long userId) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
    }

    public double totalCheck(long userId) {
        double sum = 0;
//        for (Order book : getOrders(userId)) {
//            sum = sum + book.getPrice() * book.getAmount();
//        }
        return sum;
    }

}
