package allClasses.repositories;

import allClasses.models.Cart;
import allClasses.models.CartId;
import allClasses.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartsRepository extends JpaRepository<Cart, CartId> {

    List<Cart> findByUser(User user);

    @Query("FROM Cart WHERE user = :user AND book.bookId = :bookId")
    Cart findSpecificBook(@Param("user") User user, @Param("bookId") long bookId);

    void deleteByUser(User user);

}
