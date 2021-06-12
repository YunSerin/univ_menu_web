package ewhamenu.com.demo.controller;

import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    //메인에서 보낸 검색 내용을 받아야함.
    @GetMapping("/searchKeyword")
    public ModelAndView searchKeyword(HttpServletRequest request) throws UnsupportedEncodingException {
        String[] places = {"생활관 학생식당", "생활관 교직원식당", "진선미관식당", "헬렌관식당", "공대식당", "한우리집 지하1층", "이하우스 201동", "이하우스 301동"};
        ModelAndView mav = new ModelAndView();
        int placeName = Integer.parseInt(request.getParameter("placeName"));
        String keyword = URLEncoder.encode(request.getParameter("searchKeyword"), "UTF-8");
        List<Menu> searchedMenuList = new ArrayList<>();
        Menu searchedMenuId = new Menu();
        List<Review> searchedReview = new ArrayList<>();

        if(placeName==-1){
            searchedMenuList = searchService.findAllByMenuName(keyword);
        }else{
            searchedMenuId = searchService.findByMenuNameAndPlaceId(keyword, placeName);
            searchedReview = searchService.findByTotalScore(searchedMenuId.getId().toString());
        }
        System.out.println(searchedReview.toString());

        //임시
        List<Object> reviews = new ArrayList<>();
        Map<String, Object> reviewMap = new HashMap<String, Object>();
        reviewMap.put("place", "헬렌관");
        reviewMap.put("menu_name", "돈까스");
        reviewMap.put("score", "★★★★★");
        reviewMap.put("date", "2021-06-01");
        reviewMap.put("comment", "어쩌구 저쩌구");
        reviewMap.put("writer", "닉네임");
        reviews.add(reviewMap);
        Map<String, Object> reviewMap2 = new HashMap<String, Object>();
        reviewMap2.put("place", places[searchedReview.get(0).getPlaceId()]);
        reviewMap2.put("menu_name", "돈까스");
        reviewMap2.put("score", "★★★★★");
        reviewMap2.put("date", searchedReview.get(0).getReviewDate());
        reviewMap2.put("comment", searchedReview.get(0).getReviewComment());
        reviewMap2.put("writer", "닉네임");
        reviews.add(reviewMap2);
        ///////////////
        mav.addObject("placeName",placeName);
        mav.addObject("keyword",request.getParameter("searchKeyword"));
        mav.addObject("reviews", reviews);
        mav.setViewName("searchPage");
        return mav;
    }


}
