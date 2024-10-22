package allClasses.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@EqualsAndHashCode(of = {"order", "book"})
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookId implements Serializable {

    private Order order;
    private Book book;

//    // Переопределение equals для сравнения ключей
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        OrderBookId OrderBookId = (OrderBookId) o;
//        return Objects.equals(order, OrderBookId.order) &&
//                Objects.equals(book, OrderBookId.book);
//    }
//
//    // Переопределение hashCode для использования в коллекциях
//    @Override
//    public int hashCode() {
//        return Objects.hash(order, book);
//    }

}
