package ewhamenu.com.demo.controller;



import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.UserService;
import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.service.crawler.DietService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ewhamenu.com.demo.domain.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@RequiredArgsConstructor
@Controller
public class MainhomeController {
    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);

    private Model model;
    private final DietService dietService;
    Calendar cal = Calendar.getInstance();
    private final UserService userService;

    @Autowired
    public DietService getDietService() {
        return dietService;
    }
    public UserService getUserService(){ return userService; }

    @GetMapping("/")
    public String mainpage(Model model){
        ArrayList<String> diets = new ArrayList<>();

        if(cal.get(Calendar.DAY_OF_WEEK)==1){ //일요일
            for(int i=0;i<16;i++) {
                diets.add("일요일이라 등록된 식단이 없습니다.");
            }
        }else {
            if (dietService.checkDate()) {
                dietService.saveDiet();
            }
            diets = dietService.findDiets(LocalDate.now());
        }
        model.addAttribute("diets", diets);
        return "mainhome";
    }



    @GetMapping("login")
    public String loginPage(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") != null){
            return "redirect:/";
        }
        return "/user/loginPage";
    }

    @GetMapping("join")
    public String joinPage(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") != null){
            return "redirect:/";
        }
        return "user/joinPage";
    }

    @GetMapping("mypage")
    public ModelAndView mypage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("loginCheck") == null) {
            mav.addObject("data", new Message("로그인이 필요한 서비스입니다!", "/login"));
            mav.setViewName("message");
        }else{
            Users user = userService.findByUserId(session.getAttribute("userId").toString());
            mav.addObject("userData", user);
            mav.setViewName("user/mypage");
        }
        return mav;
    }


    @GetMapping("createReview")
    public String createReview(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
//        logger.info("id : {}", request.getParameter("dietId"));
          return "createReview";
    }
    @PostMapping("createReview")
    public String createReviewPage(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
        logger.info("id : {}", request.getParameter("dietId"));
        return "createReview";
    }

    @GetMapping("setPassword")
    public String setPassword(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") == null){
            return "redirect:/";
        }
        return "user/setPassword";
    }


    @GetMapping("findPassword")
    public String findPassword(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("loginCheck") != null){
            return "redirect:/";
        }
        return "user/findPassword";
    }

//    @GetMapping("searchKeyword")
//    public String searchPage(){
//        return "searchPage";
//    }
}
