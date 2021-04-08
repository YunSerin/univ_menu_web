package ewhamenu.com.demo.controller;

import ewhamenu.com.demo.service.crawler.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class MainhomeController {
    private final CrawlerService crawlerService;
    private Model model;

    @Autowired
    public MainhomeController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @GetMapping("/")
    public String mainpage(Model model){
        ArrayList<String> menu = crawlerService.getMenu();
        model.addAttribute( "menu", menu);
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
