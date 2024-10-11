package allClasses.services;

import allClasses.models.Book;
import allClasses.models.User;
import allClasses.repositories.BooksRepository;
import allClasses.repositories.CartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService  {

    private final BooksRepository booksRepository;
    private final CartsRepository cartsRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository, CartsRepository cartsRepository) {
        this.booksRepository = booksRepository;
        this.cartsRepository = cartsRepository;
    }

    public List<Book> findAllBooks() {
        return booksRepository.findAll();
    }

    public Book findBookById(long id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void editBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void deleteBookById(long id) {
        booksRepository.deleteById(id);
    }

    public int countCart(User user, long bookId) {
        try {
            return cartsRepository.findSpecificBook(user, bookId).getAmount();
        } catch (Exception e) {
            return 0;
        }
    }

}
