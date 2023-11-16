package carrotMarcket.marcket.board.repository;

import carrotMarcket.marcket.board.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
