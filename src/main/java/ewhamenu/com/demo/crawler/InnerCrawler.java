package ewhamenu.com.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
            Elements element = doc.select("ul li");
            for(Element el : element.select("div.b-day")) {
                String candDate = el.text();
                System.out.println(candDate);
                if(candDate.equals(sdf.format(date))) {
                    String lunchMenu = element.select("div.b-menu.b-menu-l.lunch pre").text();
                    String dinnerMenu = element.select("div.b-menu.b-menu-d.dinner pre").text();
                    System.out.println(lunchMenu);
                    System.out.println(dinnerMenu);
                    menu.add(lunchMenu);
                    menu.add(dinnerMenu);
                }
            }
            for (String num : menu) {
                System.out.println(num);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return menu;
    }
}
