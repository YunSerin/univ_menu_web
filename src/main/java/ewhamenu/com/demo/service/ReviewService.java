package ewhamenu.com.demo.service;


import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.repository.ReviewRepository;
import ewhamenu.com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public long saveReview(Review review, String userID){
        review.setUserId(findByUserId(userID));
        reviewRepository.save(review);
        return review.getId();
    }
    public Users findByUserId(String userId){
        Users user = userRepository.findByUserId(userId);
        return user;
    }
}
