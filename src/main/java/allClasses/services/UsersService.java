package allClasses.services;

import allClasses.models.Order;
import allClasses.models.OrderBook;
import allClasses.models.User;
import allClasses.repositories.CartsRepository;
import allClasses.repositories.OrdersRepository;
import allClasses.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UsersService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UsersRepository usersRepository;
    private final CartsRepository cartsRepository;
    private final OrdersRepository ordersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, CartsRepository cartsRepository, OrdersRepository ordersRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.cartsRepository = cartsRepository;
        this.ordersRepository = ordersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public boolean checkUser(String username) {
        return usersRepository.existsByUsername(username);
    }

    public List<Order> findAllOrders(User user) {
        return ordersRepository.findByUser(user);
    }

    @Transactional
    public void saveOrder(User user) {
        Order order = new Order(user, new ArrayList<>());
        ordersRepository.save(order);
        Session session = entityManager.unwrap(Session.class);
        session.createQuery("INSERT INTO OrderBook (order, book, amount) " +
                        "SELECT :order, book, amount FROM Cart WHERE user = :user")
                .setParameter("order", order)
                .setParameter("user", user)
                .executeUpdate();
        cartsRepository.deleteByUser(user);
    }

    public double totalCheck(User user) {
        double sum = 0;
        for (Order order : findAllOrders(user)) {
            for (OrderBook orderBook : order.getOrderBooks()) {
                sum += orderBook.getAmount() * orderBook.getBook().getPrice();
            }
        }
        return sum;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user;
    }

}
