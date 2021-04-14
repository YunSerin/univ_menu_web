package ewhamenu.com.demo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;

@Entity
@NoArgsConstructor
@Table(name="diet")
@Getter
@Setter
public class Diet {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "createdate")
    private Date date;
    @Column(name = "placeId")
    private long placeId;
    /* 생활관 학생식당 : 0
       생활관 교직원식당 : 1
       진선미관 : 2
       헬렌관 식당 : 3
       공대식당 : 4
       한우리집 : 5
       이하우스 201동 : 6
       이하우스 301동 : 7
      */
    @Column(name = "menuList")
    private String menuList ;
    @Column(name = "lunDi")
    private int when;

    /*조식:0 중식:1 석식:2*/

}
