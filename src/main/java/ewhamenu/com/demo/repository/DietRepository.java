package ewhamenu.com.demo.repository;

import ewhamenu.com.demo.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.ArrayList;


@Repository
public interface DietRepository extends JpaRepository<Diet, Long> {


    Diet findById(long id);

    ArrayList<Diet> findAllByDate(LocalDate date);

    Diet findByDate(LocalDate date);
}
