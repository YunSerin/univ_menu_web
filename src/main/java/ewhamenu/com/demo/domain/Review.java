package ewhamenu.com.demo.domain;

import ewhamenu.com.demo.repository.TotalScoreAttributeConverter;
import lombok.*;
import org.springframework.lang.Nullable;
import javax.persistence.*;
import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="review")
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="userId")
    private Users userId;

    @Column(name = "reviewDate")
    private String reviewDate;

    @ManyToOne
    @JoinColumn(name="dietId")
    @Nullable
    private Diet dietId;
    @Column(name = "placeId")
    private int placeId;
    @Column(name = "totalScore")
    @Convert(converter = TotalScoreAttributeConverter.class)
    private TotalScore totalScore;
//    @Type(type = "json")
//    @Column(name = "totalScore", columnDefinition = "json", nullable = false)
//    private Map<String, String> totalScore = new HashMap<>();
    @Column(name = "reviewComment")
    private String reviewComment;
    @Column(name = "averageScore")
    private float averageScore;

}