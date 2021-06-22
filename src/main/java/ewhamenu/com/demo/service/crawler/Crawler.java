package ewhamenu.com.demo.service.crawler;

import ewhamenu.com.demo.domain.Diet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

//@Service
public class Crawler {
    static ArrayList<String> crawlResult = new ArrayList<>();
    static InnerCrawler innerCrawler;
    Diet diet = new Diet();

//    @Autowired
//    public ArrayList<String> getMenu() {
//        crawlResult = webCrawl();
//        return crawlResult;
//    }

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
            for (String link : restaurantLink) {
                innerCrawler = new InnerCrawler();
                ArrayList<String> eachRest = innerCrawler.innerCrawler(link);
                for (String eachMenu : eachRest) {
                    crawlResult.add(eachMenu);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    return crawlResult; // 각 인덱스에 오늘의 식단이 차례로 담겨있음.
    }

//    public void toDiet(ArrayList<String> crawlResult){
//        Date today = new Date();
//        for(int i=0;i<8;i++) {
//            diet.setDate(today);
//            diet.setMenuList(crawlResult.get(i));
//            diet.setPlaceId(i/2);
//            //중식 석식
//            if(i%2==0){ //짝수
//                diet.setWhen(1);//중식
//            }else {
//                diet.setWhen(2);//석식
//            }
//        }
//    }


}
