package ewhamenu.com.demo.controller;

import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.repository.DietRepository;
import ewhamenu.com.demo.service.ReviewService;
import ewhamenu.com.demo.service.SearchService;
import ewhamenu.com.demo.service.UserService;
import ewhamenu.com.demo.service.crawler.DietService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/search")
public class SearchController {
    Model model;
    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);
    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    DietService dietService;
    @Autowired
    ReviewService reviewService;

    List<Object> reviewListing(List<Review> searchedReview){
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        List<Object> reviews = new ArrayList<>();
        for(Review r : searchedReview){
            Map<String, Object> reviewMap = new HashMap<String, Object>();
            List<String> menuNameList = new ArrayList<>();
            List<String> menuScoreList = new ArrayList<>();
            Map<String, String> reviewScores = new HashMap<>();

            reviewMap.put("place", places[r.getPlaceId()]);
            r.getTotalScore().getRates().forEach((menuName, menuScore) -> {
                menuNameList.add(searchService.findMenuNameById(Math.toIntExact(menuName)));
                switch (menuScore){
                    case "1": menuScoreList.add("★"); break;
                    case "2": menuScoreList.add("★★"); break;
                    case "3": menuScoreList.add("★★★"); break;
                    case "4": menuScoreList.add("★★★★"); break;
                    case "5": menuScoreList.add("★★★★★"); break;
                }
            });
//            logger.info("test : {} ", menuScoreList);
            reviewMap.put("menu_name", menuNameList);
            reviewMap.put("score", menuScoreList);
            reviewMap.put("date", r.getReviewDate());
            reviewMap.put("comment", r.getReviewComment());
            reviewMap.put("writer", r.getUserId().getNickname());
            reviews.add(reviewMap);
        }
        return reviews;
    }

    //메인에서 보낸 검색 내용을 받아야함.
    @GetMapping("/searchKeyword")
    public ModelAndView searchKeyword(HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        int placeName = Integer.parseInt(request.getParameter("placeName"));
        String keyword = URLEncoder.encode(request.getParameter("searchKeyword"), "UTF-8");
        logger.info("ids : {} ", request.getParameter("searchKeyword-id"));
        List<Menu> searchedMenuIdList;
        Menu searchedMenuId;
        List<Review> searchedReview = new ArrayList<>();

        if(placeName==-1){ //place All
            //검색어 없을때
            if(keyword == "") { searchedReview = searchService.findAllReview(); }
            else { //검색어만 있을때
                searchedReview = searchService.findAllKeywords(request.getParameter("searchKeyword-id"));
            }
        }else{ //특정 place
            if(keyword == ""){
                searchedReview = searchService.findAllReviewByPlaceId(placeName);
            }else{
                searchedReview = searchService.findKeywordsAndPlace(request.getParameter("searchKeyword-id"), placeName);
            }
        }
        List<Object> reviews = reviewListing(searchedReview);
        mav.addObject("placeName",placeName);
        mav.addObject("keyword",request.getParameter("searchKeyword"));
        mav.addObject("reviews", reviews);
        mav.setViewName("searchPage");
        return mav;
    }

/////////오늘 리뷰 리스트
    @GetMapping("/searchToday")
    public ModelAndView searchToday(HttpServletRequest request,
                                    @RequestParam(value="menuNo") int menuNo){
        ModelAndView mav = new ModelAndView();
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        List<Diet> todayDiet = dietService.findTodayDiets(LocalDate.now()); //오늘자 diet 데이터
        List<Review> todayReview = searchService.findAllByDietId(todayDiet.get(menuNo));
        List<Object> reviews = reviewListing(todayReview);
        mav.addObject("reviews", reviews);
        mav.addObject("dietId", todayDiet.get(menuNo).getDietId());
        mav.addObject("placeName", places[menuNo/2]);
        mav.setViewName("searchTodayPage");
        return mav;
    }


    ///
    @RequestMapping(value = "autoSearching", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> searchingAutocomplete(HttpServletRequest request){
//        logger.info("test : {} ",request.getParameter("term") );
        if(Integer.parseInt(request.getParameter("placeId")) != -1){
            return reviewService.reviewAutoComplete(request.getParameter("term"), Integer.parseInt(request.getParameter("placeId")));
        }else{
            return searchService.searchingAutocomplete(request.getParameter("term"));
        }
    }
}
