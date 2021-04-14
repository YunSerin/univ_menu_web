package ewhamenu.com.demo.service.crawler;


import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;


@Service
public class DietService {
    static Crawler crawler = new Crawler();
    //static ArrayList<Diet> diets = new ArrayList<>();
    private final Diet diet = new Diet();
    private final DietRepository dietRepository;


    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    @Transactional
    public Long saveDiet(){
        ArrayList<String> crawlResult = crawler.webCrawl();
        Date today = new Date();

        for(int i=0;i<8;i++) {
            //diet = new Diet();
            diet.setDate(today);
            diet.setMenuList(crawlResult.get(i));
            diet.setPlaceId(i/2);
            //중식 석식
            if(i%2==0){ //짝수
                diet.setWhen(1);//중식
            }else {
                diet.setWhen(2);//석식
            }
            dietRepository.save(diet);
        }
        return diet.getId();
    }

    public static ArrayList getDiet() {
        return crawler.webCrawl();
    }
}
