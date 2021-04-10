package ewhamenu.com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainhomeController {

    @GetMapping("/")
    public String mainhome(){
        return "mainhome";
    }

    @GetMapping("login")
    public String loginPage(){
        return "/user/loginPage";
    }

    @GetMapping("join")
    public String joinPage(){
        return "user/joinPage";
    }


}
