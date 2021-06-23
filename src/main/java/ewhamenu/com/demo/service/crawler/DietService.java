package ewhamenu.com.demo.service.crawler;


import ewhamenu.com.demo.domain.Diet;
import ewhamenu.com.demo.domain.Menu;
import ewhamenu.com.demo.repository.DietRepository;
import ewhamenu.com.demo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.Calendar;


@Service
public class DietService {
    Crawler crawler = new Crawler();
    Calendar cal = Calendar.getInstance();

    @Autowired
    private DietRepository dietRepository;
    @Autowired
    private MenuRepository menuRepository;
//    @Autowired
//    private MenuService menuService;

    public long saveDiet(){ //월~토만 호출됨
        ArrayList<String> crawlResult = getDiet();
        Diet diet = new Diet();
        for(int i=0;i<16;i++) {
            diet.setDate(LocalDate.now());
            String menuString = crawlResult.get(i);
            //List<String> menu = Arrays.asList(menuString.split("\\n"));
            diet.setMenuList(menuString);
            diet.setPlaceId(i/2);
            //중식 석식
            if(i%2==0){ //짝수
                diet.setWhen(1);//중식
            }else {
                diet.setWhen(2);//석식
            }
            diet.setDietId(diet.getDietId()+1);

            dietRepository.saveAndFlush(diet);
            saveMenu(crawlResult.get(i),i/2);

        }

        return diet.getDietId();
    }

    public ArrayList getDiet() {
        return crawler.webCrawl();
    }

    public ArrayList<String> findDiets(LocalDate date){
        ArrayList<String> diets = new ArrayList<>();
        ArrayList<Diet> findall = dietRepository.findAllByDate(date);
        for(Diet diet : findall){
            if(diet.getMenuList().length()>0) {
                diets.add(diet.getMenuList().toString());
            }else{
                diets.add("등록된 식단이 없습니다.");
            }
        }
        return diets;
    }

    public boolean checkDate(){
        ArrayList<Diet> d = dietRepository.findAllByDate(LocalDate.now());
        if(d.size() == 0){  // 중복 날짜 없는 경우
            return true;
        }
        else{
            return false;
        }
    }

    public List menuToList(String str){  //Diet 테이블의 메뉴컬럼을 list로 저장할 경우 씀
        List<String> list = new ArrayList<String>();
        list = Arrays.asList(str.split("/n"));
        return list;
    }

    public void saveMenu(String menuList, int placeId){
        List<String> menu = Arrays.asList(menuList.split("\\n"));
            for(int i=1;i<menu.size();i++){
                //ArrayList<Menu> findmenu = menuRepository.findAllByMenu_nameAndPlace_id(menu.get(i),placeId);
                //if(findmenu!=null) { //새로 넣어야함
                if(!menuRepository.existsMenuByMenuNameAndPlaceId(menu.get(i),placeId)){
                    if(menu.get(i)!=null||!menu.get(i).equals(" ")) {
                        Menu newMenu = new Menu();
                        newMenu.setMenuName(menu.get(i));
                        newMenu.setPlaceId(placeId);
                        menuRepository.save(newMenu);
                    }
                }
            }
    }

    public List<Diet> findTodayDiets(LocalDate date){

        List<Diet> todayDiets = dietRepository.findAllByDate(date);
        return todayDiets;
    }
}
