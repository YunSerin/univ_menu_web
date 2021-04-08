package ewhamenu.com.demo.service.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InnerCrawler {
    public ArrayList<String> innerCrawler(String link){
        Document doc = null;
        ArrayList<String> menu = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E (MM.dd)");
        try {
            doc = (Document) Jsoup.connect(link).timeout(50000).get();
            Elements element = doc.select("ul");
            for(Element el : element.select("li")) {

                    String candDate = el.select("div.b-day").text();
                    if (candDate.equals(sdf.format(date))) {
                        String lunchMenu = el.select("div.b-menu.b-menu-l.lunch pre").text();
                        String dinnerMenu = el.select("div.b-menu.b-menu-d.dinner pre").text();
                        menu.add(lunchMenu);
                        menu.add(dinnerMenu);
                    }
                    else if(el.select("span").text().equals("등록된 식단이 없습니다.")) {
                    menu.add("등록된 식단이 없습니다.");
                    menu.add("등록된 식단이 없습니다.");
                    }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu;
    }
}
