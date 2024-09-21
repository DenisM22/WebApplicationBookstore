package allClasses.dao;

import allClasses.models.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional
public class BookDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        Session session = entityManager.unwrap(Session.class);
        List<Book> books = session.createQuery("from Book", Book.class).list();
        return books;
    }

    @Transactional(readOnly = true)
    public Book getBook(long bookId) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        return book;
    }

    @Transactional
    public void addNewBook(Book book) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(book);
    }

    @Transactional
    public void editBook(long id, Book book) {
        book.setBookId(id);
        Session session = entityManager.unwrap(Session.class);
        session.merge(book);
    }

    @Transactional
    public void deleteBook(long bookId) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, bookId);
        session.remove(book);
    }

}
