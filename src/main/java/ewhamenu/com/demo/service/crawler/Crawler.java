package ewhamenu.com.demo.service.crawler;

import ewhamenu.com.demo.controller.MainhomeController;
import ewhamenu.com.demo.domain.Diet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.ReturnedType;

import java.io.IOException;
import java.util.ArrayList;

//@Service
public class Crawler {
    static ArrayList<String> crawlResult = new ArrayList<>();
    static InnerCrawler innerCrawler;
    Diet diet = new Diet();
    String url = "http://www.ewha.ac.kr/ewha/life/restaurant.do";
    Document doc = null;
//    @Autowired
//    public ArrayList<String> getMenu() {
//        crawlResult = webCrawl();
//        return crawlResult;
//    }
    public Crawler(){
        try{
            doc = (Document) Jsoup.connect(url).timeout(50000).get();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String> webCrawl(){
        ArrayList<String> restaurantLink = new ArrayList<>();
            Elements element = doc.select("ul li");
            for(Element el : element.select("div.b-title-box a.b-title")) { //각 식당 링크를 restaurantLink에 저장하는 반복문
                String hreflink = el.attr("href").toString(); 
                restaurantLink.add(url+hreflink); //각 식당 링크 
            }
            for (String link : restaurantLink) { //InnerCrawler 들어가서 
                innerCrawler = new InnerCrawler(); 
                ArrayList<String> eachRest = innerCrawler.innerCrawler(link); //해당 식당의 점심,  저녁 가져옴
                for (String s : eachRest) {
                    crawlResult.add(s); //점심, 저녁, 점심, 저녁 쌓여감
                }
            }

    return crawlResult; // 각 인덱스에 모든 식당의 오늘의 식단이 차례로 담겨있음.
    }

    public ArrayList<String> RestaurantNameCrawl(){
        ArrayList<String> restaurantName = new ArrayList<>();
        Elements element = doc.select("ul li");
        for(Element el : element.select("div.b-title-box a.b-title span.hide")) { //각 식당 이름을 restaurantLink에 저장하는 반복문
            restaurantName.add(el.text()); //각 식당 링크 
        }
        return restaurantName;
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
