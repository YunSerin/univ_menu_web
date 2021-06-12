package ewhamenu.com.demo.service;

import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.repository.MenuRepository;
import ewhamenu.com.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public Menu findByMenuNameAndPlaceId(String menuName, int placeId){
        Menu menuSearched = menuRepository.findByMenuNameAndPlaceId(menuName, placeId);
        return menuSearched;
    }

    public List<Menu> findAllByMenuName(String menuName){
        List<Menu> menuSearched = menuRepository.findAllByMenuName(menuName);
        return menuSearched;
    }

    public List<Review> findByTotalScore(@Param("key") String key){
        List<Review> reviewSearched = reviewRepository.findByTotalScore(key);
        return reviewSearched;
    }
}
