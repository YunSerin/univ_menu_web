package ewhamenu.com.demo.repository;


import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findById(long id);

    @Query("SELECT r FROM Review AS r WHERE FUNCTION('JSON_EXTRACT', r.totalScore, :key ) IS NOT NULL")
    public List<Review> findByTotalScore(@Param("key") String key);


}
