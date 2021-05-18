package ewhamenu.com.demo.controller;


import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/cerateReview")
    public ModelAndView signOut(Users user, ModelAndView mav, HttpServletRequest request) {
        HttpSession session = request.getSession();

    return mav;
    }
}
