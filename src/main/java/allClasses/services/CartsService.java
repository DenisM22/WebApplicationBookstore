package allClasses.services;

import allClasses.models.Book;
import allClasses.models.Cart;
import allClasses.models.User;
import allClasses.repositories.BooksRepository;
import allClasses.repositories.CartsRepository;
import allClasses.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class CartsService {

    private final BooksRepository booksRepository;
    private final CartsRepository cartsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public CartsService(BooksRepository booksRepository, CartsRepository cartsRepository, UsersRepository usersRepository) {
        this.booksRepository = booksRepository;
        this.cartsRepository = cartsRepository;
        this.usersRepository = usersRepository;
    }

    public List<Cart> findCart(User user) {
        return cartsRepository.findByUser(user);
    }

    public boolean containsBook(long id) {
        return booksRepository.existsByBookIdAndAmountGreaterThan(id, 0);
    }

    @Transactional
    public void addToCart(User user, long bookId) {
        if (!containsBook(bookId)) {
            return;
        }
        Book book = booksRepository.findById(bookId).get();
        book.setAmount(book.getAmount() - 1);

        user = usersRepository.findByUsername(user.getUsername());
        Cart cart = cartsRepository.findSpecificBook(user, bookId);

        if (cart == null) {
            cart = new Cart(user, book, 1);
            cartsRepository.save(cart);
        }
        else
            cart.setAmount(cart.getAmount() + 1);
    }

    @Transactional
    public void clearCart(User user) {
        List<Cart> carts = cartsRepository.findByUser(user);
        for (Cart cart : carts) {
            Book book = cart.getBook();
            book.setAmount(book.getAmount() + cart.getAmount());
        }
        cartsRepository.deleteByUser(user);
    }

    @Transactional
    public void deleteFromCart(User user, long bookId) {
        Cart cart = cartsRepository.findSpecificBook(user, bookId);
        Book book = cart.getBook();
        book.setAmount(book.getAmount() + cart.getAmount());
        cartsRepository.delete(cart);
    }

    public double totalCheck(User user) {
        double sum = 0;
        List<Cart> carts = findCart(user);
        for (Cart cart : carts)
            sum = sum + cart.getBook().getPrice() * cart.getAmount();
        return sum;
    }

}
