package ewhamenu.com.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@NoArgsConstructor
@Table(name="menu")
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name = "placeId")
    private int placeId;
    @Column(name = "menuName")
    private String menuName;

}
