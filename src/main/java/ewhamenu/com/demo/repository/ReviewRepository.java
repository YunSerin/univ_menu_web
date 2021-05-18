package ewhamenu.com.demo.repository;


import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findById(long id);
}
