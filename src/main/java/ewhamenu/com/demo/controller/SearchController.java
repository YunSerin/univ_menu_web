package ewhamenu.com.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SearchController {
    Model model;

    //메인에서 보낸 검색 내용을 받아야함.
    @GetMapping("/search")
    public String searchpage(@RequestParam("search-menu") String name, Model model){

        return "searchpage";
    }


}
