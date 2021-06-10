package ewhamenu.com.demo.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Table(name="diet")
@Getter
@Setter
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "dietId")
    private long dietId;
    @Column(name = "createdate")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
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
