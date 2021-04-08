package ewhamenu.com.demo.service.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;

@Service
public class CrawlerService {
    static ArrayList<String> menu = new ArrayList<>();
    static InnerCrawler innerCrawler;


    @Autowired
    public ArrayList<String> getMenu() {
        menu = webCrawl();
        return menu;
    }

    public ArrayList<String> webCrawl(){
        String url = null;
        Document doc = null;

        ArrayList<String> restaurantLink = new ArrayList<>();
        url ="http://www.ewha.ac.kr/ewha/life/restaurant.do";
        try {
            doc = (Document) Jsoup.connect(url).timeout(50000).get();
            Elements element = doc.select("ul li");
            for(Element el : element.select("div.b-title-box a.b-title")) { //각 식당 링크를 restaurantLink에 저장
                int i=0;
                String hreflink = el.attr("href").toString();
                restaurantLink.add(url+hreflink);

            }
            for (String num : restaurantLink) {
                innerCrawler = new InnerCrawler();
                ArrayList<String> eachRest = innerCrawler.innerCrawler(num);
                for (String eachMenu : eachRest) {
                    menu.add(eachMenu);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    return menu;
    }


}
