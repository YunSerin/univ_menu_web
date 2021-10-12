package ewhamenu.com.demo.service;

import ewhamenu.com.demo.controller.MainhomeController;
import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.repository.MenuRepository;
import ewhamenu.com.demo.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);

    ////////////////Menu Service
    public Menu findByMenuNameAndPlaceId(String menuName, int placeId){
        Menu menuSearched = menuRepository.findAllByMenuNameAndPlaceId(menuName, placeId);
        return menuSearched;
    }

    public List<Menu> findAllByMenuName(String menuName){
        List<Menu> menuSearched = menuRepository.findAllByMenuName(menuName);
        return menuSearched;
    }

    public String findMenuNameById(int menuId){
        String menuName = menuRepository.findById(menuId).getMenuName();
        return menuName;
    }

    public List<Object> searchingAutocomplete(String keyword){
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        List<Object> keywords = new ArrayList<>();
        for(Menu m : menuRepository.searchingAutocomplete(keyword)){
            Map<String, Object> keywordMap = new HashMap<String, Object>();
            keywordMap.put("menuId", m.getId());
            keywordMap.put("name", m.getMenuName());
            keywordMap.put("place", places[m.getPlaceId()]);
            keywords.add(keywordMap);
        }
        return keywords;
    }

//////////////Review Service
    public List<Review> findByTotalScore(@Param("key") String key){
        List<Review> reviewSearched = reviewRepository.findByTotalScoreOrderByIdDesc(key);
        return reviewSearched;
    }

    public List<Review> findAllReview(){
        List<Review> reviewSearched = reviewRepository.findAllByOrderByIdDesc();
        return reviewSearched;
    }

    public List<Review> findAllReviewByPlaceId(int placeId){
        List<Review> reviewSearched = reviewRepository.findAllByPlaceIdOrderByIdDesc(placeId);
        return reviewSearched;
    }

    public List<Review> findAllByDietId(Diet dietSelected){
        List<Review> reviewToday = reviewRepository.findAllByDietId(dietSelected);
        return reviewToday;
    }

    public List<Review> findAllKeywords(String menuIds){
        String[] ids = menuIds.split(",");
        for(int i=0;i<ids.length;i++){
            ids[i] = "\""+ids[i]+"\"";
        }
        String menuIds_keyword = Arrays.deepToString(ids);
        logger.info("test : {} ",menuIds_keyword );
        List<Review> reviewSearched = reviewRepository.findAllByTotalScoreOrderByIdDesc(menuIds_keyword);
        return reviewSearched;
    }

    public List<Review> findKeywordsAndPlace(String menuIds, int placeId){
        String[] ids = menuIds.split(",");
        for(int i=0;i<ids.length;i++){
            ids[i] = "\""+ids[i]+"\"";
        }
        String menuIds_keyword = Arrays.deepToString(ids);
        logger.info("test : {} ",menuIds_keyword );
        return reviewRepository.findAllByTotalScoreAndPlaceIdOrderByIdDesc(menuIds_keyword, placeId);
    }
}
