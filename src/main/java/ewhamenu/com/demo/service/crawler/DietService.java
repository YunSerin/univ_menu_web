package ewhamenu.com.demo.service.crawler;


import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.domain.Users;
import ewhamenu.com.demo.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;


@Service
public class DietService {
    Crawler crawler = new Crawler();
    Calendar cal = Calendar.getInstance();

    @Autowired
    private DietRepository dietRepository;

    public long saveDiet(){
        ArrayList<String> crawlResult = getDiet();
        Diet diet = new Diet();
        for(int i=0;i<16;i++) {
            diet.setDate(LocalDate.now());
            if(cal.get(Calendar.DAY_OF_WEEK)==1){
                diet.setMenuList("일요일이라 등록된 식단이 없습니다.");
            }else {
                diet.setMenuList(crawlResult.get(i));
            }
            diet.setPlaceId(i/2);
            //중식 석식
            if(i%2==0){ //짝수
                diet.setWhen(1);//중식
            }else {
                diet.setWhen(2);//석식
            }
            diet.setId(diet.getId()+1);

            dietRepository.saveAndFlush(diet);


        }

        return diet.getId();
    }

    public ArrayList getDiet() {
        return crawler.webCrawl();
    }

    public ArrayList<String> findDiets(LocalDate date){
        ArrayList<String> diets = new ArrayList<>();
        ArrayList<Diet> findall = dietRepository.findAllByDate(date);
        for(Diet diet : findall){
            diets.add(diet.getMenuList());
        }
        return diets;
    }

    public boolean checkDate(){
        ArrayList<Diet> d = dietRepository.findAllByDate(LocalDate.now());
        if(d == null){  // 중복 날짜 없는 경우
            return true;
        }
        else{
            return false;
        }
    }
}
