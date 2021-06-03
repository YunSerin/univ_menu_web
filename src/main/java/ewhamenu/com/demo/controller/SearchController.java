package ewhamenu.com.demo.controller;

import lombok.RequiredArgsConstructor;
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

    //메인에서 보낸 검색 내용을 받아야함.
    @GetMapping("/searchKeyword")
    public ModelAndView searchKeyword(HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView();
        String placeName = request.getParameter("placeName");
        String keyword = URLEncoder.encode(request.getParameter("searchKeyword"), "UTF-8");
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
        reviewMap2.put("place", "헬렌관");
        reviewMap2.put("menu_name", "돈까스");
        reviewMap2.put("score", "★★★★★");
        reviewMap2.put("date", "2021-06-01");
        reviewMap2.put("comment", "어쩌구 저쩌구");
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
