package ewhamenu.com.demo.repository;

import ewhamenu.com.demo.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    public Users findByUserIdAndEmail (String userId, String password);

    public Users findByUserId(String userId);

    @Transactional
    public void deleteUsersByUserId(String userId);
}
