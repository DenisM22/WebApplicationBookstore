package allClasses.repositories;

import allClasses.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    boolean existsByBookIdAndAmountGreaterThan(long id, int amount);
}
