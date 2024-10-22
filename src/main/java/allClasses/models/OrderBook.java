package allClasses.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "orders_books")
@IdClass(OrderBookId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
