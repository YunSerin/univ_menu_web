package ewhamenu.com.demo.controller;

import ewhamenu.com.demo.domain.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainhomeController {

    @GetMapping("/")
    public String mainhome(){
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
