package allClasses.repositories;

import allClasses.models.Order;
import allClasses.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
