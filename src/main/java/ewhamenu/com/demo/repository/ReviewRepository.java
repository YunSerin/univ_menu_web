package ewhamenu.com.demo.repository;


import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review findById(long id);
@Query(value = "SELECT * FROM Review AS r where JSON_SEARCH(json_keys(r.total_score, '$.rates'), 'one', :key) is not null", nativeQuery = true)
    public List<Review> findByTotalScoreOrderByIdDesc(@Param("key") String key);

    public List<Review> findAllByOrderByIdDesc();

    public List<Review> findAllByPlaceIdOrderByIdDesc(int placeId);

    public List<Review> findAllByDietId(Diet dietId);
    @Query(value = "SELECT * FROM review AS r WHERE JSON_CONTAINS(JSON_KEYS(r.total_score, '$.rates'), '[\"1\",\"2\"]', '$')", nativeQuery = true)
    public List<Review> findAllByTotalScoreOrderByIdDesc();

    public List<Review> findAllByUserId(Users user);
}
