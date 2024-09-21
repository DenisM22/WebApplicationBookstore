package allClasses.models;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {

    private User user;
    private Book book;

    public CartId() {
    }

    public CartId(User user, Book book) {
        this.user = user;
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    // Переопределение equals для сравнения ключей
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartId cartId = (CartId) o;
        return Objects.equals(user, cartId.user) &&
                Objects.equals(book, cartId.book);
    }

    // Переопределение hashCode для использования в коллекциях
    @Override
    public int hashCode() {
        return Objects.hash(user, book);
    }
}
