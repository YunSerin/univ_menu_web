package ewhamenu.com.demo.controller;

import ewhamenu.com.demo.service.crawler.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


}
