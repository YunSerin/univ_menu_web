package ewhamenu.com.demo.controller;

import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.repository.DietRepository;
import ewhamenu.com.demo.service.SearchService;
import ewhamenu.com.demo.service.UserService;
import ewhamenu.com.demo.service.crawler.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/search")
public class SearchController {
    Model model;

    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    DietService dietService;

    List<Object> reviewListing(List<Review> searchedReview){
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        List<Object> reviews = new ArrayList<>();
        for(Review r : searchedReview){
            Map<String, Object> reviewMap = new HashMap<String, Object>();
            reviewMap.put("place", places[r.getPlaceId()]);
            reviewMap.put("menu_name", "돈까스");
            reviewMap.put("score", "★★★★★");
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
        List<Menu> searchedMenuList = new ArrayList<>();
        Menu searchedMenuId = new Menu();
        List<Review> searchedReview = new ArrayList<>();

        if(placeName==-1){ //place All
            //검색어 없을때
            if(keyword == ""){ searchedReview = searchService.findAllReview(); }
            else { //처리필요
                searchedMenuList = searchService.findAllByMenuName(keyword);
            }
        }else{ //특정 place
            if(keyword == ""){
                searchedReview = searchService.findAllReviewByPlaceId(placeName);
            }else{
                searchedMenuId = searchService.findByMenuNameAndPlaceId(keyword, placeName);
                searchedReview = searchService.findByTotalScore(searchedMenuId.getId().toString());
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

}
