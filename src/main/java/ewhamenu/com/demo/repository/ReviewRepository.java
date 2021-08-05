package ewhamenu.com.demo.repository;


import ewhamenu.com.demo.domain.Diet;
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
//select json_extract(review.total_score, '$.rates') from review where json_extract(review.total_score, '$.rates.1') is not null;
    // select * from review as r where json_contains(json_keys(r.total_score, '$.rates'), "1",'$' );
    ////select * from review as r where json_search( json_keys(r.total_scores, '$.rates'), 'one', "1") is not null
//    @Query("SELECT r FROM Review AS r WHERE JSON_EXTRACT(r.totalScore, '$.rates.:key') is not null")
//    @Query(value = "SELECT * FROM Review AS r where JSON_EXTRACT(r.total_score, '$.rates', ?1)", nativeQuery = true)
@Query(value = "SELECT * FROM Review AS r where JSON_SEARCH(json_keys(r.total_score, '$.rates'), 'one', :key) is not null", nativeQuery = true)
    public List<Review> findByTotalScoreOrderByIdDesc(@Param("key") String key);

    public List<Review> findAllByOrderByIdDesc();

    public List<Review> findAllByPlaceIdOrderByIdDesc(int placeId);

    public List<Review> findAllByDietId(Diet dietId);

}
