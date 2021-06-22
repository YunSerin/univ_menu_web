package ewhamenu.com.demo.controller;


import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.TotalScore;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.ReviewService;
import ewhamenu.com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class ReviewController {
    private Model model;

    @Autowired
    private ReviewService reviewService;

//    @GetMapping("/cerateReview")
//    public ModelAndView createReveiwPage(Users user, ModelAndView mav, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
//        mav.addObject("today", formatter);
//        //model.addAttribute("today", formatter);
//    return mav;
//    }

    @PostMapping("createReview")
    public String ReviewInput(Review review, HttpServletRequest request, ModelAndView mav){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
        mav.addObject("today", formatter);
        HttpSession session = request.getSession();
        String userID = session.getAttribute("userId").toString();
        review.getReviewDate();
        review.getReviewComment();
        review.getPlaceId();
        String totalscore1 = request.getParameter("star1");
        String totalscore2 = request.getParameter("star2");
        String totalscore3 = request.getParameter("star3");
        String totalscore4 = request.getParameter("star4");
        String totalscore5 = request.getParameter("star5");
        TotalScore totalScore = new TotalScore();
        Map<String, String> rates = new LinkedHashMap<>();
        rates.put("1", totalscore1);
        rates.put("2", totalscore2);
        rates.put("3", totalscore3);
        rates.put("4", totalscore4);
        rates.put("5", totalscore5);
//        totalScore.setMenu1(totalscore1);
//        totalScore.setMenu2(totalscore2);
//        totalScore.setMenu3(totalscore3);
//        totalScore.setMenu4(totalscore4);
//        totalScore.setMenu5(totalscore5);
//        totalScore.getMenu1();
//        totalScore.getMenu2();
//        totalScore.getMenu3();
        totalScore.setRates(rates);
        review.setTotalScore(totalScore);
        float averageScore=0;
        for(String key : rates.keySet()) {
            String value = rates.get(key);
            averageScore += Integer.parseInt(value);
        }
        averageScore /= rates.size();
        review.setAverageScore(averageScore);
//        review.getTotalScore();
        reviewService.saveReview(review, userID);
        return "redirect:/";
    }
//    @PostMapping
//    public
}
