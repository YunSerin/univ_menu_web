package ewhamenu.com.demo.repository;

import ewhamenu.com.demo.domain.Diet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DietRepository extends JpaRepository<Diet, Long> {


    Diet findById(long id);

}
