package ewhamenu.com.demo.controller;


import ewhamenu.com.demo.service.crawler.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ewhamenu.com.demo.domain.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class MainhomeController {
    private Model model;
    private final DietService dietService;

    @Autowired
    public DietService getDietService() {
        return dietService;
    }

    @GetMapping("/")
    public String mainpage(Model model){
        ArrayList<String> menu = dietService.getDiet();
        //dietService.saveDiet();
        model.addAttribute( "menu", menu);
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
    public String mypage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginCheck") == null) {
            return "redirect:/";
        }
        return "user/mypage";
    }


}
