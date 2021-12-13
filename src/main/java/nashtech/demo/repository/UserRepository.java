package nashtech.demo.repository;

import nashtech.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsernameAndDeletedIsFalse(String userName);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
