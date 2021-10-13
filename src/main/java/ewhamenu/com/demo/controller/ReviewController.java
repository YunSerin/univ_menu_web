package ewhamenu.com.demo.controller;


import ewhamenu.com.demo.domain.*;
import ewhamenu.com.demo.repository.DietRepository;
import ewhamenu.com.demo.service.ReviewService;
import ewhamenu.com.demo.service.SearchService;
import ewhamenu.com.demo.service.UserService;
import ewhamenu.com.demo.service.crawler.DietService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class ReviewController {
    private Model model;
    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);
    private Diet dietId_fk;
    private String placeId_Default;

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private DietRepository dietRepository;
    @Autowired
    private SearchService searchService;


    @PostMapping("createTodayReview")    // 오늘의 메뉴 -> 리뷰작성 버튼
    public String createTodayReviewpage(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD").withLocale(Locale.KOREA);
        model.addAttribute("today", formatter);
        String dietId_str = request.getParameter("dietId");
        long placeId;
        if(dietId_str!=null) {
            dietId_fk = dietRepository.findById(Long.parseLong(dietId_str));
            placeId = dietId_fk.getPlaceId();
        }else{
            placeId =-1;
        }
        model.addAttribute("placeId", placeId);

        List<String> menuList = Arrays.asList(dietId_fk.getMenuList().split("\\s\\n"));
        model.addAttribute("menuList", menuList);
        return "createTodayReview";
    }


    @GetMapping("createReviewDefault") //상단 리뷰작성 버튼
    public String createReview(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
        Calendar cal = Calendar.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD").withLocale(Locale.KOREA);
        model.addAttribute("today", formatter);
          return "createReviewDefault";
    }

    @RequestMapping(value = "getPlaceId", method = RequestMethod.POST)
    @ResponseBody
    public String getPlaceId(String placeId){  //placeId 받아오기
        this.placeId_Default = placeId;
        return placeId;
    }

    @RequestMapping(value = "menuAuto", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> menuAuto(HttpServletRequest request){  //자동완성
        String menuAuto = request.getParameter("term");
        List<Object> menus = reviewService.reviewAutoComplete(menuAuto, Integer.parseInt(placeId_Default));
        return menus;
    }

    @PostMapping("/saveTodayReivew") //오늘의 메뉴 리뷰 저장시 (리뷰 작성 페이지에서 받아오기)
    public String saveTodayReview(Review review, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
        ModelAndView mav = new ModelAndView();
        String userID = session.getAttribute("userId").toString();
        review.getReviewDate();
        review.getReviewComment();
        int placeId = review.getPlaceId();
        List<String> menuList = Arrays.asList(dietId_fk.getMenuList().split("\\s\\n"));
        List<Long> menuListId = new ArrayList<>();
        for(int i=0;i<menuList.size();i++){
            menuListId.add(reviewService.findMenuByNameAndPlaceId(menuList.get(i),placeId));
        }
        TotalScore totalScore = new TotalScore();
        Map<Long, String> rates = new LinkedHashMap<>();
        for(int i=0;i<menuListId.size();i++){
            rates.put(menuListId.get(i), request.getParameter("star"+i+1));
        }
        totalScore.setRates(rates);
        review.setTotalScore(totalScore);
        float averageScore=0;
        for(Long key : rates.keySet()) {
            String value = rates.get(key);
            averageScore += Integer.parseInt(value);
        }
        averageScore /= rates.size();
        review.setAverageScore(averageScore);
        review.setDietId(dietId_fk);
        reviewService.saveReview(review, userID);
        mav.addObject("data", new Message("리뷰가 등록되었습니다!", "/"));
        mav.setViewName("message");
        return "redirect:/";
    }
    @PostMapping("saveDefaultMenu") //상단 리뷰작성 버튼 저장시
    public String saveDefaultReview(Review review, HttpServletRequest request, @RequestParam(value="menuAuto", required=true) List<String> menuList){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
        ModelAndView mav = new ModelAndView();
        String userID = session.getAttribute("userId").toString();
        review.getReviewDate();
        review.getReviewComment();
        int placeId = review.getPlaceId();
        TotalScore totalScore = new TotalScore();
        Map<Long, String> rates = new LinkedHashMap<>();
        for(int i=0;i<menuList.size();i++){
            if(menuList.get(i)!="") {
                int j = i+1;
                rates.put(reviewService.findMenuByNameAndPlaceId(menuList.get(i),placeId), request.getParameter("star" + j));
            }else{

            }

        }
        totalScore.setRates(rates);
        review.setTotalScore(totalScore);
        float averageScore=0;
        for(Long key : rates.keySet()) {
            String value = rates.get(key);
            averageScore += Integer.parseInt(value);
        }
        averageScore /= rates.size();
        review.setAverageScore(averageScore);
        review.setDietId(null);
        reviewService.saveReview(review, userID);
        mav.addObject("data", new Message("리뷰가 등록되었습니다!", "/"));
        mav.setViewName("message");
        return"redirect:/";
    }
}
