package ewhamenu.com.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

public class WebCrawler {
    public static void main(String[] args){
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
                restaurantLink.add(i++, url+hreflink);
            }
            for (String num : restaurantLink) {
                //System.out.println(num);
                InnerCrawler innercrawler = new InnerCrawler();
                ArrayList<String> menu = innercrawler.innerCrawler(num);
                for (String eachMenu : menu) {
                    System.out.println(eachMenu);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
