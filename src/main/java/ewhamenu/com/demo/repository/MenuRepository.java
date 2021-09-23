package ewhamenu.com.demo.repository;


import ewhamenu.com.demo.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

//    Menu findById(long id);
    //ArrayList<Menu> findAllByMenu_nameAndPlace_id(String menuName, int placeId);
    boolean existsMenuByMenuNameAndPlaceId(String menuName, int placeId);

    public Menu findAllByMenuNameAndPlaceId(String menuName, int placeId);

    public List<Menu> findAllByMenuName(String MenuName);

    public Menu findById(long menuId);

    @Query(value = "SELECT * FROM menu where menu_name like %:keyword%", nativeQuery = true)
    public List<Menu> searchingAutocomplete(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM menu where place_id = :placeId AND menu_name like %:keyword%", nativeQuery = true)
    public List<Menu> findMenusByKeyword(@Param("keyword") String keyword, @Param("placeId") int placeId);
}
