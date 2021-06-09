package ewhamenu.com.demo.service;


import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public long saveReview(Review review){
        reviewRepository.save(review);
        return review.getId();
    }
}
