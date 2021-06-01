package ewhamenu.com.demo.domain;

import ewhamenu.com.demo.repository.TotalreviewAttributeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Table(name="review")
@Getter
@Setter
public class Review {

    @Id
    private Long id;
    @Column(name = "reviewDate")
    private LocalDate reviewDate;
    @Column(name = "dietId")
    private int dietId;
    @Column(name = "placeId")
    private int placeId;
    @Column(name = "totalScore")
    @Convert(converter = TotalreviewAttributeConverter.class)
    private TotalReview totalScore;
    @Column(name = "reviewComment")
    private String reviewComment;

}
