package allClasses.models;

import java.io.Serializable;
import java.util.Objects;

public class OrderBookId implements Serializable {

    private Order order;
    private Book book;

    public OrderBookId() {
    }

    public OrderBookId(Order order, Book book) {
        this.order = order;
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        OrderBookId OrderBookId = (OrderBookId) o;
        return Objects.equals(order, OrderBookId.order) &&
                Objects.equals(book, OrderBookId.book);
    }

    // Переопределение hashCode для использования в коллекциях
    @Override
    public int hashCode() {
        return Objects.hash(order, book);
    }
}
