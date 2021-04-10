package ewhamenu.com.demo.controller;

import com.fasterxml.jackson.databind.DatabindContext;
import ewhamenu.com.demo.domain.Message;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);

    UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/signIn")
    public ModelAndView signIn(String input_userId, String input_password, ModelAndView mav, HttpServletRequest request){
        HttpSession session = request.getSession();
        logger.info("id : {} , pw : {}", input_userId, input_password);
        Users user = userService.findByUserIdAndPassword(input_userId, input_password);
        if(user != null){
            session.setAttribute("loginCheck",true);
            session.setAttribute("userId",user.getUserId());
            mav.addObject("data", new Message(String.format("%s 님 안녕하세요!", user.getNickname()), "/"));
            mav.setViewName("message");
        }
        else{
            mav.addObject("data", new Message("로그인 실패", "/login"));
            mav.setViewName("message");
        }
        return mav;
    }

    @PostMapping("/signUp")
    public ModelAndView signUp(Users user, ModelAndView mav){
        user.setDate(LocalDate.now());
        if(userService.checkValidation(user.getUserId())){
            userService.save(user);
            mav.addObject("data", new Message("회원가입 성공", "/login"));
            mav.setViewName("message");
        }
        else{
            mav.addObject("data", new Message("이미 존재하는 아이디 입니다!", "/join"));
            mav.setViewName("message");
        }
        return mav;
    }

    @GetMapping("/signOut")
    public ModelAndView signOut(Users user, ModelAndView mav, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("loginCheck",null);
        session.setAttribute("userId",null);
        mav.addObject("data", new Message("로그아웃 성공", "/"));
        mav.setViewName("message");

        return mav;
    }

}
