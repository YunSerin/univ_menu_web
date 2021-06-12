package ewhamenu.com.demo.controller;


import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.TotalScore;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.ReviewService;
import ewhamenu.com.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class ReviewController {
    private Model model;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/cerateReview")
    public ModelAndView createReveiwPage(Users user, ModelAndView mav, HttpServletRequest request) {
        HttpSession session = request.getSession();
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD");
        mav.addObject("today", formatter);
        //model.addAttribute("today", formatter);
    return mav;
    }

    @PostMapping("/createReview")
    public String ReviewInput(Review review,HttpServletRequest request){
        HttpSession session = request.getSession();
        String userID = session.getAttribute("userId").toString();
        review.getReviewDate();
        review.getReviewComment();
        review.getPlaceId();
//        review.setUserId(user);
//        TotalScore totalScore = new TotalScore();
//        totalScore.setMenu1(star1);
//        review.setTotalScore(totalScore);
        reviewService.saveReview(review, userID);
        return "redirect:/";
    }
//    @PostMapping
//    public
}
