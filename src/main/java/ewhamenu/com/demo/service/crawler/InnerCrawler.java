package ewhamenu.com.demo.service.crawler;

import ewhamenu.com.demo.controller.MainhomeController;
import org.aspectj.weaver.patterns.PatternNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InnerCrawler {

    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);
    public ArrayList<String> innerCrawler(String link){
        Document doc = null;
        ArrayList<String> menus = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("E (MM.dd)", Locale.KOREAN);
        logger.info(sdf.format(date));
        try {
            doc = (Document) Jsoup.connect(link).timeout(50000).get();
            Elements element = doc.select("ul");
            for(Element el : element.select("li")) {
                    String candDate = el.select("div.b-day").text();
                    if (candDate.equals(sdf.format(date))) {
                        String lunchMenu = el.select("div.b-menu.b-menu-l.lunch pre").text();
                        String dinnerMenu = el.select("div.b-menu.b-menu-d.dinner pre").text();
                        menus.add(sort(lunchMenu));
                        menus.add(sort(dinnerMenu));
                    }
                    else if(el.select("span").text().equals("등록된 식단이 없습니다.")) {
                    menus.add("");
                    menus.add("");
                    }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return menus;
    }
    public String sort(String menu){
        String excep1 = "(\\*[^*]*\\*[\\r\\n]+)";  //예시 : *메뉴제목*
        String excep2 = "([a-zA-Z]+코너+\\)[가-힣]*[\\r\\n]+)";  //예시 : A코너)
        String excep3 = "(\\[[가-힣]*\\][\\r\\n]+)";   //예시 : [공통메뉴]
        //String excep4 = "([\\r\\n]+)";

        Pattern pattern1 = Pattern.compile(excep1);
        Pattern pattern2 = Pattern.compile(excep2);
        Pattern pattern3 = Pattern.compile(excep3);
        //Pattern pattern4 = Pattern.compile(excep4);
        Matcher matcher1 = pattern1.matcher(menu);
        Matcher matcher2 = pattern2.matcher(menu);
        Matcher matcher3 = pattern3.matcher(menu);
        //Matcher matcher4 = pattern4.matcher(menu);
        while(matcher1.find()){
            menu = menu.replace(matcher1.group(1),"");
        }
        while(matcher2.find()) {
            menu = menu.replace(matcher2.group(1),"");
        }
        while(matcher3.find()) {
            menu = menu.replace(matcher3.group(1),"");
        }
//        while(matcher4.find()) {
//            menu = menu.replace(matcher4.group(1),",");
//        }
//        String[] trimed = menu.split("[\\r\\n]+");
//        for(int i=0;i<trimed.length;i++) {
//            System.out.println(trimed[i]);
//        }

        return menu;
    }
}
