package ewhamenu.com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainhomeController {

    @GetMapping("/")
    public String mainhome(){
        return "mainhome";
    }

//    @ResponseBody
//    public String mainhome(@RequestParam("name") String name){
//        Homeclass home = new Homeclass();
//        home.setName(name);
//        return "mainhome";
//    }
//
//
//    public class Homeclass{
//        private String name;
//
//        public String getName(){
//            return name;
//        }
//        public void setName(String name){
//            this.name = name;
//        }
//    }

}
