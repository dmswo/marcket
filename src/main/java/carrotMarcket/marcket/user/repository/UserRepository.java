package carrotMarcket.marcket.user.repository;

import carrotMarcket.marcket.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long>, UserCustomRepository {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    Optional<Users> findByLoginId(String loginId);
}
