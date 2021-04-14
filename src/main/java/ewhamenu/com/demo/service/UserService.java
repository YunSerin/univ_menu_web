package ewhamenu.com.demo.service;

import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Users findByUserIdAndPassword(String userId, String password){
        Users user = userRepository.findByUserIdAndPassword(userId, password);
        return user;
    }

    public Users save(Users user){
        userRepository.save(user);
        return user;
    }

    public boolean checkValidation(String userId){
        Users e = userRepository.findByUserId(userId);
        if(e == null){  // 중복 아이디 없는 경우
            return true;
        }
        else{
            return false;
        }
    }
}
