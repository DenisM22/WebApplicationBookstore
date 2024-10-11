package allClasses.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders_books")
@IdClass(OrderBookId.class)
public class OrderBook implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "amount", nullable = false)
    private int amount;

    public OrderBook() {
    }

    public OrderBook(Order order, Book book, int amount) {
        this.order = order;
        this.book = book;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
