package ewhamenu.com.demo.repository;


import ewhamenu.com.demo.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findById(long id);
    //ArrayList<Menu> findAllByMenu_nameAndPlace_id(String menuName, int placeId);
    boolean existsMenuByMenuNameAndPlaceId(String menuName, int placeId);

}