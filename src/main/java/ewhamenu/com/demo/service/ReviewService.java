package ewhamenu.com.demo.service;


import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.repository.MenuRepository;
import ewhamenu.com.demo.repository.ReviewRepository;
import ewhamenu.com.demo.repository.UserRepository;
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
    private MenuRepository menuRepository;

    public long saveReview(Review review, String userID){
        review.setUserId(findByUserId(userID));
        reviewRepository.save(review);
        return review.getId();
    }
    public Users findByUserId(String userId){
        Users user = userRepository.findByUserId(userId);
        return user;
    }
    //get Menu Autocomplete by keyword
    public List<Object> reviewAutoComplete(String keyword, int placeId){
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        List<Object> keywords = new ArrayList<>();
        for(Menu m : menuRepository.findMenusByKeyword(keyword, placeId)){
            Map<String, Object> keywordM = new HashMap<>();
            keywordM.put("menuId", m.getId());
            keywordM.put("name", m.getMenuName());
            keywordM.put("place", places[m.getPlaceId()]);
            keywords.add(keywordM);
        }
        return keywords;
    }
}
