package ewhamenu.com.demo.controller;


import ewhamenu.com.demo.domain.Review;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.ReviewService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public String ReviewInput(Review review){
        review.getReviewComment();
        reviewService.saveReview(review);
        return "redirect:/";
    }
//    @PostMapping
//    public
}
