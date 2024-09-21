package allClasses.dao;

import allClasses.models.Book;
import allClasses.models.Cart;
import allClasses.models.CartId;
import allClasses.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CartDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Cart> getCart(long userId) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userId);
        if(user != null)
            return user.getCart();
        else
            return new ArrayList<>();
    }

    public void addToCart(long userId, long bookId) {
        if (containsBook(bookId)) {
            Session session = entityManager.unwrap(Session.class);
            User user = session.get(User.class, userId);
            Book book = session.get(Book.class, bookId);
            book.setAmount(book.getAmount() - 1);
            session.persist(book);

            if (cartContainsBook(userId, bookId)){
                Cart cart = session.get(Cart.class, new CartId(user, book));
                cart.setAmount(cart.getAmount() + 1);
                session.persist(cart);
            }
            else {
                Cart cart = new Cart(user, book, 1);
                session.persist(cart);
            }
        }
    }

    public boolean containsBook(long bookId) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        return book.getAmount() > 0;
    }

    public boolean cartContainsBook(long userId, long bookId) {
        return countCart(userId, bookId) > 0;
    }

    public int countCart(long userId, long bookId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Integer> query = session.createQuery("SELECT amount FROM Cart WHERE user.id = :userId AND book.id = :bookId", Integer.class);
        query.setParameter("userId", userId);
        query.setParameter("bookId", bookId);
        Integer result = query.uniqueResult();
        return (result != null) ? result : 0;
    }

    public void clearCart(long userId) {
        Session session = entityManager.unwrap(Session.class);
        List<Cart> cartList = getCart(userId);
        for (Cart cart : cartList) {
            Book book = cart.getBook();
            book.setAmount(book.getAmount() + cart.getAmount());
        }
        session.createQuery("delete from Cart where user.id = :userId")
                .setParameter("userId", userId).executeUpdate();
    }

    public void deleteFromCart(long userId, long bookId) {
        Session session = entityManager.unwrap(Session.class);
        Query<Cart> query = session.createQuery("from Cart where user = :userId AND book = :bookId");
        query.setParameter("userId", session.get(User.class, userId));
        query.setParameter("bookId", session.get(Book.class, bookId));
        Cart cart = query.uniqueResult();
        Book book = cart.getBook();
        book.setAmount(book.getAmount() + cart.getAmount());
        session.remove(cart);
    }

    public double totalCheck(long userId) {
        double sum = 0;
        List<Cart> cartList = getCart(userId);
        for (Cart cart : cartList)
            sum = sum + cart.getBook().getPrice() * cart.getAmount();

        return sum;
        }

}
