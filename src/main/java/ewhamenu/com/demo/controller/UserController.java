package ewhamenu.com.demo.controller;

import com.fasterxml.jackson.databind.DatabindContext;
import ewhamenu.com.demo.domain.Email;
import ewhamenu.com.demo.domain.Message;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.service.MailService;
import ewhamenu.com.demo.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);

    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;


    @Autowired
    BCryptPasswordEncoder pwEncoder;

    public UserController(UserService userService){
        this.userService = userService;
    }
    @PostMapping("/signIn")
    public ModelAndView signIn(String input_userId, String input_password, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
//        logger.info("id : {} , pw : {}", input_userId, input_password);
        Users user = userService.findByUserId(input_userId);
        if(user != null && pwEncoder.matches(input_password, user.getPassword())){ // login success
            if(!user.getIsActive()){  //임시비번일때
                session.setAttribute("loginCheck",true);
                session.setAttribute("userId",user.getUserId());
                mav.addObject("data", new Message("새로운 비밀번호를 설정해주세요!", "/setPassword"));
                mav.setViewName("message");
            }else{
                session.setAttribute("loginCheck",true);
                session.setAttribute("userId",user.getUserId());
                mav.addObject("data", new Message(String.format("%s 님 안녕하세요!", user.getNickname()), "/"));
                mav.setViewName("message");
            }
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
            user.setPassword(pwEncoder.encode(user.getPassword()));
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

    @PostMapping("/settingPassword")
    public ModelAndView settingPassword(ModelAndView mav, HttpServletRequest request){
        HttpSession session = request.getSession();
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        Users user = userService.findByUserId(session.getAttribute("userId").toString());
        if(newPassword.equals(request.getParameter("newPassword_check"))){
            if(pwEncoder.matches(oldPassword, user.getPassword())){
                String testpw = user.getPassword();
                user.setIsActive(true);
                user.setPassword(pwEncoder.encode(newPassword));
                userService.save(user);
                session.setAttribute("userId", null);
                session.setAttribute("loginCheck",null);
                mav.addObject("data", new Message("비밀번호 변경 성공", "/login"));
                mav.setViewName("message");
            }else{
                mav.addObject("data", new Message("기존 비밀번호가 일치하지 않습니다!", "/setPassword"));
                mav.setViewName("message");
            }

        }else{
            mav.addObject("data", new Message("입력된 새로운 비밀번호가 일치하지 않습니다!", "/setPassword"));
            mav.setViewName("message");
        }

        return mav;
    }

    @PostMapping("/findingPassword")
    public ModelAndView findingPassword(ModelAndView mav, HttpServletRequest request){

        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("email");
        Users user = userService.findByUserIdAndEmail(userId,userEmail);
        //pw 랜덤값 만들기
        String newPassword = RandomStringUtils.randomAlphabetic(10);
        if(user != null){
            Email mail = new Email();
            mail.setAddress(userEmail);
            mail.setMessage(userId, newPassword);
            mailService.emailSend(mail);
            user.setPassword(pwEncoder.encode(newPassword));
            user.setIsActive(false);
            userService.save(user);
            mav.addObject("data", new Message("등록된 이메일로 임시 비밀번호가 발급되었습니다!", "/login"));
        }
        else{
            mav.addObject("data", new Message("입력한 아이디와 이메일을 확인해주세요", "/findPassword"));
        }
        mav.setViewName("message");
        return mav;
    }

    @PostMapping("/updateUserInfo")
    public boolean updateUserInfo(ModelAndView mav, HttpServletRequest request){
        HttpSession session = request.getSession();
        String newNickname = request.getParameter("newNickname");
        String newEmail = request.getParameter("newEmail");
        if(newNickname=="" || newEmail==""){
            return false;
        }else{
            Users user = userService.findByUserId(session.getAttribute("userId").toString());
            user.setEmail(newEmail);
            user.setNickname(newNickname);
            userService.save(user);
        }
       return true;
    }

    @PostMapping("/dropOffUser")
    public boolean dropOffUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(userService.dropOffUser(session.getAttribute("userId").toString())){
            session.setAttribute("userId", null);
            session.setAttribute("loginCheck",null);
            return true;
        }else{
            return false;
        }
    }
}
