package allClasses.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@EqualsAndHashCode(of = {"user", "book"})
@NoArgsConstructor
@AllArgsConstructor
public class CartId implements Serializable {

    private User user;
    private Book book;

//    // Переопределение equals для сравнения ключей
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CartId cartId = (CartId) o;
//        return Objects.equals(user, cartId.user) &&
//                Objects.equals(book, cartId.book);
//    }
//
//    // Переопределение hashCode для использования в коллекциях
//    @Override
//    public int hashCode() {
//        return Objects.hash(user, book);
//    }

}
