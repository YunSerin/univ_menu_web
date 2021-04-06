package ewhamenu.com.demo.crawler;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import javax.swing.text.Document;
import java.io.IOException;

public class webCrawler {
    public static void main(String args){
        String url = null;
        Document doc = null;

        url ="http://www.ewha.ac.kr/ewha/life/restaurant.do";
        try {
            doc = (Document) Jsoup.connect(url).timeout(50000).get();
            // Elements element = doc.select("div.store_box"); 왜안돼????????

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
