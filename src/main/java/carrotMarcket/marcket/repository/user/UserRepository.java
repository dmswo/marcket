package carrotMarcket.marcket.repository.user;

import carrotMarcket.marcket.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    Optional<Users> findByLoginId(String loginId);
}
