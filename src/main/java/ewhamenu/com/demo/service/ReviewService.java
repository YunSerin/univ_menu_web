package ewhamenu.com.demo.service;


import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.repository.ReviewRepository;
import ewhamenu.com.demo.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SearchService searchService;

    public long saveReview(Review review, String userID){
        review.setUserId(findByUserId(userID));
        reviewRepository.save(review);
        return review.getId();
    }
    public Users findByUserId(String userId){
        Users user = userRepository.findByUserId(userId);
        return user;
    }

    public List<Object> findReviewsWrittenByUser(Users user){
        List<Review> userReviewList = reviewRepository.findAllByUserId(user);
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        List<Object> usersReviews = new ArrayList<>();
        for(Review r : userReviewList){
            Map<String, Object> reviewMap = new HashMap<String, Object>();
            List<String> menuNameList = new ArrayList<>();

            reviewMap.put("place", places[r.getPlaceId()]);
            r.getTotalScore().getRates().forEach((menuName, menuScore) -> {
                menuNameList.add(searchService.findMenuNameById(Integer.parseInt(menuName)));
            });
            reviewMap.put("menu_name", StringUtils.join(menuNameList, ", "));
            reviewMap.put("average_score", r.getAverageScore());
            reviewMap.put("date", r.getReviewDate());
            reviewMap.put("comment", r.getReviewComment());
            usersReviews.add(reviewMap);
        }
        return usersReviews;
    }
}
